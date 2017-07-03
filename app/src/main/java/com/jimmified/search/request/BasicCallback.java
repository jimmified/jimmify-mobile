package com.jimmified.search.request;

import java.lang.reflect.ParameterizedType;
import java.net.ConnectException;
import java.net.UnknownHostException;

import com.jimmified.search.JimmifyApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BasicCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() != 200) {
            JimmifyApplication.showToast(getGenericName() + " (" + response.code() + ")");
            handleStatusError(response.code());
        } else {
            handleSuccess(response.body());
        }
        onFinish();
    }

    private String getGenericName() {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            handleConnectionError();
        }
        onFinish();
    }

    /**
     *  Dev Reference
     *  public void handleSuccess(T responseModel) {
     *      if (responseModel.isSuccess()) {
     *          // DO STUFF HERE
     *      } else {
     *          handleCommonError();
     *      }
     *  }
     *
     * @param responseModel
     */
    public abstract void handleSuccess(T responseModel);

    /**
     * Dev Reference
     * public void handleConnectionError() {
     *     // Display toast or something
     * }
     */
    public abstract void handleConnectionError();

    /**
     * Dev Reference
     * public void handleStatusError(int responseCode) {
     *     switch(responseCode) {
     *         case 500:
     *             // INTERNAL SERVER ERROR
     *             handleConnectionError();
     *             break;
     *         case 400:
     *             // REQUEST ERROR
     *             break;
     *         case 401:
     *         default:
     *             // USER ERROR
     *             handleCommonError();
     *             break;
     *     }
     * }
     * @param responseCode response status code
     */
    public abstract void handleStatusError(int responseCode);

    /**
     * Dev Reference
     * public void handleCommonError() {
     *     // Do user error handling here
     * }
     */
    public abstract void handleCommonError();

    /**
     * Dev Reference
     * public void onFinish() {
     *     // Other onFinish functionality
     *     call = null;
     * }
     */
    public abstract void onFinish();
}