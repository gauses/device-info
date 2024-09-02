package com.song.deviceinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.song.deviceinfo.file.SaveFileToSd;
import com.song.deviceinfo.utils.LanguageUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor TYPE_ACCELEROMETER, TYPE_ACCELEROMETER_UNCALIBRATED, TYPE_ALL, TYPE_AMBIENT_TEMPERATURE, TYPE_DEVICE_PRIVATE_BASE, TYPE_GAME_ROTATION_VECTOR,
            TYPE_GEOMAGNETIC_ROTATION_VECTOR, TYPE_GRAVITY, TYPE_GYROSCOPE, TYPE_GYROSCOPE_UNCALIBRATED,
            TYPE_HEART_BEAT, TYPE_HEART_RATE, TYPE_HINGE_ANGLE, TYPE_LIGHT, TYPE_LINEAR_ACCELERATION, TYPE_LOW_LATENCY_OFFBODY_DETECT,
            TYPE_MAGNETIC_FIELD, TYPE_MAGNETIC_FIELD_UNCALIBRATED, TYPE_MOTION_DETECT, TYPE_ORIENTATION, TYPE_POSE_6DOF, TYPE_PRESSURE,
            TYPE_PROXIMITY, TYPE_RELATIVE_HUMIDITY, TYPE_ROTATION_VECTOR, TYPE_SIGNIFICANT_MOTION, TYPE_STATIONARY_DETECT, TYPE_STEP_COUNTER,
            TYPE_STEP_DETECTOR, TYPE_TEMPERATURE, REPORTING_MODE_CONTINUOUS, REPORTING_MODE_ONE_SHOT, REPORTING_MODE_ON_CHANGE, REPORTING_MODE_SPECIAL_TRIGGER;

    private static Integer	updateInterval = 500;

    private static Activity mActivity;
    static JSONArray sensorJsonArray = new JSONArray();

    //开始收集
    private static boolean startCollect = false;


    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static final Runnable dataCollectionRunnable = new Runnable() {
        @Override
        public void run() {
            startCollect = true;

            handler.postDelayed(this, 200); // 每0.1秒钟重复一次
        }
    };


    public static void startDataCollection() {

        Toast.makeText(mActivity, "10秒之后，1分钟采集就会开始,结束时候会有结束提示...........", Toast.LENGTH_LONG).show();

        handler.postDelayed(dataCollectionRunnable, 10000); // 10秒钟后开始

        handler.postDelayed(
                (new Runnable() {
                    @Override
                    public void run() {
                        handler.removeCallbacks(dataCollectionRunnable);
                        //保存
                        SaveFileToSd.saveToLocal(mActivity, sensorJsonArray);
                        ((Activity)mActivity).runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                startCollect = false;
                                showTips();

                            }
                        });
                    }
                }),
                10000 + 10000); // 10s后停止
    }

    static {
        System.loadLibrary("native-lib");
    }


    public static void showTips(){
        // Permission Denied
        AlertDialog mDialog = new AlertDialog.Builder(mActivity)
                .setTitle("友好提醒")
                .setMessage("1分钟采集已经结束，可以去SD卡/NestConfigxia看文件是否保存成功！")
                .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                })
                .setCancelable(true)
                .create();
        mDialog.show();
    }

    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtils.updateResources(newBase, LanguageUtils.getDefaultLanguage(newBase)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_system_file,
                R.id.nav_net, R.id.nav_thermal, R.id.nav_battery, R.id.nav_system, R.id.nav_build,
                R.id.nav_partitions, R.id.nav_store, R.id.nav_camera, R.id.nav_applications, R.id.nav_codecs,
                R.id.nav_input, R.id.nav_usb, R.id.nav_soc, R.id.nav_emulator, R.id.nav_virtual,
                R.id.nav_debug, R.id.nav_root, R.id.nav_hook, R.id.nav_device, R.id.nav_app,
                R.id.nav_wifi, R.id.nav_maps, R.id.nav_bluetooth, R.id.nav_others, R.id.nav_hardware)
                .setDrawerLayout(drawer)
                .build();

        mActivity = this;


        permissionHandler();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        //YPE_ACCELEROMETER, TYPE_ACCELEROMETER_UNCALIBRATED, TYPE_ALL, TYPE_AMBIENT_TEMPERATURE, TYPE_DEVICE_PRIVATE_BASE, TYPE_GAME_ROTATION_VECTOR,
        //            TYPE_GEOMAGNETIC_ROTATION_VECTOR, TYPE_GRAVITY, TYPE_GYROSCOPE, TYPE_GYROSCOPE_UNCALIBRATED,
        //            TYPE_HEART_BEAT, TYPE_HEART_RATE, TYPE_HINGE_ANGLE, TYPE_LIGHT, TYPE_LINEAR_ACCELERATION, TYPE_LOW_LATENCY_OFFBODY_DETECT,
        //            TYPE_MAGNETIC_FIELD, TYPE_MAGNETIC_FIELD_UNCALIBRATED, TYPE_MOTION_DETECT, TYPE_ORIENTATION, TYPE_POSE_6DOF, TYPE_PRESSURE,
        //            TYPE_PROXIMITY, TYPE_RELATIVE_HUMIDITY, TYPE_ROTATION_VECTOR, TYPE_SIGNIFICANT_MOTION, TYPE_STATIONARY_DETECT, TYPE_STEP_COUNTER,
        //            TYPE_STEP_DETECTOR, TYPE_TEMPERATURE, REPORTING_MODE_CONTINUOUS, REPORTING_MODE_ONE_SHOT, REPORTING_MODE_ON_CHANGE, REPORTING_MODE_SPECIAL_TRIGGER;

        //TYPE_ACCELEROMETER, TYPE_ACCELEROMETER_UNCALIBRATED, TYPE_ALL, TYPE_AMBIENT_TEMPERATURE, TYPE_DEVICE_PRIVATE_BASE, TYPE_GAME_ROTATION_VECTOR,
        TYPE_ACCELEROMETER = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        TYPE_ACCELEROMETER_UNCALIBRATED = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);
        TYPE_ALL = mSensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        TYPE_AMBIENT_TEMPERATURE = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        TYPE_DEVICE_PRIVATE_BASE = mSensorManager.getDefaultSensor(Sensor.TYPE_DEVICE_PRIVATE_BASE);
        TYPE_GAME_ROTATION_VECTOR = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        //TYPE_GEOMAGNETIC_ROTATION_VECTOR, TYPE_GRAVITY, TYPE_GYROSCOPE, TYPE_GYROSCOPE_UNCALIBRATED,
        TYPE_GEOMAGNETIC_ROTATION_VECTOR = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        TYPE_GRAVITY = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        TYPE_GYROSCOPE = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        TYPE_GYROSCOPE_UNCALIBRATED = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);

        //TYPE_HEART_BEAT, TYPE_HEART_RATE, TYPE_HINGE_ANGLE, TYPE_LIGHT, TYPE_LINEAR_ACCELERATION, TYPE_LOW_LATENCY_OFFBODY_DETECT,
        TYPE_HEART_BEAT = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_BEAT);
        TYPE_HEART_RATE = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        TYPE_HINGE_ANGLE = mSensorManager.getDefaultSensor(Sensor.TYPE_HINGE_ANGLE);
        TYPE_LIGHT = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        TYPE_LINEAR_ACCELERATION = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        TYPE_LOW_LATENCY_OFFBODY_DETECT = mSensorManager.getDefaultSensor(Sensor.TYPE_LOW_LATENCY_OFFBODY_DETECT);

        //TYPE_MAGNETIC_FIELD, TYPE_MAGNETIC_FIELD_UNCALIBRATED, TYPE_MOTION_DETECT, TYPE_ORIENTATION, TYPE_POSE_6DOF, TYPE_PRESSURE,
        TYPE_MAGNETIC_FIELD = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        TYPE_MAGNETIC_FIELD_UNCALIBRATED = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        TYPE_MOTION_DETECT = mSensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
        TYPE_ORIENTATION = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        TYPE_POSE_6DOF = mSensorManager.getDefaultSensor(Sensor.TYPE_POSE_6DOF);
        TYPE_PRESSURE = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        //TYPE_PROXIMITY, TYPE_RELATIVE_HUMIDITY, TYPE_ROTATION_VECTOR, TYPE_SIGNIFICANT_MOTION, TYPE_STATIONARY_DETECT, TYPE_STEP_COUNTER,
        TYPE_PROXIMITY = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        TYPE_RELATIVE_HUMIDITY = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        TYPE_ROTATION_VECTOR = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        TYPE_SIGNIFICANT_MOTION = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        TYPE_STATIONARY_DETECT = mSensorManager.getDefaultSensor(Sensor.TYPE_STATIONARY_DETECT);
        TYPE_STEP_COUNTER = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        //TYPE_STEP_DETECTOR, TYPE_TEMPERATURE, REPORTING_MODE_CONTINUOUS, REPORTING_MODE_ONE_SHOT, REPORTING_MODE_ON_CHANGE, REPORTING_MODE_SPECIAL_TRIGGER;
        TYPE_STEP_DETECTOR = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        TYPE_TEMPERATURE = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        REPORTING_MODE_CONTINUOUS = mSensorManager.getDefaultSensor(Sensor.REPORTING_MODE_CONTINUOUS);
        REPORTING_MODE_ONE_SHOT = mSensorManager.getDefaultSensor(Sensor.REPORTING_MODE_ONE_SHOT);
        REPORTING_MODE_ON_CHANGE = mSensorManager.getDefaultSensor(Sensor.REPORTING_MODE_ON_CHANGE);
        REPORTING_MODE_SPECIAL_TRIGGER = mSensorManager.getDefaultSensor(Sensor.REPORTING_MODE_SPECIAL_TRIGGER);

    }



    private JSONObject putSensorToJson(Sensor sensor){
                JSONObject sensorDataJson = new JSONObject();

                try {
                    sensorDataJson.put("Id", sensor.getId());
                    sensorDataJson.put("Name", sensor.getName());
                    sensorDataJson.put("Type", sensor.getType());
                    sensorDataJson.put("Vendor", sensor.getVendor());
                    sensorDataJson.put("Resolution", sensor.getResolution());

                    sensorDataJson.put("StringType", sensor.getStringType());
                    sensorDataJson.put("Resolution", sensor.getResolution());
                    sensorDataJson.put("ReportingMode", sensor.getReportingMode());
                    sensorDataJson.put("MaximumRange", sensor.getMaximumRange());
                    sensorDataJson.put("MaxDelay", sensor.getMaxDelay());
                    sensorDataJson.put("FifoReservedEventCount", sensor.getFifoReservedEventCount());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        sensorDataJson.put("HighestDirectReportRateLevel", sensor.getHighestDirectReportRateLevel());
                    }
                    sensorDataJson.put("FifoMaxEventCount", sensor.getFifoMaxEventCount());
                    sensorDataJson.put("Power", sensor.getPower());
                    sensorDataJson.put("MinDelay", sensor.getMinDelay());
                    sensorDataJson.put("Version", sensor.getVersion());

                    if (sensor.isAdditionalInfoSupported() == true) {
                        sensorDataJson.put("isAdditionalInfoSupported", sensor.isAdditionalInfoSupported());
                    }
                    if (sensor.isDynamicSensor()) {
                        sensorDataJson.put("isDynamicSensor", sensor.isDynamicSensor());
                    }
                    if (sensor.isWakeUpSensor()) {
                        sensorDataJson.put("isWakeUpSensor", sensor.isWakeUpSensor());
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        //https://developer.android.com/reference/android/hardware/Sensor#isDirectChannelTypeSupported(int)
                        if (sensor.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_MEMORY_FILE)){
                            sensorDataJson.put("isDirectChannelTypeSupported-TYPE_MEMORY_FILE", sensor.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_MEMORY_FILE));
                        }
                        if (sensor.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_HARDWARE_BUFFER)){
                            sensorDataJson.put("isDirectChannelTypeSupported-TYPE_HARDWARE_BUFFER", sensor.isDirectChannelTypeSupported(SensorDirectChannel.TYPE_HARDWARE_BUFFER));
                        }
                        if (sensor.isDirectChannelTypeSupported(0)) {
                            sensorDataJson.put("isDirectChannelTypeSupported-0", sensor.isDirectChannelTypeSupported(0));
                        }
                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Log.i("sensorDataList", "sensorDataJson = " + sensorDataJson);

                return sensorDataJson;


    }



    private final SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            if (startCollect == true) {
                JSONObject sensorDataJson = putSensorToJson(event.sensor);
                try {
                    sensorDataJson.put("accuracy", event.accuracy);
                    sensorDataJson.put("timestamp", event.timestamp);
                    if (event!= null) {
                        Log.i("TYPE_PRESSURE_X = ", Float.toString(event.values.length));
                        if (event.values.length == 1) {
                            sensorDataJson.put("X", event.values[0]);
                        }else if (event.values.length == 2){
                            sensorDataJson.put("X", event.values[0]);
                            sensorDataJson.put("Y", event.values[1]);
                        }else if (event.values.length == 3){
                            sensorDataJson.put("X", event.values[0]);
                            sensorDataJson.put("Y", event.values[1]);
                            sensorDataJson.put("Z", event.values[2]);
                        }else if (event.values.length == 4){
                            sensorDataJson.put("X", event.values[0]);
                            sensorDataJson.put("Y", event.values[1]);
                            sensorDataJson.put("Z", event.values[2]);
                            sensorDataJson.put("P", event.values[3]);

                        }else if (event.values.length == 5){
                            sensorDataJson.put("X", event.values[0]);
                            sensorDataJson.put("Y", event.values[1]);
                            sensorDataJson.put("Z", event.values[2]);
                            sensorDataJson.put("P", event.values[3]);
                            sensorDataJson.put("Q", event.values[4]);

                        }else if (event.values.length == 6){
                            sensorDataJson.put("X", event.values[0]);
                            sensorDataJson.put("Y", event.values[1]);
                            sensorDataJson.put("Z", event.values[2]);
                            sensorDataJson.put("P", event.values[3]);
                            sensorDataJson.put("Q", event.values[4]);
                            sensorDataJson.put("W", event.values[5]);

                        }



                    }


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                sensorJsonArray.put(sensorDataJson);
            }




            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                if (startCollect == true) {
//                    JSONObject sensorDataJson = putSensorToJson(event.sensor);
//                    try {
//                        sensorDataJson.put("accuracy", event.accuracy);
//                        sensorDataJson.put("timestamp", event.timestamp);
//                        if (event!= null) {
//                            sensorDataJson.put("X", event.values[0]);
//                            sensorDataJson.put("Y", event.values[1]);
//                            sensorDataJson.put("Z", event.values[2]);
//                        }
//
//
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    sensorJsonArray.put(sensorDataJson);
//                }




            }  else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {

                Log.i("TYPE_PRESSURE_X = ", Float.toString(event.values.length));
//                Log.i("TYPE_PRESSURE_X = ", Float.toString(event.values[0]));
//                Log.i("TYPE_PRESSURE_Y = ", event.values[1]+"");
//                Log.i("TYPE_PRESSURE_Z = ", Float.toString(event.values[2]));

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(navigationView, navController);
            } else {
                // Permission Denied
                AlertDialog mDialog = new AlertDialog.Builder(this)
                        .setTitle("友好提醒")
                        .setMessage("您已拒绝权限,请开启权限！")
                        .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                startDataCollection();

                            }
                        })
                        .setCancelable(true)
                        .create();
                mDialog.show();
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();


