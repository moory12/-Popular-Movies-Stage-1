package com.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Movies> moviesArrayList;

    public MoviesRecyclerAdapter(Context context, ArrayList<Movies> moviesArrayList) {
        this.context = context;
        this.moviesArrayList = moviesArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.movie_cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
          Glide.with(context).
                load("http://image.tmdb.org/t/p/w185/"+moviesArrayList.get(position).getUrlToPoster()).
                into(holder.moviePoster);

          holder.moviePoster.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent intent = new Intent(context,MoviesDetails.class);
                intent.putExtra("Movies", moviesArrayList.get(position));
                context.startActivity(intent);
              }
          });


    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView moviePoster;

        public MyViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_poster);
        }
    }
}
