package com.example.videoplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

public class Video_player extends AppCompatActivity {

    StyledPlayerView playerView;
    ImageView fbLink,twtLink,ytLink,webLink;
    TextView description;
    ImageView fullScreen;
    boolean isFullScreen = false;
    ExoPlayer player;
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        playerView = findViewById(R.id.player_view);
        fullScreen = playerView.findViewById(R.id.exo_fullscreen_icon);
//        progressBar = findViewById(R.id.exo_progress_placeholder);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton imgbtn =  findViewById(R.id.play_btn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()){
                    player.pause();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imgbtn.setImageDrawable(getDrawable(R.drawable.pause2));
                    }

                }else{
                    player.play();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imgbtn.setImageDrawable(getDrawable(R.drawable.play2));
                    }
                }
            }
        });

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullScreen){

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

//                    Toast.makeText(Details.this, "We are Now going back to normal mode.", Toast.LENGTH_SHORT).show();
                    isFullScreen = false;
                }else {

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);

//                    Toast.makeText(Details.this, "We are going to FullScreen Mode.", Toast.LENGTH_SHORT).show();
                    isFullScreen = true;
                }
            }
        });

        playvideo(url);
    }


    private  void playvideo(String url) {
        try {
//            player = new ExoPlayer.Builder(this).build();
//            DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
//// Create a HLS media source pointing to a playlist uri.
//            HlsMediaSource hlsMediaSource =
//                    new HlsMediaSource.Factory(dataSourceFactory)
//                            .createMediaSource(MediaItem.fromUri(url));
//
//            StyledPlayerView styledPlayerView = findViewById(R.id.player_view);
//
//            styledPlayerView.setPlayer(player);
//            player.setMediaSource(hlsMediaSource);
//            player.prepare();
//            player.play();


            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);

            DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
            HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory).
                    createMediaSource(MediaItem.fromUri(url));
            player.setMediaSource(mediaSource);
            player.prepare();
            player.setPlayWhenReady(true);
            player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if(state == Player.STATE_READY){
//                        progressBar.setVisibility(View.GONE);
                        player.setPlayWhenReady(true);
                    }else if(state == Player.STATE_BUFFERING){
//                        progressBar.setVisibility(View.VISIBLE);
                        playerView.setKeepScreenOn(true);
                    }else {
//                        progressBar.setVisibility(View.GONE);
                        player.setPlayWhenReady(true);
                    }
                }
            });
        }catch (Exception e) {
            Log.i("Tanivir", "playvideo: " + e.toString());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        player.seekToDefaultPosition();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }
}

