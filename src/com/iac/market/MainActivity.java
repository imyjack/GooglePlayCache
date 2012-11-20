
package com.iac.market;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.iac.market.model.AppInfo;
import com.iac.market.model.Const;
import com.iac.market.net.NetUtil;
import com.iac.market.util.ShareUtil;

public class MainActivity extends Activity implements OnClickListener {

    private ListView mAppListView;

    private String mIconUrl;

    private Bitmap mBitmap;

    List<AppInfo> mAppInfoList = new ArrayList<AppInfo>();

    AppListAdapter mAppListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppListView = (ListView)findViewById(R.id.app_list);
        mAppListAdapter = new AppListAdapter(MainActivity.this, mAppInfoList);
        mAppListView.setAdapter(mAppListAdapter);
        mAppListView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()) {
                    case R.id.app_icon:
                        ShareUtil.shareAttachment(MainActivity.this, "");
                        break;

                    default:
                        break;
                }
                AppInfo appInfo = (AppInfo)parent.getItemAtPosition(position);
                try {
                    Uri uri = Uri.parse("market://details?id=" + appInfo.getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    MainActivity.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        init();
    }

    private ProgressDialog mDialogForBackup;

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:

                break;
            default:
                break;
        }
    }

    private void init() {
        mDialogForBackup = new ProgressDialog(MainActivity.this);
        mDialogForBackup.setMessage("Loading...");
        mDialogForBackup.setCancelable(false);
        mDialogForBackup.show();
        Log.e("","---------------------BEGIN");
        new Thread() {
            public void run() {
                JSONArray jArray = NetUtil.getJsonArrayFromUrl(Const.URL, "IAC APK");
                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject jobj = jArray.getJSONObject(i);
                        String apkname = jobj.getString(Const.APK_NAME);
                        String packagename = jobj.getString(Const.PACKAGE_NAME);
                        String appRatingCount = jobj.getString(Const.APP_RATING_COUNT);
                        String appDownloadCount = jobj.getString(Const.APP_DOWNLOAD_COUNT);
                        String appSize = jobj.getString(Const.APP_SIZE);
                        String appIconUrl = jobj.getString(Const.APP_ICON_URL);
                        Log.i(Const.APK_NAME, apkname);
                        Log.i(Const.PACKAGE_NAME, packagename);
                        Log.i(Const.APP_RATING_COUNT, appRatingCount);
                        Log.i(Const.APP_DOWNLOAD_COUNT, appDownloadCount);
                        Log.i(Const.APP_SIZE, appSize);
                        Log.i(Const.APP_ICON_URL, appIconUrl);
                        if (null == mIconUrl) {
                            mIconUrl = appIconUrl;
                        }
                        AppInfo info = new AppInfo();
                        info.setAppName(apkname);
                        info.setPackageName(packagename);
                        info.setAppRatingCount(appRatingCount);
                        info.setAppIconUrl(appIconUrl);
                        info.setAppDownloadCount(appDownloadCount);
                        info.setAppSize(appSize);
                        mAppInfoList.add(info);
                        NetUtil.downloadIconFromUrl(appIconUrl.toString(), packagename + ".png",
                                MainActivity.this);
                        Log.e("","...");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(0);
            };
        }.start();
        Log.e("","---------------------END");
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        if (null != mDialogForBackup && mDialogForBackup.isShowing()) {
                            mDialogForBackup.dismiss();
                        } else {
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                    mAppListAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }
        };
    };
}
