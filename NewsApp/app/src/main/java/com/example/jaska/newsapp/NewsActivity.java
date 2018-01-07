package com.example.jaska.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>{
    private static final String NEWS_URL = "https://content.guardianapis.com/search?";
    public static final String LOG_TAG = NewsActivity.class.getName();
    TextView blankscreenHelper;
    ProgressBar progressBar;
    private NewsAdapter mAdapter;
    private static boolean firstLoadFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        android.app.LoaderManager loaderManager = getLoaderManager();
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        blankscreenHelper = (TextView)findViewById(R.id.empty_view);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        // Checking Internet Connectivity
        if (!isConnected){
            progressBar.setVisibility(View.GONE);
            blankscreenHelper.setText("Internet Connection not available");
            return;
        }
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass i1n this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(1, null, this);
        ListView newsListView = (ListView) findViewById(R.id.list);
        newsListView.setEmptyView(blankscreenHelper);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this,  new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String queryName = sharedPrefs.getString(
                getString(R.string.settings_query_key),
                getString(R.string.settings_query_default));

        String sectionName  = sharedPrefs.getString(
                getString(R.string.settings_section_key),
                getString(R.string.settings_section_default)
        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(NEWS_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        //appended so we can see the auuthor details too
        uriBuilder.appendQueryParameter("q", queryName);
        uriBuilder.appendQueryParameter("show-references","author");
        if(!firstLoadFlag){
            uriBuilder.appendQueryParameter("section", sectionName);
        }
        firstLoadFlag = false;
        uriBuilder.appendQueryParameter("api-key", "test");
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        if(news==null)return;
        blankscreenHelper.setText(R.string.no_news);
        progressBar.setVisibility(View.GONE);
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }


    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
