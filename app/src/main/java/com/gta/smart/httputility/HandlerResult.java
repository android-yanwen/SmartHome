package com.gta.smart.httputility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 解析红外操作的JSON数据
 * 例如电视红外码：
 * {
     "reqId":"ioxppf78_yr",
     "result":0,
     "des":"success",
     "datalist":[
         {
            "rcame":"电视遥控",
            "remoteType":1,
            "userId":"4087267829",
            "orders":[  {"order":"tv_1464317166#1464790698","action":"向上"},
                        {"order":"tv_1464317166#1464790709","action":"向右"},
                        {"order":"tv_1464317166#1464790715","action":"向左"},
                        {"order":"tv_1464317166#1464790721","action":"向下"},
                        {"order":"tv_1464317166#1464790725","action":"OK"},
                        {"order":"tv_1464317166#1464790756","action":"电视开关"},
                        {"order":"tv_1464317166#1464790763","action":"声音加"},
                        {"order":"tv_1464317166#1464790773","action":"声音减"}  ],
            "kname":"智能插座",
            "kid":"141a1df7-9253-4e6e-9ada-69917dd626a3"
         }
     ]
   }
 * Created by Administrator on 2016/6/2.
 * Author: 鄢文
 */
public class HandlerResult {
    private String result;
    private JSONObject ksmall_0;
    public static final String ACTION_UP = "向上";
    public static final String ACTION_RIGHT = "向右";
    public static final String ACTION_LEFT = "向左";
    public static final String ACTION_DOWN = "向下";
    public static final String ACTION_OK = "OK";
    public static final String ACTION_TV_ON_OFF = "电视开关";
    public static final String ACTION_VOLUME_ADD = "声音加";
    public static final String ACTION_VOLUME_SUBTRACT = "声音减";
    public HandlerResult(String result) {
        this.result = result;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray datalist = jsonObject.getJSONArray("datalist");
            // 这里只考虑只有1个小K的情况
            ksmall_0 = datalist.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据result解析出userid，并返回
     * @return
     */
    public String getUserId() {
        String userid = null;
        if (null != ksmall_0) {
            try {
                userid = ksmall_0.getString("userId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userid;
    }

    /**
     * 获取小k的kid
     * @return
     */
    public String getKid() {
        String kid = null;
        if (null != ksmall_0) {
            try {
                kid = ksmall_0.getString("kid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return kid;
    }
    /**
     * 获取remoteType
     * @return
     */
    public String getRemoteType() {
        String remoteType = null;
        if (null != ksmall_0) {
            try {
                int remoteTypeInt = ksmall_0.getInt("remoteType");
                remoteType = Integer.toString(remoteTypeInt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return remoteType;
    }

    /**
     * 根据传入的Action获取，返回需要的红外码
     * 例如：action = ACTION_UP（向上）
     *      返回红外码order = "tv_1464317166#1464790698"
     * @param action
     * @return order
     */
    public String getOrder(String action) {
        String order = null;
        if (null != ksmall_0) {
            try {
                JSONArray orders = ksmall_0.getJSONArray("orders");
                for (int i = 0; i < orders.length(); i++) {
                    JSONObject orderAction = orders.getJSONObject(i);
                    if (action.equals(orderAction.getString("action"))) {
                        order = orderAction.getString("order");
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    /**
     * 获得小K的开关状态
     * @param result
     * @return
     */
    public static String getSmallKStatus(String result) {
        String status = "close";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            status = jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status;
    }
}
