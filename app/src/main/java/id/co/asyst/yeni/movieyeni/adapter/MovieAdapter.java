package id.co.asyst.yeni.movieyeni.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.co.asyst.yeni.movieyeni.R;
import id.co.asyst.yeni.movieyeni.model.MovieModel;
import id.co.asyst.yeni.movieyeni.utility.Constant;
import id.co.asyst.yeni.movieyeni.utility.DateUtils;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<MovieModel> mListMovie;
    onItemClickListener listener;


    public MovieAdapter(Context context, ArrayList<MovieModel> listMovie) {
        this.mContext = context;
        this.mListMovie = listMovie;
    }

    public MovieAdapter(Context context, ArrayList<MovieModel> listMovie, onItemClickListener listener) {
        this.mContext = context;
        this.mListMovie = listMovie;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new MovieAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MovieModel movieModel = mListMovie.get(position);

        holder.movieTV.setText(movieModel.getTitle());
//            holder.releaseTV.setText(movieModel.getRelease_date());
        String date = movieModel.getRelease_date().equals("") ? "Tidak diketahui" : DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movieModel.getRelease_date());
        holder.releaseTV.setText(date);
        holder.overViewTV.setText(movieModel.getOverview());

        holder.readMoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(movieModel);
            }
        });

        RequestOptions requestOptions = new RequestOptions().placeholder(mContext.getResources().getDrawable(R.drawable.ic_error)).error(R.drawable.ic_error);
        Glide.with(mContext).load(Constant.BASE_IMAGE + movieModel.getPoster_path()).apply(requestOptions).into(holder.movieIV);

    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }

    public interface onItemClickListener {
        void onItemClickListener(MovieModel movieModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView movieIV;
        TextView movieTV, releaseTV, overViewTV, readMoreTV;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            movieIV = itemView.findViewById(R.id.movie_imgview);
            movieTV = itemView.findViewById(R.id.movie_tv);
            releaseTV = itemView.findViewById(R.id.release_tv);
            overViewTV = itemView.findViewById(R.id.overview_tv);
            readMoreTV = itemView.findViewById(R.id.readmore_tv);

            cardView = itemView.findViewById(R.id.card_view);

        }

    }


}
