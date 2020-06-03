package com.example.btl.pmnghenhac.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btl.pmnghenhac.R;
import com.example.btl.pmnghenhac.fragments.FragmentAlbum;
import com.example.btl.pmnghenhac.fragments.FragmentArtist;
import com.example.btl.pmnghenhac.fragments.FragmentSongList;

public class ViewPagerDetailAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public ViewPagerDetailAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentSongList();
                break;
            case 1:
                fragment = new FragmentAlbum();
                break;
            case 2:
                fragment = new FragmentArtist();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = mContext.getString(R.string.list_song);
        switch (position) {
            case 0:
                pageTitle = mContext.getString(R.string.list_song);
                break;
            case 1:
                pageTitle = mContext.getString(R.string.album_list);
                break;
            case 2:
                pageTitle = mContext.getString(R.string.artist_list);
                break;
            default:
                pageTitle = mContext.getString(R.string.list_song);
                break;
        }

        return pageTitle;
    }
}
