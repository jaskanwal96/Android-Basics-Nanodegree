package com.example.jaska.newsapp;

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
 * Created by jaska on 22-Dec-17.
 */

class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static final String JSON_RESPONSE = "response";
    public static final String JSON_RESULTS = "results";
    public static final String JSON_SECTION_NAME = "sectionName";
    public static final String JSON_WEB_PUBLICATION_DATE = "webPublicationDate";
    public static final String JSON_WEB_TITLE = "webTitle";
    public static final String JSON_WEB_URL = "webUrl";
    public static final String JSON_REFERENCES = "references";
    public static final String JSON_REFERENCES_ID = "id";
    private static final String DEFAULT_AUTHOR = "Author Unavailable";


    public static List<News> fetchNewsData(String mUrl) throws IOException, JSONException {
        URL myURL = createUrl(mUrl);
        String jsonResponse = null;
        jsonResponse = makeHttpRequest(myURL);
        if (jsonResponse == "")return null;
        return extractNews(jsonResponse);

    }

    private static List<News> extractNews(String jsonResponse) throws JSONException {
        // Create an empty ArrayList that we can start adding news to
        List<News> news = new ArrayList<>();
        if(jsonResponse==null)return null;
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject j = new JSONObject(jsonResponse);
            j = j.getJSONObject(JSON_RESPONSE);
            JSONArray jArray = j.getJSONArray(JSON_RESULTS);
            for (int i = 0; i < jArray.length(); i++){
                JSONObject jO = (JSONObject) jArray.get(i);
                JSONArray joReferences = (JSONArray) jO.get(JSON_REFERENCES);
                String author = DEFAULT_AUTHOR;
                if(joReferences.length() != 0){
                    JSONObject authorJSON = (JSONObject) joReferences.get(0);
                    author = authorJSON.getString(JSON_REFERENCES_ID).split("/")[1];

                }
                news.add(new News(jO.getString(JSON_SECTION_NAME),
                        jO.getString(JSON_WEB_PUBLICATION_DATE), jO.getString(JSON_WEB_TITLE),
                        jO.getString(JSON_WEB_URL), author));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }
        //Return the list of news
        return news;
    }

    private static String makeHttpRequest(URL myURL) throws IOException {
        String jsonResponse = "";
        if (myURL == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) myURL.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "Bad Request,Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder myStr = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bfReader = new BufferedReader(inputStreamReader);
            String line = bfReader.readLine();
            while(line!=null){
                myStr.append(line);
                line = bfReader.readLine();
            }
        }
        return myStr.toString();
    }

    private static URL createUrl(String mUrl) {
        URL url = null;
        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
}
