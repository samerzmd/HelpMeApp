package com.apps.kawaii.helpme.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.Transformer;
import com.apps.kawaii.helpme.Utils.MCrypt;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Samer on 22/05/2015.
 */
public class AjaxFactory  {
    //region services KEYS
    public static final String KEY_GET_HELPS_AROUND = "aroundhelps";
    public static final String KEY_ASK_FOR_HELP="requesthelp";
    //endregion
    //region constant & vars
    private static final String TAG = "AjaxRequest";
    private static final boolean DEBUG = true;

    public static final int METHOD_NONE = -1;
    public static final int METHOD_GET = 0;
    public static final int METHOD_POST = 1;
    public static final int METHOD_DELETE = 2;
    public static final int METHOD_PUT = 3;
    public static final int METHOD_DEFAULT = METHOD_GET;

    public static final int HTTP_OK = 200;

    private int method;
    private String url;
    private Transformer mTransformer;
    private Map<String, Object> mParams;
    private String mContentType;

    public static final String CONTENT_TYPE_APPLICATION_FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json; charset=utf-8";
    public static final String CONTENT_TYPE_MULTI_PART = "multipart/form-data";

    private HashMap<String, String> headers;
    //endregion an

    //region constrictors and helpers methods
    protected static String generateUrl(Object... urlParts) {
        StringBuilder builder = new StringBuilder("http://79.134.150.46/service/get");

        if (null != urlParts && urlParts.length > 0) {
            for (int i = 0; i < urlParts.length; i++) {
                builder.append("/");
                builder.append(urlParts[i]);
            }
        }

        return builder.toString();
    }
    public AjaxFactory() {
        this(null, METHOD_NONE);
    }

    public AjaxFactory(String url) {
        this(url, METHOD_NONE);
    }
    private AjaxFactory(String url, int method) {
        this.url = url;
        this.method = method;
        mTransformer = new GsonTransformer();
        mParams = new HashMap<String, Object>();
        mContentType = CONTENT_TYPE_APPLICATION_FORM_URL_ENCODED;
    }
    public static AjaxFactory newGetRequest(String url) {
        return new AjaxFactory(url, METHOD_GET);
    }

    public static AjaxFactory newPostRequest(String url) {
        return new AjaxFactory(url, METHOD_POST);
    }

    public static AjaxFactory newDeleteRequest(String url) {
        return new AjaxFactory(url, METHOD_DELETE);
    }

    public static AjaxFactory newPutRequest(String url) {
        return new AjaxFactory(url, METHOD_PUT);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return mParams;
    }

    public void put(String key, int value) {
        put(key, String.valueOf(value));
    }

    public void put(String key, long value) {
        put(key, String.valueOf(value));
    }

    public void put(String key, Object value) {
        if (TextUtils.isEmpty(key) || null == value) {
            return;
        }

        if (!(value instanceof String)) {
            setContentType(AjaxFactory.CONTENT_TYPE_MULTI_PART);
        }

        if (value instanceof String) {
            if (TextUtils.isEmpty((String) value)) {
                return;
            }
        }

        mParams.put(key, value);
    }

    public void put(JSONObject value) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(value.toString(), "UTF-8");
        mParams.put(AQuery.POST_ENTITY, entity);

        if (DEBUG) {
            Log.d(TAG, "Post Entity: " + value.toString());

        }

        setContentType(AjaxFactory.CONTENT_TYPE_APPLICATION_JSON);
    }

    public Transformer getTransformer() {
        return mTransformer;
    }

    public void setTransformer(Transformer transformer) {
        this.mTransformer = transformer;
    }

    public void setContentType(String contentType) {
        this.mContentType = contentType;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void putHeader(String name, String value) {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        headers.put(name, value);

    }

    public HashMap<String, String> getHeaders() {
        return headers;

    }
    //endregion

    public static AjaxFactory getHelpsAround(String Lat, String Lon){

        AjaxFactory request = null;
        try {
            //31.988616, 35.905881
            request = AjaxFactory.newGetRequest(generateUrl(KEY_GET_HELPS_AROUND,Lat,Lon));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
    public static AjaxFactory askForHelp(String title,String Lat, String Lon,String category, String desc,String asker_Id) {

        AjaxFactory request = null;
        try {
            //31.988616, 35.905881
            request = AjaxFactory.newGetRequest(generateUrl(KEY_ASK_FOR_HELP,title,Lat,Lon,category,desc,asker_Id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }
}
