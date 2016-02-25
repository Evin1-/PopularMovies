package com.loopcupcakes.udacity.popularmovies.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.loopcupcakes.udacity.popularmovies.R;

/**
 * Created by evin on 1/30/16.
 */
public class ShareAppMagic {

    private static final String APP_PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";
    private static final String MORE_APPS_BY_DEVELOPER_URL = "https://play.google.com/store/apps/developer?id=Loop+Cupcakes";
    private static final String FACEBOOK_PAGE_ID = "1702080766716349";
    private static final String FACEBOOK_APP_PACKAGE = "com.facebook.katana";

    private static final String MARKETPLACE_PREFIX = "market://details?id=";
    private static final String FACEBOOK_APP_PREFIX = "fb://page/";
    private static final String FACEBOOK_WEB_PREFIX = "https://www.facebook.com/";

    public static void shareApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.checkoutAppMessage) + APP_PLAY_STORE_URL + context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent chooser = Intent.createChooser(intent, context.getString(R.string.tellFriendMessage) + context.getString(R.string.app_name));
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooser);
    }

    public static void openMoreApps(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MORE_APPS_BY_DEVELOPER_URL));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void likeApp(Context context) {
        Intent intent = null;
        try {
            context.getPackageManager().getPackageInfo(FACEBOOK_APP_PACKAGE, 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_APP_PREFIX + FACEBOOK_PAGE_ID));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_WEB_PREFIX + FACEBOOK_PAGE_ID));
        } finally {
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    public static void rateApp(Context context) {
        Uri uri = Uri.parse(MARKETPLACE_PREFIX + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        if (Build.VERSION.SDK_INT >= 21) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_PLAY_STORE_URL + context.getPackageName()));
            intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentWeb);
        }
    }
}
