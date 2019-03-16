package com.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    String title;
    String urlToPoster;
    String overview;
    String vote_average;
    String release_date;


    public Movies() {
    }




    public Movies(String title, String urlToPoster, String overview, String vote_average, String release_date) {
        this.title = title;
        this.urlToPoster = urlToPoster;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToPoster() {
        return urlToPoster;
    }

    public void setUrlToPoster(String urlToPoster) {
        this.urlToPoster = urlToPoster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }



    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(urlToPoster);
        dest.writeString(overview);
        dest.writeString(vote_average);
        dest.writeString(release_date);
    }


    public Movies(Parcel parcel){
        title = parcel.readString();
        urlToPoster = parcel.readString();
        overview = parcel.readString();
        vote_average = parcel.readString();
        release_date = parcel.readString();
    }



    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>(){

        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[0];
        }
    };
}
