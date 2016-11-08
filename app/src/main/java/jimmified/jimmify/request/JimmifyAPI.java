package jimmified.jimmify.request;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JimmifyAPI {

    @GET("")
    Call attemptConnection();

}
