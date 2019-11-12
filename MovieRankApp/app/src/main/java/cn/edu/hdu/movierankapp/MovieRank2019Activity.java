package cn.edu.hdu.movierankapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieRank2019Activity extends Activity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rank2019);

        List<Movie> movieList = Movie.getMovieRank2019();

        recyclerView = findViewById(R.id.recycler_movie);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        MovieAdapter adapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(adapter);
    }
}
