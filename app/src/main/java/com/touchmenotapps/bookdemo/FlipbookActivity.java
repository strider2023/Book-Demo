package com.touchmenotapps.bookdemo;

import android.content.res.Configuration;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.touchmenotapps.bookdemo.adapter.PageFlipAdapter;
import com.touchmenotapps.bookdemo.dao.PageContentDAO;
import com.touchmenotapps.bookdemo.dao.interfaces.OnPlayAudioListener;
import com.touchmenotapps.flipview.FlipView;
import com.touchmenotapps.flipview.FlipView.OnFlipListener;
import com.touchmenotapps.flipview.FlipView.OnOverFlipListener;
import com.touchmenotapps.flipview.OverFlipMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlipbookActivity extends AppCompatActivity implements OnFlipListener, OnOverFlipListener, OnPlayAudioListener {

    @BindView(R.id.flip_view) FlipView flipView;

    private TextToSpeech textToSpeech;
    private PageFlipAdapter pageFlipAdapter;
    private List<PageContentDAO> pageContentDAOList = new ArrayList<>();
    private boolean isLandscape = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        PageContentDAO pageContentDAO = new PageContentDAO();
        pageContentDAO.setImageName("PinJump.json");
        pageContentDAO.setSubtitleText("This is the jumping pin! Also some very long text to check if the layout is able to show the complete text.");

        PageContentDAO pageContentDAO1 = new PageContentDAO();
        pageContentDAO1.setImageName("SpiderLoader.json");
        pageContentDAO1.setSubtitleText("Oh look at the spider!");

        PageContentDAO pageContentDAO2 = new PageContentDAO();
        pageContentDAO2.setImageName("TwitterHeart.json");
        pageContentDAO2.setSubtitleText("Human heart is it?");

        isLandscape = getIntent().getBooleanExtra("doubleMode", false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            isLandscape = false;
        }
        else{
            isLandscape = true;
        }

        pageFlipAdapter = new PageFlipAdapter(this, isLandscape, this);
        pageContentDAOList.add(pageContentDAO2);
        pageContentDAOList.add(pageContentDAO1);
        pageContentDAOList.add(pageContentDAO);
        pageContentDAOList.add(pageContentDAO2);
        pageContentDAOList.add(pageContentDAO1);
        pageContentDAOList.add(pageContentDAO);

        pageFlipAdapter.setPageContent(pageContentDAOList);

        flipView.setAdapter(pageFlipAdapter);
        flipView.setOnFlipListener(this);
        flipView.peakNext(false);
        flipView.setOnOverFlipListener(this);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        playPageAnimation(position);
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode, boolean overFlippingPrevious, float overFlipDistance, float flipDistancePerPage) {

    }

    private void playPageAnimation(int position) {
        View rootView = flipView.getChildAt(position);
        if(rootView != null) {

            final LottieAnimationView lottieAnimationView = (LottieAnimationView) rootView.findViewById(R.id.left_page_animation_view);
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setProgress(0f);
            lottieAnimationView.loop(true);
            lottieAnimationView.playAnimation();

            if(isLandscape) {
                final LottieAnimationView lottieAnimationView1 = (LottieAnimationView) rootView.findViewById(R.id.right_page_animation_view);
                lottieAnimationView1.cancelAnimation();
                lottieAnimationView1.setProgress(0f);
                lottieAnimationView1.loop(true);
                lottieAnimationView1.playAnimation();
            }
        }
    }

    @Override
    public void onPlayButtonSelected(String sentence) {
        if(textToSpeech.isSpeaking()) {
            textToSpeech.stop();
        }
        textToSpeech.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, sentence);
    }

    @Override
    public void onPlayTextSelected(String text) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
