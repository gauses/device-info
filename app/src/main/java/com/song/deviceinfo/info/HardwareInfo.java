package com.song.deviceinfo.info;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

import static android.content.Context.SENSOR_SERVICE;

import com.google.gson.JsonObject;
import com.song.deviceinfo.file.SaveFileToSd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chensongsong on 2020/9/22.
 */
public class HardwareInfo {

    public static List<Pair<String, String>> getHardwareInfo(Context context) {
        List<Pair<String, String>> list = new ArrayList<>();



        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        List<Pair<String, Sensor>> allTypeList = new ArrayList<>();
        Sensor TYPE_ACCELEROMETER = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor TYPE_ACCELEROMETER_UNCALIBRATED = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
        Sensor TYPE_ALL = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        Sensor TYPE_AMBIENT_TEMPERATURE = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor TYPE_DEVICE_PRIVATE_BASE = sensorManager.getDefaultSensor(Sensor.TYPE_DEVICE_PRIVATE_BASE);
        Sensor TYPE_GAME_ROTATION_VECTOR = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        Sensor TYPE_GEOMAGNETIC_ROTATION_VECTOR = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        Sensor TYPE_GRAVITY = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor TYPE_GYROSCOPE = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor TYPE_GYROSCOPE_UNCALIBRATED = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        Sensor TYPE_HEART_BEAT = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
        Sensor TYPE_HEART_RATE = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        Sensor TYPE_HINGE_ANGLE = sensorManager.getDefaultSensor(Sensor.TYPE_HINGE_ANGLE);
        Sensor TYPE_LIGHT = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor TYPE_LINEAR_ACCELERATION = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Sensor TYPE_LOW_LATENCY_OFFBODY_DETECT = sensorManager.getDefaultSensor(Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT);
        Sensor TYPE_MAGNETIC_FIELD = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor TYPE_MAGNETIC_FIELD_UNCALIBRATED = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        Sensor TYPE_MOTION_DETECT = sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
        Sensor TYPE_ORIENTATION = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        Sensor TYPE_POSE_6DOF = sensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF);
        Sensor TYPE_PRESSURE = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Sensor TYPE_PROXIMITY = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor TYPE_RELATIVE_HUMIDITY = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        Sensor TYPE_ROTATION_VECTOR = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Sensor TYPE_SIGNIFICANT_MOTION = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        Sensor TYPE_STATIONARY_DETECT = sensorManager.getDefaultSensor(Sensor.TYPE_STATIONARY_DETECT);
        Sensor TYPE_STEP_COUNTER = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor TYPE_STEP_DETECTOR = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor TYPE_TEMPERATURE = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);

        Sensor REPORTING_MODE_CONTINUOUS = sensorManager.getDefaultSensor(Sensor.REPORTING_MODE_CONTINUOUS);
        Sensor REPORTING_MODE_ONE_SHOT = sensorManager.getDefaultSensor(Sensor.REPORTING_MODE_ONE_SHOT);
        Sensor REPORTING_MODE_ON_CHANGE = sensorManager.getDefaultSensor(Sensor.REPORTING_MODE_ON_CHANGE);
        Sensor REPORTING_MODE_SPECIAL_TRIGGER = sensorManager.getDefaultSensor(Sensor.REPORTING_MODE_SPECIAL_TRIGGER);


        allTypeList.add(new Pair<>("TYPE_ACCELEROMETER", TYPE_ACCELEROMETER));
        allTypeList.add(new Pair<>("TYPE_ACCELEROMETER_UNCALIBRATED", TYPE_ACCELEROMETER_UNCALIBRATED));
        allTypeList.add(new Pair<>("TYPE_ALL", TYPE_ALL));
        allTypeList.add(new Pair<>("TYPE_AMBIENT_TEMPERATURE", TYPE_AMBIENT_TEMPERATURE));

        allTypeList.add(new Pair<>("TYPE_DEVICE_PRIVATE_BASE", TYPE_DEVICE_PRIVATE_BASE));

        allTypeList.add(new Pair<>("TYPE_GAME_ROTATION_VECTOR",TYPE_GAME_ROTATION_VECTOR));
        allTypeList.add(new Pair<>("TYPE_GEOMAGNETIC_ROTATION_VECTOR",TYPE_GEOMAGNETIC_ROTATION_VECTOR));
        allTypeList.add(new Pair<>("TYPE_GRAVITY",TYPE_GRAVITY));
        allTypeList.add(new Pair<>("TYPE_GYROSCOPE",TYPE_GYROSCOPE));
        allTypeList.add(new Pair<>("TYPE_GYROSCOPE_UNCALIBRATED",TYPE_GYROSCOPE_UNCALIBRATED));
        allTypeList.add(new Pair<>("TYPE_HEART_BEAT",TYPE_HEART_BEAT));
        allTypeList.add(new Pair<>("TYPE_HEART_RATE",TYPE_HEART_RATE));
        allTypeList.add(new Pair<>("TYPE_HINGE_ANGLE",TYPE_HINGE_ANGLE));

        allTypeList.add(new Pair<>("TYPE_LIGHT",TYPE_LIGHT));
        allTypeList.add(new Pair<>("TYPE_LINEAR_ACCELERATION",TYPE_LINEAR_ACCELERATION));

        allTypeList.add(new Pair<>("TYPE_LOW_LATENCY_OFFBODY_DETECT",TYPE_LOW_LATENCY_OFFBODY_DETECT));
        allTypeList.add(new Pair<>("TYPE_MAGNETIC_FIELD",TYPE_MAGNETIC_FIELD));
        allTypeList.add(new Pair<>("TYPE_MAGNETIC_FIELD_UNCALIBRATED",TYPE_MAGNETIC_FIELD_UNCALIBRATED));

        allTypeList.add(new Pair<>("TYPE_MOTION_DETECT",TYPE_MOTION_DETECT));
        allTypeList.add(new Pair<>("TYPE_ORIENTATION",TYPE_ORIENTATION));
        allTypeList.add(new Pair<>("TYPE_POSE_6DOF",TYPE_POSE_6DOF));
        allTypeList.add(new Pair<>("TYPE_PRESSURE",TYPE_PRESSURE));
        allTypeList.add(new Pair<>("TYPE_PROXIMITY",TYPE_PROXIMITY));
        allTypeList.add(new Pair<>("TYPE_RELATIVE_HUMIDITY",TYPE_RELATIVE_HUMIDITY));
        allTypeList.add(new Pair<>("TYPE_ROTATION_VECTOR",TYPE_ROTATION_VECTOR));

        allTypeList.add(new Pair<>("TYPE_SIGNIFICANT_MOTION",TYPE_SIGNIFICANT_MOTION));
        allTypeList.add(new Pair<>("TYPE_STATIONARY_DETECT",TYPE_STATIONARY_DETECT));
        allTypeList.add(new Pair<>("TYPE_STEP_COUNTER",TYPE_STEP_COUNTER));
        allTypeList.add(new Pair<>("TYPE_STEP_DETECTOR",TYPE_STEP_DETECTOR));
        allTypeList.add(new Pair<>("TYPE_TEMPERATURE",TYPE_TEMPERATURE));

        allTypeList.add(new Pair<>("REPORTING_MODE_CONTINUOUS",REPORTING_MODE_CONTINUOUS));
        allTypeList.add(new Pair<>("REPORTING_MODE_ONE_SHOT",REPORTING_MODE_ONE_SHOT));
        allTypeList.add(new Pair<>("REPORTING_MODE_ON_CHANGE",REPORTING_MODE_ON_CHANGE));
        allTypeList.add(new Pair<>("REPORTING_MODE_SPECIAL_TRIGGER",REPORTING_MODE_SPECIAL_TRIGGER));


