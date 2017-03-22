package com.jimmified.search.request;

import com.jimmified.search.request.model.GoogleCustomSearchModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleCustomSearchAPI {

    @GET("")
    Call attemptConnection();

    //    https://www.googleapis.com/customsearch/v1?key=AIzaSyAZppQdSESj1hAXAN8Scw3dEm7ZzJbiAU8&cx=006135040620612225420:tzglo_hy9ag&q=lectures
//    @GET("v1?key={gcsKey}&cx={gcsCX}&q={gcsQuery}")
//    Call<GoogleCustomSearchModel> attemptGoogleCustomSearch(@Path("gcsKey") String gcsKey,
//                                                            @Path("gcsCX") String gcsCX,
//                                                            @Path("gcsQuery") String gcsQuery);

    @GET("v1")
    Call<GoogleCustomSearchModel> attemptGoogleCustomSearch(@Query("key") String gcsKey,
                                                            @Query("cx") String gcsCX,
                                                            @Query("q") String gcsQuery);
}
