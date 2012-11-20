/*******************************************************************************
 *
 *
 *
 *    AndroidSDK
 *
 *    ShareUtil
 *
 *    @author:
 *    @since:
 *    @version: 1.0
 *
 ******************************************************************************/

package com.iac.market.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * ShareUtil of AndroidSDK.
 *
 * @author zuochen
 */

public class ShareUtil {
    public static void share(Context context, String subjcet, String body) {
        final Uri mUri = Uri.parse("mailto:");
        final Intent intent = new Intent(Intent.ACTION_SEND, mUri);
        final Uri marketUri = Uri.parse("market://details?id=" + context.getPackageName());
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {
            ""
        });
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subjcet);
        intent.putExtra(Intent.EXTRA_TEXT, body + marketUri);
        intent.putExtra(Intent.EXTRA_CC, new String[] {
            ""
        });
        context.startActivity(Intent.createChooser(intent, "Choose Email Client"));
    }

    public static void shareAttachment(Context context, String filePath) {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");
        it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");
        context.startActivity(Intent.createChooser(it, "Choose Share Client"));

    }
}
