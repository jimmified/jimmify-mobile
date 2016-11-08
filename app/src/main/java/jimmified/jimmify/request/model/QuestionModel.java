package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionModel {

    @SerializedName("Question")
    @Expose(serialize = false, deserialize = true)
    private String question;

    public String getQuestion() {
        return this.question;
    }

    public QuestionModel() {}
}
