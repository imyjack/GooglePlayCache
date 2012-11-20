
package com.iac.market.model;


public class AppInfo {
    // no need search
    private String packageName;

    private String appName;

    private String appRatingCount;

    private String appDownloadCount;

    private String appSize;

    private String appIconUrl;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppRatingCount() {
        return appRatingCount;
    }

    public void setAppRatingCount(String appRatingCount) {
        this.appRatingCount = appRatingCount;
    }

    public String getAppDownloadCount() {
        return appDownloadCount;
    }

    public void setAppDownloadCount(String appDownloadCount) {
        this.appDownloadCount = appDownloadCount;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public String getAppIconUrl() {
        return appIconUrl;
    }

    public void setAppIconUrl(String appIconUrl) {
        this.appIconUrl = appIconUrl;
    }
}
