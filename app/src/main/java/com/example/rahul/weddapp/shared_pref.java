package com.example.rahul.weddapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 01-10-2015.
 */
public class shared_pref
{

    private static int Frag_shw=0;
    static int Update_flag=0;
    private SharedPreferences sp;
    public shared_pref(String file_nm,Context ctx)
    {
        setSp(ctx.getSharedPreferences(file_nm,ctx.MODE_PRIVATE));
    }

    public static int getFrag_shw() {
        return Frag_shw;
    }



    public static void setFrag_shw(int frag_shw) {
        Frag_shw = frag_shw;
    }


    void AddtoPreferance(String key, String value)
    {
        SharedPreferences.Editor editor = getSp().edit();
        editor.putString(key,value);
        editor.commit();

    }
    String GetFromPreference(String key)
    {
        return getSp().getString(key, "NO_VAL");
    }

    void RemoveFieldfromPref(String field_name)
    {
        getSp().edit().remove(field_name).commit();
    }

    static void RemovingAlldatainPreference(String file_nm , Context ctx)
    {
        SharedPreferences sp = ctx.getSharedPreferences(file_nm,ctx.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


    public SharedPreferences getSp() {
        return sp;
    }

    public void setSp(SharedPreferences sp) {
        this.sp = sp;
    }
}
