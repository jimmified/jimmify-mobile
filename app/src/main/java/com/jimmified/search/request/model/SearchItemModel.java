package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class SearchItemModel {

    @SerializedName("kind")
    @Expose(serialize = false)
    @Getter
    private String kind;

    @SerializedName("title")
    @Expose(serialize = false)
    @Getter
    private String title;

    @SerializedName("htmlTitle")
    @Expose(serialize = false)
    @Getter
    private String htmlTitle;

    @SerializedName("link")
    @Expose(serialize = false)
    @Getter
    private String link;

    @SerializedName("displayLink")
    @Expose(serialize = false)
    @Getter
    private String displayLink;

    @SerializedName("snippet")
    @Expose(serialize = false)
    @Getter
    private String snippet;

    @SerializedName("htmlSnippet")
    @Expose(serialize = false)
    @Getter
    private String htmlSnippet;

    @SerializedName("cacheId")
    @Expose(serialize = false)
    @Getter
    private String cacheID;

    @SerializedName("formattedUrl")
    @Expose(serialize = false)
    @Getter
    private String formattedURL;

    @SerializedName("htmlFormattedUrl")
    @Expose(serialize = false)
    @Getter
    private String htmlFormattedURL;

}
