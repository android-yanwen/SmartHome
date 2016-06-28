package com.gta.smart.utility;

/**
 * Created by Administrator on 2016/6/28.
 */
public class Utility {
    public static void get_crc16(byte[] bufData, int buflen, byte[] pcrc) {
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < buflen; i++) {
            CRC ^= ((int) bufData[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
            // System.out.println(Integer.toHexString(CRC));
        }
        System.out.println(Integer.toHexString(CRC));
        pcrc[0] = (byte) (CRC & 0x00ff);
        pcrc[1] = (byte) (CRC >> 8);
    }

    public static String bytesToHexString(byte[] data) {
        String hexStr = "";
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xff);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            hexStr += hex;
        }
        return hexStr;
    }
}
