package id.co.asyst.yeni.movieyeni.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
    String poster_path;
    String backdrop_path;
    String overview;
    String title;
    String release_date;

    protected MovieModel(Parcel in) {
        poster_path = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        title = in.readString();
        release_date = in.readString();
    }

    public String getPoster_path() {
        return poster_path;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }


    public String getOverview() {
        return overview;
    }


    public String getTitle() {
        return title;
    }


    public String getRelease_date() {
        return release_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(title);
        dest.writeString(release_date);
    }
}
