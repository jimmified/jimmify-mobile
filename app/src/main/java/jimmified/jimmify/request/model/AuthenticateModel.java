package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @Expose(serialize = true, deserialize = false)
    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @SerializedName("password")
    @Expose(serialize = true, deserialize = false)
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("status")
    @Expose(serialize = false, deserialize = true)
    private boolean status;

    public boolean getStatus() {
        return this.status;
    }

    @SerializedName("token")
    @Expose(serialize = false, deserialize = true)
    private String token;

    public String getToken() {
        return this.token;
    }

    public AuthenticateModel() {
    }

    public AuthenticateModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
