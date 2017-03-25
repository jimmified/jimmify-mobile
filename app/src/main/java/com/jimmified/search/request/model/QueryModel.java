package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class QueryModel {

    @SerializedName("key")
    @Expose(serialize = false)
    @Getter
    private int key;

    @SerializedName("type")
    @Expose(serialize = false)
    @Getter
    private String type;

    @SerializedName("text")
    @Expose(serialize = false)
    @Getter
    private String text;

    @SerializedName("answer")
    @Expose(serialize = false)
    @Getter
    @Setter
    private String answer;

    @SerializedName("link")
    @Expose(serialize = false)
    @Getter
    @Setter
    private String link;

    public QueryModel(int key, String type, String text) {
        this.key = key;
        this.type = type;
        this.text = text;
    }

}
