package com.gta.smart.httputility;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30.
 * Author: 鄢文
 */
public class HttpUtility {
    private static final String tag = "HttpUtility";
    /**
     * client_id 和 client_secret 是供应商提供的id号
     */
    private static final String client_id="37JXo2E928xb78Sa";
    private static final String client_secret = "lZA516V1ayM15Jw5";
    /**
     * 登录控客官网申请的个人用户名和密码
     */
    public static final String K_SMALL_USER_NAME = "13040872678";
    public static final String K_SMALL_PASSWORD = "ywcsdn";

    /**
     * 获取AccessToken
     * @param username
     * @param password
     * @return access_token
     */
    public static String getAccessToken(String username, String password) {
        String access_token = null;
        try {
            Connection conn = null;
            String url = "http://kk.bigk2.com:8080/KOAuthDemeter/authorize?response_type=code&client_id="
                    + client_id + "&redirect_uri=http://www.baidu.com";
            conn = Jsoup.connect(url);
            Document doc = conn.get();

            // System.out.println(doc.toString());

            List<FormElement> formList = doc.getAllElements().forms();
            if (formList.size() > 0) {
                FormElement form = formList.get(0);
                Elements usernameInput = form.select("input[name=username]");
                Elements passwordInput = form.select("input[name=password]");
                usernameInput.attr("value", username);
                passwordInput.attr("value", password);

                conn = form.submit();
                doc = conn.post();
            }

            String code = doc.baseUri().split("=")[1];

            System.out.println("code : " + code);

            // ------------------------------------------------------------------------

            String tokenUrl = "http://kk.bigk2.com:8080/KOAuthDemeter/accessToken";
            conn = Jsoup.connect(tokenUrl);

            conn.data("grant_type", "authorization_code");
            conn.data("client_id", client_id);
            conn.data("client_secret", client_secret);
            conn.data("redirect_uri", "http://www.baidu.com");
            conn.data("code", code);

            doc = conn.post();

            String tokenResp = doc.body().text();

            JSONObject jo = new JSONObject(tokenResp);

            access_token = jo.getString("access_token");
            // String refresh_token = jo.getString("refresh_token");
            // ------------------------------------------------------------------------
            System.out.println("access_token : " + access_token);
            // System.out.println("refresh_token : " + refresh_token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }
/*
    *//**
     * 获取UserID
     * @param accessToken
     * @return userID
     *//*
    public static String getUserID(String accessToken){
        *//*String userID = null;
        String userName = null;
        Connection conn = null;
        String userIDUrl = "http://kk.bigk2.com:8080/KOAuthDemeter/User/queryUserId"*//**//*"http://kk.bigk2.com:8080/KOAuthDemeter/UserInfo"*//**//*;
        conn = Jsoup.connect(userIDUrl);
        conn.header("Authorization", "Bearer " + accessToken);
        Document doc = null;
        try {
            doc = conn.post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String idResp = doc.body().text();
        JSONObject jo = null;
        try {
            jo = new JSONObject(idResp);
            userName = jo.getString("username");
            userID = jo.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("userName : " + userName + " , userID : " + userID);*//*
        String userID = null;
        String userName = null;
        Connection conn = null;
        String userIDUrl = "http://kk.bigk2.com:8080/KOAuthDemeter/User/queryUserId"*//*"http://kk.bigk2.com:8080/KOAuthDemeter/UserInfo"*//*;
        conn = Jsoup.connect(userIDUrl);
        conn.header("Authorization", "Bearer " + accessToken);
        conn.data("Accept", "application/json");
        conn.data("Content-Type", "application/json");
        conn.data("Accept-Encoding", "identity");
        conn.data("uesrname", "13040872678");
        Document doc = null;
        try {
            doc = conn.post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String idResp = doc.body().text();
        JSONObject jo = null;
        try {
            jo = new JSONObject(idResp);
            userName = jo.getString("username");
            userID = jo.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("userName : " + userName + " , userID : " + userID);
        return userID;
    }*/


    public static String post(String strURL, String access_token, String msg
                              /*String info*/) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("Accept-Encoding", "identity");

            // 将token放到头部
            connection.setRequestProperty("Authorization", "Bearer "
                    + access_token);

            connection.connect();

            // 向服务器POST信息
            if (null != msg && msg.length() > 0) {
                OutputStreamWriter out = new OutputStreamWriter(
                        connection.getOutputStream(), "UTF-8"); // 服务器采用UTF-8编码
                out.append(msg);
                out.flush();
                out.close();
            }

            // 读取服务器响应(最大长度10K)
            int length = 10 * 1024;
            // int length =connection.getContentLength();// 获取长度,这里一直返回0,不知道什么原因
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];// 每次读取512字节
                int readLen = 0;// 单次读取的长度
                int destPos = 0;// 总字节数
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                byte[] fixed_data = Arrays.copyOf(data, destPos);
                String result = new String(fixed_data, "UTF-8"); // 响应也是UTF-8编码
//                System.out.println(info + "服务器返回结果：" + result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

    public static String getUserID(String jsondata) {
        String userId = null;
        JSONObject jo = null;
        try {
            jo = new JSONObject(jsondata);
//            userName = jo.getString("username");
            userId = jo.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(tag, "userId:" + userId);
        return userId;
    }

}
