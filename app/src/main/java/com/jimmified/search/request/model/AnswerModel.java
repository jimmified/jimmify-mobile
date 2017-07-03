package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;

public class AnswerModel {

    @SerializedName("status")
    @Expose(serialize = false)
    @Getter
    private boolean success;

    @Expose(deserialize = false)
    private int key;

    @Expose(deserialize = false)
    private String answer;

    @Expose(deserialize = false)
    private List<String> list;

    @Expose(deserialize = false)
    private String token;

    @Expose(deserialize = false)
    private String type = "search";

    public AnswerModel(int key, String answer, List<String> list, String token) {
        this.key = key;
        this.answer = answer;
        this.list = list;
        this.token = token;
    }

}
