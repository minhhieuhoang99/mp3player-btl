package com.example.btl.pmnghenhac.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.btl.pmnghenhac.R;
import com.example.btl.pmnghenhac.activities.ArtistListActivity;
import com.example.btl.pmnghenhac.models.Artist;

import java.util.ArrayList;


public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolderArtist> {

    public static final String KEY_ARTIST = "key_artist";

    Context mContext;
    ArrayList<Artist> mData;
    LayoutInflater mLayoutInflater;

    public ArtistAdapter(Context mContext, ArrayList<Artist> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolderArtist onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_artist, null);
        ViewHolderArtist holder = new ViewHolderArtist(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolderArtist holder, int position) {
        holder.tvArtist.setText(mData.get(position).getName());
        holder.setId(position);
    }

    public void filter(ArrayList<Artist> lstArtist) {
        mData = new ArrayList<>();
        mData.addAll(lstArtist);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderArtist extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvArtist;
        int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ViewHolderArtist(View itemView) {
            super(itemView);
            tvArtist = (TextView) itemView.findViewById(R.id.artist_title_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ArtistListActivity.class);
            intent.putExtra(KEY_ARTIST, mData.get(id).getId());
            mContext.startActivity(intent);
        }
    }
}
