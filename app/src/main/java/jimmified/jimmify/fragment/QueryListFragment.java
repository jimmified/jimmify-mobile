package jimmified.jimmify.fragment;

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
import jimmified.jimmify.R;
import jimmified.jimmify.application.JimmifyApplication;
import jimmified.jimmify.request.BasicCallback;
import jimmified.jimmify.request.adapter.QueryAdapter;
import jimmified.jimmify.request.model.QueryListModel;
import jimmified.jimmify.request.model.QueryModel;
import retrofit2.Call;

public abstract class QueryListFragment extends Fragment {

    public enum Type {
        QUEUE,
        RECENT
    }
    protected Type type = Type.RECENT;

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
        if (type == Type.RECENT)
            queryAdapter = new QueryAdapter(this.getActivity(), queryList, mQueryListRecyclerView);
        else if (type == Type.QUEUE)
            queryAdapter = new QueryAdapter(this.getActivity(), queryList, mQueryListRecyclerView, mOnClickListener);

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

    @Override
    public void onResume() {
        super.onResume();

        getQueryList();
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
                    if (responseModel.getStatus()) {
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
