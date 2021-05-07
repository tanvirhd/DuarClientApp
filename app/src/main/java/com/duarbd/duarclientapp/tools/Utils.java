package com.duarbd.duarclientapp.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.duarbd.duarclientapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    // save data to sharedPreference
    public static void savePref(String name, String value) {
        SharedPreferences pref = App.getInstance().getContext().getSharedPreferences(App.getInstance().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        editor.apply();
    } // save data to sharedPreference

    public static void savePref(String name, int value) {
        SharedPreferences pref = App.getInstance().getContext().getSharedPreferences(App.getInstance().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    public static void savePrefBoolean(String name, boolean value) {
        SharedPreferences pref = App.getInstance().getContext().getSharedPreferences(App.getInstance().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    public static void clearPref() {
        SharedPreferences pref = App.getInstance().getContext().getSharedPreferences(App.getInstance().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().apply();
    }

    // get data EventFrom shared preference
    public static String getPref(String name, String defaultValue) {
        SharedPreferences pref = App.getInstance().getContext().getSharedPreferences(App.getInstance().getPackageName(), Context.MODE_PRIVATE);
        return pref.getString(name, defaultValue);
    }

    public static int getPref(String name, int defaultValue) {
        SharedPreferences pref = App.getInstance().getContext().getSharedPreferences(App.getInstance().getPackageName(), Context.MODE_PRIVATE);
        return pref.getInt(name, defaultValue);
    }

    public static Boolean getPrefBoolean(String name, boolean defaultValue) {
        SharedPreferences pref = App.getInstance().getContext().getSharedPreferences(App.getInstance().getPackageName(), Context.MODE_PRIVATE);
        return pref.getBoolean(name, defaultValue);
    }

    public static String getCustentDateTime24HRFormat(){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dateFormatTime = new SimpleDateFormat("HH:MM");
        return dateFormatDate.format(calendar.getTime())+" "+dateFormatTime.format(calendar.getTime());
    }

    public static String getCustentDate(){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormatDate.format(calendar.getTime());
    }

    public static String[] getCustentDateArray(){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
        String date=dateFormatDate.format(calendar.getTime());
        String[] seperated=date.split("-");
        return seperated;
    }

    public static Dialog setupLoadingDialog(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);  //this prevents dimming effect
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
