package com.example.btl.pmnghenhac.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.pmnghenhac.R;
import com.example.btl.pmnghenhac.adapter.AlbumListAdapter;
import com.example.btl.pmnghenhac.adapter.SongListAdapter;
import com.example.btl.pmnghenhac.models.Song;
import com.example.btl.pmnghenhac.utils.AppController;
import com.example.btl.pmnghenhac.utils.Common;

import java.io.File;
import java.util.ArrayList;

public class AlbumListActivity extends AppCompatActivity {

    RecyclerView mRvSongList;
    ImageView mIvAlbumCover;
    ArrayList<Song> mListSong;
    SongListAdapter mAdapter;
    ImageView imgBackGround;
    TextView tvAlbumTitle;
    int mAlbumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_album_detail);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Common.setStatusBarTranslucent(true,this);
        initControls();
        getAndShowSongList();
        showCover();
        AppController.getInstance().setDefaultWallpaper(imgBackGround);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCover() {

        String path = mListSong.get(0).getAlbumImagePath();
        if (path != null) {
            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            mIvAlbumCover.setImageURI(uri);
        }
        tvAlbumTitle.setText(mListSong.get(0).getAlbum());
    }

    private void getAndShowSongList() {
        Intent intent = getIntent();
        mAlbumId = intent.getExtras().getInt(AlbumListAdapter.ALBUM_KEY);
        mListSong = AppController.getInstance().getListSongOfAlbum(mAlbumId);
        mAdapter = new SongListAdapter(this, mListSong);
        mRvSongList.setAdapter(mAdapter);
    }

    private void initControls() {
        mRvSongList = (RecyclerView) findViewById(R.id.rv_album_list_play);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRvSongList.setLayoutManager(layoutManager);
        imgBackGround = (ImageView) findViewById(R.id.img_back_ground_album);
        tvAlbumTitle = (TextView) findViewById(R.id.tv_album_title);
        mIvAlbumCover = (ImageView) findViewById(R.id.img_album_list_play);
    }
}
