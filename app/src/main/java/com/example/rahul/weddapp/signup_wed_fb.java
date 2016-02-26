package com.example.rahul.weddapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


public class signup_wed_fb extends Fragment {

    View parent;
    FragmentManager f_manager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_signup_wed_fb, container, false);
        return parent;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View parent_view = getActivity().findViewById(R.id.rel_lay_show_grp_1);
        parent_view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

        final shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
        final shared_pref sp = new shared_pref("LOGIN", getActivity().getApplicationContext());


        final ImageView imv = (ImageView) view.findViewById(R.id.imgvu);
        imv.setVisibility(View.VISIBLE);

        final TextView tvnm = (TextView) view.findViewById(R.id.txtvunm);
        //tvnm.setVisibility(View.VISIBLE);


        final TextView tvphn = (TextView) view.findViewById(R.id.txtvuphn);


        final TextView tvemail = (TextView) view.findViewById(R.id.txtvuemail);
        //tvemail.setVisibility(View.VISIBLE);

        LinearLayout radio = (LinearLayout) view.findViewById(R.id.radioSex);
        //radio.setVisibility(View.VISIBLE);


        LinearLayout btns = (LinearLayout) view.findViewById(R.id.llbtns);

        Button fb_logout = (Button) view.findViewById(R.id.btnNext);

        final Button save = (Button) view.findViewById(R.id.btnsave);

        //btns.setVisibility(View.VISIBLE);
        //radio.setVisibility(View.VISIBLE);

//            final ImageView imv = (ImageView)findViewById(R.id.imgvu);
//            TextView tvnm = (TextView) findViewById(R.id.txtvunm);
//            TextView tvemail = (TextView)findViewById(R.id.txtvuemail);
//            TextView tvphn = (TextView)findViewById(R.id.txtvuphn);
//
        final RadioButton radmale = (RadioButton) radio.findViewById(R.id.rbtnMale);
        final RadioButton radfemale = (RadioButton) radio.findViewById(R.id.rbtnFemale);

