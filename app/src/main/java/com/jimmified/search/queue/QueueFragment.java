package com.jimmified.search.queue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.jimmified.search.R;
import com.jimmified.search.JimmifyApplication;
import com.jimmified.search.settings.SaveSharedPreference;
import com.jimmified.search.request.BasicCallback;
import com.jimmified.search.request.model.AnswerModel;
import com.jimmified.search.request.model.QueryModel;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;

public class QueueFragment extends QueryListFragment implements View.OnClickListener {

    private static final String TAG = "QueueFragment";

    public enum AnswerViewType {
        MATERIAL_DIALOG,
        EXPANDABLE_CARD;

        private static final AnswerViewType DEFAULT = MATERIAL_DIALOG;

        public static AnswerViewType decodeFromString(String type) {
            switch (type) {
                case "Material Dialog":
                case "0":
                    return MATERIAL_DIALOG;
                case "Expandable Card":
                case "1":
                    return EXPANDABLE_CARD;
                default:
                    return DEFAULT;
            }
        }
    }

    private Call<AnswerModel> answerCall = null;

    private void answer(final QueryModel queryModel, String answer, String link) {
        Log.i(TAG, "Question: " + queryModel.getText());
        Log.i(TAG, "Answer: " + (answer == null ? "" : answer));
        if (answerCall == null) {
            AnswerModel answerModel = new AnswerModel(queryModel.getKey(), answer == null ? "" : answer,
                    Arrays.asList(link == null ? "" : link), SaveSharedPreference.getToken());

            answerCall = JimmifyApplication.getJimmifyAPI().attemptAnswer(answerModel);
            answerCall.enqueue(new AnswerCallback(queryModel));
        }
    }

    public static QueueFragment newInstance() {
        QueueFragment queueFragment = new QueueFragment();
        Bundle bundle = new Bundle();

        queueFragment.setArguments(bundle);
        queueFragment.mOnClickListener = queueFragment;

        return queueFragment;
    }

    @Override
    public void getQueryList() {
        if (queryListCall == null) {
            queryListCall = JimmifyApplication.getJimmifyAPI().attemptGetQueue();
            queryListCall.enqueue(new QueryListCallback());
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.queryRootLayout:
                buildAnswerView(view);
                break;
            case R.id.queryAnswerButton:
                QueryModel qm = queryAdapter.getQuery((int) view.getTag());

                answer(qm, qm.getAnswer(), qm.getLink());
                break;
            default:
                break;
        }
    }

    public void buildAnswerView(View view) {
        int itemPosition = mQueryListRecyclerView.getChildLayoutPosition(view);
        final QueryModel qm = queryList.get(itemPosition);

        switch (SaveSharedPreference.getAnswerViewType()) {
            case MATERIAL_DIALOG:
                buildMaterialDialog(qm).show();
                break;
            case EXPANDABLE_CARD:
                queryAdapter.openCard(itemPosition);
                break;
            default:
                break;
        }
    }

    public MaterialDialog.Builder buildMaterialDialog(final QueryModel queryModel) {
        return new MaterialDialog.Builder(getActivity())
                .title(queryModel.getText())
                .customView(R.layout.query_answer, true)
                .positiveText("Answer")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                                        @NonNull DialogAction which) {
                        String answerText = ((EditText) dialog.findViewById(R.id.queryCustomAnswer)).getText().toString();
                        String linkText = ((EditText) dialog.findViewById(R.id.queryLinkField)).getText().toString();
                        answer(queryModel, answerText, linkText);
                    }
                });
    }

    private class AnswerCallback extends BasicCallback<AnswerModel> {

        private QueryModel queryModel;

        AnswerCallback(QueryModel queryModel) {
            this.queryModel = queryModel;
        }

        @Override
        public void handleSuccess(AnswerModel responseModel) {
            queryList.remove(queryModel);
        }

        @Override
        public void handleConnectionError() {
            JimmifyApplication.showServerConnectionToast();
        }

        @Override
        public void handleStatusError(int responseCode) {
        }

        @Override
        public void handleCommonError() {
        }

        @Override
        public void onFinish() {
            queryAdapter.notifyDataSetChanged();
            answerCall = null;
        }
    }
}
