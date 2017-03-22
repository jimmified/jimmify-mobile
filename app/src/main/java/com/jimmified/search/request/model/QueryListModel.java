package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryListModel {

    @SerializedName("status")
    @Expose(serialize = false, deserialize = true)
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    @SerializedName("queue")
    @Expose(serialize = false, deserialize = true)
    private QueryModel[] queue; // should always be top 10 queries

    @SerializedName("recents")
    @Expose(serialize = false, deserialize = true)
    private QueryModel[] recents;

    public QueryModel[] getQueryList() {
        return (this.queue == null ? this.recents : this.queue);
    }

    public QueryListModel() {}

}
