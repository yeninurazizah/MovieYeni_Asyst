package id.co.asyst.yeni.movieyeni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.co.asyst.yeni.movieyeni.model.MovieModel;
import id.co.asyst.yeni.movieyeni.utility.Constant;
import id.co.asyst.yeni.movieyeni.utility.DateUtils;

public class OverviewActivity extends AppCompatActivity {

    TextView movieTV, releaseTV, overViewTV;
    ImageView movieIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        movieIV = findViewById(R.id.movie_imgview);
        movieTV = findViewById(R.id.title_tv);
        releaseTV = findViewById(R.id.release_tv);
        overViewTV = findViewById(R.id.overview_tv);

        if (getIntent().getExtras() != null) {

            MovieModel movieModel = getIntent().getExtras().getParcelable("movie");
            Glide.with(this).load(Constant.BACKDROP_PATH + movieModel.getBackdrop_path()).into(movieIV);

            movieTV.setText(movieModel.getTitle());
            releaseTV.setText(DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movieModel.getRelease_date()));
            overViewTV.setText(movieModel.getOverview());
        }

    }
}
