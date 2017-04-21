package com.touchmenotapps.bookdemo.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.touchmenotapps.bookdemo.R;
import com.touchmenotapps.bookdemo.dao.PageContentDAO;
import com.touchmenotapps.bookdemo.dao.interfaces.OnPlayAudioListener;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by i7 on 06-04-2017.
 */

public class PageFlipAdapter extends BaseAdapter {

    private List<PageContentDAO> pageContentDAOs = new ArrayList<>();
    private LayoutInflater inflater;
    private boolean isLandscape;
    private OnPlayAudioListener onPlayAudioListener;
    private Context context;

    public PageFlipAdapter(Context context, boolean isLandscape, OnPlayAudioListener onPlayAudioListener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.isLandscape = isLandscape;
        this.onPlayAudioListener = onPlayAudioListener;
    }

    public void setPageContent(List<PageContentDAO> pageContentDAOs) {
        this.pageContentDAOs = pageContentDAOs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(isLandscape) {
            return pageContentDAOs.size()/2;
        } else {
            return pageContentDAOs.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(isLandscape) {
            return new PageContentDAO[] {pageContentDAOs.get(position*2), pageContentDAOs.get((position*2) + 1)};
        } else {
            return pageContentDAOs.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MobileViewHolder mobileViewHolder;
        final int page = isLandscape ? (position * 2) : position;

        if(convertView == null){
            mobileViewHolder = new MobileViewHolder();
            convertView = inflater.inflate(R.layout.adapter_page, parent, false);
            mobileViewHolder.subtitleTextLeft = (TextView) convertView.findViewById(R.id.left_page_text_container);
            mobileViewHolder.subtitleTextRight = (TextView) convertView.findViewById(R.id.right_page_text_container);
            mobileViewHolder.graphicViewLeft = (LottieAnimationView) convertView.findViewById(R.id.left_page_animation_view);
            mobileViewHolder.graphicViewRight = (LottieAnimationView) convertView.findViewById(R.id.right_page_animation_view);
            mobileViewHolder.playAudioLeft = (FloatingActionButton) convertView.findViewById(R.id.left_page_read_text_btn);
            mobileViewHolder.playAudioRight = (FloatingActionButton) convertView.findViewById(R.id.right_page_read_text_btn);

            if(!isLandscape) {
                convertView.findViewById(R.id.right_page_container).setVisibility(View.GONE);
            }
            convertView.setTag(mobileViewHolder);
        } else {
            mobileViewHolder = (MobileViewHolder) convertView.getTag();
        }

        mobileViewHolder.graphicViewLeft.setAnimation(pageContentDAOs.get(page).getImageName());
        setText(mobileViewHolder.subtitleTextLeft, pageContentDAOs.get(page).getSubtitleText());

        mobileViewHolder.graphicViewLeft.cancelAnimation();
        mobileViewHolder.graphicViewLeft.setProgress(0f);
        mobileViewHolder.graphicViewLeft.loop(true);
        mobileViewHolder.graphicViewLeft.playAnimation();

        mobileViewHolder.playAudioLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onPlayAudioListener != null) {
                    onPlayAudioListener.onPlayButtonSelected(pageContentDAOs.get(page).getSubtitleText());
                }
            }
        });

        mobileViewHolder.graphicViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(isLandscape) {
            mobileViewHolder.graphicViewRight.setAnimation(pageContentDAOs.get(page+1).getImageName());
            setText(mobileViewHolder.subtitleTextRight, pageContentDAOs.get(page+1).getSubtitleText());

            mobileViewHolder.graphicViewRight.cancelAnimation();
            mobileViewHolder.graphicViewRight.setProgress(0f);
            mobileViewHolder.graphicViewRight.loop(true);
            mobileViewHolder.graphicViewRight.playAnimation();

            mobileViewHolder.subtitleTextRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onPlayAudioListener != null) {
                        onPlayAudioListener.onPlayButtonSelected(pageContentDAOs.get(page+1).getSubtitleText());
                    }
                }
            });
        }

        return convertView;
    }

    private void setText(TextView textView, String text) {
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(text, TextView.BufferType.SPANNABLE);
        Spannable spans = (Spannable) textView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String possibleWord = text.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
                ClickableSpan clickSpan = getClickableSpan(possibleWord);
                spans.setSpan(clickSpan, start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.d("tapped on:", word);
                if(onPlayAudioListener != null) {
                    onPlayAudioListener.onPlayButtonSelected(word);
                }
            }

            public void updateDrawState(TextPaint ds) {
                //super.updateDrawState(ds);
            }
        };
    }

    static class MobileViewHolder{
        LottieAnimationView graphicViewLeft, graphicViewRight;
        TextView subtitleTextLeft, subtitleTextRight;
        FloatingActionButton playAudioLeft, playAudioRight;
    }
}
