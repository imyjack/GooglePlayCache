
package com.iac.market;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iac.market.model.AppInfo;
import com.iac.market.model.Const;
import com.iac.market.util.StorageUtil;

public class AppListAdapter extends ArrayAdapter<AppInfo> {
    private final LayoutInflater mInflater;

    private final MainActivity mActivity;

    public AppListAdapter(final MainActivity activity, final List<AppInfo> objects) {
        super(activity, 0, objects);
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AppInfo currentAppInfo = getItem(position);
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.app_list_item, null);
        }// end if

        ViewHolder viewCache = (ViewHolder)convertView.getTag();
        if (null == viewCache) {
            viewCache = new ViewHolder();
            viewCache.mAppIcon = (ImageView)convertView.findViewById(R.id.app_icon);
            viewCache.mAppName = (TextView)convertView.findViewById(R.id.app_name);
            viewCache.mAppRatingCount = (TextView)convertView.findViewById(R.id.app_rating);
            viewCache.mAppSize = (TextView)convertView.findViewById(R.id.app_size);
            viewCache.mAppDownloadCount = (TextView)convertView.findViewById(R.id.app_download);
            viewCache.mAppPackageName = (TextView)convertView.findViewById(R.id.app_packagename);
        }
        String appName = currentAppInfo.getAppName();
        String packageName = currentAppInfo.getPackageName();
        String iconName = packageName + ".png";
        String iconPath = StorageUtil.getExternalStorageDirectory(mActivity).getAbsolutePath()
                + Const.APP_ICON_DIR + iconName;
        Bitmap bm = getBitmapFromStorage(iconPath);
        viewCache.mAppIcon.setImageBitmap(bm);
        viewCache.mAppName.setText(appName);

        String ratingCount = currentAppInfo.getAppRatingCount();
        String appSize = currentAppInfo.getAppSize();
        String downloadCount = currentAppInfo.getAppDownloadCount();

        viewCache.mAppRatingCount.setText(" [" + ratingCount + "]");
        viewCache.mAppPackageName.setText(packageName);
        viewCache.mAppSize.setText(" [" + appSize + "]");
        viewCache.mAppDownloadCount.setText(downloadCount);

        return convertView;
    }

    private class ViewHolder {

        private ImageView mAppIcon;

        private TextView mAppName;

        private TextView mAppRatingCount;

        private TextView mAppSize;

        private TextView mAppDownloadCount;

        private TextView mAppPackageName;

    }

    private Bitmap getBitmapFromStorage(String path) {
        return BitmapFactory.decodeFile(path);
    }
}
