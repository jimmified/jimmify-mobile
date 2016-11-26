package jimmified.jimmify.request;

import jimmified.jimmify.request.model.AnswerModel;
import jimmified.jimmify.request.model.QueryListModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JimmifyAPI {

    @GET("")
    Call attemptConnection();

    @GET("queue")
    Call<QueryListModel> attemptGetQueue();

    @GET("recent")
    Call<QueryListModel> attemptGetRecent();

    @POST("answer")
    Call<AnswerModel> attemptAnswer(@Body AnswerModel answerModel);
}
