package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerModel {

    @SerializedName("status")
    @Expose(serialize = false, deserialize = true)
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    @SerializedName("key")
    @Expose(serialize = true, deserialize = false)
    private int key;

    @SerializedName("answer")
    @Expose(serialize = true, deserialize = false)
    private String answer;

    @SerializedName("token")
    @Expose(serialize = true, deserialize = false)
    private String token;

    public AnswerModel(int key, String answer, String token) {
        this.key = key;
        this.answer = answer;
        this.token = token;
    }

    public AnswerModel() {}

}
