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

        //文件夹路径
        File dir = new File(Environment.getExternalStorageDirectory() + "/NestConfig/");
//        File dir = new File("data/local/tmp");

        //文件名
        String fileName = "NestConfig.json";


        try {
            //文件夹不存在和传入的value值为1时，才允许进入创建
            if (!dir.exists()) {
                dir.mkdirs();
            }

                File file = new File(dir, fileName);

                OutputStream out = new FileOutputStream(file, false);
                out.write(jsonArray.toString().getBytes());
                out.close();
                Log.i("RecordApp", "保存Config成功 path:" + file.getPath());
                return file.getPath();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
