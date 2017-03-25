package com.jimmified.search.queue;

import android.os.Bundle;

import com.jimmified.search.JimmifyApplication;

public class RecentFragment extends QueryListFragment {

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

        return recentFragment;
    }
}
