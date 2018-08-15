package id.co.asyst.yeni.movieyeni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import id.co.asyst.yeni.movieyeni.adapter.MovieAdapter;
import id.co.asyst.yeni.movieyeni.fragment.FilterFragment;
import id.co.asyst.yeni.movieyeni.fragment.SearchFragment;
import id.co.asyst.yeni.movieyeni.model.MovieModel;
import id.co.asyst.yeni.movieyeni.retrofit.ApiClient;
import id.co.asyst.yeni.movieyeni.retrofit.ApiServices;
import id.co.asyst.yeni.movieyeni.retrofit.response.MovieResponse;
import id.co.asyst.yeni.movieyeni.utility.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FilterFragment.OnSubmitButtonListener, SearchFragment.OnSubmitButton {

    RecyclerView recylerView;
    MovieAdapter movieAdapter;
    String year = "";
    String sort_by = "";
    String search = "";

    int page = 1;
    int totalpages;
    ProgressBar progressBar;
    boolean isLoading = false;

    ArrayList<MovieModel> listMovie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recylerView = findViewById(R.id.recyclerview);

        progressBar = findViewById(R.id.progress_bar);


        movieAdapter = new MovieAdapter(this, listMovie, new MovieAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(MovieModel movieModel) {

                Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
                intent.putExtra("movie", movieModel);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerView.setLayoutManager(LayoutManager);
        recylerView.setItemAnimator(new DefaultItemAnimator());

        recylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        if (totalpages >= page) {
                            progressBar.setVisibility(View.INVISIBLE);
                            isLoading = true;

                            if (search.equalsIgnoreCase("")) {
                                getDataWithRetrofit();
                            } else {
                                searchDataWithRetrofit();
                            }

                        }
                    }
                }
            }
        });
        recylerView.setAdapter(movieAdapter);

        getDataWithRetrofit();

    }

    private void getDataWithRetrofit() {
        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);

        Call<MovieResponse> call = apiServices.getMovie(Constant.API_KEY, year, page, sort_by);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null) {

                    progressBar.setVisibility(View.INVISIBLE);
                    if (response.body().getResults().size() > 0) {

                        totalpages = response.body().getTotal_pages();
                        page = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                        isLoading = false;

                    }

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }

    private void searchDataWithRetrofit() {
        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);

        Call<MovieResponse> call = apiServices.searchMovie(Constant.API_KEY, search, page);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() != null) {

                    progressBar.setVisibility(View.INVISIBLE);
                    if (response.body().getResults().size() > 0) {

                        totalpages = response.body().getTotal_pages();
                        page = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                        isLoading = false;

                    }

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_item:
                FilterFragment filterFragment = FilterFragment.newInstance(year, sort_by);
                filterFragment.show(getSupportFragmentManager(), "Filter");
                break;

            case R.id.search_item:
                SearchFragment searchFragment = SearchFragment.newInstance(search);
                searchFragment.show(getSupportFragmentManager(), "Search");
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSubmitButton(String year, String sort) {
        this.year = year;
        this.sort_by = sort;
        this.search = "";

        listMovie.clear();
        movieAdapter.notifyDataSetChanged();

        page = 1;
        getDataWithRetrofit();
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onSubmitButtonListener(String search) {
        this.search = search;

        page = 1;
        listMovie.clear();
        movieAdapter.notifyDataSetChanged();
        searchDataWithRetrofit();
    }
}

//https://image.tmdb.org/t/p/w185_and_h278_bestv2