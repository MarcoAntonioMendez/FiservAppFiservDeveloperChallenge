package com.fiserv.fiservappfiservdeveloperchallenge;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The AnimationActivity class is in charge of the welcome animation for the user.
 * It displays the Fiserv's Logo and its motto.
 * When the animations on the logo and motto are finished, the LogInActivity starts automatically.
 *
 * @author  MarcoAntonioMéndez
 * @version 1.0
 * @since   2020-10-13
 */

public class AnimationActivity extends AppCompatActivity {
    public static final int TRANSITION = 5000;

    private Animation topAnimation,bottomAnimation;
    private ImageView fiservLogo;
    private TextView fiservMotto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Loading animations from xml files
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        // Assigning graphics components to variables in class
        fiservLogo = findViewById(R.id.fiserv_logo);
        fiservMotto = findViewById(R.id.fiserv_motto);

        // Starting the animations
        fiservLogo.setAnimation(topAnimation);
        fiservMotto.setAnimation(bottomAnimation);

        // This handler will start the LogInActivity when the animations are finished.
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(AnimationActivity.this,LogInActivity.class);
                startActivity(intent);
                finish();
            }
        },TRANSITION);

        hideSystemUI();
    }

    /**
     * Hides the systemUI (TOP and BOTTOM bars of and android device),
     */
    private void hideSystemUI(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}
