package com.apps.kawaii.helpme.net;

import android.util.Log;

import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.Transformer;
import com.google.gson.Gson;

public class GsonTransformer implements Transformer {
    private static final String TAG = "GsonTransformer";

    public <T> T transform(String url, Class<T> type, String encoding,
                           byte[] data, AjaxStatus status) {

        Gson g = new Gson();
        String response = new String(data);
        //Log.d(TAG, "response: " + response);

        return g.fromJson(new String(data), type);
    }

}