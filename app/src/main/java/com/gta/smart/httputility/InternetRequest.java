package com.gta.smart.httputility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/5/31.
 */
public class InternetRequest {
    public static final String GET_KLIST_IFG = "0";
    public static final String GET_GENERAL_REMOTE_LIST = "13";
//    private ProgressDialog mProgressDialog;
    private Context context;
    private static final String tag = "InternetRequest";
    public static String accessToken;
    public static String userId;
//    public static String[] kids;

    public InternetRequest(Context context) {
        this.context = context;
    }

    public class RequestParamsBean {
        public String username;
        public String userid;
        public String kid;
        public String key;
        public String remoteType;
        public String order;
    }

    /**
     * 异步处理获取AccessToken和UserID
     */
    public class RequestUserID extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(context);
//            mProgressDialog.setTitle("Request remote data");
//            mProgressDialog.setMessage("Loading.....");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.show();
            if (null != mOnAsyncTaskListener) {
                mOnAsyncTaskListener.onTaskStart();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(tag, s);
            /*try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("datalist");
                kids = new String[jsonArray.length()];
                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(index);
                    kids[index] = jsonObject1.getString("kid");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
//            mProgressDialog.dismiss();
            if (null != mOnAsyncTaskListener) {
                mOnAsyncTaskListener.onTaskFinish(s);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            accessToken = HttpUtility.getAccessToken(HttpUtility.K_SMALL_USER_NAME, HttpUtility.K_SMALL_PASSWORD);
            String query = HttpUtility.post("http://kk.bigk2.com:8080/KOAuthDemeter/User/queryUserId", accessToken, params[0]);
            userId = HttpUtility.getUserID(query);
            return userId;
        }
    }

    /**
     * 请求小k的控制和信息等获取
     */
    public class RequestSmallK extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(context);
//            mProgressDialog.setTitle("Request remote data");
//            mProgressDialog.setMessage("Loading.....");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.show();
            if (null != mOnAsyncTaskListener) {
                mOnAsyncTaskListener.onTaskStart();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            mProgressDialog.dismiss();
            Log.i(tag, s);
            if (null != mOnAsyncTaskListener) {
                mOnAsyncTaskListener.onTaskFinish(s);
            }
        }

/*        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mOnProgressListener != null) {
                mOnProgressListener.onProgress(values[0]);
            }
            Log.i(tag, "values[0]=" + values[0]);
        }*/

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            String method = params[0];
            String jsonParam = params[1];
//            String requestFlag = params[2];
//            if (requestFlag.equals(GET_KLIST_IFG))
            result = HttpUtility.post("http://kk.bigk2.com:8080/KOAuthDemeter" + method, accessToken, jsonParam);
            return result;
        }
    }


    public interface OnAsyncTaskListener {
        void onTaskStart();
        void onTaskFinish(String result);
    }

    private OnAsyncTaskListener mOnAsyncTaskListener;

    public void setOnAsyncTaskListener(OnAsyncTaskListener mOnAsyncTaskListener) {
        this.mOnAsyncTaskListener = mOnAsyncTaskListener;
    }


}
