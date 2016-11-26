package jimmified.jimmify.request;

import java.lang.reflect.ParameterizedType;
import java.net.ConnectException;
import java.net.UnknownHostException;

import jimmified.jimmify.application.JimmifyApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BasicCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() != 200) {
            JimmifyApplication.showToast(getGenericName() + " (" + String.valueOf(response.code()) + ")");
            handleStatusError(response.code());
        } else {
            handleSuccess(response.body());
        }
        onFinish();
    }

    protected String getGenericName() {
        return ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof UnknownHostException | t instanceof ConnectException) {
            handleConnectionError();
        } else {
//            handleStatusError(); TODO: How to get response code?
        }
        onFinish();
    }

    public abstract void handleSuccess(T responseModel);

//    For dev reference. Should start handle success like this.
//    public void handleSuccess(T responseModel) {
//        if (responseModel.getStatus()) {
//            /* DO STUFF HERE */
//        } else {
//            handleStatusError();
//        }
//    }

    public abstract void handleConnectionError();
    public abstract void handleStatusError(int responseCode);

    // Set call to null here
    public abstract void onFinish();
}
