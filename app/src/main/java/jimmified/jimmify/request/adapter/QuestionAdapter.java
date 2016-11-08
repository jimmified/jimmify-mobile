package jimmified.jimmify.request.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jimmified.jimmify.R;
import jimmified.jimmify.request.model.QuestionModel;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View questionContainerView;
        public TextView questionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            questionContainerView = itemView.findViewById(R.id.questionContainerView);
            questionTextView = (TextView) itemView.findViewById(R.id.questionTextView);
        }
    }

    private List<QuestionModel> mQuestions;
    private Context mContext;

    public QuestionAdapter(Context context, List<QuestionModel> questions) {
        mQuestions = questions;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View questionView = inflater.inflate(R.layout.question, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(questionView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        QuestionModel question = mQuestions.get(position);

        // Set item views based on your views and data model
        TextView questionView = viewHolder.questionTextView;
        questionView.setText(question.getQuestion());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public void remove(int position) {
        if (position < 0 || position >= mQuestions.size())
            return;
        mQuestions.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        while (!mQuestions.isEmpty())
            mQuestions.remove(0);
    }
}
