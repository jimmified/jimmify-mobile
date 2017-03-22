package com.jimmified.search.queue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jimmified.search.JimmifyApplication;

public class RecentFragment extends QueryListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public void getQueryList() {
        if (queryListCall == null) {
            queryListCall = JimmifyApplication.getJimmifyAPI().attemptGetRecent();
            queryListCall.enqueue(new QueryListCallback());
        }
    }

    public static RecentFragment newInstance() {
        RecentFragment recentFragment = new RecentFragment();
        Bundle bundle = new Bundle();

        recentFragment.setArguments(bundle);
        recentFragment.mOnClickListener = null;
        recentFragment.type = Type.RECENT;

        return recentFragment;
    }
}
