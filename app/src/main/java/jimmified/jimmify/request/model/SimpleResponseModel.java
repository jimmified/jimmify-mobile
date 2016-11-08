package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Serialized: Java to JSON
// Deserialized: JSON to Java
// Define setters for serialized fields
// Define getters for deserialized fields
public class SimpleResponseModel {
//    Returns:
//    Status - true or false based on success.

    @SerializedName("Status")
    @Expose(serialize = false, deserialize = true)
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    public SimpleResponseModel() {}
}
