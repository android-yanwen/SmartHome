package com.gta.smart.modbus_model;

import android.util.Log;

import com.gta.smart.utility.Utility;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ModbusParser {
    public static final byte SWITCH_ON = 0x01;
    public static final byte SWITCH_OFF = 0x00;
    public static final byte TYPE_ELECTRIC_CURTAIN = 0x01; //电动窗帘
    public static final byte TYPE_HUMIDIFIER = 0x02;  //加湿器
    public static final byte TYPE_ALERTOR = 0x03;  //报警器
    public static final byte TYPE_LEDS = 0x04;  //LED灯带
    public static final byte TYPE_CORRIDOR_LED = 0x05;  //LED灯带
//    public static final byte TYPE_ADJUSTABLE_LED = 0x05;  //可调灯

    public ModbusParser() {

    }

    /**
     * 获取到控制模块的控制命令
     * @param sensorType
     * @param onOrOff
     * @return
     */
    public byte[] controlOrder(byte sensorType, byte onOrOff) {
        byte[] b = new byte[12];
        b[0] = 0x7e;  //数据头
        b[1] = 0x10;  //cmd
        b[2] = 0x11;  //src addrL
        b[3] = 0x11;  //src addrH
        b[4] = 0x02;  //dest addrL
        b[5] = 0x00;  //dest addrH
        b[6] = 0x70;  //interface
        b[7] = sensorType;  //type
        b[8] = 0x01;  //length
        b[9] = onOrOff;  //  data
        byte[] crc = new byte[2];
        Utility.get_crc16(b, 8, crc);
        b[10] = crc[0]; //crc1
        b[11] = crc[1]; //crc2
        return b;
    }

    /**
     * 查询控制器开关状态
     * @param sensorType
     * @return
     */
    public byte[] controlStatusOrder(byte sensorType) {
        byte[] b = new byte[10];
        b[0] = 0x7e;
        b[1] = 0x10;
        b[2] = 0x11;
        b[3] = 0x11;
        b[4] = 0x01;
        b[5] = 0x00;
        b[6] = 0x10;
        b[7] = sensorType;
        byte[] crc = new byte[2];
        Utility.get_crc16(b, 8, crc);
        b[8] = crc[0];
        b[9] = crc[1];
        return b;
    }

    /**
     * 获取并返回控制器状态 （0 or 1）
     * SWITCH_ON  or  SWITCH_OFF
     * @param command
     * @return
     */
    public byte getControlStatus(byte[] command) {
        byte status = SWITCH_OFF;
        return command.length > 0 ? command[9] : status;
    }

}
