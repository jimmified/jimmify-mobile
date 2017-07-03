package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class RenewTokenModel {

    @SerializedName("status")
    @Expose(serialize = false)
    @Getter
    private boolean success;

    @Expose
    @Getter
    private String token;

    public RenewTokenModel(String token) {
        this.token = token;
    }
}
