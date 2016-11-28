package jimmified.jimmify.request.adapter;

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

import jimmified.jimmify.R;
import jimmified.jimmify.application.JimmifyApplication;
import jimmified.jimmify.request.model.QueryModel;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View queryContainerView;
        public TextView queryTextView;
        public View queryAnswerView;
        public Button queryAnswerButton;
        public EditText queryCustomAnswer;

        public ViewHolder(View itemView) {
            super(itemView);
            queryContainerView = itemView.findViewById(R.id.queryContainerView);
            queryTextView = (TextView) itemView.findViewById(R.id.queryTextView);
            queryAnswerView = itemView.findViewById(R.id.queryAnswerSection);
            queryCustomAnswer = (EditText) queryAnswerView.findViewById(R.id.queryCustomAnswer);
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
    public void onBindViewHolder(QueryAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final QueryModel query = mQueries.get(position);

        // Set item views based on your views and data model
        viewHolder.queryTextView.setText(query.getText());

        final boolean isExpanded = (position == mExpandedPosition);
        viewHolder.queryAnswerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        viewHolder.queryAnswerButton.setTag(position);
        viewHolder.itemView.setActivated(isExpanded);
        viewHolder.queryCustomAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                query.setAnswer(editable.toString());
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
