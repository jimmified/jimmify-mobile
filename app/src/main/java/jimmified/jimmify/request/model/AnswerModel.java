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

    @SerializedName("items")
    @Expose(serialize = true, deserialize = false)
    private SearchItemModel[] items;

    public AnswerModel(int key, String answer, String token, SearchItemModel[] items) {
        this.key = key;
        this.answer = answer;
        this.token = token;
        this.items = items;
    }

    public AnswerModel(int key, String answer, String token) {
        this.key = key;
        this.answer = answer;
        this.token = token;
    }

    public AnswerModel() {}

}
