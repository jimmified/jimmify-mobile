package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentModel {

    @SerializedName("status")
    @Expose(serialize = false, deserialize = true)
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    @SerializedName("recent")
    @Expose(serialize = false, deserialize = true)
    private QueryModel[] recent;

    public QueryModel[] getRecent() {
        return this.recent;
    }

    public RecentModel() {}

}
