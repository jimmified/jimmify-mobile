package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

// Serialized: Java to JSON
// Deserialized: JSON to Java
// Define setters for serialized fields
// Define getters for deserialized fields
public class StatusResponseModel {
//    Returns:
//    Status - true or false based on success.

    @SerializedName("Status")
    @Expose(serialize = false)
    @Getter
    private boolean status;
}
