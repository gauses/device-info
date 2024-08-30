package com.song.deviceinfo.file;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import androidx.core.util.Pair;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class SaveFileToSd {

    /**
     * 方法名：saveToLocal()
     * 功    能：创建隐藏文件夹,保存json到本地
     * 参    数：无
     * 返回值：String
     */
    public static String saveToLocal(Context context, JSONArray jsonArray) {

        //List<Pair<String, JSONObject>> jsonList

        //文件夹路径
        File dir = new File(Environment.getExternalStorageDirectory() + "/NestConfig/");
        //文件名
        String fileName = "config.json";


        try {
            //文件夹不存在和传入的value值为1时，才允许进入创建
            if (!dir.exists()) {
                dir.mkdirs();
            }

//                String json = jsonObject.toString();
                File file = new File(dir, fileName);

                OutputStream out = new FileOutputStream(file, false);
//                out.write(json.getBytes());
                out.write(jsonArray.toString().getBytes());
                out.close();
                Log.i("RecordApp", "保存Config成功 path:" + file.getPath());
//                Toast.makeText(context, "保存传感器配置成功,路径："+file.getPath(), Toast.LENGTH_LONG).show();
                return file.getPath();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
