package com.example.rahul.weddapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Admin on 29-09-2015.
 */
public class Misc
{
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
   public static int DptoPixel(String dpi_str,int dp)
   {
       int dpi = Integer.valueOf(dpi_str);
       int pixel = dp * (dpi/160);
       return pixel;
   }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //(!activeNetwork.isConnected())
        if ((null != activeNetwork)) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

//    public static String getConnectivityStatusString(Context context) {
//        int conn = Misc.getConnectivityStatus(context);
//        String status = null;
//        if (conn == Misc.TYPE_WIFI) {
//            status = "Wifi enabled";
//        } else if (conn == Misc.TYPE_MOBILE) {
//            status = "Mobile data enabled";
//        } else if (conn == Misc.TYPE_NOT_CONNECTED) {
//            status = "Not connected to Internet";
//        }
//        return status;
//    }
public static String getYear(String d)
{
    String resp_year = null;
    resp_year = d.substring(0,4);
    return resp_year;
}

    public static String getdayMonth(String d)
    {
        String resp_day_mon=null;

        String mn = d.substring(5, 7);
        String dd = d.substring(8,10);
        int mm = Integer.parseInt(mn);
        if(mm==1)
        {
            resp_day_mon = "JAN "+dd;
        }
        else if(mm==2)
        {
            resp_day_mon = "FEB "+dd;
        }
        else if(mm==3)
        {
            resp_day_mon = "MAR "+dd;
        }
        else if(mm==4)
        {
            resp_day_mon = "APR "+dd;
        }
        else if(mm==5)
        {
            resp_day_mon = "MAY "+dd;
        }
        else if(mm==6)
        {
            resp_day_mon = "JUN "+dd;
        }
        else if(mm==7)
        {
            resp_day_mon = "JUL "+dd;
        }
        else if(mm==8)
        {
            resp_day_mon = "AUG "+dd;
        }
        else if(mm==9)
        {
            resp_day_mon = "SEP "+dd;
        }
        else if(mm==10)
        {
            resp_day_mon = "OCT "+dd;
        }
        else if(mm==11)
        {
            resp_day_mon = "NOV "+dd;
        }
        else if(mm==12)
        {
            resp_day_mon = "DEC "+dd;
        }

        return resp_day_mon;
    }

    public static void setClipboard(Context context,String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

}
