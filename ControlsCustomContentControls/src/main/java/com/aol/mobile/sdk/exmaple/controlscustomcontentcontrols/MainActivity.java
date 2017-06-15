package com.aol.mobile.sdk.exmaple.controlscustomcontentcontrols;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aol.mobile.sdk.player.OneSDK;
import com.aol.mobile.sdk.player.OneSDKBuilder;
import com.aol.mobile.sdk.player.Player;
import com.aol.mobile.sdk.player.http.model.Environment;
import com.aol.mobile.sdk.player.view.PlayerFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String VIDEO_ID = "577d09391313230df40d1893";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager();
        final PlayerFragment playerFragment = (PlayerFragment) fm.findFragmentById(R.id.player_fragment);
        playerFragment.setRetainInstance(true);

        try {
            new OneSDKBuilder(getApplicationContext())
                    // Remove.
                    .setExtra(new JSONObject("{\"noAds\":true}"))
                    // Remove.
                    .create(new OneSDKBuilder.Callback() {
                        @Override
                        public void onSuccess(@NonNull OneSDK oneSDK) {
                            useSDK(oneSDK, playerFragment);
                        }

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error Creating SDK", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void useSDK(@NonNull OneSDK oneSDK, @NonNull final PlayerFragment playerFragment) {
        oneSDK.createBuilder()
                .setAutoplay(true)
//                .buildForPlaylist("574d8444e4b06ca29f12d0d4", new Player.Callback() {
//                    @Override
//                    public void success(@NonNull Player player) {
//                        playerFragment.getBinder()
//                                .getPlayerView()
//                                .setVideoControlsView(new CustomContentControls(getApplicationContext(), null, 0));
//                        playerFragment.getBinder().setPlayer(player);
//                    }
//
//                    @Override
//                    public void error(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
                .buildForVideo("59397934955a316f1c4f65b4", new Player.Callback() {
                    @Override
                    public void success(@NonNull Player player) {
                        playerFragment.getBinder()
                                .getPlayerView()
                                .setVideoControlsView(new CustomContentControls(getApplicationContext(), null, 0));
                        player.turnOffSubtitles();
                        playerFragment.getBinder().setPlayer(player);
                    }

                    @Override
                    public void error(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
