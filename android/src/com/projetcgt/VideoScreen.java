package com.projetcgt;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.projetocgt.StarAssault;

public class VideoScreen extends Activity implements OnCompletionListener {
	@Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.video);
        String fileName = "android.resource://"+  getPackageName() +"/raw/gamplay";

         VideoView vv = (VideoView) this.findViewById(R.id.surface);
         vv.setVideoURI(Uri.parse(fileName));
         vv.setOnCompletionListener(this);
         vv.start();

    }

    @Override
    public void onCompletion(MediaPlayer mp) 
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);      
        finish();
    }
}
