package com.jimmified.search.request.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import com.jimmified.search.R;
import com.jimmified.search.JimmifyApplication;
import com.jimmified.search.request.BasicCallback;
import com.jimmified.search.request.model.GoogleCustomSearchModel;
import com.jimmified.search.request.model.QueryModel;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View queryContainerView;
        public TextView queryTextView;
        public View queryAnswerView;
        public Button queryAnswerButton;
        public EditText queryCustomAnswer;
        public CheckBox queryAutoAnswer1;
        public CheckBox queryAutoAnswer2;
        public CheckBox queryAutoAnswer3;

        public ViewHolder(View itemView) {
            super(itemView);
            queryContainerView = itemView.findViewById(R.id.queryContainerView);
            queryTextView = (TextView) itemView.findViewById(R.id.queryTextView);
            queryAnswerView = itemView.findViewById(R.id.queryAnswerSection);
            queryCustomAnswer = (EditText) queryAnswerView.findViewById(R.id.queryCustomAnswer);
            queryAnswerButton = (Button) queryAnswerView.findViewById(R.id.queryAnswerButton);
            queryAutoAnswer1 = (CheckBox) queryAnswerView.findViewById(R.id.googleCustomSearch1);
            queryAutoAnswer2 = (CheckBox) queryAnswerView.findViewById(R.id.googleCustomSearch2);
            queryAutoAnswer3 = (CheckBox) queryAnswerView.findViewById(R.id.googleCustomSearch3);
        }
    }

    private List<QueryModel> mQueries;

    private Context mContext;
    private View.OnClickListener mOnClickListener = null;
    private int mExpandedPosition = -1;

    public QueryAdapter(Context context, List<QueryModel> queries, View.OnClickListener onClickListener) {
        mContext = context;
        mQueries = queries;
        mOnClickListener = onClickListener;
    }

    public QueryAdapter(Context context, List<QueryModel> queries) {
        mContext = context;
        mQueries = queries;
    }

    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public QueryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View queryView = inflater.inflate(R.layout.query, parent, false);

        // Set on click listener
        queryView.setOnClickListener(mOnClickListener);
        queryView.findViewById(R.id.queryAnswerButton).setOnClickListener(mOnClickListener);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(queryView);

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final QueryAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final QueryModel query = mQueries.get(position);

        if (mOnClickListener != null) {
            if (query.queryGoogleCustomSearchCall == null) {
                query.queryGoogleCustomSearchCall = JimmifyApplication.getGoogleCustomSearchAPI().attemptGoogleCustomSearch(
                        JimmifyApplication.getGoogleCustomSearchKey(),
                        JimmifyApplication.getGoogleCustomSearchCX(),
                        query.getText()
                );

                query.queryGoogleCustomSearchCall.enqueue(new BasicCallback<GoogleCustomSearchModel>() {
                    @Override
                    public void handleSuccess(GoogleCustomSearchModel responseModel) {
                        String[] snippets = responseModel.getItemSnippets();

                        viewHolder.queryAutoAnswer1.setText(snippets[0]);
                        viewHolder.queryAutoAnswer1.setVisibility(View.VISIBLE);
                        viewHolder.queryAutoAnswer2.setText(snippets[1]);
                        viewHolder.queryAutoAnswer2.setVisibility(View.VISIBLE);
                        viewHolder.queryAutoAnswer3.setText(snippets[2]);
                        viewHolder.queryAutoAnswer3.setVisibility(View.VISIBLE);

                        Log.i("JEREMIAH", "Completed search.");
                    }

                    @Override
                    public void handleConnectionError() {
                        JimmifyApplication.showServerConnectionToast();
                    }

                    @Override
                    public void handleStatusError(int responseCode) {
                        JimmifyApplication.showToast("Error getting google custom search answers...");
                    }

                    @Override
                    public void onFinish() {
//                        mQueryListRefreshLayout.setRefreshing(false);
//                        queryGoogleCustomSearchCall = null;
                    }
                });
            }
        }

        // Set item views based on your views and data model
        viewHolder.queryTextView.setText(query.getText());

        final boolean isExpanded = (position == mExpandedPosition);
        viewHolder.queryAnswerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        viewHolder.queryAnswerButton.setTag(position);
        viewHolder.itemView.setActivated(isExpanded);
        viewHolder.queryCustomAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String answer = editable.toString();
                if (viewHolder.queryAutoAnswer1.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer1.getText();
                if (viewHolder.queryAutoAnswer2.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer2.getText();
                if (viewHolder.queryAutoAnswer3.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer3.getText();

                query.setAnswer(answer);
            }
        });
        viewHolder.queryAutoAnswer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String answer = viewHolder.queryCustomAnswer.getText().toString();
                if (viewHolder.queryAutoAnswer1.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer1.getText();
                if (viewHolder.queryAutoAnswer2.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer2.getText();
                if (viewHolder.queryAutoAnswer3.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer3.getText();

                query.setAnswer(answer);
            }
        });
        viewHolder.queryAutoAnswer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String answer = viewHolder.queryCustomAnswer.getText().toString();
                if (viewHolder.queryAutoAnswer1.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer1.getText();
                if (viewHolder.queryAutoAnswer2.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer2.getText();
                if (viewHolder.queryAutoAnswer3.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer3.getText();

                query.setAnswer(answer);
            }
        });
        viewHolder.queryAutoAnswer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String answer = viewHolder.queryCustomAnswer.getText().toString();
                if (viewHolder.queryAutoAnswer1.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer1.getText();
                if (viewHolder.queryAutoAnswer2.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer2.getText();
                if (viewHolder.queryAutoAnswer3.isChecked())
                    answer += "\n\n" + viewHolder.queryAutoAnswer3.getText();

                query.setAnswer(answer);
            }
        });

        JimmifyApplication.hideKeyboard((Activity) mContext);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mQueries.size();
    }

    public void remove(int position) {
        if (position < 0 || position >= mQueries.size())
            return;
        mQueries.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        while (!mQueries.isEmpty())
            mQueries.remove(0);
    }

    public void openCard(final int itemPosition) {
        notifyItemChanged(mExpandedPosition);
        if (mExpandedPosition == itemPosition)
            mExpandedPosition = -1;
        else
            mExpandedPosition = itemPosition;
        notifyItemChanged(mExpandedPosition);
    }

    public QueryModel getQuery(final int itemPosition) {
        return mQueries.get(itemPosition);
    }
}
