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
 * Created by i7 on 20-04-2017.
 */

public class FeeDuesFragment extends Fragment {

    public FeeDuesAdapter adapter;
    private LinearLayoutManager layoutManager;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public FeeDuesFragment() { }

    public static FeeDuesFragment newInstance() {
        FeeDuesFragment fragment = new FeeDuesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fees, container, false);
        ButterKnife.bind(this, rootView);

        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new FeeDuesAdapter(makeFeeDetails());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