//        JsonObject jsonList = new JsonObject();
        JSONArray jsonArray = new JSONArray();


        for (int i = 0; i < allTypeList.size(); i++) {
            if (allTypeList.get(i).second != null) {
                JSONObject sensorDataJson = new JSONObject();
                Log.i("sensorDataList", allTypeList.get(i).second.toString());

                try {
                    sensorDataJson.put("Id", allTypeList.get(i).second.getId());
                    sensorDataJson.put("Name", allTypeList.get(i).second.getName());
                    sensorDataJson.put("Type", allTypeList.get(i).second.getType());
                    sensorDataJson.put("Vendor", allTypeList.get(i).second.getVendor());
                    sensorDataJson.put("Resolution", allTypeList.get(i).second.getResolution());

                    sensorDataJson.put("StringType", allTypeList.get(i).second.getStringType());
                    sensorDataJson.put("Resolution", allTypeList.get(i).second.getResolution());
                    sensorDataJson.put("ReportingMode", allTypeList.get(i).second.getReportingMode());
                    sensorDataJson.put("MaximumRange", allTypeList.get(i).second.getMaximumRange());
                    sensorDataJson.put("MaxDelay", allTypeList.get(i).second.getMaxDelay());
                    sensorDataJson.put("FifoReservedEventCount", allTypeList.get(i).second.getFifoReservedEventCount());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        sensorDataJson.put("HighestDirectReportRateLevel", allTypeList.get(i).second.getHighestDirectReportRateLevel());
                    }
                    sensorDataJson.put("FifoMaxEventCount", allTypeList.get(i).second.getFifoMaxEventCount());
                    sensorDataJson.put("Power", allTypeList.get(i).second.getPower());
                    sensorDataJson.put("MinDelay", allTypeList.get(i).second.getMinDelay());
                    sensorDataJson.put("Version", allTypeList.get(i).second.getVersion());

                    if (allTypeList.get(i).second.isAdditionalInfoSupported() == true) {
                        sensorDataJson.put("isAdditionalInfoSupported", allTypeList.get(i).second.isAdditionalInfoSupported());
                    }
                    if (allTypeList.get(i).second.isDynamicSensor()) {
                        sensorDataJson.put("isDynamicSensor", allTypeList.get(i).second.isDynamicSensor());
                    }
                    if (allTypeList.get(i).second.isWakeUpSensor()) {
                        sensorDataJson.put("isWakeUpSensor", allTypeList.get(i).second.isWakeUpSensor());
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //https://developer.android.com/reference/android/hardware/Sensor#isDirectChannelTypeSupported(int)
                        if (allTypeList.get(i).second.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_MEMORY_FILE)){
                            sensorDataJson.put("isDirectChannelTypeSupported-TYPE_MEMORY_FILE", allTypeList.get(i).second.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_MEMORY_FILE));
                        }
                        if (allTypeList.get(i).second.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_HARDWARE_BUFFER)){
                            sensorDataJson.put("isDirectChannelTypeSupported-TYPE_HARDWARE_BUFFER", allTypeList.get(i).second.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_HARDWARE_BUFFER));
                        }
                        if (allTypeList.get(i).second.isDirectChannelTypeSupported(0)) {
                            sensorDataJson.put("isDirectChannelTypeSupported-0", allTypeList.get(i).second.isDirectChannelTypeSupported(0));
                        }
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.i("sensorDataList", "sensorDataJson = " + sensorDataJson);

                jsonArray.put(sensorDataJson);



                //UI展示
                list.add(new Pair<>(allTypeList.get(i).first, allTypeList.get(i).second.toString()));

            }

        }

        SaveFileToSd.saveToLocal(context, jsonArray);



        return list;
    }


}
