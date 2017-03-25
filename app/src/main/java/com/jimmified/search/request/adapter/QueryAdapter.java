package com.jimmified.search.request.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import com.jimmified.search.R;
import com.jimmified.search.JimmifyApplication;
import com.jimmified.search.request.model.QueryModel;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.QueryViewHolder> {

    private static final String TAG = QueryAdapter.class.getName();

    static class QueryViewHolder extends RecyclerView.ViewHolder {
        private View queryContainerView;
        private TextView queryTextView;
        private View queryAnswerView;
        private Button queryAnswerButton;
        private EditText queryCustomAnswer;
        private EditText queryLinkView;

        QueryViewHolder(View itemView) {
            super(itemView);
            queryContainerView = itemView.findViewById(R.id.queryContainerView);
            queryTextView = (TextView) itemView.findViewById(R.id.queryTextView);
            queryAnswerView = itemView.findViewById(R.id.queryAnswerSection);
            queryCustomAnswer = (EditText) queryAnswerView.findViewById(R.id.queryCustomAnswer);
            queryLinkView = (EditText) queryAnswerView.findViewById(R.id.queryLinkField);
            queryAnswerButton = (Button) queryAnswerView.findViewById(R.id.queryAnswerButton);
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

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public QueryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View queryView = inflater.inflate(R.layout.query, parent, false);

        // Set on click listener
        queryView.setOnClickListener(mOnClickListener);
        queryView.findViewById(R.id.queryAnswerButton).setOnClickListener(mOnClickListener);

        // Return a new holder instance
        return new QueryViewHolder(queryView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final QueryViewHolder queryViewHolder, final int position) {
        // Get the data model based on position
        final QueryModel query = mQueries.get(position);

        // Set item views based on your views and data model
        queryViewHolder.queryTextView.setText(query.getText());

        final boolean isExpanded = position == mExpandedPosition;
        queryViewHolder.queryAnswerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        queryViewHolder.queryAnswerButton.setTag(position);
        queryViewHolder.itemView.setActivated(isExpanded);
        queryViewHolder.queryCustomAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String answer = editable.toString();
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
