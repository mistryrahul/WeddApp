package com.example.rahul.weddapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Admin on 31-10-2015.
 */
public class create_wed_frgmnt extends StatedFragment {

   // final int RESULT_LOAD_IMAGE = 1;
    private final String[] items = new String[]{"From Cam", "From SD Card"};

    private int No_Of_Year=0;
    private EditText verifi_code;
    private EditText b_nm;
    private EditText g_nm;
    private EditText story;
    private ImageView imv;
    private Button btn;
    private static int PICK_FROM_CAMERA=1;
    private static int PICK_FROM_FILE=2;
    private Uri imagecaptureuri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_wed_frgmnt, container, false);
        //View user_nav_view = inflater.inflate(R.layout.activity_user_nav);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View parent_view = getActivity().findViewById(R.id.rel_lay_show_grp_1);
       // if(parent_view==null)
        //{
        //    parent_view = getActivity().findViewById(R.id.Linlay2);
            parent_view.getLayoutParams().height = 200;
       // }
        /*else
        {
            parent_view.getLayoutParams().height = 200;
        }*/

        //Log.d("FB", "RUNNING......");
    }

    //loading Image

    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        build.setTitle("Select Image");
        build.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());


                if(b_nm.getText().toString()!="")
                {
                    sp.AddtoPreferance("B_NAME",b_nm.getText().toString());
                }
                if(g_nm.getText().toString()!="")
                {
                    sp.AddtoPreferance("G_NAME",g_nm.getText().toString());
                }
                if(story.getText().toString()!="")
                {
                    sp.AddtoPreferance("STORY",story.getText().toString());
                }

                try {
                    if (which == 0) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                        File file = new File(Environment.getExternalStorageDirectory(), "tmp_avatr"+String.valueOf(System.currentTimeMillis()+".jpg"));
                        imagecaptureuri = Uri.fromFile(file);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagecaptureuri);
                        intent.putExtra("return data", true);
                        shared_pref.setFrag_shw(1); // for Pic Upload
                        //sp.AddtoPreferance("FRGMNT_SHOW", "YES"); //for pic upload..
                        getActivity().startActivityForResult(intent, PICK_FROM_CAMERA);


                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        shared_pref.setFrag_shw(1); // for Pic Upload
                       // sp.AddtoPreferance("FRGMNT_SHOW", "YES"); //for pic upload..
                        getActivity().startActivityForResult(Intent.createChooser(intent, "Complete action Using"), PICK_FROM_FILE);
                    }


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),"ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("FB","ERROR "+e.getMessage());
                }

                dialog.cancel();
            }
        });

        final AlertDialog dialog = build.create();


        final View view = getView();

        verifi_code = (EditText) view.findViewById(R.id.Etveri);
        b_nm = (EditText) view.findViewById(R.id.Etbride);
        g_nm = (EditText) view.findViewById(R.id.Etgroom);
        story = (EditText) view.findViewById(R.id.ETStory);
        imv = (ImageView) view.findViewById(R.id.imageView3);
        final shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());



        // for showing dta on view



        final Button btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (((b_nm.getText().toString() == "") || (b_nm.getText().toString().equals(""))) || ((g_nm.getText().toString() == "") || (g_nm.getText().toString().equals(""))) || ((story.getText().toString() == "")) || ((story.getText().toString().equals(""))) )
                {
                    if ((b_nm.getText().toString() == "") || ((b_nm.getText().toString().equals("")))) {
                        b_nm.setError("Can not be blank");
                    }
                    if ((g_nm.getText().toString() == "") || ((g_nm.getText().toString().equals("")))) {
                        g_nm.setError("Can not be Blank");
                    }
                    if ((story.getText().toString() == "") || ((story.getText().toString().equals("")))) {
                        story.setError("Can not be Blank");
                    }
                }
                else
                {
                  //  Toast.makeText(getActivity().getApplicationContext(),"Going in the Place",Toast.LENGTH_SHORT).show();


                    final shared_pref nt = new shared_pref("NETWORK",getActivity().getApplicationContext());
                    final shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
                    Bitmap bg_img = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                    final byte[] img_byte;

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bg_img.compress(Bitmap.CompressFormat.JPEG, 60, bos);
                    img_byte = bos.toByteArray();

                    final String bride_nm = b_nm.getText().toString();
                    final String groom_nm= g_nm.getText().toString();
                    final String stroy_str = story.getText().toString();
                    final String user_nm = sp.GetFromPreference("EMAIL");


                    //Toast.makeText(getActivity().getApplicationContext(),"Saving Data",Toast.LENGTH_LONG).show();
                    class uploadBrideGroom extends AsyncTask<Void,Void,String>
                    {
                        ProgressDialog dialog;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            dialog = new ProgressDialog(getContext());
                            //dialog = ProgressDialog.show(User_nav.this,"Please wait..","Please Wait..",true);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.show();
                        }

                        @Override
                        protected String doInBackground(Void... params) {

                            String resp="NO_VAL";
                            byte[] finalimg_byte = img_byte;

                            try {



                                {
                                    String url = nt.GetFromPreference("B_URI") + "/Bridegrooms/AddBrideGroomNew"; ///////////////
                                    Log.d("FB", "URL-->" + url);
                                    HttpClient httpclient = new DefaultHttpClient();
                                    HttpPost post = new HttpPost(url);
                                    MultipartEntityBuilder entity = MultipartEntityBuilder.create();

                                    //Log.e("FB", "SAVE CLICK....Async task");
                                    entity.addPart("B_NAME", new StringBody(bride_nm));
                                    entity.addPart("G_NAME", new StringBody(groom_nm));
                                    entity.addPart("STORY", new StringBody(stroy_str));
                                    entity.addPart("U_NAME", new StringBody(user_nm));
                                    entity.addPart("S_YEAR", new StringBody(String.valueOf(No_Of_Year)));
                                    entity.addPart("PIC", new ByteArrayBody(finalimg_byte, "image/jpeg"));

                                    HttpEntity ent = entity.build();

                                    post.setEntity(ent);
                                    HttpResponse responce = httpclient.execute(post);
                                    HttpEntity respentity = responce.getEntity();

                                    //if (responce.getStatusLine().getStatusCode() == 201)
                                    // {
                                    resp = EntityUtils.toString(respentity);
                                    // }
                                }


                            }
                            catch (Exception e)
                            {
                                resp = "ERROR"+e.getMessage();
                                Log.d("FB","ERROR->"+e.getMessage());
                                return resp;
                            }



                                return resp;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);

                            dialog.dismiss();

                              if(s.startsWith("ERROR"))
                              {
                                  Toast.makeText(getActivity().getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                              }
                            else if(s.startsWith("NO_VAL"))
                              {
                                  Toast.makeText(getActivity().getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                              }
                              else if(s.startsWith("UPDATED"))
                              {
                                  Toast.makeText(getActivity().getApplicationContext(),"Records UPdated Successfully...",Toast.LENGTH_SHORT).show();
                              }
                            else
                              {
                                  Toast.makeText(getActivity().getApplicationContext(),"Success.Your Wedding Id is been mailed in your Account. your Wedding Code is-"+s,Toast.LENGTH_LONG).show();

                                  shared_pref cntnt = new shared_pref("WED_CD",getActivity().getApplicationContext());
                                  if ((cntnt.GetFromPreference("ITM") == "NO_VAL") || (cntnt.GetFromPreference("ITM").equals("NO_VAL"))) {


                                      cntnt.AddtoPreferance("ITM", s + " ");
                                  } else {
                                      String old = cntnt.GetFromPreference("ITM");
                                      if (old.indexOf(s) == -1) {
                                          old = old + " " + String.valueOf(s);
                                          cntnt.AddtoPreferance("ITM",old);
                                      }
                                  }

                                  android.support.v4.app.FragmentManager f_manager = getFragmentManager();
                                  FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                                  //t_manager_1.add(R.id.downmenu, navmenu, "NAV_MENU");
                                  t_manager_1.detach((create_wed_frgmnt) getFragmentManager().findFragmentByTag("ADD_WEDD"));
                                  t_manager_1.addToBackStack("NAV_MENU");
                                  t_manager_1.add((User_nav_menu_fragment) getFragmentManager().findFragmentByTag("NAV_MENU"), "NAV_MENU");
                                  t_manager_1.commit();
                                  //Toast.makeText(User_nav.this, "Complete YouPro First", Toast.LENGTH_SHORT).show();



                              }



                        }
                    }

                    uploadBrideGroom bg = new uploadBrideGroom();
                    bg.execute();

                }



            }
        });

        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (((b_nm.getText().toString() == "") || (b_nm.getText().toString().equals(""))) || ((g_nm.getText().toString() == "") || (g_nm.getText().toString().equals(""))) || ((story.getText().toString() == "")) || ((story.getText().toString().equals(""))) )
