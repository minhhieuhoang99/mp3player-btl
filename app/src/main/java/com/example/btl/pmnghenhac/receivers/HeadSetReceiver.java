package com.example.btl.pmnghenhac.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import com.example.btl.pmnghenhac.activities.PlayMusicActivity;
import com.example.btl.pmnghenhac.services.PlayMusicService;
import com.example.btl.pmnghenhac.utils.AppController;

public class HeadSetReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        PlayMusicActivity musicActivity = (PlayMusicActivity) AppController.getInstance().getPlayMusicActivity();
        PlayMusicService musicService = (PlayMusicService) AppController.getInstance().getPlayMusicService();
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
            if (musicActivity != null) {
                musicActivity.pauseMusic();
                Log.d("playpause","test");
            } else {
                musicService.pauseMusic();
            }
            Log.d("Headphone", "Headset unplugged");
//            musicService.setShowNotification(false);
            musicService.showNotification(true);
//            musicService.setShowNotification(true);
        }

        }
}
