package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class AuthenticateModel {

    @Expose(deserialize = false)
    private String username;

    @Expose(deserialize = false)
    private String password;

    @Expose(serialize = false)
    @Getter
    private String token;

    @SerializedName("status")
    @Expose(serialize = false)
    @Getter
    private boolean success;

    public AuthenticateModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
