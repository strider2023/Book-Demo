package com.touchmenotapps.bookdemo;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.touchmenotapps.bookdemo.adapter.GameAdapter;
import com.touchmenotapps.bookdemo.dao.QuestionDAO;
import com.touchmenotapps.bookdemo.dao.interfaces.OnQuestionSelected;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KinderActivity extends AppCompatActivity implements OnQuestionSelected {

    @BindView(R.id.game_container)
    LinearLayout gameContainer;
    @BindView(R.id.select_view)
    LinearLayout selectView;
    @BindView(R.id.level_name)
    TextView levelName;
    @BindView(R.id.script_text)
    TextView scriptText;
    @BindView(R.id.object_container)
    RecyclerView recyclerView;
    @BindView(R.id.congrats_image)
    ImageView congatsImage;
    @BindView(R.id.score_counter)
    TextView scoreCounter;

    private int levelCount = 1, correctAnswerCount = 0;
    private boolean isPlaying = false;
    private Handler levelLoaderHandler = new Handler();
    private Runnable levelChanger = new Runnable() {
        @Override
        public void run() {
            //Start Game as per level
            correctAnswerCount = 0;
            scoreCounter.setText(String.valueOf(correctAnswerCount));
            levelName.setVisibility(View.GONE);
            gameContainer.setVisibility(View.VISIBLE);
            createLevel(levelCount);
        }
    };

    private List<QuestionDAO> questionDAOList = new ArrayList<>();
    private GameAdapter gameAdapter = new GameAdapter(this);
    private GridLayoutManager gridLayoutManager;
    private TextToSpeech textToSpeech;
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                scriptText.setVisibility(View.GONE);
                selectView.setVisibility(View.VISIBLE);
            }
        }, 2500);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    isInit = true;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @OnClick(R.id.show_me)
    void showMe() {
        selectView.setVisibility(View.GONE);
        gameContainer.setVisibility(View.VISIBLE);
        isPlaying = true;
    }

    @OnClick(R.id.play)
    void playGame() {
        isPlaying = true;
        changeLevel();
    }

    private void changeLevel() {
        if(levelCount <= 3) {
            //Show the level text
            selectView.setVisibility(View.GONE);
            gameContainer.setVisibility(View.GONE);
            levelName.setVisibility(View.VISIBLE);
            levelName.setText("Level " + String.valueOf(levelCount));
            levelLoaderHandler.postDelayed(levelChanger, 2500);
        } else {
            levelCount = 1;
            correctAnswerCount = 0;
            selectView.setVisibility(View.VISIBLE);
            gameContainer.setVisibility(View.GONE);
            isPlaying = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (isPlaying) {
            levelCount = 1;
            correctAnswerCount = 0;
            levelLoaderHandler.removeCallbacks(levelChanger);
            selectView.setVisibility(View.VISIBLE);
            gameContainer.setVisibility(View.GONE);
            levelName.setVisibility(View.GONE);
            isPlaying = false;
        } else {
            super.onBackPressed();
        }
    }

    private void createLevel(int complexity) {
        questionDAOList.clear();
        switch (complexity) {
            case 1:
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_red, true));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_green));
                gridLayoutManager = new GridLayoutManager(this, 2);
                break;
            case 2:
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_red, true));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_green));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_red, true));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_green));
                gridLayoutManager = new GridLayoutManager(this, 2);
                break;
            case 3:
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_red, true));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_green));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_red, true));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_green));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_red, true));
                questionDAOList.add(new QuestionDAO(R.drawable.shape_oval_green));
                gridLayoutManager = new GridLayoutManager(this, 2);
                break;
        }
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(gameAdapter);
        gameAdapter.setQuestionDAOList(questionDAOList);
        if(isInit) {
            if(textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
            textToSpeech.speak("Select the red colored items", TextToSpeech.QUEUE_FLUSH, null, "Red");
        }
    }

    @Override
    public void onRightAnswer() {
        if(isInit) {
            if(textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
            textToSpeech.speak("Correct", TextToSpeech.QUEUE_FLUSH, null, "Correct");
        }
        correctAnswerCount++;
        scoreCounter.setText(String.valueOf(correctAnswerCount));
        switch (levelCount) {
            case 1:
                if(correctAnswerCount == 1) {
                    showLevelComplete();
                }
                break;
            case 2:
                if(correctAnswerCount == 2) {
                    showLevelComplete();
                }
                break;
            case 3:
                if(correctAnswerCount == 3) {
                    showLevelComplete();
                }
                break;
        }
    }

    @Override
    public void onWrongAnswer() {
        if(isInit) {
            if(textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
            textToSpeech.speak("Wrong", TextToSpeech.QUEUE_FLUSH, null, "Wrong");
        }
    }

    private void showLevelComplete() {
        if(isInit) {
            if(textToSpeech.isSpeaking()) {
                textToSpeech.stop();
            }
            textToSpeech.speak("Good Job", TextToSpeech.QUEUE_FLUSH, null, "Good Job");
        }
        congatsImage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                congatsImage.setVisibility(View.GONE);
                levelCount++;
                changeLevel();
            }
        }, 2500);
    }
}
