package jimmified.jimmify.request.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jimmified.jimmify.R;
import jimmified.jimmify.request.model.QueryModel;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View queryContainerView;
        public TextView queryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            queryContainerView = itemView.findViewById(R.id.queryContainerView);
            queryTextView = (TextView) itemView.findViewById(R.id.queryTextView);
        }

        @Override
        public void onClick(View view) {
            Log.d("JEREMIAH", "onClick " + getAdapterPosition() + " " + queryTextView.getText());
        }
    }

    private List<QueryModel> mQueries;
    private Context mContext;
    private View.OnClickListener mOnClickListener = null;

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
        if (mOnClickListener != null)
            queryView.setOnClickListener(mOnClickListener);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(queryView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(QueryAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        QueryModel query = mQueries.get(position);

        // Set item views based on your views and data model
        TextView queryView = viewHolder.queryTextView;
        queryView.setText(query.getText());
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
}
