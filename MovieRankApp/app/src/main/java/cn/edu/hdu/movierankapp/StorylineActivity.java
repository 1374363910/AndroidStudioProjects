package cn.edu.hdu.movierankapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StorylineActivity extends Activity {
    TextView tvTitle;
    TextView tvStoryline;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyline);

        tvStoryline = findViewById(R.id.tv_storyline);
        tvTitle = findViewById(R.id.tv_title);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String storyline = intent.getStringExtra("storyline");
        tvTitle.setText(name);
        tvStoryline.setText(storyline);

    }
}
