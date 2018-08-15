package id.co.asyst.yeni.movieyeni.retrofit.response;

import java.util.ArrayList;

import id.co.asyst.yeni.movieyeni.model.MovieModel;

public class MovieResponse {

    int page;
    int total_results;
    int total_pages;

    ArrayList<MovieModel> results;


    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }


    public int getTotal_pages() {
        return total_pages;
    }


    public ArrayList<MovieModel> getResults() {
        return results;
    }


}
