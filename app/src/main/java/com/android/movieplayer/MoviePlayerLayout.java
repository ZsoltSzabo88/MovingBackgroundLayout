package com.android.movieplayer;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Zsolt Szabo Negyedi on 9/30/2015.
 */
public class MoviePlayerLayout extends TextureView implements Runnable, SurfaceTextureListener {

    private MediaPlayer mediaPlayer;
    private Uri videoPath;
    private CountDownLatch latch;

    /**
     * Default Constructor.
     *
     * @param context Context
     */
    public MoviePlayerLayout(Context context) {
        this(context, null);
    }

    /**
     * Default Constructor.
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public MoviePlayerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Default Constructor.
     *
     * @param context      Context
     * @param attrs        AttributeSet
     * @param defStyleAttr int
     */
    public MoviePlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        latch = new CountDownLatch(1);
        setSurfaceTextureListener(this);
    }

    /**
     * Stops and releases the mediaPlayer.
     */
    public void onPause() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    /**
     * Loads the video on the surfaceView.
     *
     * @param resId int the resource Id of the video to play. For Example: R.raw.myVideo.
     */
    public void onResume(int resId) {
        setVideoPath(resId);
        latch = new CountDownLatch(1);
        new Thread(this).start();
    }

    private void setVideoPath(int resId) {
        videoPath = Uri.parse("android.resource://" + getContext().getPackageName() + "/"
                + resId);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        latch.countDown();
    }

    @Override
    public void run() {
        mediaPlayer = MediaPlayer.create(getContext(), videoPath);
        mediaPlayer.setLooping(true);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mediaPlayer.setSurface(new Surface(getSurfaceTexture()));
        mediaPlayer.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}