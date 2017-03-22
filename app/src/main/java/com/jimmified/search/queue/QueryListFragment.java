package com.jimmified.search.queue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jimmified.search.R;
import com.jimmified.search.main.MainActivity;
import com.jimmified.search.JimmifyApplication;
import com.jimmified.search.request.BasicCallback;
import com.jimmified.search.request.adapter.QueryAdapter;
import com.jimmified.search.request.model.QueryListModel;
import com.jimmified.search.request.model.QueryModel;
import retrofit2.Call;

public abstract class QueryListFragment extends Fragment {

    public enum Type {
        QUEUE,
        RECENT
    }
    protected Type type = Type.RECENT;
    private final String TEST_TYPE = "test";

    protected Call<QueryListModel> queryListCall = null;
    final QueryList queryList = new QueryList();
    View.OnClickListener mOnClickListener = null;
    QueryAdapter queryAdapter;

    @BindView(R.id.queryListSwipeToRefresh)
    SwipeRefreshLayout mQueryListRefreshLayout;
    @BindView(R.id.queryListRecyclerView)
    RecyclerView mQueryListRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_query_list, container, false);
        ButterKnife.bind(this, view);

        // Initialize contacts
        if (type == Type.QUEUE)
            queryAdapter = new QueryAdapter(this.getActivity(), queryList, mOnClickListener);
        else
            queryAdapter = new QueryAdapter(this.getActivity(), queryList);

        mQueryListRecyclerView.setHasFixedSize(true);
        mQueryListRecyclerView.setAdapter(queryAdapter);
        mQueryListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mQueryListRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQueryList();
            }
        });

        return view;
    }

    public void useTestQueries(int numTests) {
        clearTestQueries();
        createTests(numTests);
    }

    public void clearTestQueries() {
        int i = 0;
        while (i < queryList.size()) {
            if (queryList.get(i).getType().equals(TEST_TYPE))
                queryList.remove(i);
            else
                i++;
        }
        if (queryAdapter != null)
            queryAdapter.notifyDataSetChanged();
    }

    public void createTests(int numTests) {
        for (int i = 0; i < numTests; i++)
            queryList.add(new QueryModel(i, TEST_TYPE, "Test #" + String.valueOf(i)));
        if (queryAdapter != null)
            queryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        getQueryList();
    }

    public void closeCards() {
        if (queryAdapter != null)
            queryAdapter.openCard(-1);
    }

    public void getQueryList() {
        if (queryListCall == null) {
            if (type == Type.QUEUE)
                queryListCall = JimmifyApplication.getJimmifyAPI().attemptGetQueue();
            else if (type == Type.RECENT)
                queryListCall = JimmifyApplication.getJimmifyAPI().attemptGetRecent();

            queryListCall.enqueue(new BasicCallback<QueryListModel>() {
                @Override
                public void handleSuccess(QueryListModel responseModel) {
                    if (responseModel.isStatus()) {
                        QueryModel[] queryModels = responseModel.getQueryList();
                        if (queryModels != null) {
                            for (QueryModel qm : queryModels) {
                                if (!queryList.contains(qm))
                                    queryList.add(qm);
                            }
                            queryAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void handleConnectionError() {
                    JimmifyApplication.showServerConnectionToast();
                }

                @Override
                public void handleStatusError(int responseCode) {
                    ((MainActivity) QueryListFragment.this.getActivity()).onLogout();
                }

                @Override
                public void onFinish() {
                    mQueryListRefreshLayout.setRefreshing(false);
                    queryListCall = null;
                }
            });
        }
    }

    protected class QueryList extends ArrayList<QueryModel> {
        @Override
        public boolean contains(Object o) {
            try {
                int isContainedKey = ((QueryModel) o).getKey();
                for (QueryModel qm : this) {
                    if (qm.getKey() == isContainedKey)
                        return true;
                }
            } catch (ClassCastException e) {
                Log.e("QueryList", "QueryList does not contain non-QueryModel objects.");
            } return false;
        }
    }

}
