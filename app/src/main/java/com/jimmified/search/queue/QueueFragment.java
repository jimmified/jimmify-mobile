package com.jimmified.search.queue;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.jimmified.search.R;
import com.jimmified.search.JimmifyApplication;
import com.jimmified.search.settings.SaveSharedPreference;
import com.jimmified.search.request.BasicCallback;
import com.jimmified.search.request.model.AnswerModel;
import com.jimmified.search.request.model.QueryModel;
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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void answer(final QueryModel queryModel, String answer) {
        Log.i(TAG, "Question: " + queryModel.getText());
        Log.i(TAG, "Answer: " + (answer == null ? "" : answer));
        if (answerCall == null) {
            AnswerModel answerModel = new AnswerModel(queryModel.getKey(), answer == null ? "" : answer, SaveSharedPreference.getToken());

            answerCall = JimmifyApplication.getJimmifyAPI().attemptAnswer(answerModel);
            answerCall.enqueue(new AnswerCallback(queryModel));
        }
    }

    public static QueueFragment newInstance() {
        QueueFragment queueFragment = new QueueFragment();
        Bundle bundle = new Bundle();

        queueFragment.setArguments(bundle);
        queueFragment.mOnClickListener = queueFragment;
        queueFragment.type = Type.QUEUE;

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
            case R.id.queryRootLayout: {
                int itemPosition = mQueryListRecyclerView.getChildLayoutPosition(view);
                final QueryModel qm = queryList.get(itemPosition);

                switch (SaveSharedPreference.getAnswerViewType()) {
                    case MATERIAL_DIALOG:
                        new MaterialDialog.Builder(getActivity())
                                .title(qm.getText())
                                .customView(R.layout.query_answer, true)
                                .positiveText("Answer")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog,
                                                        @NonNull DialogAction which) {
                                        String answerText = ((EditText) dialog.findViewById(R.id.queryCustomAnswer)).getText().toString();

                                        CheckBox gcs1 = (CheckBox) dialog.findViewById(R.id.googleCustomSearch1);
                                        if (gcs1.isChecked())
                                            answerText += "\n\n" + gcs1.getText().toString();

                                        CheckBox gcs2 = (CheckBox) dialog.findViewById(R.id.googleCustomSearch2);
                                        if (gcs2.isChecked())
                                            answerText += "\n\n" + gcs2.getText().toString();

                                        CheckBox gcs3 = (CheckBox) dialog.findViewById(R.id.googleCustomSearch3);
                                        if (gcs3.isChecked())
                                            answerText += "\n\n" + gcs3.getText().toString();

                                        answer(qm, answerText);
                                    }
                                })
                                .show();
                        break;
                    case EXPANDABLE_CARD:
                        queryAdapter.openCard(itemPosition);
                        break;
                    default:
                        break;
                }
                break;
            }
            case R.id.queryAnswerButton: {
                QueryModel qm = queryAdapter.getQuery((int) view.getTag());

                answer(qm, qm.getAnswer());
                break;
            }
            default:
                break;
        }
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
