
package com.iac.market.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.anim;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.iac.market.model.Const;
import com.iac.market.util.FileUtil;
import com.iac.market.util.StorageUtil;

public class NetUtil {

    private static final String TAG = NetUtil.class.getSimpleName();

    public static JSONObject getJsonObjectFromUrl(String url) {

        String strResult = "";
        HttpGet httpRequest = new HttpGet(url);
        try {
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(strResult.toString());
            Log.d(TAG, jsonObject.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray getJsonArrayFromUrl(String url, String objName) {
        JSONObject jsonObject = getJsonObjectFromUrl(url);
        JSONArray array = null;
        try {
            array = (JSONArray)jsonObject.get(objName);
            Log.d(TAG, array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static void downloadIconFromUrl(String url, String iconName, Context context) {
        URL connUrl = null;
        try {
            connUrl = new URL(url);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        String path = StorageUtil.getExternalStorageDirectory(context).getAbsolutePath() + Const.APP_ICON_DIR + iconName;
        File tempFile = new File(path);
        if(!tempFile.exists()){
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection)connUrl.openConnection();
                conn.setConnectTimeout(4 * 1000);
                conn.connect();
                InputStream is = conn.getInputStream();
                FileUtil.copyStreamToFile(is, tempFile);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.d(TAG, iconName + "is already exists...");
        }
    }
    public static Bitmap getBitmapFromUrl(URL url, Context context) {
        Bitmap bitmap = null;
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(4 * 1000);
            conn.connect();
            InputStream is = conn.getInputStream();
            String path = StorageUtil.getExternalStorageDirectory(context).getAbsolutePath() + Const.APP_ICON_DIR;
            //FileUtil.copyStreamToFile(is, new File(path+"abcdefgx.png"));

            //bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
