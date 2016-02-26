package com.example.rahul.weddapp;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Admin on 17-12-2015.
 */
public class rsvp_frgmnt extends Fragment {

    private TableLayout table;
    private TextView bride;
    private TextView groom;
    private ImageView imgvu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rsvp_frgmnt, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        table = (TableLayout)view.findViewById(R.id.table_layout);
        bride = (TextView)view.findViewById(R.id.tvbride);
        groom = (TextView)view.findViewById(R.id.tvgroom);
        imgvu = (ImageView)view.findViewById(R.id.imageView);
        //define the async tasks for getting the Events and Wedding Image
        shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
        bride.setText(sp.GetFromPreference("BRD_NM"));
        groom.setText(sp.GetFromPreference("GRM_NM"));
        DownloadImage dimg = new DownloadImage();
        dimg.execute(sp.GetFromPreference("WED_IMG"));
      //  Event_Lst nel = new Event_Lst();
      // nel.execute();
        
    }
    class Event_Lst extends AsyncTask<Void,Void,String>
    {
       @Override
        protected String doInBackground(Void... params) {
            shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
            shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
            String url_chk = nt.GetFromPreference("B_URI") + "/Events/Eventall/"+sp.GetFromPreference("WED_CD");
            String res_str = "NO_VAL";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet hgt = new HttpGet(url_chk);
            try
            {
                HttpResponse rspns = httpclient.execute(hgt);
                if (rspns.getStatusLine().getStatusCode() == 200)
                {
                    res_str = EntityUtils.toString(rspns.getEntity());
                }
            }
            catch (Exception e) {
                res_str = "ERROR " + e.getMessage();
                return res_str;
            }


            return res_str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.startsWith("ERROR "))
            {
                Log.d("FB","ERROR Occured "+s);
            }
            else if(s.startsWith("NO_VAL"))
            {
                Log.d("FB","No Val"+s);
            }
            else
            {
                try {

                    //List<Pair<String,String>> nwlst = new ArrayList<Pair<String,String>>();

                    JSONArray jarr = new JSONArray(s);
                    JSONObject job;
                          for(int i=0;i<jarr.length();i++)
                          {
                              job = jarr.getJSONObject(i);
                              String day = job.getString("day");
                              String wedd_name = job.getString("event_name");
                                //Pair<String,String> pr = new Pair<>(day,wedd_name);
                              TableRow tr = new TableRow(getActivity().getApplicationContext());
                              tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                              TextView dy = new TextView(getActivity().getApplicationContext());
                              dy.setText(day);
                              TextView evnt_nm = new TextView(getActivity().getApplicationContext());
                              evnt_nm.setText(wedd_name);

                              tr.addView(dy);
                              tr.addView(tr);
                              table.addView(tr);

                              Log.e("FB","DAY"+day);
                              Log.e("FB","WEdding Name"+wedd_name);

                          }

                    }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }

    class DownloadImage extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params) {

            HttpURLConnection connection = null;
            InputStream input = null;
            //Log.d("FB","");
             Bitmap img=null;
            URL url = null;
            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                input = connection.getInputStream();
                img = BitmapFactory.decodeStream(input);

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
          return img;

        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);


            imgvu.setImageBitmap(bmp);


        }
    }

}

