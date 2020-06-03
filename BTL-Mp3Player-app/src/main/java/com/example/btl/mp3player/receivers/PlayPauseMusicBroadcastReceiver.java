package com.example.btl.mp3player.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.btl.mp3player.activities.PlayMusicActivity;
import com.example.btl.mp3player.services.PlayMusicService;
import com.example.btl.mp3player.utils.AppController;



public class PlayPauseMusicBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PlayMusicActivity musicActivity = (PlayMusicActivity) AppController.getInstance().getPlayMusicActivity();
        PlayMusicService musicService = (PlayMusicService) AppController.getInstance().getPlayMusicService();
//        musicService.setShowNotification(false);
        if (musicActivity != null) {
            musicActivity.playPauseMusic();
            Log.d("playpause","test");
        } else {
            musicService.playPauseMusic();
        }
        musicService.showNotification(true);
//        musicService.setShowNotification(true);
    }
}
