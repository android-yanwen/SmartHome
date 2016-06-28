package com.gta.smart.webserver;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/6/28.
 */
public class WebServerThread extends Thread {
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String RtDataSelectByType = "RtDataSelectByType ";
    private static String SOAP_ACTION = "http://tempuri.org/RtDataSelectByType ";
    private String URL = null;
    private WebServerResult webServerResult;

    public WebServerThread(WebServerResult webServerResult, String URL) {
        this.URL = URL;
        if (webServerResult != null) {
            this.webServerResult = webServerResult;
        }
    }

    @Override
    public void run() {
        if (this.URL == null) {
            return;
        }
        HttpTransportSE ht = new HttpTransportSE(this.URL);
        ht.debug = true;
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(NAMESPACE, RtDataSelectByType);
        // 这里添加被调用的方法的参数 soapObject.addProperty("key", "value");
//        soapObject.addProperty("systemid", 1);
//        soapObject.addProperty("collegeid", 1);
//        soapObject.addProperty("sensorTypeId", 1);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        try {
            ht.call(SOAP_ACTION, envelope);
            SoapObject object = (SoapObject) envelope.getResponse();
            if (object != null) {
                // 读取数据
                webServerResult.onResult(object.getProperty("data").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        super.run();
    }

    public interface WebServerResult {
        void onResult(String result);
    }

}
