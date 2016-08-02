package com.android.yl.phonemanager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Setup3Activity extends BaseSetupActivity {
    private Button btnselectContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        btnselectContact = (Button) findViewById(R.id.selectContact);
        btnselectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MediaPlayer player = MediaPlayer.create(Setup3Activity.this, R.raw.ylzs);
                player.setVolume(1f, 1f);
                player.setLooping(false);
                player.start();
            }
        });
    }

    @Override
    public void showNextPage() {
        startActivity(new Intent(this, Setup4Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    @Override
    public void showPreviousPage() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out);
    }

}
