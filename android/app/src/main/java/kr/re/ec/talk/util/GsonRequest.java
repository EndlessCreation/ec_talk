package kr.re.ec.talk.util;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by milkirre on 15. 5. 2..
 */
public class GsonRequest<T> extends JsonRequest<T> {
    private final Gson mGson = new GsonBuilder()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            .registerTypeAdapter(Date.class, new ISODateAdapter())
            .create();
    private final Class<T> mClassType;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;


    public GsonRequest(int method, String url, String requestBody,
                       Class<T> classType, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        mClassType = classType;
        mHeaders = headers;
        mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.d("GsonRequest", "json => " + json);

//            if(mClassType.equals(CarWashManagerListTabDataResponse.class)){
//                JSONObject jo = new JSONObject(json);
//                json = new String(jo.get("managerList").toString());
//
//            }

            return Response.success(
                    mGson.fromJson(json, mClassType),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
//        } catch (JSONException e) {
//            return Response.error(new ParseError(e));
//        }
    }
}