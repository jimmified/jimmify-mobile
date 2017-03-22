package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class AnswerModel extends StatusResponseModel {

    @SerializedName("key")
    @Expose(deserialize = false)
    private int key;

    @SerializedName("answer")
    @Expose(deserialize = false)
    private String answer;

    @SerializedName("token")
    @Expose(deserialize = false)
    private String token;

    @SerializedName("items")
    @Expose(deserialize = false)
    private SearchItemModel[] items;

    public AnswerModel(int key, String answer, String token) {
        this.key = key;
        this.answer = answer;
        this.token = token;
    }

}
