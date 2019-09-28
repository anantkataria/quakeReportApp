package com.example.earthquakereports;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


//    /** Sample JSON response for a USGS query */
//    private static final String SAMPLE_JSON_RESPONSE = "{type\":\"FeatureCollection\",\"metadata\":{\"generated\":1569419215000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.8.1\",\"count\":14},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":7.7999999999999998,\"place\":\"27km SSE of Muisne, Ecuador\",\"time\":1460851116980,\"updated\":1560187286899,\"tz\":-300,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20005j32\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20005j32&format=geojson\",\"felt\":208,\"cdi\":8.9000000000000004,\"mmi\":8,\"alert\":\"orange\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1185,\"net\":\"us\",\"code\":\"20005j32\",\"ids\":\",at00o5r3xd,us20005j32,pt16107050,gcmt20160416235837,atlas20160416235836,\",\"sources\":\",at,us,pt,gcmt,atlas,\",\"types\":\",cap,dyfi,finite-fault,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,poster,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.4399999999999999,\"rms\":0.93999999999999995,\"gap\":15,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.8 - 27km SSE of Muisne, Ecuador\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-79.921800000000005,0.38190000000000002,20.59]},\"id\":\"us20005j32\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":7,\"place\":\"1km E of Kumamoto-shi, Japan\",\"time\":1460737506220,\"updated\":1540331704218,\"tz\":540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20005iis\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20005iis&format=geojson\",\"felt\":74,\"cdi\":9.0999999999999996,\"mmi\":9.4000000000000004,\"alert\":\"red\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":2067,\"net\":\"us\",\"code\":\"20005iis\",\"ids\":\",at00o5oo9v,pt16106053,us20005iis,gcmt20160415162506,atlas20160415162506,\",\"sources\":\",at,pt,us,gcmt,atlas,\",\"types\":\",cap,dyfi,finite-fault,general-text,geoserve,ground-failure,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,poster,scitech-link,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.34899999999999998,\"rms\":0.84999999999999998,\"gap\":32,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.0 - 1km E of Kumamoto-shi, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[130.7543,32.790599999999998,10]},\"id\":\"us20005iis\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.9000000000000004,\"place\":\"75km SE of Mawlaik, Burma\",\"time\":1460555717800,\"updated\":1507757047484,\"tz\":390,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20005hqz\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20005hqz&format=geojson\",\"felt\":289,\"cdi\":5.9000000000000004,\"mmi\":5.7999999999999998,\"alert\":\"yellow\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":903,\"net\":\"us\",\"code\":\"20005hqz\",\"ids\":\",us20005hqz,gcmt20160413135517,atlas20160413135517,\",\"sources\":\",us,gcmt,atlas,\",\"types\":\",cap,dyfi,general-text,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":3.0009999999999999,\"rms\":1.03,\"gap\":14,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.9 - 75km SE of Mawlaik, Burma\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[94.865399999999994,23.0944,136]},\"id\":\"us20005hqz\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.5999999999999996,\"place\":\"42km WSW of Ashkasham, Afghanistan\",\"time\":1460284138720,\"updated\":1508178421567,\"tz\":270,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20005gsg\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20005gsg&format=geojson\",\"felt\":117,\"cdi\":5.7999999999999998,\"mmi\":3.8999999999999999,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":738,\"net\":\"us\",\"code\":\"20005gsg\",\"ids\":\",us20005gsg,gcmt20160410102858,atlas20160410102858,\",\"sources\":\",us,gcmt,atlas,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.78900000000000003,\"rms\":1.03,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 42km WSW of Ashkasham, Afghanistan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[71.131100000000004,36.472499999999997,212]},\"id\":\"us20005gsg\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"124km ENE of Codrington, Barbuda\",\"time\":1458386793230,\"updated\":1478816081786,\"tz\":-240,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20005azy\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20005azy&format=geojson\",\"felt\":80,\"cdi\":5,\"mmi\":3.5099999999999998,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":594,\"net\":\"us\",\"code\":\"20005azy\",\"ids\":\",us20005azy,gcmt20160319112634,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.0820000000000001,\"rms\":1.1599999999999999,\"gap\":27,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - 124km ENE of Codrington, Barbuda\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-60.701900000000002,17.995999999999999,26]},\"id\":\"us20005azy\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":5.0999999999999996,\"place\":\"31km NW of Fairview, Oklahoma\",\"time\":1455383226290,\"updated\":1507756947265,\"tz\":-360,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20004zy8\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004zy8&format=geojson\",\"felt\":4044,\"cdi\":6.7999999999999998,\"mmi\":5.5999999999999996,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":1080,\"net\":\"us\",\"code\":\"20004zy8\",\"ids\":\",us20004zy8,gcmt20160213170706,atlas20160213170706,\",\"sources\":\",us,gcmt,atlas,\",\"types\":\",cap,dyfi,general-link,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.218,\"rms\":1.0900000000000001,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 5.1 - 31km NW of Fairview, Oklahoma\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-98.709000000000003,36.489800000000002,8.3100000000000005]},\"id\":\"us20004zy8\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"36km W of Ovalle, Chile\",\"time\":1455064385340,\"updated\":1509720327782,\"tz\":-180,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20004z5b\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004z5b&format=geojson\",\"felt\":71,\"cdi\":6.5,\"mmi\":6,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":657,\"net\":\"us\",\"code\":\"20004z5b\",\"ids\":\",us20004z5b,gcmt20160210003305,atlas20160210003305,\",\"sources\":\",us,gcmt,atlas,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.11,\"rms\":0.81000000000000005,\"gap\":45,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 36km W of Ovalle, Chile\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-71.583799999999997,-30.572299999999998,29]},\"id\":\"us20004z5b\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.4000000000000004,\"place\":\"25km SE of Yujing, Taiwan\",\"time\":1454702247380,\"updated\":1555947637590,\"tz\":480,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20004y6h\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004y6h&format=geojson\",\"felt\":146,\"cdi\":8.1999999999999993,\"mmi\":7.2000000000000002,\"alert\":\"yellow\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":770,\"net\":\"us\",\"code\":\"20004y6h\",\"ids\":\",pt16036050,at00o23bfs,us20004y6h,gcmt20160205195727,atlas20160205195727,\",\"sources\":\",pt,at,us,gcmt,atlas,\",\"types\":\",cap,dyfi,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-moment-tensor,\",\"nst\":null,\"dmin\":0.36199999999999999,\"rms\":1.1899999999999999,\"gap\":14,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.4 - 25km SE of Yujing, Taiwan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[120.6014,22.9375,23]},\"id\":\"us20004y6h\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":5.2000000000000002,\"place\":\"19km N of Kathmandu, Nepal\",\"time\":1454689211740,\"updated\":1461029599040,\"tz\":345,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us20004y4t\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004y4t&format=geojson\",\"felt\":113,\"cdi\":5.2999999999999998,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":476,\"net\":\"us\",\"code\":\"20004y4t\",\"ids\":\",us20004y4t,\",\"sources\":\",us,\",\"types\":\",cap,dyfi,geoserve,impact-text,nearby-cities,origin,phase-data,tectonic-summary,\",\"nst\":null,\"dmin\":0.16600000000000001,\"rms\":0.81999999999999995,\"gap\":44,\"magType\":\"mb\",\"type\":\"earthquake\",\"title\":\"M 5.2 - 19km N of Kathmandu, Nepal\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[85.337699999999998,27.8782,23.530000000000001]},\"id\":\"us20004y4t\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2999999999999998,\"place\":\"50km NNE of Al Hoceima, Morocco\",\"time\":1453695722730,\"updated\":1507756883299,\"tz\":0,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004gy9\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gy9&format=geojson\",\"felt\":117,\"cdi\":7.2000000000000002,\"mmi\":5.2999999999999998,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":695,\"net\":\"us\",\"code\":\"10004gy9\",\"ids\":\",gcmt20160125042203,us10004gy9,gcmt20160125042202,atlas20160125042202,\",\"sources\":\",gcmt,us,gcmt,atlas,\",\"types\":\",associate,cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.2010000000000001,\"rms\":0.92000000000000004,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 50km NNE of Al Hoceima, Morocco\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-3.6818,35.649299999999997,12]},\"id\":\"us10004gy9\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.0999999999999996,\"place\":\"86km E of Old Iliamna, Alaska\",\"time\":1453631430230,\"updated\":1558419847696,\"tz\":-540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004gqp\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gqp&format=geojson\",\"felt\":1816,\"cdi\":7.2000000000000002,\"mmi\":6.5,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1496,\"net\":\"us\",\"code\":\"10004gqp\",\"ids\":\",ak12496371,at00o1gd6r,us10004gqp,ak01613v15nv,atlas20160124103030,atlas20160124103029,\",\"sources\":\",ak,at,us,ak,atlas,atlas,\",\"types\":\",associate,cap,dyfi,finite-fault,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-origin,\",\"nst\":null,\"dmin\":0.71999999999999997,\"rms\":2.1099999999999999,\"gap\":19,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 86km E of Old Iliamna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-153.4051,59.636299999999999,129]},\"id\":\"us10004gqp\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.7000000000000002,\"place\":\"52km SE of Shizunai, Japan\",\"time\":1452741933640,\"updated\":1507756815178,\"tz\":540,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004ebx\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebx&format=geojson\",\"felt\":51,\"cdi\":5.7999999999999998,\"mmi\":5.7999999999999998,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":720,\"net\":\"us\",\"code\":\"10004ebx\",\"ids\":\",us10004ebx,gcmt20160114032534,pt16014050,at00o0xauk,gcmt20160114032533,atlas20160114032533,\",\"sources\":\",us,gcmt,pt,at,gcmt,atlas,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.28100000000000003,\"rms\":0.97999999999999998,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 52km SE of Shizunai, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[142.78100000000001,41.972299999999997,46]},\"id\":\"us10004ebx\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":5.7000000000000002,\"place\":\"31km SSE of Jarm, Afghanistan\",\"time\":1452629099710,\"updated\":1478815684208,\"tz\":270,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004dtm\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004dtm&format=geojson\",\"felt\":84,\"cdi\":4.5999999999999996,\"mmi\":3.0699999999999998,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":538,\"net\":\"us\",\"code\":\"10004dtm\",\"ids\":\",gcmt20160112200459,gcmt20160112200500,us10004dtm,\",\"sources\":\",gcmt,gcmt,us,\",\"types\":\",associate,cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.60599999999999998,\"rms\":1.0700000000000001,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 5.7 - 31km SSE of Jarm, Afghanistan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[70.950299999999999,36.597900000000003,239]},\"id\":\"us10004dtm\"},\n"+
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.7000000000000002,\"place\":\"30km W of Imphal, India\",\"time\":1451862322270,\"updated\":1562693393711,\"tz\":330,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us10004b2n\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004b2n&format=geojson\",\"felt\":974,\"cdi\":7.5999999999999996,\"mmi\":6.7999999999999998,\"alert\":\"orange\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":1740,\"net\":\"us\",\"code\":\"10004b2n\",\"ids\":\",us10004b2n,gcmt20160103230522,atlas20160103230522,\",\"sources\":\",us,gcmt,atlas,\",\"types\":\",cap,dyfi,general-text,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.794,\"rms\":1.01,\"gap\":16,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 30km W of Imphal, India\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[93.650499999999994,24.803599999999999,55]},\"id\":\"us10004b2n\"}],\"bbox\":[-153.4051,-30.5723,8.31,142.781,59.6363,239]}";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<word> fetchEarthquakeData(String USGS_REQUEST_URL){

        URL url = createUrl(USGS_REQUEST_URL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (Exception e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<word> earthquakes = QueryUtils.extractEarthquakes(jsonResponse);

        return earthquakes;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e){

        }
        return url;
    }

    private static String makeHttpRequest(URL url){
        String jsonResponse = " ";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
               try { inputStream.close();}catch(IOException e){

               }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link word} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<word> extractEarthquakes(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<word> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject responseInJSONObjectForm = new JSONObject(jsonResponse);
            JSONArray responses = responseInJSONObjectForm.getJSONArray("features");
            for(int i=0; i<responses.length(); i++){
                JSONObject c = responses.getJSONObject(i);
                JSONObject properties = c.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                Log.e("###","MAG IS " + mag);
                String place = properties.getString("place");
                Log.e("###","MAG IS " + mag);
                long time = properties.getLong("time");
                Log.e("###","MAG IS " + mag);
                String url = properties.getString("url");
                word earthQuake = new word(mag, place, time, url);
                earthquakes.add(earthQuake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}