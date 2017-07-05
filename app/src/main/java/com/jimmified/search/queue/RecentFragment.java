package com.jimmified.search.queue;

import android.os.Bundle;
import android.view.View;

import com.jimmified.search.JimmifyApplication;

public class RecentFragment extends QueryListFragment implements View.OnClickListener {

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
        recentFragment.mOnClickListener = recentFragment;

        return recentFragment;
    }

    @Override
    public void onClick(View view) {
        int itemPosition = mQueryListRecyclerView.getChildLayoutPosition(view);
        queryAdapter.openCard(itemPosition);
    }
}
