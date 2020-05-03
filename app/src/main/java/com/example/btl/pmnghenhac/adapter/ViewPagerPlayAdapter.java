package com.example.btl.pmnghenhac.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.btl.pmnghenhac.fragments.FragmentPlay;
import com.example.btl.pmnghenhac.fragments.FragmentSongListPlaying;
import com.example.btl.pmnghenhac.models.Song;

import java.util.ArrayList;

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
