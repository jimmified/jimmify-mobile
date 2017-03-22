package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

// Serialized: Java to JSON
// Deserialized: JSON to Java
// Define setters for serialized fields
// Define getters for deserialized fields
public class RenewTokenModel {
//    Sent:
//    token - string token

//    Returns:
//    Status - true or false based on success.
//    token - Return the new JWT Auth Token string.

    @SerializedName("status")
    @Expose(serialize = false)
    @Getter
    private boolean status;

    @SerializedName("token")
    @Expose
    @Getter
    private String token;

    public RenewTokenModel(String token) {
        this.token = token;
    }
}