//        private Sensor TYPE_ACCELEROMETER, TYPE_ACCELEROMETER_UNCALIBRATED, TYPE_ALL, TYPE_AMBIENT_TEMPERATURE, TYPE_DEVICE_PRIVATE_BASE, TYPE_GAME_ROTATION_VECTOR,
//                TYPE_GEOMAGNETIC_ROTATION_VECTOR, TYPE_GRAVITY, TYPE_GYROSCOPE, TYPE_GYROSCOPE_UNCALIBRATED,
//                TYPE_HEART_BEAT, TYPE_HEART_RATE, TYPE_HINGE_ANGLE, TYPE_LIGHT, TYPE_LINEAR_ACCELERATION, TYPE_LOW_LATENCY_OFFBODY_DETECT,
//                TYPE_MAGNETIC_FIELD, TYPE_MAGNETIC_FIELD_UNCALIBRATED, TYPE_MOTION_DETECT, TYPE_ORIENTATION, TYPE_POSE_6DOF, TYPE_PRESSURE,
//                TYPE_PROXIMITY, TYPE_RELATIVE_HUMIDITY, TYPE_ROTATION_VECTOR, TYPE_SIGNIFICANT_MOTION, TYPE_STATIONARY_DETECT, TYPE_STEP_COUNTER,
//                TYPE_STEP_DETECTOR, TYPE_TEMPERATURE, REPORTING_MODE_CONTINUOUS, REPORTING_MODE_ONE_SHOT, REPORTING_MODE_ON_CHANGE, REPORTING_MODE_SPECIAL_TRIGGER;

        mSensorManager.registerListener(mSensorEventListener, TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_ACCELEROMETER_UNCALIBRATED, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_ALL, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_AMBIENT_TEMPERATURE, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_DEVICE_PRIVATE_BASE, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_GAME_ROTATION_VECTOR, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(mSensorEventListener, TYPE_GEOMAGNETIC_ROTATION_VECTOR, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_GRAVITY, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_GYROSCOPE, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_GYROSCOPE_UNCALIBRATED, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(mSensorEventListener, TYPE_HEART_BEAT, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_HEART_RATE, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_HINGE_ANGLE, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_LIGHT, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_LINEAR_ACCELERATION, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_LOW_LATENCY_OFFBODY_DETECT, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(mSensorEventListener, TYPE_MAGNETIC_FIELD, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_MAGNETIC_FIELD_UNCALIBRATED, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_MOTION_DETECT, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_ORIENTATION, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_POSE_6DOF, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_PRESSURE, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(mSensorEventListener, TYPE_PROXIMITY, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_RELATIVE_HUMIDITY, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_ROTATION_VECTOR, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_SIGNIFICANT_MOTION, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_STATIONARY_DETECT, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_SIGNIFICANT_MOTION, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_STEP_COUNTER, SensorManager.SENSOR_DELAY_NORMAL);

        //                TYPE_STEP_DETECTOR, TYPE_TEMPERATURE, REPORTING_MODE_CONTINUOUS, REPORTING_MODE_ONE_SHOT, REPORTING_MODE_ON_CHANGE, REPORTING_MODE_SPECIAL_TRIGGER;

        mSensorManager.registerListener(mSensorEventListener, TYPE_STEP_DETECTOR, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, TYPE_TEMPERATURE, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, REPORTING_MODE_CONTINUOUS, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, REPORTING_MODE_ONE_SHOT, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, REPORTING_MODE_ON_CHANGE, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, REPORTING_MODE_SPECIAL_TRIGGER, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
    }


    private void permissionHandler() {
        // 存储和定位权限申请
        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission_group.PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA}, 1);
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.action_settings:
                navController.navigate(R.id.nav_settings);
                break;
            case R.id.action_about:
                navController.navigate(R.id.nav_about);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
