package com.example.btl.mp3player.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.btl.mp3player.R;
import com.example.btl.mp3player.adapter.MainAdapter;
import com.example.btl.mp3player.models.ItemListMain;
import com.example.btl.mp3player.models.Song;
import com.example.btl.mp3player.services.PlayMusicService;
import com.example.btl.mp3player.utils.AppController;
import com.example.btl.mp3player.utils.Common;
import com.example.btl.mp3player.utils.Constants;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRvListMain;
    ArrayList<ItemListMain> mListMain;
    MainAdapter mMainAdapter;
    LinearLayout currentPlayingBar;
    PlayMusicService musicService;
    ImageView btnPlayPauseCurrent;
    ImageView btnNextCurrent;
    ImageView imgAlbumArtCurrent;
    TextView tvTitle;
    TextView tvArtist;
    ImageView imgBackGround;
    ImageView mIvAlbumCover;
    ArrayList<Song> mListSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        //toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        AppController.getInstance().setMainActivity(this);
        initControls();
        initEvents();
        Common.setStatusBarTranslucent(true,this);
        showListMain();
        if (musicService != null) {
            updatePlayingState();
            showCurrentSong();
        }
        registerBroadcastUpdatePlaying();
        initDefaultWallpaper();
    }

    private void initDefaultWallpaper() {
        AppController.getInstance().setDefaultWallpaper(imgBackGround);
    }



    private void showListMain() {
        mListMain = new ArrayList<>();
        int numSongs = AppController.getInstance().getListSong().size();
        int numAlbums = AppController.getInstance().getListAlbum().size();
        int numArtists = AppController.getInstance().getListArtist().size();
        mListMain.add(new ItemListMain(R.drawable.ic_mm_song, getString(R.string.list_song), numSongs));
        mListMain.add(new ItemListMain(R.drawable.ic_album_white, getString(R.string.album_list), numAlbums));
        mListMain.add(new ItemListMain(R.drawable.ic_artist, getString(R.string.artist_list), numArtists));
        mMainAdapter = new MainAdapter(MainActivity.this, mListMain);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRvListMain.setLayoutManager(layoutManager);
        mRvListMain.setAdapter(mMainAdapter);

    }

    private void initControls() {
        mRvListMain = (RecyclerView) findViewById(R.id.rv_list_main);
        currentPlayingBar = (LinearLayout) findViewById(R.id.current_playing_bar);
        btnPlayPauseCurrent = (ImageView) findViewById(R.id.btn_play_pause_current);
        btnNextCurrent = (ImageView) findViewById(R.id.btn_next_current);
        imgAlbumArtCurrent = (ImageView) findViewById(R.id.img_album_current_bar);
        tvTitle = (TextView) findViewById(R.id.tv_song_title_current);
        tvArtist = (TextView) findViewById(R.id.tv_artist_current);
        imgBackGround = (ImageView) findViewById(R.id.img_wallpaper_main);
        musicService = (PlayMusicService) AppController.getInstance().getPlayMusicService();
        mIvAlbumCover = (ImageView) findViewById(R.id.img_album_list_play);

        if (musicService != null) {
            currentPlayingBar.setVisibility(View.VISIBLE);
        } else {
            currentPlayingBar.setVisibility(View.GONE);
        }
    }

    private void initEvents() {
        currentPlayingBar.setOnClickListener(this);
        btnPlayPauseCurrent.setOnClickListener(this);
        btnNextCurrent.setOnClickListener(this);
    }

    BroadcastReceiver broadcastReceiverUpdatePlaying = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            musicService = (PlayMusicService) AppController.getInstance().getPlayMusicService();
            if (musicService != null) {
                currentPlayingBar.setVisibility(View.VISIBLE);
            } else {
                currentPlayingBar.setVisibility(View.GONE);
            }
            showCurrentSong();
            if (musicService != null) {
                if (musicService.isPlaying()) {
                    btnPlayPauseCurrent.setImageResource(R.drawable.pb_pause);
                } else {
                    btnPlayPauseCurrent.setImageResource(R.drawable.pb_play);
                }
            }
        }
    };

    private void registerBroadcastUpdatePlaying() {
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_UPDATE_PlAY_STATUS);
        registerReceiver(broadcastReceiverUpdatePlaying, intentFilter);
    }

    private void unRegisterBroadcastUpdatePlaying() {
        unregisterReceiver(broadcastReceiverUpdatePlaying);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    private void updatePlayingState() {
        if (musicService.isPlaying()) {
            btnPlayPauseCurrent.setImageResource(R.drawable.pb_pause);
        } else {
            btnPlayPauseCurrent.setImageResource(R.drawable.pb_play);
        }
    }

    public void showCurrentSong() {
        if (musicService != null) {
            tvTitle.setText(musicService.getCurrentSong().getTitle());
            tvArtist.setText(musicService.getCurrentSong().getArtist());
            String albumPath = musicService.getCurrentSong().getAlbumImagePath();
            if (albumPath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(albumPath);
                imgAlbumArtCurrent.setImageBitmap(bitmap);
            } else {
                imgAlbumArtCurrent.setImageResource(R.drawable.default_cover);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_playing_bar:
                if (musicService != null) {
                    Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
                    intent.putExtra(PlayMusicActivity.IS_PlAYING, true);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up,R.anim.no_change);
                }
                break;
            case R.id.btn_play_pause_current:
                if (musicService != null) {
                    Intent intent = new Intent(Constants.ACTION_PLAY_PAUSE);
                    if (musicService.isPlaying()) {
                        btnPlayPauseCurrent.setImageResource(R.drawable.pb_play);
                    } else {
                        btnPlayPauseCurrent.setImageResource(R.drawable.pb_pause);
                    }
                    sendBroadcast(intent);
                    showCurrentSong();
                }
                break;
            case R.id.btn_next_current:
                if (musicService != null) {
                    Intent intent = new Intent(Constants.ACTION_NEXT);
                    showCurrentSong();
                    sendBroadcast(intent);

                }
                break;
        }
    }
    public void updatePlayPauseButton(){
        if (musicService != null) {
            if (musicService.isPlaying()) {
                btnPlayPauseCurrent.setImageResource(R.drawable.pb_play);
            } else {
                btnPlayPauseCurrent.setImageResource(R.drawable.pb_pause);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppController.getInstance().setMainActivity(null);
        unRegisterBroadcastUpdatePlaying();
    }








    private void showCover() {

        String path = mListSong.get(0).getAlbumImagePath();
        if (path != null) {
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            mIvAlbumCover.setImageURI(uri);
        }
    }

}
