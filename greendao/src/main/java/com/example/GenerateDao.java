package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GenerateDao {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.zjianhao.entity");
        schema.setDefaultJavaPackageDao("com.zjianhao.dao");
        addDevice(schema);
        addKey(schema);
        addAirCmd(schema);
        try {
            new DaoGenerator().generateAll(schema, "/home/zjianhao/workspace/AndroidStudioWorkSpace/UniversalController/app/src/main/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void addDevice(Schema schema) {
        Entity device = schema.addEntity("Device");
        device.addIdProperty();
        device.addIntProperty("type_id");
        device.addIntProperty("brand_id");
        device.addIntProperty("device_id");
        device.addStringProperty("device_name");
        device.addLongProperty("backup_time");
    }

    public static void addKey(Schema schema) {
        Entity device = schema.addEntity("Keyas");
        device.addIdProperty();
        device.addIntProperty("device_id");
        device.addStringProperty("cmd");
        device.addStringProperty("name");
        device.addStringProperty("data");
    }

    public static void addAirCmd(Schema schema) {
        Entity airCmd = schema.addEntity("AirCmd");
        airCmd.addIdProperty();
        airCmd.addIntProperty("device_id");
        airCmd.addStringProperty("cmd");
        airCmd.addIntProperty("temp");
        airCmd.addStringProperty("data");

    }

}
