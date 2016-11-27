package jimmified.jimmify.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import jimmified.jimmify.R;
import jimmified.jimmify.application.JimmifyApplication;
import jimmified.jimmify.request.BasicCallback;
import jimmified.jimmify.request.model.AnswerModel;
import jimmified.jimmify.request.model.QueryModel;
import retrofit2.Call;

public class QueueFragment extends QueryListFragment {

    private Call<AnswerModel> answerCall = null;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle
                                     savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        for (int i = 0; i < 10; i++)
            queryList.add(new QueryModel(i, "search", "Test #" + String.valueOf(i)));

        return view;
    }

    private void answer(final QueryModel qm) {
        Log.i("JEREMIAH", "YO");
//        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
//                .title(qm.getText())
//                .items("Random Answer #1", "Random Answer #2", "Random Answer #3")
//                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
//                        /**
//                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
//                         * returning false here won't allow the newly selected check box to actually be selected.
//                         * See the limited multi choice dialog example in the sample project for details.
//                         **/
//                        return true;
//                    }
//                })
//                .input(null, "Custom Answer", new MaterialDialog.InputCallback() {
//                    @Override
//                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        // Do something
//                    }
//                })
//                .positiveText("Fuck yeah!")
//                .negativeText("Fuck no!")
//                .show();

        new MaterialDialog.Builder(getActivity())
                .title(qm.getText())
                .customView(R.layout.query_answer, true)
                .positiveText("Answer")
                .show();

        if (answerCall == null) {
            AnswerModel answerModel = new AnswerModel(qm.getKey(), "Answered.");

            answerCall = JimmifyApplication.getJimmifyAPI().attemptAnswer(answerModel);
            answerCall.enqueue(new BasicCallback<AnswerModel>() {
                @Override
                public void handleSuccess(AnswerModel responseModel) {
                    queryList.remove(qm);
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
                    answerCall = null;
                }
            });
        }
    }

    public static QueueFragment newInstance() {
        QueueFragment queueFragment = new QueueFragment();
        Bundle bundle = new Bundle();

        queueFragment.setArguments(bundle);
        queueFragment.mOnClickListener = queueFragment.createOnClickListener();
        queueFragment.type = Type.QUEUE;

        return queueFragment;
    }

    protected View.OnClickListener createOnClickListener() {
        return new QueueOnClickListener();
    }

    private class QueueOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int itemPosition = mQueryListRecyclerView.getChildLayoutPosition(view);
            QueryModel qm = queryList.get(itemPosition);
            answer(qm);
        }
    }
}
