package com.example.btl.mp3player.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btl.mp3player.fragments.FragmentPlay;
import com.example.btl.mp3player.fragments.FragmentSongListPlaying;
import com.example.btl.mp3player.models.Song;

import java.util.ArrayList;

/**
 * Created by IceMan on 11/20/2016.
 */

public class ViewPagerPlayAdapter extends FragmentStatePagerAdapter {

    ArrayList<Song> mData;
    String coverPath;

    public ViewPagerPlayAdapter(FragmentManager fm, ArrayList<Song> mData, String coverPath) {
        super(fm);
        this.mData = mData;
        this.coverPath = coverPath;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = FragmentSongListPlaying.newInstance(mData);
                break;
            case 1:
                fragment = FragmentPlay.newInstance(coverPath);
                break;
        }
        return fragment;
    }



    @Override
    public int getCount() {
        return 2;
    }
}
