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

import com.afollestad.materialdialogs.MaterialDialog;
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
import jimmified.jimmify.request.model.QueueModel;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;

public class QueueFragment extends Fragment {

    final QueryQueue queue = new QueryQueue();
    QueryAdapter queryAdapter;

    @BindView(R.id.queueSwipeToRefresh)
    SwipeRefreshLayout mQueueRefreshLayout;
    @BindView(R.id.queueRecyclerView)
    RecyclerView mQueueRecyclerView;
    private Call<QueueModel> queueCall = null;
    private Call<AnswerModel> answerCall = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queue, container, false);
        ButterKnife.bind(this, view);

        // Initialize contacts
        queryAdapter = new QueryAdapter(this.getActivity(), queue, new QueueOnClickListener());

        if (mQueueRecyclerView == null)
            mQueueRecyclerView = (RecyclerView) view.findViewById(R.id.queueRecyclerView);
        mQueueRecyclerView.setHasFixedSize(true);
        mQueueRecyclerView.setAdapter(queryAdapter);
        mQueueRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mQueueRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQueue();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getQueue();
    }

    private void answer(final QueryModel qm) {
        Log.i("JEREMIAH", "YO");
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(String.valueOf(qm.getKey()))
                .content(qm.getText())
                .positiveText("Fuck yeah!")
                .negativeText("Fuck no!")
                .show();

        if (answerCall == null) {
            AnswerModel answerModel = new AnswerModel(qm.getKey(), "Answered.");

            answerCall = JimmifyApplication.getJimmifyAPI().attemptAnswer(answerModel);
            answerCall.enqueue(new BasicCallback<AnswerModel>() {
                @Override
                public void handleSuccess(AnswerModel responseModel) {
                    queue.remove(qm);
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
                    queryAdapter.notifyDataSetChanged();
                    queueCall = null;
                }
            });
        }
    }

    public void getQueue() {
        if (queueCall == null) {
            queueCall = JimmifyApplication.getJimmifyAPI().attemptGetQueue();
            queueCall.enqueue(new BasicCallback<QueueModel>() {
                @Override
                public void handleSuccess(QueueModel responseModel) {
                    if (responseModel.getStatus()) {
                        QueryModel[] queryModels = responseModel.getQueue();
                        if (queryModels != null) {
                            for (QueryModel qm : queryModels) {
                                if (!queue.contains(qm))
                                    queue.add(qm);
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
                    mQueueRefreshLayout.setRefreshing(false);
                    queueCall = null;
                }
            });
        }
    }

    public static QueueFragment newInstance() {
        QueueFragment queueFragment = new QueueFragment();
        Bundle bundle = new Bundle();

        queueFragment.setArguments(bundle);

        return queueFragment;
    }

    private class QueryQueue extends ArrayList<QueryModel> {
        @Override
        public boolean contains(Object o) {
            try {
                int isContainedKey = ((QueryModel) o).getKey();
                for (QueryModel qm : this) {
                    if (qm.getKey() == isContainedKey)
                        return true;
                }
            } catch (ClassCastException e) {
                Log.e("QueryQueue", "QueryQueue does not contain non-QueryModel objects.");
            } return false;
        }
    }

    private class QueueOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int itemPosition = mQueueRecyclerView.getChildLayoutPosition(view);
            QueryModel qm = queue.get(itemPosition);
            answer(qm);
        }
    }
}
