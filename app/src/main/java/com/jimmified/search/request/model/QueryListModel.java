package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class QueryListModel extends StatusResponseModel {

    @SerializedName("queue")
    @Expose(serialize = false)
    private QueryModel[] queue; // should always be top 10 queries

    @SerializedName("recents")
    @Expose(serialize = false)
    private QueryModel[] recents;

    public QueryModel[] getQueryList() {
        return (this.queue == null ? this.recents : this.queue);
    }

}
