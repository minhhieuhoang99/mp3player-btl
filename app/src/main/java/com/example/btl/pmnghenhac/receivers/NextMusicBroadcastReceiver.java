package com.example.btl.pmnghenhac.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.btl.pmnghenhac.activities.PlayMusicActivity;
import com.example.btl.pmnghenhac.services.PlayMusicService;
import com.example.btl.pmnghenhac.utils.AppController;

public class NextMusicBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PlayMusicActivity musicActivity = (PlayMusicActivity) AppController.getInstance().getPlayMusicActivity();
        PlayMusicService musicService = (PlayMusicService) AppController.getInstance().getPlayMusicService();
//        musicService.setShowNotification(false);
        if (musicActivity != null) {
            musicActivity.nextMusic();

        } else {
            musicService.nextMusic();
        }
        musicService.showNotification(true);
//        musicService.setShowNotification(true);

    }
}