//                {
//                    if ((b_nm.getText().toString() == "") || ((b_nm.getText().toString().equals("")))) {
//                        b_nm.setError("Can not be blank");
//                    }
//                    if ((g_nm.getText().toString() == "") || ((g_nm.getText().toString().equals("")))) {
//                        g_nm.setError("Can not be Blank");
//                    }
//                    if ((story.getText().toString() == "") || ((story.getText().toString().equals("")))) {
//                        story.setError("Can not be Blank");
//                    }
//                } else {
                //Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i, RESULT_LOAD_IMAGE); // put the imaage in the imagebox

                //}
                    dialog.show();
            }
        });


        final int no_of_year[] = new int[2];
//        LinearLayout usr_nav_grp = (LinearLayout)view.findViewById(R.id.rel_lay_show_grp_1);
//        usr_nav_grp.getLayoutParams().height=60;
//        usr_nav_grp.getLayoutParams().width=ViewGroup.LayoutParams.MATCH_PARENT;

        // Log.d("FB","In Resume....");


        verifi_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if ((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_DONE) {

                    Toast.makeText(getActivity().getApplicationContext(), "GOING IN THE DONE...", Toast.LENGTH_SHORT).show();

                    if (verifi_code.getText().toString() == "") {
                        verifi_code.setError("Field can not be left blank.");
                        return false;
                    } else {
                        class CheckCode extends AsyncTask<String, Void, String> // Async TAsk to send verification code
                        {
                            ProgressDialog dialog;

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                dialog = new ProgressDialog(getContext());
                                //dialog = ProgressDialog.show(User_nav.this,"Please wait..","Please Wait..",true);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.show();
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
                                HttpClient httpclient = new DefaultHttpClient();
                                String uri = nt.GetFromPreference("B_URI") + "/Verification/"+params[0];
                                String res_str = "";
                                Log.d("FB","URI-->"+uri);
                                try {

                                    // Log.d("FB","POINT 1"+uri);
                                    HttpGet httpget = new HttpGet(uri);
                                    HttpResponse responce = httpclient.execute(httpget);

                                    if (responce.getStatusLine().getStatusCode() == 200) {
                                        res_str = EntityUtils.toString(responce.getEntity());
                                        // add the data to A String variable
                                        //   Log.d("FB","POINT SUCCESS");
                                    } else {
                                        res_str = "NO_REC";

                                        //   Log.d("FB","POINT NO_REC"+EntityUtils.toString(responce.getEntity()));
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                    res_str = "ERROR";
                                    //   Log.d("FB","POINT ERROR");
                                }


                                return res_str;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                dialog.dismiss();

                                Log.d("FB", "( PostExecute )VERIFICATION CODE->" + s);

                                if (s.startsWith("NO_REC")) {
                                    Toast.makeText(getActivity().getApplicationContext(), "NO_REC", Toast.LENGTH_LONG).show();
                                } else if (s.startsWith("NOT_VALID")) {
                                    Toast.makeText(getActivity().getApplicationContext(), "NOT VALID", Toast.LENGTH_LONG).show();
                                } else if (s.startsWith("ERROR")) {
                                    Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                                } else if(s.startsWith("Code_Already_Used"))
                                {
                                    Toast.makeText(getActivity().getApplicationContext(), "Verification Code Already Used", Toast.LENGTH_LONG).show();

                                }
                                else {

                                    // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // for hiding the key board....

                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (getActivity().getCurrentFocus() != null) {
                                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    }


                                    Toast.makeText(getContext(), "Verification Code Accepted...", Toast.LENGTH_SHORT).show();

                                    Log.d("FB", "VERIFICATION CODE->" + s);

                                    no_of_year[0] = Integer.parseInt(s);
                                    No_Of_Year = Integer.parseInt(s);

                                    View parent_view = getActivity().findViewById(R.id.rel_lay_show_grp_1);
                                    parent_view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                                    // mastr.getLayoutParams().height=ViewGroup.LayoutParams.MATCH_PARENT;
                                    // mastr.getLayoutParams().width=ViewGroup.LayoutParams.MATCH_PARENT;


                                    LinearLayout veri_code = (LinearLayout) view.findViewById(R.id.verifi);
                                    veri_code.setVisibility(View.GONE);
                                    RelativeLayout create_wedding = (RelativeLayout) view.findViewById(R.id.addbg);
                                    create_wedding.setVisibility(View.VISIBLE);


                                }


                            }
                        }

                        CheckCode ck = new CheckCode();
                        ck.execute(verifi_code.getText().toString());


                        // if verification code is okey open the Next part of the page and make invisible the previous part of this page.

                    }


                }
                return true;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("FB","GOING in the FRagment Activity");

           if(resultCode != Activity.RESULT_OK)
               return;

           Bitmap bitmap = null;
           String path="";
         shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());

        if(sp.GetFromPreference("B_NM")!="NO_VAL" ) {
            b_nm.setText(sp.GetFromPreference("B_NM"));
        }
        if(sp.GetFromPreference("G_NM")!="NO_VAL" )
        {
            g_nm.setText(sp.GetFromPreference("G_NM"));
        }
        if(sp.GetFromPreference("STORY")!="NO_VAL")
        {
            story.setText(sp.GetFromPreference("STORY"));
        }




           if(requestCode == PICK_FROM_FILE) {

               shared_pref.setFrag_shw(0);
               imagecaptureuri = data.getData();
               Uri selectedimage = data.getData();
               String[] filepathColumn = {MediaStore.Images.Media.DATA};

               Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedimage, filepathColumn, null, null, null);
               cursor.moveToFirst();

               int ColumnIndex = cursor.getColumnIndex(filepathColumn[0]);
               String pic_path = cursor.getString(ColumnIndex);
               cursor.close();


               imv.setImageBitmap(BitmapFactory.decodeFile(pic_path));

           }


    }


}
