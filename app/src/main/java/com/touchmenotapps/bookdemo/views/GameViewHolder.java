package com.touchmenotapps.bookdemo.views;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.touchmenotapps.bookdemo.R;
import com.touchmenotapps.bookdemo.dao.QuestionDAO;
import com.touchmenotapps.bookdemo.dao.interfaces.OnQuestionSelected;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by i7 on 31-05-2017.
 */

public class GameViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.card_grid_image)
    ImageView cardImg;
    @BindView(R.id.answer_type)
    ImageView answerType;

    private QuestionDAO questionDAO;
    private OnQuestionSelected onQuestionSelected;

    public GameViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setOnQuestionSelected(OnQuestionSelected onQuestionSelected) {
        this.onQuestionSelected = onQuestionSelected;
    }

    @OnClick(R.id.base_card_container)
    void onOptionSelected() {
        answerType.setVisibility(View.VISIBLE);
        if(questionDAO.isCorrect()) {
            if(onQuestionSelected != null) {
                onQuestionSelected.onRightAnswer();
            }
            answerType.setImageResource(R.drawable.ic_check_black_24dp);
        } else {
            if(onQuestionSelected != null) {
                onQuestionSelected.onWrongAnswer();
            }
            answerType.setImageResource(R.drawable.ic_cancel_black_24dp);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    answerType.setVisibility(View.GONE);
                }
            }, 2500);
        }
    }

    public void setData(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
        cardImg.setImageResource(questionDAO.getImage());
    }
}
