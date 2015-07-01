package app.android.box.waveprotocol.org.androidwave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class MainActivity extends Activity{

    Animation move;
    ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myImageView = (ImageView) findViewById(R.id.myImage);
        move = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move);
        myImageView.startAnimation(move);


        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent openLoginActivity = new Intent("app.android.box.waveprotocol.org.androidwave.LOGINACTIVITY");
                    startActivity(openLoginActivity);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
