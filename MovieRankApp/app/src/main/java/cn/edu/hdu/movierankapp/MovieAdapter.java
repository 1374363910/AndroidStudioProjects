package cn.edu.hdu.movierankapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Context context;
    List<Movie> movieList;
    public MovieAdapter(Context context,List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        int rank = movieList.get(position).getRank();
        holder.tvRank.setText(String.valueOf(rank));
        final String movieName = movieList.get(position).getName();
        holder.tvMovieName.setText(movieName);
        String releaseDate = movieList.get(position).getReleaseDate();
        holder.tvReleaseDate.setText(releaseDate + "上映");
        int boxOffice = movieList.get(position).getBoxOffice();
        holder.tvBoxOffice.setText(String.valueOf(boxOffice));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StorylineActivity.class);
                intent.putExtra("name", movieName);
                intent.putExtra("storyline", movieList.get(position).getStoryline());
                context.startActivity(intent);
            }
        });

    }
    public int getItemCount(){
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView tvRank;
        public TextView tvMovieName;
        public TextView tvReleaseDate;
        public TextView tvBoxOffice;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.tv_rank);
            tvMovieName = itemView.findViewById(R.id.tv_movie_name);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
            tvBoxOffice = itemView.findViewById(R.id.tv_box_office);

        }

    }
}



