package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @Expose(serialize = false, deserialize = true)
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    @SerializedName("token")
    @Expose(serialize = true, deserialize = true)
    private String token;

    public String getToken() {
        return this.token;
    }

    public RenewTokenModel(String token) {
        this.token = token;
    }
}
