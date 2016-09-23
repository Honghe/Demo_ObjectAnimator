package top.honhe.demo.objectanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    private View textView1;
    private ViewGroup layout;
    private View textView2;
    private String TAG = MainActivity.class.getName();
    private ObjectAnimator animator2;
    private ObjectAnimator animator;
    int duration = 5000;
    private int layoutLeft;
    private int layoutWidth;
    boolean trigger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoAnimate();
            }
        });

        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        layout = (ViewGroup) findViewById(R.id.content_main);
    }

    protected void autoAnimate() {
        layoutWidth = layout.getWidth();
        layoutLeft = layout.getLeft();
        textView2.setTranslationX(-textView2.getWidth());
        animate(-textView1.getWidth());
    }

    private void animate(int startPosition) {
        textView1.setTranslationX(startPosition);
        float curTranslationX = textView1.getTranslationX();
        animator = ObjectAnimator.ofFloat(textView1, "translationX", curTranslationX,
                layoutWidth);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
                if ((float) animation.getAnimatedValue() >= 0) {
                    if (!trigger) {
                        trigger = true;
                        animate2();
                    }
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                trigger = false;
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
    }

    private void animate2() {
        textView2.setTranslationX(-textView2.getWidth());
        float curTranslationX2 = textView2.getTranslationX();
        animator2 = ObjectAnimator.ofFloat(textView2, "translationX", curTranslationX2,
                layoutWidth);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate2: " + animation.getAnimatedValue());
            }
        });
        animator2.setDuration(duration);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        animator.end();
        animator2.end();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
