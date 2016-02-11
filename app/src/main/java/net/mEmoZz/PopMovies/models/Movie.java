package net.mEmoZz.PopMovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (C) 2015 Mohamed Fathy
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Movie implements Parcelable {

    private String poster;
    private String backdrop;
    private String title;
    private String overview;
    private String date;
    private String voteAvr;
    private String id;

    public Movie() {
    }

    protected Movie(Parcel in) {
        poster = in.readString();
        backdrop = in.readString();
        title = in.readString();
        overview = in.readString();
        date = in.readString();
        voteAvr = in.readString();
        id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVoteAvr() {
        return voteAvr;
    }

    public void setVoteAvr(String voteAvr) {
        this.voteAvr = voteAvr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(date);
        dest.writeString(voteAvr);
        dest.writeString(id);
    }
}
