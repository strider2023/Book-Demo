package com.touchmenotapps.bookdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touchmenotapps.bookdemo.R;
import com.touchmenotapps.bookdemo.dao.QuestionDAO;
import com.touchmenotapps.bookdemo.dao.interfaces.OnQuestionSelected;
import com.touchmenotapps.bookdemo.views.GameViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i7 on 31-05-2017.
 */

public class GameAdapter extends RecyclerView.Adapter<GameViewHolder> {

    private List<QuestionDAO> questionDAOList = new ArrayList<>();
    private OnQuestionSelected onQuestionSelected;

    public GameAdapter(OnQuestionSelected onQuestionSelected) {
        this.onQuestionSelected = onQuestionSelected;
    }

    public void setQuestionDAOList(List<QuestionDAO> questionDAOList) {
        this.questionDAOList = questionDAOList;
        notifyDataSetChanged();
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_grid_item,
                parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        holder.setOnQuestionSelected(onQuestionSelected);
        holder.setData(questionDAOList.get(position));
    }

    @Override
    public int getItemCount() {
        return questionDAOList.size();
    }
}
