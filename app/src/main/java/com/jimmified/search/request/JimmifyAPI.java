package com.jimmified.search.request;

import com.jimmified.search.request.model.AnswerModel;
import com.jimmified.search.request.model.AuthenticateModel;
import com.jimmified.search.request.model.QueryListModel;
import com.jimmified.search.request.model.RenewTokenModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JimmifyAPI {

    @GET("")
    Call attemptConnection();

    @POST("login")
    Call<AuthenticateModel> attemptAuthentication(@Body AuthenticateModel authModel);

    @POST("renew")
    Call<RenewTokenModel> attemptRenewToken(@Body RenewTokenModel renewTokenModel);

    @GET("queue")
    Call<QueryListModel> attemptGetQueue();

    @GET("recent")
    Call<QueryListModel> attemptGetRecent();

    @POST("answer")
    Call<AnswerModel> attemptAnswer(@Body AnswerModel answerModel);
}
