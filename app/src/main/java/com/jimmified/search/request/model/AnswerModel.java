package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class AnswerModel {

    @SerializedName("status")
    @Expose(serialize = false)
    @Getter
    private boolean status;

    @SerializedName("key")
    @Expose(deserialize = false)
    private int key;

    @SerializedName("answer")
    @Expose(deserialize = false)
    private String answer;

    @SerializedName("link")
    @Expose(deserialize = false)
    private String link;

    @SerializedName("token")
    @Expose(deserialize = false)
    private String token;

    public AnswerModel(int key, String answer, String link, String token) {
        this.key = key;
        this.answer = answer;
        this.link = link;
        this.token = token;
    }

}
