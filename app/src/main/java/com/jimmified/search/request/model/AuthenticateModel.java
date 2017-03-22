package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

// Serialized: Java to JSON
// Deserialized: JSON to Java
// Define setters for serialized fields
// Define getters for deserialized fields
public class AuthenticateModel {
//    Sent:
//    Username - string username
//    Password - string password

//    Returns:
//    Status - true or false based on success.
//    Token - Return the JWT Auth Token string.

    @SerializedName("username")
    @Expose(deserialize = false)
    private String username;

    @SerializedName("password")
    @Expose(deserialize = false)
    private String password;

    @SerializedName("status")
    @Expose(serialize = false)
    @Getter
    private boolean status;

    @SerializedName("token")
    @Expose(serialize = false)
    @Getter
    private String token;

    public AuthenticateModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
