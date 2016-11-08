package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedModel {

    @SerializedName("Questions")
    @Expose(serialize = false, deserialize = true)
    private QuestionModel[] questions;

    public QuestionModel[] getQuestions() {
        return this.questions;
    }

    public FeedModel() {}

}
