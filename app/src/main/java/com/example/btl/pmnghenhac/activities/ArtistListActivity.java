package com.example.btl.pmnghenhac.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.pmnghenhac.R;
import com.example.btl.pmnghenhac.adapter.ArtistAdapter;
import com.example.btl.pmnghenhac.adapter.SongListAdapter;
import com.example.btl.pmnghenhac.models.Song;
import com.example.btl.pmnghenhac.utils.AppController;
import com.example.btl.pmnghenhac.utils.Common;

import java.util.ArrayList;

public class ArtistListActivity extends AppCompatActivity {

    TextView tvArtistName;
    RecyclerView mRvListSong;
    SongListAdapter mSongAdapter;
    ArrayList<Song> mLstSong;
    ImageView imgBackGround;
    int mArtistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_artist);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initControls();
        Common.setStatusBarTranslucent(true, this);
        getDataFromIntentAndShow();
        AppController.getInstance().setDefaultWallpaper(imgBackGround);
    }

    private void getDataFromIntentAndShow() {
        Intent intent = getIntent();
        mArtistId = intent.getExtras().getInt(ArtistAdapter.KEY_ARTIST);
        mLstSong = AppController.getInstance().getListSongOfArtist(mArtistId);
        mSongAdapter = new SongListAdapter(this, mLstSong);
        mRvListSong.setAdapter(mSongAdapter);
        tvArtistName.setText(mLstSong.get(0).getArtist());
    }

    private void initControls() {
        tvArtistName = (TextView) findViewById(R.id.artist_name_toolbar);
        mRvListSong = (RecyclerView) findViewById(R.id.rv_artist_list_play);
        imgBackGround = (ImageView) findViewById(R.id.img_back_ground_artist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvListSong.setLayoutManager(layoutManager);
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
}
