package com.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MoviesDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        Intent intent = getIntent();

        Movies movies = intent.getParcelableExtra("Movies");


        TextView title = findViewById(R.id.title);
        TextView rating = findViewById(R.id.rating);
        ImageView poster = findViewById(R.id.poster);
        TextView plot_synopsis = findViewById(R.id.plot_synopsis);
        TextView release_date = findViewById(R.id.relaese_date);


        title.setText(movies.getTitle());
        rating.setText(movies.getVote_average());
        plot_synopsis.setText(movies.getOverview());
        release_date.setText(movies.getRelease_date());
        Glide.with(this).
                load("http://image.tmdb.org/t/p/w185/"+movies.getUrlToPoster()).
                into(poster);


    }
}
