package com.gta.smart.socket_utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/15.
 */
public class TcpSocketUtility {
    private Socket mSocket = null;
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final String IP_ADDRESS = "10.1.37.10";
    private static final int PORT = 58007;
    private String tag = "TcpSocketUtility";
//    private final ReentrantLock lock = new ReentrantLock(true);

    public TcpSocketUtility() {
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mSocket = new Socket(IP_ADDRESS, PORT);
                        inputStream = mSocket.getInputStream();
                        outputStream = mSocket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/
    }

/*    public void getOriginalData(final SocketDataHandle dataHandle) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(IP_ADDRESS, PORT);
                    inputStream = mSocket.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (inputStream != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        char[] buffer = new char[1024];
                        int size = br.read(buffer);
                        if (size > 0) {
                            if (dataHandle != null) {
                                dataHandle.getData(buffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            inputStream.close();
                            br.close();
                            mSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }*/

    /**
     * 定义接收socket发来的数据接口
     */
    public interface SocketDataHandle {
        void getData(byte[] data);
    }

    public void requestData(final byte[] data_b, final SocketDataHandle dataHandle) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    mSocket = new Socket("10.1.37.34", 8000);
                    mSocket = new Socket(IP_ADDRESS, PORT);
                    inputStream = mSocket.getInputStream();
                    outputStream = mSocket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (outputStream != null){
                    try {
                        outputStream.write(data_b);
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    try {
                        byte[] buffer = new byte[1024];
                        int size = inputStream.read(buffer);

//                        char[] buffer = new char[1024];
//                        int size = br.read(buffer);  //阻塞数据到来
//                        String data = br.readLine();
//                        buffer = data.getBytes("UTF-8");
//                        int size = buffer.length;
                        if (size > 0) {
                            if (dataHandle != null) {
                                dataHandle.getData(Arrays.copyOf(buffer, size));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                /**
                 * 关闭socket
                 */
                if (mSocket != null) {
                    try {
                        outputStream.close();
                        inputStream.close();
                        mSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
