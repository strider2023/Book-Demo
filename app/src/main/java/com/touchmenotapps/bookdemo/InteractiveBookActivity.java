package com.touchmenotapps.bookdemo;

import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchmenotapps.waveview.WaveHelper;
import com.touchmenotapps.waveview.WaveView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InteractiveBookActivity extends AppCompatActivity {

    @BindView(R.id.interactive_water)
    WaveView waveView;
    @BindView(R.id.interactive_boat)
    ImageView boatImage;
    @BindView(R.id.interactive_text)
    TextView rhymeText;
    @BindView(R.id.cloud_image_1)
    ImageView cloudImage1;
    @BindView(R.id.cloud_image_2)
    ImageView cloudImage2;
    @BindView(R.id.cloud_image_3)
    ImageView cloudImage3;
    @BindView(R.id.cloud_image_4)
    ImageView cloudImage4;
    @BindView(R.id.interactive_fish)
    ImageView fishImage;

    private WaveHelper waveHelper;
    private Animation animFast, animSlow, animVerySlow;
    private String[] rhyme;
    private int counter = 0;

    private TextToSpeech textToSpeech;
    private boolean isInit;
    private boolean isAnimating = false;

    private Animation moveUp, moveDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive_book);
        ButterKnife.bind(this);

        animFast = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_anim);
        animSlow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_anim_slow);
        animVerySlow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.float_anim_very_slow);

        waveHelper = new WaveHelper(waveView);
        waveView.setWaveColor(Color.parseColor("#88b8f1ed"), Color.parseColor("#b8f1ed"));
        fishAnimation();
        animateFloatingClouds();
        boatImage.startAnimation(animSlow);

        rhyme = getResources().getStringArray(R.array.rhyme);

        waveView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        waveHelper.startHorizontalAnimation();
                        /*animateMovingClouds(cloudImage1, 100000l);
                        animateMovingClouds(cloudImage2, 500000l);
                        animateMovingClouds(cloudImage3, 800000l);
                        animateMovingClouds(cloudImage4, 400000l);*/
                        if(isInit) {
                            if(textToSpeech.isSpeaking()) {
                                textToSpeech.stop();
                            }
                            textToSpeech.speak(rhyme[counter], TextToSpeech.QUEUE_FLUSH, null, rhyme[counter]);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        waveHelper.stopHorizontalAnimation();
                        //animateFloatingClouds();
                        if(isInit) {
                            textToSpeech.stop();
                        }
                        break;
                }
                return true;
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    isInit = true;
                }
            }
        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rhymeText.setText(rhyme[counter]);
                    }
                });
            }

            @Override
            public void onDone(String utteranceId) {
                if(counter < (rhyme.length - 1)) {
                    counter++;
                } else {
                    counter = 0;
                }
                if(counter == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isAnimating = false;
                            fishImage.clearAnimation();
                            fishImage.setVisibility(View.GONE);
                        }
                    });
                }
                if(counter == 5) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fishImage.setVisibility(View.VISIBLE);
                            fishImage.startAnimation(moveUp);
                            isAnimating = true;
                        }
                    });
                }
                textToSpeech.speak(rhyme[counter], TextToSpeech.QUEUE_FLUSH, null, rhyme[counter]);
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        waveHelper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        waveHelper.cancel();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private void animateMovingClouds(ImageView imageView, Long time) {
        Animation mAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, 1f,
                TranslateAnimation.RELATIVE_TO_PARENT, -1f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f);
        mAnimation.setDuration(time);
        mAnimation.setRepeatCount(-1);
        mAnimation.setFillAfter(true);
        mAnimation.setRepeatMode(Animation.INFINITE);
        mAnimation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(mAnimation);
    }

    private void fishAnimation() {
        moveUp = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, -3.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.5f,
                TranslateAnimation.RELATIVE_TO_SELF, -1f);
        moveUp.setDuration(1000);
        moveUp.setInterpolator(new LinearInterpolator());

        moveDown = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, -3.0f,
                TranslateAnimation.RELATIVE_TO_SELF, -6.0f,
                TranslateAnimation.RELATIVE_TO_SELF, -1f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.5f);
        moveDown.setDuration(1000);
        moveDown.setInterpolator(new LinearInterpolator());

        moveUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isAnimating)
                    fishImage.startAnimation(moveDown);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        moveDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isAnimating)
                    fishImage.startAnimation(moveUp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void animateFloatingClouds() {
        cloudImage1.startAnimation(animVerySlow);
        cloudImage2.startAnimation(animSlow);
        cloudImage3.startAnimation(animFast);
        cloudImage4.startAnimation(animVerySlow);
    }
}
