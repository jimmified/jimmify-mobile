package jimmified.jimmify.request.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import jimmified.jimmify.R;
import jimmified.jimmify.request.model.QueryModel;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> implements View.OnClickListener {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View queryContainerView;
        public TextView queryTextView;
        public View queryAnswerView;

        public ViewHolder(View itemView) {
            super(itemView);
            queryContainerView = itemView.findViewById(R.id.queryContainerView);
            queryTextView = (TextView) itemView.findViewById(R.id.queryTextView);
            queryAnswerView = itemView.findViewById(R.id.queryAnswerSection);
        }
    }

    private RecyclerView mRecyclerView;
    private List<QueryModel> mQueries;

    private Context mContext;
    private View.OnClickListener mOnClickListener = this;
    private int mExpandedPosition = -1;

    public QueryAdapter(Context context, List<QueryModel> queries, RecyclerView recyclerView, View.OnClickListener onClickListener) {
        mContext = context;
        mQueries = queries;
        mRecyclerView = recyclerView;
        mOnClickListener = onClickListener;
    }

    public QueryAdapter(Context context, List<QueryModel> queries, RecyclerView recyclerView) {
        mContext = context;
        mQueries = queries;
        mRecyclerView = recyclerView;
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

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(queryView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(QueryAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        QueryModel query = mQueries.get(position);

        // Set item views based on your views and data model
        viewHolder.queryTextView.setText(query.getText());

        final boolean isExpanded = (position == mExpandedPosition);
        viewHolder.queryAnswerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        viewHolder.itemView.setActivated(isExpanded);
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

    @Override
    public void onClick(View view) {
        final int itemPosition = mRecyclerView.getChildLayoutPosition(view);

        notifyItemChanged(mExpandedPosition);
        if (mExpandedPosition == itemPosition)
            mExpandedPosition = -1;
        else
            mExpandedPosition = itemPosition;
        notifyItemChanged(mExpandedPosition);
    }
}
