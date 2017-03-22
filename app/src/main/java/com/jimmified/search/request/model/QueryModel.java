package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class QueryModel {

    @SerializedName("key")
    @Expose(serialize = false, deserialize = true)
    private int key;

    public int getKey() {
        return this.key;
    }

    @SerializedName("type")
    @Expose(serialize = false, deserialize = true)
    private String type;

    public String getType() {
        return this.type;
    }

    @SerializedName("text")
    @Expose(serialize = false, deserialize = true)
    private String text;

    public String getText() {
        return this.text;
    }

    @SerializedName("answer")
    @Expose(serialize = false, deserialize = true)
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QueryModel() {}

    public QueryModel(int key, String type, String text) {
        this.key = key;
        this.type = type;
        this.text = text;
    }

    public Call<GoogleCustomSearchModel> queryGoogleCustomSearchCall;
}
