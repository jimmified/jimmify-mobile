package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueueModel {

    @SerializedName("status")
    @Expose(serialize = false, deserialize = true)
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    @SerializedName("queue")
    @Expose(serialize = false, deserialize = true)
    private QueryModel[] queue; // should always be top 10 queries

    public QueryModel[] getQueue() {
        return this.queue;
    }

    public QueueModel() {}

}
