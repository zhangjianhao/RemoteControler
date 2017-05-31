package com.zjianhao.http;

import android.util.Log;
import android.view.View;

import com.zjianhao.utils.SnackBar;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 张建浩（Clarence) on 2017-4-21 13:56.
 * the author's website:http://www.zjianhao.cn
 * the author's github: https://github.com/zhangjianhao
 * contact: zhangjianhao1111@gmail.com
 */

/**
 * 网络访问的一个默认回调实现
 *
 * @param <T>
 */
public abstract class DefaultCallback<T> implements Callback<ResponseHeader<T>> {
    private WeakReference<View> view;
    public static final String TAG = DefaultCallback.class.getName();

    public DefaultCallback(View view) {
        if (view != null)
        this.view = new WeakReference<View>(view);
    }

    @Override
    public void onResponse(Call<ResponseHeader<T>> call, Response<ResponseHeader<T>> response) {

        ResponseHeader<T> body = response.body();
        if (body == null) {
            showSnackbar("网络访问异常");
            onFailure(null);
        } else if (body.getCode() != 200 && body.getCode() > 0) {
            showSnackbar("服务器异常" + body.getCode() + ":" + body.getErrorMsg());
        } else if (body.getCode() <= 0) {
            showSnackbar(body.getErrorMsg());

        } else {
            //访问成功
            onResponse(body.getResult());
        }
    }

    @Override
    public void onFailure(Call<ResponseHeader<T>> call, Throwable t) {
        t.printStackTrace();
        showSnackbar("网络访问异常,请检查网络连接");
        this.onFailure(t);
    }

    private void showSnackbar(String msg) {
        if (view != null)
            SnackBar.show(view.get(), msg);
        else
            Log.e(TAG, msg);
    }

    public abstract void onResponse(T data);

    public void onFailure(Throwable t) {

    }


}
