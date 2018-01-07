package com.example.jaska.newsapp;

/**
 * Created by jaska on 22-Dec-17.
 */
import android.content.AsyncTaskLoader;
import android.content.Context;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>>{
    String mUrl;
    public NewsLoader(Context context, String URL) {
        super(context);
        mUrl = URL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<News> loadInBackground() {
        List<News> myData = null;
        if (mUrl == null) {
            return null;
        }
        try {
            myData = QueryUtils.fetchNewsData(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myData;
    }
}
