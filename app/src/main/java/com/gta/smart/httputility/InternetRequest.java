package com.gta.smart.httputility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/5/31.
 */
public class InternetRequest {
    private ProgressDialog mProgressDialog;
    private Context context;
    private static final String tag = "InternetRequest";

    public InternetRequest(Context context) {
        this.context = context;
    }

    public class RequestParamsBean {
        public String username;
    }

    public class RequestUserID extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setTitle("Request remote data");
            mProgressDialog.setMessage("Loading.....");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(tag, s);
            mProgressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            String access_token = HttpUtility.getAccessToken(HttpUtility.K_SMALL_USER_NAME, HttpUtility.K_SMALL_PASSWORD);
            String query = HttpUtility.post("http://kk.bigk2.com:8080/KOAuthDemeter/User/queryUserId", access_token, params[0]);
            String user_id = HttpUtility.getUserID(query);
            return user_id;
        }
    }

}
