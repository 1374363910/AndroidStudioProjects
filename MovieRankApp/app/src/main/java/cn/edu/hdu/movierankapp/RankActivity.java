package cn.edu.hdu.movierankapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RankActivity extends AppCompatActivity {

    private Button movieRankButton;
    private Button movieRank2019Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        movieRankButton = findViewById(R.id.btn_movie_rank);
        movieRank2019Button = findViewById(R.id.btn_movie_rank_2019);

        movieRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, MovieRankActivity.class);
                startActivity(intent);
                return;
            }
        });

        movieRank2019Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, MovieRank2019Activity.class);
                startActivity(intent);
                return;
            }
        });

    }
}