//        tvemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus == false) {
//                    if (tvemail.getText().toString() == "") {
//                        tvemail.setError("");
//                        tvemail.requestFocus(); // can be removed....
//
//                    } else {
//                        class checkEmail extends AsyncTask<Void, Void, String> {
//                            TransparentDialog td = new TransparentDialog();
//                            @Override
//                            protected void onPreExecute() {
//                                super.onPreExecute();
//
//                                android.support.v4.app.FragmentManager fm = getFragmentManager();
//                                td.show(fm, "CUSTM_DIALOG");
//
//                            }
//
//                            @Override
//                            protected String doInBackground(Void... params) {
//                                String resp="";
//                                HttpClient httpclient = new DefaultHttpClient();
//                                String uri = nt.GetFromPreference("B_URI")+"/users/Check?phone=NO_VAL&email="+sp.GetFromPreference("EMAIL");
//                                //Log.d("HASH_KEY_GEN",URLEncoder.encode(uri));
//                                //contxt = params[0];
//
//                                HttpGet httpget = new HttpGet(uri);
//                                //HttpPost httpPost = new HttpPost(uri);
//                                try {
//                                    HttpResponse responce = httpclient.execute(httpget);
//                                    //  Log.d("FB","IN RESPONCE...");
//                                    Log.d("FB","RESPONCE-->"+responce.toString());
//
//                                    if (responce.getStatusLine().getStatusCode() ==200 ) {
//                                        resp = EntityUtils.toString(responce.getEntity());
//                                        //  Log.d("FB","IN OK RESPONCE");
//                                    } else {
//                                        resp = "NO_REC";
//                                        //   Log.d("HASH_KEY_GEN","RUNNING in ERROR");
//                                    }
//                                    return resp;
//                                } catch (ConnectTimeoutException e) {
//                                    resp = "Error..Host Unreachable";
//                                    return resp;
//                                } catch (Exception e) {
//                                    resp = "Error..Please try again";
//                                    return resp;
//                                }
//                            }
//
//
//                            @Override
//                            protected void onPostExecute(String aVoid) {
//                                super.onPostExecute(aVoid);
//
//                                td.dismiss();
//                            }
//                        }
//
//                        checkEmail ci = new checkEmail();
//                        ci.execute();
//                    }
//
//                }
//
//
//            }
//        });


        class DownLoadImage extends AsyncTask<String, Integer, Bitmap> {
            ProgressDialog dialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(getActivity(),"Please Wait","Process in Progress",true);
                dialog.setCancelable(false);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmpa = null;



                HttpURLConnection connection = null;
                InputStream input = null;
                try {
                    URL url = new URL((params[0]).toString());
                    connection = (HttpURLConnection) url.openConnection();
                    //content_len= connection.getContentLength();
                    connection.setDoInput(true);
                    connection.connect();
                    input = connection.getInputStream();
                    bitmpa = BitmapFactory.decodeStream(input);


                } catch (Exception e) {
                    e.printStackTrace();
                }


                return bitmpa;
            }


            @Override
            protected void onPostExecute(Bitmap bitmap) {
                dialog.dismiss();
                super.onPostExecute(bitmap);
                imv.setImageBitmap(bitmap);
            }
        }

        class GetUserInfo extends AsyncTask<String, Void, String> {

            ProgressDialog dialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(getActivity(),"Please Wait","Process in Progress",true);
                dialog.setCancelable(false);
            }


            @Override
            protected String doInBackground(String... params) {

                String resp_str = "";
                HttpClient client = new DefaultHttpClient();
                String url = nt.GetFromPreference("B_URI") + "/users/" + params[0];
                HttpGet httpget = new HttpGet(url);
                Log.d("FB","GETUSERINFO STARTING");
                Log.d("FB","URI--"+url);

                try {
                    HttpResponse httpresponce = client.execute(httpget);

                    if (httpresponce.getStatusLine().getStatusCode() == 200) {
                        resp_str = EntityUtils.toString(httpresponce.getEntity());
                        Log.d("FB","GETUSERINFO SUCCESS"+resp_str);
                    }


                } catch (Exception e) {
                    resp_str = "ERROR " + e.getMessage();
                    Log.d("FB","GET_USER_INFO ERROR");
                }

                return resp_str;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();

                try {
                    if (s.startsWith("ERROR")) {
                        Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("FB","OUTPUT--->"+s);
                        JSONObject jobj = new JSONObject(s);
                        if (((jobj.getString("name")) != "") || (jobj.getString("name") != null)) {
                            tvnm.setText((jobj.getString("name")));
                        }
                        if (((jobj.getString("email")) != "") || (jobj.getString("email") != null)) {
                            tvemail.setText((jobj.getString("email")));
                        }
                        if (((jobj.getString("phone")) != "") || (jobj.getString("phone") != null)) {
                            tvphn.setText((jobj.getString("phone")));
                        }
                        if (((jobj.getString("gender")) != "") || (jobj.getString("gender") != null)) {
                            if ((jobj.getString("gender") == "MALE") ||(jobj.getString("gender").equals("MALE"))) {
                                radmale.setChecked(true);
                            } else {
                                radfemale.setChecked(true);
                            }
                        }
                        if (((jobj.getString("dp")) != "") || (jobj.getString("dp") != null)) {
                            DownLoadImage dw = new DownLoadImage();
                            dw.execute(jobj.getString("dp"));
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        ///////

        //Bitmap mIcon_val = null;

        if ((sp.GetFromPreference("BACK_REG") == "NO") || (sp.GetFromPreference("BACK_REG").equals("NO"))) {
            if (sp.GetFromPreference("GENDER") == "MALE") {
                radmale.setChecked(true);
            }
            if (sp.GetFromPreference("GENDER") == "FEMALE") {
                radfemale.setChecked(true);
            }
            if (sp.GetFromPreference("EMAIL") != "NO_VAL") {
                tvemail.setText(sp.GetFromPreference("EMAIL"));
            }
            if (sp.GetFromPreference("NAME") != "NO_VAL") {
                tvnm.setText(sp.GetFromPreference("NAME"));
            }


            if ((sp.GetFromPreference("PIC_URL") != "NO_VAL")) {
                DownLoadImage dw = new DownLoadImage();
                dw.execute(sp.GetFromPreference("PIC_URL"));
                //dw.execute("http://192.168.1.8:8080/WeddingApp/GetimageServ?image=1");
            }
        } else if ((sp.GetFromPreference("BACK_REG") == "YES") || (sp.GetFromPreference("BACK_REG").equals("YES"))) {
            save.setText("UPDATE");
            GetUserInfo gui = new GetUserInfo();
            gui.execute(sp.GetFromPreference("EMAIL"));

        }


        fb_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Log.e("FB", "LOG OUT facebook");
                shared_pref sp = new shared_pref("LOGIN", getActivity().getApplicationContext());
                sp.RemoveFieldfromPref("ACCESSTOKEN");
                sp.RemoveFieldfromPref("PIC_URL");
                sp.RemoveFieldfromPref("EMAIL");
                sp.RemoveFieldfromPref("NAME");
                sp.RemoveFieldfromPref("BACK_REG");
                sp.RemoveFieldfromPref("WED_CD");
                sp.RemoveFieldfromPref("BRD_NM");
                sp.RemoveFieldfromPref("GRM_NM");
                Intent intnt = new Intent(getActivity().getApplicationContext(), splash.class);
                getActivity().finish(); // finishing this Activity  // added
                intnt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);     // added
                intnt.putExtra("LOGGED_IN", "LOGOUT");
                startActivity(intnt);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((tvemail.getText().toString() == "") || (tvnm.getText().toString() == "") || (tvphn.getText().toString() == "")) {
                    if (tvemail.getText().toString() == "") {
                        tvemail.setError("Can not be blank.");
                    } else if (tvnm.getText().toString() == "") {
                        tvnm.setError("Can not be blank.");
                    } else if (tvphn.getText().toString() == "") {
                        tvphn.setError("Can not be blank.");
                    }

                } else {
                    Log.e("FB", "SAVE CLICK....");


                    Bitmap fb_img = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                    byte[] img_byte = null;


                    try {
                        Log.e("FB", "SAVE CLICK....try block");
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        fb_img.compress(Bitmap.CompressFormat.JPEG, 60, bos);
                        img_byte = bos.toByteArray();
                        final String name, email, phone;
                        String gender = "FEMALE";

                        name = tvnm.getText().toString();
                        email = tvemail.getText().toString();
                        phone = tvphn.getText().toString();
                        if (radmale.isChecked()) {
                            gender = "MALE";
                        }

                        // image successfull converted to bytee

//                    String resp=null;
//                    HttpClient httpclient = new DefaultHttpClient();
//                    HttpPost post = new HttpPost(url);
//                    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
//                    entity.addPart("NAME", new StringBody(sp.GetFromPreference("NAME")));
//                    entity.addPart("EMAIL", new StringBody(sp.GetFromPreference("EMAIL")));
//                    entity.addPart("GENDER", new StringBody(sp.GetFromPreference("GENDER")));
//                    entity.addPart("", new ByteArrayBody(img_byte, "image/jpeg"));
//
//                    HttpEntity ent =  entity.build();
//
//                    post.setEntity(ent);
//                    HttpResponse responce = httpclient.execute(post);
//                    HttpEntity respentity = responce.getEntity();
//                    resp = EntityUtils.toString((HttpEntity) responce);

                        final byte[] finalImg_byte = img_byte;
                        final String finalGender = gender;
                        class uploadtoServ extends AsyncTask<String, Void, String> {

                            TransparentDialog td = new TransparentDialog();

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                android.support.v4.app.FragmentManager fm = getFragmentManager();
                                td.show(fm, "CUSTM_DIALOG");

                            }

                            @Override
                            protected String doInBackground(String... params) {
                                String resp = null;
                                Log.d("FB", "PARAM VALUE-->" + params[0]);

                                if ((params[0] == "Save") || (params[0].equals("Save"))) {
                                    Log.d("FB", "IN SAVE");
                                    String url = nt.GetFromPreference("B_URI") + "/users/AddUserNew";
                                    HttpClient httpclient = new DefaultHttpClient();
                                    HttpPost post = new HttpPost(url);
                                    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
                                    try {
                                        //Log.e("FB", "SAVE CLICK....Async task");
                                        entity.addPart("NAME", new StringBody(name));
                                        entity.addPart("EMAIL", new StringBody(email));
                                        entity.addPart("GENDER", new StringBody(finalGender));
                                        entity.addPart("PHONE", new StringBody(phone));
                                        entity.addPart("PIC", new ByteArrayBody(finalImg_byte, "image/jpeg"));

                                        HttpEntity ent = entity.build();

                                        post.setEntity(ent);
                                        HttpResponse responce = httpclient.execute(post);
                                        HttpEntity respentity = responce.getEntity();
                                        resp = EntityUtils.toString(respentity);
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                        resp = e.getMessage();
                                        return resp;
                                    }
                                } else {
                                    Log.d("FB", "IN UPDATE");
                                    String url = nt.GetFromPreference("B_URI")+"/users/UpdateUsr";
                                    Log.d("FB","Link-->"+url);
                                    HttpClient httpclient = new DefaultHttpClient();
                                    //HttpPut put = new HttpPut(url);
                                    HttpPost post = new HttpPost(url);
                                    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
                                    try {
                                        entity.addPart("USR_NM", new StringBody(sp.GetFromPreference("EMAIL")));
                                        entity.addPart("NAME", new StringBody(name));
                                        //entity.addPart("EMAIL", new StringBody(email));
                                        entity.addPart("GENDER", new StringBody(finalGender));
                                        entity.addPart("PHONE", new StringBody(phone));
                                        entity.addPart("PIC", new ByteArrayBody(finalImg_byte, "image/jpeg"));

                                        HttpEntity ent = entity.build();
                                       // put.setEntity(ent);
                                        post.setEntity(ent);
                                        //HttpResponse responce = httpclient.execute(put);
                                        HttpResponse responce = httpclient.execute(post);
                                        HttpEntity respentity = responce.getEntity();
                                        resp = EntityUtils.toString(respentity);
                                        Log.d("FB","REsponse String--> "+resp);
                                        Log.d("FB","Update Executed....");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        resp = e.getMessage();
                                        return resp;
                                    }

                                }

                                return resp;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                td.dismiss();

                                // Log.e("FB", "SAVE CLICK....ON POST EXECUTE");

                                if (s.startsWith("ERROR")) {
                                    Toast.makeText(getActivity().getApplicationContext(), "ERROR->" + s, Toast.LENGTH_LONG).show();
                                    //LoginManager.getInstance().logOut(); / logout properly from System also..
                                }
                                if(s.startsWith("NO_VAL"))
                                {
                                    Toast.makeText(getActivity().getApplicationContext(),"Not UPDATED",Toast.LENGTH_SHORT).show();
                                }
                                else

                                {
                                    if (s.startsWith("SUCCESS_RESPONSE"))
                                    {
                                        Toast.makeText(getActivity().getApplicationContext(), "Sussessfully Saved", Toast.LENGTH_SHORT).show();

                                        sp.AddtoPreferance("BACK_REG", "YES"); // Updating Back Ground Registration as already Registered with Back ground
                                    }
                                    if(s.startsWith("UPDATED"))
                                    {
                                        Toast.makeText(getActivity().getApplicationContext(), "Sussessfully UPDATED", Toast.LENGTH_SHORT).show();
                                    }

//                                    f_manager = getFragmentManager();
//                                    android.support.v4.app.FragmentTransaction t_manager_1 = f_manager.beginTransaction();
//                                    //t_manager_1.add(R.id.downmenu, navmenu, "NAV_MENU");
//
//                                    User_nav_menu_fragment frg = (User_nav_menu_fragment) getFragmentManager().findFragmentByTag("NAV_MENU");
//                                    if(frg!=null)
//                                    {
//                                        frg = new User_nav_menu_fragment();
//                                        t_manager_1.attach(frg);
//                                    }
//                                    else
//                                    {
//                                        t_manager_1.add(R.id.downmenu, frg, "NAV_MENU");
//                                    }
//                                    t_manager_1.commit();
                                    f_manager = getFragmentManager();
                                    FragmentTransaction t_manager = f_manager.beginTransaction();
                                    t_manager.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                                    User_nav_menu_fragment unv = new User_nav_menu_fragment();
                                    t_manager.remove((signup_wed_fb) getFragmentManager().findFragmentByTag("FB_SHW"));
                                    t_manager.add(R.id.downmenu, unv, "NAV_MENU");
                                    t_manager.addToBackStack("FB_SHW");
                                    //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                                    t_manager.commit();

                                }


                            }
                        }
                        uploadtoServ up = new uploadtoServ();
                        up.execute(save.getText().toString());


                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error (2)->" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }


            }
        });


    }
}