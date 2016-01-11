package com.android.movieplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    private MoviePlayerLayout moviePlayerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviePlayerLayout = (MoviePlayerLayout) findViewById(R.id.movie_background);
        clearAppBackgroundColor();
    }

    @Override
    protected void onResume() {
        moviePlayerLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        moviePlayerLayout.onResume(R.raw.intro_movie);
        super.onResume();
    }

    @Override
    protected void onPause() {
        moviePlayerLayout.onPause();
        super.onPause();
    }

    private void clearAppBackgroundColor() {
        getWindow().setBackgroundDrawable(null);
    }
}