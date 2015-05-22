package com.apps.kawaii.helpme.net;

/**
 * Created by Samer on 22/05/2015.
 */
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AbstractAjaxCallback;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.util.Constants;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class AjaxClient {
    private static final String TAG = "AjaxClient";

    private static final boolean DEBUG = true;

    public static <K> void sendRequest(Context context, AjaxFactory request,
                                       Class<K> type, AjaxCallback<K> callback) {

        injectHeaders(request, callback);

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            AbstractAjaxCallback.setSSF(sf);
        } catch(Exception ex) {
            Log.d(TAG, "unable to set ssl factory:" + ex.toString());
        }

        int method = request.getMethod();
        if (AjaxFactory.METHOD_NONE == method) {
            method = AjaxFactory.METHOD_DEFAULT;
        }

        if (AjaxFactory.METHOD_GET == method) {
            get(context, request, type, callback);
        } else if (AjaxFactory.METHOD_POST == method) {
            post(context, request, type, callback);
        } else if (AjaxFactory.METHOD_DELETE == method) {
            delete(context, request, type, callback);
        } else if (AjaxFactory.METHOD_PUT == method) {
            put(context, request, type, callback);
        } else {
            throw new IllegalArgumentException("Unknown method " + method);
        }
    }

    private static void injectHeaders(AjaxFactory request, AjaxCallback callback) {
        HashMap<String, String> headers = request.getHeaders();
        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                callback.header(key, headers.get(key));

            }

        }
    }

    public static String getUrlWithQueryString(AjaxFactory request) {
        return getUrlWithQueryString(request.getUrl(), request.getParams());
    }

    public static <K> AjaxCallback<K> sendSynRequest(Context context,
                                                     AjaxFactory request, Class<K> type) {
        final String url = getUrlWithQueryString(request);
        if (DEBUG) {
            Log.i(TAG, url);
        }

        AjaxCallback<K> callback = new AjaxCallback<K>();
        callback.url(url).type(type);
        AQuery aq = new AQuery(context);
        aq.sync(callback);

        return callback;
    }


    private static <K> void post(Context context, AjaxFactory request,
                                 Class<K> type, AjaxCallback<K> callback) {
        if (DEBUG) {
            final String url = getUrlWithQueryString(request.getUrl(), request.getParams());
            Log.i(TAG, url);
        }

        AQuery aq = new AQuery(context);
        aq.transformer(request.getTransformer());

        injectContentTypeHeader(callback, request);

        aq.ajax(request.getUrl(), request.getParams(), type, callback);

    }


    private static <K> void get(Context context, AjaxFactory request,
                                Class<K> type, AjaxCallback<K> callback) {
        final String url = getUrlWithQueryString(request);

        if (DEBUG) {
            Log.i(TAG, url);

        }

        AQuery aq = new AQuery(context);
        aq.transformer(request.getTransformer());


        aq.ajax(url, type, callback);
    }


    private static <K> void put(Context context, AjaxFactory request,
                                Class<K> type, AjaxCallback<K> callback) {
        if (DEBUG) {
            final String url = getUrlWithQueryString(request);
            Log.i(TAG, url);
        }

        AQuery aq = new AQuery(context);
        aq.transformer(request.getTransformer());

        injectContentTypeHeader(callback, request);

        aq.ajax(request.getUrl(), request.getParams(), type,
                callback.method(Constants.METHOD_PUT));
    }


    private static <K> void delete(Context context, AjaxFactory request,
                                   Class<K> type, AjaxCallback<K> callback) {
        final String url = getUrlWithQueryString(request);

        if (DEBUG) {
            Log.i(TAG, url);
        }

        AQuery aq = new AQuery(context);
        aq.transformer(request.getTransformer());

        aq.ajax(url, type, callback.method(Constants.METHOD_DELETE));
    }

    private static <K> void injectContentTypeHeader(AjaxCallback<K> callback,
                                                    AjaxFactory request) {
        /*
         * leave the default form-url-encoded content type header, and the
		 * multi-part/form-data header. Otherwise inject the content type header
		 * according to the header specified inside the AjaxRequest
		 */
        String contentType = request.getContentType();
        if (!AjaxFactory.CONTENT_TYPE_APPLICATION_FORM_URL_ENCODED
                .equals(contentType)
                && !AjaxFactory.CONTENT_TYPE_MULTI_PART.equals(contentType)) {
            callback.header("Content-Type", contentType);

        }
    }

    private static String getUrlWithQueryString(final String url,
                                                final Map<String, Object> map) {
        if (null == map || map.size() < 1) {
            return url;
        }

        final StringBuilder urlBuilder = new StringBuilder(url);
        urlBuilder.append("?");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                if (null != key && key.length() > 0) {
                    urlBuilder.append(key);
                    urlBuilder.append("=");
                    String encoded = value;
                    try {
                        encoded = URLEncoder.encode(value, "utf-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    urlBuilder.append(encoded);
                    urlBuilder.append("&");
                }
            }
        }

        final int lastCharIndex = urlBuilder.length() - 1;
        final char c = urlBuilder.charAt(lastCharIndex);
        if ('?' == c || '&' == c) {
            urlBuilder.deleteCharAt(lastCharIndex);
        }

        return urlBuilder.toString();
    }
}