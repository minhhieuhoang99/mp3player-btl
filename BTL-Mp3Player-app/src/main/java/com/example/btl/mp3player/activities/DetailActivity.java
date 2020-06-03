package com.example.btl.mp3player.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.btl.mp3player.R;
import com.example.btl.mp3player.adapter.MainAdapter;
import com.example.btl.mp3player.adapter.ViewPagerDetailAdapter;
import com.example.btl.mp3player.services.PlayMusicService;
import com.example.btl.mp3player.utils.AppController;
import com.example.btl.mp3player.utils.Common;
import com.example.btl.mp3player.utils.Constants;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager mViewPager;
    ViewPagerDetailAdapter mVPAdapter;
    ImageView imgBackGround;
    TabLayout tabLayoutDetail;
    LinearLayout currentPlayingBar;
    PlayMusicService musicService;
    String idType;
    ImageView btnPlayPauseCurrent;
    ImageView btnNextCurrent;
    ImageView imgAlbumArtCurrent;
    TextView tvTitle;
    TextView tvArtist;
    ImageView mIvAlbumCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initControls();
        Common.setStatusBarTranslucent(true,this);
        initEvents();
        getType();
        AppController.getInstance().setDefaultWallpaper(imgBackGround);
        if (musicService != null) {
            updatePlayingState();
            showCurrentSong();
        }
        registerBroadcastUpdatePlaying();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private void getType() {
        Intent intent = getIntent();
        idType = intent.getStringExtra(MainAdapter.KEY_MAIN);

        if (idType.equals(getString(R.string.list_song))) {
            mViewPager.setCurrentItem(0);
        } else if (idType.equals(getString(R.string.album_list))) {
            mViewPager.setCurrentItem(1);
        } else if (idType.equals(getString(R.string.artist_list))) {
            mViewPager.setCurrentItem(2);
        }
    }

    private void initEvents() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        currentPlayingBar.setOnClickListener(this);
        btnPlayPauseCurrent.setOnClickListener(this);
        btnNextCurrent.setOnClickListener(this);
    }

    private void initControls() {
        imgBackGround = findViewById(R.id.img_back_ground_detail);
        currentPlayingBar = findViewById(R.id.current_playing_bar);
        tabLayoutDetail = findViewById(R.id.tablayout_detail);
        mViewPager = findViewById(R.id.view_pager_detail);
        tabLayoutDetail.setupWithViewPager(mViewPager);
        mVPAdapter = new ViewPagerDetailAdapter(getSupportFragmentManager(), this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mVPAdapter);
        musicService = (PlayMusicService) AppController.getInstance().getPlayMusicService();
        btnPlayPauseCurrent = findViewById(R.id.btn_play_pause_current);
        btnNextCurrent = findViewById(R.id.btn_next_current);
        imgAlbumArtCurrent = findViewById(R.id.img_album_current_bar);
        tvTitle = findViewById(R.id.tv_song_title_current);
        tvArtist = findViewById(R.id.tv_artist_current);
        mIvAlbumCover = findViewById(R.id.img_album_list_play);

        if (musicService != null) {
            currentPlayingBar.setVisibility(View.VISIBLE);
        } else {
            currentPlayingBar.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.current_playing_bar:
                if (musicService != null) {
                    Intent intent = new Intent(DetailActivity.this, PlayMusicActivity.class);
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


}
