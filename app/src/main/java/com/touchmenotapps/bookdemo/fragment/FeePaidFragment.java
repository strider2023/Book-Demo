package com.touchmenotapps.bookdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.touchmenotapps.bookdemo.R;
import com.touchmenotapps.bookdemo.adapter.FeeDuesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.touchmenotapps.bookdemo.dao.FeeDataFactory.makeFeeDetails;

/**
 * Created by i7 on 21-04-2017.
 */

public class FeePaidFragment extends Fragment {

    public FeeDuesAdapter adapter;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public FeePaidFragment() { }

    public static FeePaidFragment newInstance() {
        FeePaidFragment fragment = new FeePaidFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fees, container, false);
        ButterKnife.bind(this, rootView);
        layoutManager = new LinearLayoutManager(getActivity());

        // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
        // Specifically when you call notifyItemChanged() it does a fade animation for the changing
        // of the data in the ViewHolder. If you would like to disable this you can use the following:
        /*RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }*/

        adapter = new FeeDuesAdapter(makeFeeDetails());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
