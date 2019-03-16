package com.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MoviesRecyclerAdapter moviesRecyclerAdapter;
    ArrayList<Movies> moviesArrayList;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView n_error;
    QueryTask queryTask;
    FloatingActionButton star;
    FloatingActionButton arrow;
    FloatingActionButton sort;
    int counter = 0;
    Context context;
    String oldjson;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setRefreshing(true);
        n_error = findViewById(R.id.n_error);
        queryTask = new QueryTask();
        oldjson = "";
        context = this;

        moviesArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        moviesRecyclerAdapter = new MoviesRecyclerAdapter(this,moviesArrayList);
        recyclerView.setAdapter(moviesRecyclerAdapter);


        if(isOnline()){
            Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/now_playing?api_key="+getResources().getString(R.string.tmdb_api_key)+"&language=en-US");
            try {
                URL url = new URL(uri.toString());
                queryTask.execute(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            n_error.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
        else{
            swipeRefreshLayout.setRefreshing(false);
            n_error.setText(R.string.no_internet_connection);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryTask = new QueryTask();
                if(isOnline()){
                    Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/now_playing?api_key="+getResources().getString(R.string.tmdb_api_key)+"&language=en-US");
                    try {
                        URL url = new URL(uri.toString());
                         queryTask.execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    n_error.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    n_error.setText(R.string.no_internet_connection);
                }
            }
        });

        sort = findViewById(R.id.sort);
        star = findViewById(R.id.star);
         arrow = findViewById(R.id.arrow);


        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter ==0) {
                    star.animate().translationY(-250).start();
                    arrow.animate().translationY(-500).start();
                    counter++;
                }
                else{
                    star.animate().translationY(0).start();
                    arrow.animate().translationY(0).start();
                    counter = 0;
                }

            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setRefreshing(true);
                star.animate().translationY(0).start();
                arrow.animate().translationY(0).start();
                counter = 0;

                queryTask = new QueryTask();
                if(isOnline()){
                    Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/top_rated?api_key="+getResources().getString(R.string.tmdb_api_key)+"&language=en-US");
                    try {
                        URL url = new URL(uri.toString());
                        queryTask.execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    n_error.setVisibility(View.GONE);
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    n_error.setText(R.string.no_internet_connection);
                }

            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                swipeRefreshLayout.setRefreshing(true);
                star.animate().translationY(0).start();
                arrow.animate().translationY(0).start();
                counter = 0;

                queryTask = new QueryTask();
                if(isOnline()){
                    Uri uri = Uri.parse("https://api.themoviedb.org/3/movie/popular?api_key="+getResources().getString(R.string.tmdb_api_key)+"&language=en-US");
                    try {
                        URL url = new URL(uri.toString());
                        queryTask.execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    n_error.setVisibility(View.GONE);
                }
                else{
                    swipeRefreshLayout.setRefreshing(false);
                    n_error.setText(R.string.no_internet_connection);
                }
            }
        });








    }









    //This method were copied from  https://stackoverflow.com/a/4009133
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    public class QueryTask extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String movieURL = null;

            try {
                movieURL = getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieURL;
        }


        @Override
        protected void onPostExecute(String MovieResults) {
                if(oldjson.equals(MovieResults)){
                    Toast.makeText(context, R.string.up_to_date, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    if (MovieResults != null && !MovieResults.equals("")) {

                        moviesArrayList.clear();
                        moviesRecyclerAdapter.notifyDataSetChanged();

                        try {
                            JSONObject jsonObject = new JSONObject(MovieResults);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                moviesArrayList.add(new Movies(json.getString("title"),
                                        json.getString("poster_path"),
                                        json.getString("overview"),
                                        json.getString("vote_average"),
                                        json.getString("release_date")));
                                moviesRecyclerAdapter.notifyDataSetChanged();
                            }

                            oldjson = MovieResults;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        }



        public  String getResponseFromHttpUrl(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } finally {
                urlConnection.disconnect();
            }
        }
    }




}
