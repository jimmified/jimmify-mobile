package jimmified.jimmify.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jimmified.jimmify.R;
import jimmified.jimmify.application.JimmifyApplication;
import jimmified.jimmify.request.BasicCallback;
import jimmified.jimmify.request.adapter.QueryAdapter;
import jimmified.jimmify.request.model.AnswerModel;
import jimmified.jimmify.request.model.QueryModel;
import jimmified.jimmify.request.model.RecentModel;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;

public class RecentFragment extends Fragment {

    final List<QueryModel> recent = new ArrayList<>();
    QueryAdapter queryAdapter;

    @BindView(R.id.recentSwipeToRefresh)
    SwipeRefreshLayout mRecentRefreshLayout;
    @BindView(R.id.recentRecyclerView)
    RecyclerView mRecentView;
    private Call<RecentModel> recentCall = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        ButterKnife.bind(this, view);

        // Initialize contacts
        queryAdapter = new QueryAdapter(this.getActivity(), recent);

        mRecentView.setHasFixedSize(true);
        mRecentView.setAdapter(queryAdapter);
        mRecentView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mRecentRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecent();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getRecent();
    }

    public void getRecent() {
        if (recentCall == null) {
            recentCall = JimmifyApplication.getJimmifyAPI().attemptGetRecent();
            recentCall.enqueue(new BasicCallback<RecentModel>() {
                @Override
                public void handleSuccess(RecentModel responseModel) {
                    if (responseModel.getStatus()) {
                        QueryModel[] queryModels = responseModel.getRecent();
                        if (queryModels != null) {
                            for (QueryModel qm : queryModels) {
                                if (!recent.contains(qm))
                                    recent.add(qm);
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
                    mRecentRefreshLayout.setRefreshing(false);
                    recentCall = null;
                }
            });
        }
    }

    public static RecentFragment newInstance() {
        RecentFragment recentFragment = new RecentFragment();
        Bundle bundle = new Bundle();

        recentFragment.setArguments(bundle);

        return recentFragment;
    }

}
