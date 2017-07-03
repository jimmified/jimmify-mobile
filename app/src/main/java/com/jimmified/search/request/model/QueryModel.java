package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.Setter;

public class QueryModel {

    @Expose(serialize = false)
    @Getter
    private int key;

    @Expose(serialize = false)
    @Getter
    private String type;

    @Expose(serialize = false)
    @Getter
    private String text;

    @Expose(serialize = false)
    @Getter
    @Setter
    private String answer;

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
