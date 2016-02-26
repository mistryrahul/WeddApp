package com.example.rahul.weddapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.weddapp.R;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Admin on 23-09-2015.
 */
public class second_fragment extends Fragment
{
    EditText email,name,messaage,phone;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View parent = inflater.inflate(R.layout.second_fragment, container, false);

        return parent;



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public void onResume() {
        super.onResume();
        View v = getView();
        email = (EditText)v.findViewById(R.id.email_et);
        name = (EditText)v.findViewById(R.id.name_et);
        messaage = (EditText)v.findViewById(R.id.msg_et);
        phone = (EditText)v.findViewById(R.id.phn_et);
        final shared_pref nt = new shared_pref("NETWORK",getActivity().getApplicationContext());
        messaage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_DONE) {
                    //do something here.
                   // Toast.makeText(getActivity(), "GETTING THE EVENT PROPERLY", Toast.LENGTH_SHORT).show();

                    final String _name,_msg,_phn,_email;

                    if ((email.getText().toString().equals("")) || (email.getText().toString()==""))
                    {
                        email.setError("Emil is required");
                        return false;
                    }
                    else if((name.getText().toString().equals("")) || (name.getText().toString()==""))
                    {
                        name.setError("Name is required");
                        return false;
                    }
                    else if((messaage.getText().toString().equals("")) || (messaage.getText().toString()==""))
                    {
                        messaage.setError("Message is required");
                        return false;
                    }
                    else if((phone.getText().toString().equals("")) || (phone.getText().toString()==""))
                    {
                        phone.setText("NO");
                    }
                    else
                    {
                        //Toast.makeText(getActivity(),"WORKING",Toast.LENGTH_LONG).show();
                        _name = name.getText().toString();
                        _email = email.getText().toString();
                        _phn = phone.getText().toString();
                        _msg = messaage.getText().toString();

                      //  Log.d("HASH_KEY_GEN","name-"+_name);
                     //   Log.d("HASH_KEY_GEN","email-"+_email);
                    //    Log.d("HASH_KEY_GEN","Phone-"+_phn);
                     //   Log.d("HASH_KEY_GEN","Message-"+_msg);

                        class SaveRectoServer extends AsyncTask<Context,Void , String>
                        {
                            private Context contxt;
                            private String resp=null;
                            private ProgressDialog dialog = null;
                            @Override
                            protected void onPreExecute() {
                                //super.onPreExecute();
                                dialog = ProgressDialog.show(getActivity(),"Please Wait","Process in Progress",true);
                                dialog.setCancelable(false);


                            }

                            @Override
                            protected String doInBackground(Context... params) {

                                HttpClient httpclient = new DefaultHttpClient();
                               // String uri = "http://192.168.1.11:8080/WeddingApp/Resources/Misc/Contactus?email="+URLEncoder.encode(_email)+"&name="+URLEncoder.encode(_name)+"&phone="+URLEncoder.encode(_phn)+"&message="+URLEncoder.encode(_msg);
                                String uri = nt.GetFromPreference("B_URI")+"/Misc/Contactus?email="+URLEncoder.encode(_email)+"&name="+URLEncoder.encode(_name)+"&phone="+URLEncoder.encode(_phn)+"&message="+URLEncoder.encode(_msg);
                                //Log.d("HASH_KEY_GEN",URLEncoder.encode(uri));
                                HttpPost httpPost = new HttpPost(uri);
                                try
                                {
                                 /*   List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(4);
                                    namevaluepairs.add(new BasicNameValuePair("email",_email));
                                    namevaluepairs.add(new BasicNameValuePair("name",_name));
                                    namevaluepairs.add(new BasicNameValuePair("phone",_phn));
                                    namevaluepairs.add(new BasicNameValuePair("message", _msg));
                                 */

//                                    namevaluepairs.add(new BasicNameValuePair("email","abcd_gmail.com"));
//                                    namevaluepairs.add(new BasicNameValuePair("name","rahul"));
//                                    namevaluepairs.add(new BasicNameValuePair("phone","1234"));
//                                    namevaluepairs.add(new BasicNameValuePair("message", "kya pata ....."));



                                   // httpPost.setEntity(new UrlEncodedFormEntity(namevaluepairs));


                                    HttpResponse responce = httpclient.execute(httpPost);

                                  //  Log.d("HASH_KEY_GEN","RUNNING AT THIS POINT");
                                    if(responce.getStatusLine().getStatusCode()==200)
                                    {
                                        resp = EntityUtils.toString(responce.getEntity());
                                //        Log.d("HASH_KEY_GEN","RUNNING in responce");
                                    }
                                    else
                                    {
                                        resp = "ERROR";
                                     //   Log.d("HASH_KEY_GEN","RUNNING in ERROR");
                                    }
                                    return resp;
                                }
                                catch(ConnectTimeoutException e)
                                {
                                    //Toast.makeText(getActivity(), "Error..Host Unreachable", Toast.LENGTH_SHORT).show();
                                    resp="Error..Host Unreachable";
                                  //  Log.d("HASH_KEY_GEN","RUNNING in Exception 1"+e.getMessage());
                                    return resp;
                                }

                                catch(Exception e)
                                {
                                    //e.printStackTrace();
                                    //Toast.makeText(getActivity(), "Error..Please try again.", Toast.LENGTH_SHORT).show();
                                    resp="Error..Please try again";
                                   // Log.d("HASH_KEY_GEN","RUNNING in Exception 2"+e.getMessage());

                                    return resp;
                                }

                            }

                            @Override
                            protected void onPostExecute(String s) {
                                //super.onPostExecute(s);
                                dialog.dismiss();
                                if(s.startsWith("Error"))
                                {
                                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Request Sent..We will Contact you Soon", Toast.LENGTH_SHORT).show();
                                }
                                second_fragment sf =(second_fragment) getActivity().getSupportFragmentManager().findFragmentByTag("SECOND");
                                getActivity().getSupportFragmentManager().beginTransaction().remove(sf).attach((opening) getActivity().getSupportFragmentManager().findFragmentByTag("FIRST")).commit();

                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


                                 if(getActivity().getCurrentFocus()!=null)
                                 {
                                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                                 }

                            }
                        }

                        SaveRectoServer bb = new SaveRectoServer();
                        bb.execute(getActivity().getApplicationContext());



                    }





                }

                return true;
            }
        });

    }
}
