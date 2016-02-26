package com.example.rahul.weddapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
 * Created by Admin on 10-11-2015.
 */
public class edit_wed_frgmnt extends StatedFragment {

    private static int PICK_FROM_CAMERA=1;
    private static int PICK_FROM_FILE=2;
    private Uri imagecaptureuri;
    private final String[] items = new String[]{"From Cam", "From SD Card"};
    private EditText b_nm;
    private EditText g_nm;
    private EditText story;
    private ImageView imv;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.edit_wed_frgmnt, container, false);
        return parent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        final shared_pref sp = new shared_pref("LOGIN", getActivity().getApplicationContext());
        final View view = getView();
        b_nm = (EditText) view.findViewById(R.id.Etbrideedtwed);
        g_nm = (EditText) view.findViewById(R.id.Etgroomedtwed);
        story = (EditText) view.findViewById(R.id.ETStoryedtwed);
        btn = (Button) view.findViewById(R.id.buttonedtwed);
        imv = (ImageView) view.findViewById(R.id.imageViewedtwed);


        //// Browse Image
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder build = new AlertDialog.Builder(getActivity());
        build.setTitle("Select Image");
        build.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());


//                if(b_nm.getText().toString()!="")
//                {
//                    sp.AddtoPreferance("B_NAME",b_nm.getText().toString());
//                }
//                if(g_nm.getText().toString()!="")
//                {
//                    sp.AddtoPreferance("G_NAME",g_nm.getText().toString());
//                }
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
                }

                dialog.cancel();
            }
        });

        final AlertDialog dialog = build.create();

        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((b_nm.getText().toString() == "") || (b_nm.getText().toString().equals(""))) || ((g_nm.getText().toString() == "") || (g_nm.getText().toString().equals(""))) || ((story.getText().toString() == "")) || ((story.getText().toString().equals("")))) {
                    if ((b_nm.getText().toString() == "") || ((b_nm.getText().toString().equals("")))) {
                        b_nm.setError("Can not be blank");
                    }
                    if ((g_nm.getText().toString() == "") || ((g_nm.getText().toString().equals("")))) {
                        g_nm.setError("Can not be Blank");
                    }
                    if ((story.getText().toString() == "") || ((story.getText().toString().equals("")))) {
                        story.setError("Can not be Blank");
                    }
                } else {
                    final shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
                    final shared_pref sp = new shared_pref("LOGIN", getActivity().getApplicationContext());
                    Bitmap bg_img = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                    final byte[] img_byte;

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bg_img.compress(Bitmap.CompressFormat.JPEG, 0, bos);
                    img_byte = bos.toByteArray();

                    final String bride_nm = b_nm.getText().toString();
                    final String groom_nm = g_nm.getText().toString();
                    final String stroy_str = story.getText().toString();
                    final String user_nm = sp.GetFromPreference("EMAIL");
                    String resp = "NO_VAL";

                    class uploadBrideGroom extends AsyncTask<Void, Void, String> {
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

                            String resp = "NO_VAL";
                            byte[] finalimg_byte = img_byte;

                            try {
                                String url = nt.GetFromPreference("B_URI") + "/Bridegrooms/UpdateBrideGroom"; ///////////////
                                Log.d("FB", "URL-->" + url);
                                HttpClient httpclient = new DefaultHttpClient();
                                HttpPost post = new HttpPost(url);
                                MultipartEntityBuilder entity = MultipartEntityBuilder.create();

                                //Log.e("FB", "SAVE CLICK....Async task");
                                entity.addPart("B_NAME", new StringBody(bride_nm));
                                entity.addPart("G_NAME", new StringBody(groom_nm));
                                entity.addPart("STORY", new StringBody(stroy_str));
                                entity.addPart("U_NAME", new StringBody(user_nm));
                                entity.addPart("W_CODE", new StringBody(sp.GetFromPreference("WED_CD")));
                                // entity.addPart("S_YEAR", new StringBody(String.valueOf(No_Of_Year)));
                                entity.addPart("PIC", new ByteArrayBody(img_byte, "image/jpeg"));

                                HttpEntity ent = entity.build();

                                post.setEntity(ent);
                                HttpResponse responce = httpclient.execute(post);
                                HttpEntity respentity = responce.getEntity();

                                //if (responce.getStatusLine().getStatusCode() == 201)
                                // {
                                resp = EntityUtils.toString(respentity);
                                Log.d("FB", "Executed Successfully......");

                            } catch (Exception e) {
                                resp = "ERROR" + e.getMessage();
                                Log.d("FB", "ERROR->" + e.getMessage());
                                return resp;
                            }


                            return resp;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);

                            dialog.dismiss();

                            if (s.startsWith("ERROR")) {
                                Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                            } else if (s.startsWith("NO_VAL")) {
                                Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                            } else if (s.startsWith("UPDATED")) {
                                Toast.makeText(getActivity().getApplicationContext(), "Records UPdated Successfully...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    uploadBrideGroom ubg = new uploadBrideGroom();
                    ubg.execute();
                }

            }
        });


        if ((sp.GetFromPreference("WED_CD") != "NO_VAL") || (!sp.GetFromPreference("WED_CD").equals("NO_VAL"))) {
            /// for downloading image and setting it to imageview

            class DownLoadImage extends AsyncTask<String, Integer, Bitmap> {
                @Override
                protected Bitmap doInBackground(String... params) {
                    Bitmap bitmpa = null;


                    HttpURLConnection connection = null;
                    InputStream input = null;
                    try {

                        shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());

                        //specify the height and width in INCH()
                       //+"/"+(Integer.parseInt(nt.GetFromPreference("SCR_DISPLAY")) * 2 )+"/"+(Integer.parseInt(nt.GetFromPreference("SCR_DISPLAY")) * 1.5 )

                         Log.d("FB",(params[0])+nt.GetFromPreference("SCR_RES"));

                        URL url = new URL((params[0])+"/320/240");


                        connection = (HttpURLConnection) url.openConnection();
                        //content_len= connection.getContentLength();
                        connection.setDoInput(true);
                        connection.connect();
                        input = connection.getInputStream();
//                        BitmapFactory.Options opts = new BitmapFactory.Options();
//                        opts.inPurgeable = true;
                        //Log.d("FB", "IMAGE SIZE-->" + input.available());
                        bitmpa = BitmapFactory.decodeStream(input);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    return bitmpa;
                }


                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    imv.setImageBitmap(bitmap);
                }
            }


            ///
            class getWeddDetails extends AsyncTask<String, Void, String> {

                @Override
                protected String doInBackground(String... params) {
                    String json_respons = null;
                    shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
                    String url = nt.GetFromPreference("B_URI")+"/Bridegrooms/Wedding/"+sp.GetFromPreference("WED_CD");
                   // Log.d("FB","NEW URL-->"+url);

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet httpget = new HttpGet(url);
                    try {
                        HttpResponse responce = httpclient.execute(httpget);
                        json_respons = EntityUtils.toString(responce.getEntity());
                    } catch (Exception e) {
                        json_respons = "ERROR " + e.getMessage();
                        return json_respons;
                    }


                    return json_respons;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (s.startsWith("ERROR ")) {
                        Toast.makeText(getActivity().getApplicationContext(), "ERROR OCCURED", Toast.LENGTH_SHORT).show();
                        Log.d("FB", "RESPONCE (ERROR)-->" + s);
                    } else {
                        try {
                            Log.d("FB","RESPONCE (OK)-->"+s);

                            JSONObject jboj = new JSONObject(s);
                            if (jboj.getString("bride_name") != null) {
                                b_nm.setText(jboj.getString("bride_name"));
                            }
                            if (jboj.getString("groom_name") != null) {
                                g_nm.setText(jboj.getString("groom_name"));

                            }
                            if (jboj.getString("story") != null) {
                                story.setText(jboj.getString("story"));
                            }
                            if (jboj.getString("img") != null) {
                                DownLoadImage dwi = new DownLoadImage();
                                dwi.execute(jboj.getString("img"));
                            }


                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "ERROR " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }


                }
            }
            getWeddDetails gwd = new getWeddDetails();
            Log.d("FB","WEdding Code-->"+sp.GetFromPreference("WED_CD"));
            gwd.execute(sp.GetFromPreference("WED_CD"));
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No WEDDING SELECTED", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("FB","GOING in the FRagment Activity");

        if(resultCode != Activity.RESULT_OK)
            return;

        Bitmap bitmap = null;
        String path="";
        shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());

        if(sp.GetFromPreference("BRD_NM")!="NO_VAL" ) {
            b_nm.setText(sp.GetFromPreference("BRD_NM"));
        }
        if(sp.GetFromPreference("GRM_NM")!="NO_VAL" )
        {
            g_nm.setText(sp.GetFromPreference("GRM_NM"));
        }
        if(sp.GetFromPreference("STORY")!="NO_VAL")
        {
            story.setText(sp.GetFromPreference("STORY"));
            sp.RemoveFieldfromPref("STORY");
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
