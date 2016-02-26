package com.example.rahul.weddapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Admin on 28-10-2015.
 */
// RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener
public class wed_chooser_frgmnt extends Fragment implements View.OnClickListener {

    String Wedding_id="NO_VAL";
    AutoCompleteTextView wed_cd;
    private TableLayout table;
    int flag_of_rsvp=0; //0-no selected/ 1- yes/ 2-no / 3- maybe
    TextView b_nm, g_nm;
    LinearLayout rsvp;
    private RadioButton yes, no,mayb;
    ImageView pc;
    int flag=0;
    private String wed_url_final;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wed_chooser_frgmnt, container, false);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        //LayoutInflater linf = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater linf = LayoutInflater.from(getActivity().getApplicationContext());
        //View view = linf.inflate(R.layout.wed_chooser_frgmnt,null);
        View view = getView();
        wed_cd = (AutoCompleteTextView) view.findViewById(R.id.actxtvu);
        b_nm = (TextView) view.findViewById(R.id.tvbride);
        g_nm = (TextView) view.findViewById(R.id.tvgroom);
        pc = (ImageView) view.findViewById(R.id.imageView);

        rsvp = (LinearLayout)view.findViewById(R.id.rvsp);

        yes = (RadioButton)view.findViewById(R.id.Yes);
        no = (RadioButton)view.findViewById(R.id.No);
        mayb = (RadioButton)view.findViewById(R.id.Maybe);

//        yes.setOnCheckedChangeListener(this);
//        no.setOnCheckedChangeListener(this);
//        mayb.setOnCheckedChangeListener(this);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        mayb.setOnClickListener(this);

        table = (TableLayout)view.findViewById(R.id.table_layout);

        View parent_view = getActivity().findViewById(R.id.rel_lay_show_grp_1);
        parent_view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;


        //g_nm.setText("NEW BRIDE");
        //b_nm.setText("NEW GROOM");


        final shared_pref cntnt = new shared_pref("WED_CD", getActivity().getApplicationContext());
        if ((cntnt.GetFromPreference("ITM") != "NO_VAL") || (!cntnt.GetFromPreference("ITM").equals("NO_VAL"))) {
            wed_cd.setThreshold(1);
            String[] list = (cntnt.GetFromPreference("ITM")).split(" ");
            ArrayAdapter<String> adptr = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
            wed_cd.setAdapter(adptr);
        }
        wed_cd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() >= 14) {
                    final Bitmap[] bride_groom_img = new Bitmap[1];
                   // Toast.makeText(getActivity().getApplicationContext(), "Textchanged" + s, Toast.LENGTH_SHORT).show();

                    ///////////////////////////////////////////////////////
                    class getweddinginfo extends AsyncTask<Context, Void, String> {
                        private Context contxt;
                        private String resp = null;
                        private ProgressDialog dialog = null;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            dialog = new ProgressDialog(getContext());
                            dialog.dismiss();
                           //dialog = ProgressDialog.show(User_nav.this,"Please wait..","Please Wait..",true);
                           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                           dialog.setCancelable(false);

                            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
                            wmlp.gravity = Gravity.TOP | Gravity.LEFT;
                            wmlp.x = 100;
                            wmlp.y = 100;

                            dialog.show();
                        }

                        @Override
                        protected String doInBackground(Context... params) {
                            shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
                            HttpClient httpclient = new DefaultHttpClient();
                            String uri = nt.GetFromPreference("B_URI") + "/Bridegrooms/Wedding/"+s;
                            String uri_alive = nt.GetFromPreference("B_URI")+"/Bridegrooms/Subscription/"+s;

                            Log.d("FB","URL-1-->"+uri);
                            Log.d("FB","URL-2-->"+uri_alive);
                            //Log.d("HASH_KEY_GEN",URLEncoder.encode(uri));
                            //contxt = params[0];
                            String json_string = null;
                            HttpGet httpget = new HttpGet(uri);
                            //HttpPost httpPost = new HttpPost(uri);
                            try {
                                HttpResponse responce = httpclient.execute(httpget);
                                //  Log.d("FB","IN RESPONCE...");
                                //Log.d("FB","RESPONCE-->"+responce.toString());

                                if (responce.getStatusLine().getStatusCode() == 200) {
                                    HttpClient httpclient_1 = new DefaultHttpClient();
                                    HttpGet hgt = new HttpGet(uri_alive);
                                    HttpResponse rspns = httpclient_1.execute(hgt);
                                    String res_str="NOT_ALIVE";
                                   // Log.d("FB","RESPONCE VALUE->"+EntityUtils.toString(rspns.getEntity()));
                                    if (rspns.getStatusLine().getStatusCode() == 200) {
                                        res_str = EntityUtils.toString(rspns.getEntity());
                                        //Log.d("FB","IN ALIVE (RESPONCE STRING)->"+res_str);
                                    }

                                    Log.d("FB","IS ALIVE->"+res_str);
                                    /////
                                    if ((res_str=="YES") || (res_str.equals("YES")) )
                                    {
                                        //Log.d("FB","IN ALIVE->");

                                        if ((cntnt.GetFromPreference("ITM") == "NO_VAL") || (cntnt.GetFromPreference("ITM").equals("NO_VAL"))) {


                                            cntnt.AddtoPreferance("ITM", String.valueOf(s) + " ");
                                        } else {
                                            String old = cntnt.GetFromPreference("ITM");
                                            if (old.indexOf(String.valueOf(s)) == -1) {
                                                old = old + " " + String.valueOf(s);
                                                cntnt.AddtoPreferance("ITM", old);
                                            }
                                        }

                                        flag=1; //setting flag 1 , as the wedd code inserted is correct
                                        json_string = EntityUtils.toString(responce.getEntity());
                                        JSONObject jobj = new JSONObject(json_string);
                                        String pic_url = jobj.getString("img");

                                        Bitmap bitmpa = null;
                                        HttpURLConnection connection = null;
                                        InputStream input = null;
                                        wed_url_final = pic_url;
                                        Log.d("FB",pic_url+"/320/240");

                                        URL url = new URL(pic_url+"/320/240");
                                        connection = (HttpURLConnection) url.openConnection();
                                        connection.setDoInput(true);
                                        connection.connect();
                                        input = connection.getInputStream();

                                        //Log.d("FB","Image Size-->"+input.available());
//                                        BitmapFactory.Options opts = new BitmapFactory.Options();
//                                        opts.inPurgeable = true;
                                        bitmpa = BitmapFactory.decodeStream(input);

                                        bride_groom_img[0] = bitmpa;


                                        resp = json_string;

                                        //  Log.d("FB","IN OK RESPONCE");
                                    }
                                    else
                                    {
                                        resp="NOT_ALIVE";
                                        Log.d("FB","NOT ALIVE->"+res_str);
                                    }
                                } else {
                                    resp = "NO_REC";
                                   // Log.d("FB","NO WEDDING REC FOUND->");
                                    //   Log.d("HASH_KEY_GEN","RUNNING in ERROR");
                                }
                                return resp;
                            } catch (Exception e) {
                                resp = "ERROR..Please try again";
                                //Log.d("FB","ERROR"+e.getMessage());
                               // e.printStackTrace();
                               // Log.d("FB","IN ERROR>");
                                return resp;
                            }

                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                           // Log.d("FB", "ON POST EXECI|UTE-->"+s);

                            dialog.dismiss();

                            if (s.startsWith("ERROR ")) {
                                Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                            } else if (s.startsWith("NO_REC")) {
                                Toast.makeText(getActivity().getApplicationContext(), "No Records Exist", Toast.LENGTH_SHORT).show();
                            } else if(s.startsWith("NOT_ALIVE")) {
                                Toast.makeText(getActivity().getApplicationContext(), "Subscription Ended for the wedding", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                //Toast.makeText(getActivity().getApplicationContext(), "SUCCESS"+s, Toast.LENGTH_SHORT).show();

                                try {

                                    //(1)  wedchooser (is-wed_chsr)size increase to 250 dp
                                    View parent_view = getActivity().findViewById(R.id.wed_chsr);
                                    int pixel = (int) (250 * (getActivity().getApplicationContext().getResources().getDisplayMetrics().density));
                                    parent_view.getLayoutParams().height = pixel;
                                   // (2) set visible (id- img_grp) image group
                                    View v = (View)getActivity().findViewById(R.id.img_grp);
                                    v.setVisibility(View.VISIBLE);
                                    Wedding_id= wed_cd.getText().toString();

                                    JSONObject jsnobj = new JSONObject(s);
                                    b_nm.setText(jsnobj.getString("bride_name"));
                                    g_nm.setText(jsnobj.getString("groom_name"));
                                    shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
                                    sp.AddtoPreferance("BRD_NM",jsnobj.getString("bride_name"));
                                    sp.AddtoPreferance("GRM_NM",jsnobj.getString("groom_name"));
                                    sp.AddtoPreferance("WED_IMG",wed_url_final);

                                    if (bride_groom_img[0] != null) {
                                        pc.setImageBitmap(bride_groom_img[0]);

                                    }
                                   // Log.d("FB",jsnobj.getString("bride_name"));
                                    //(3) set visible the rvsp group

                                    CheckRsvp oo = new CheckRsvp();
                                    oo.execute();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    ///////////////////////////////////////////////////////

                    getweddinginfo nn = new getweddinginfo();
                    nn.execute(getActivity().getApplicationContext());
                }
            }
        });


      wed_cd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
          @Override
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

              if ((actionId & EditorInfo.IME_MASK_ACTION) == EditorInfo.IME_ACTION_DONE)
              {
                   Toast.makeText(getActivity().getApplicationContext(),"WEDDDING SELECTED...",Toast.LENGTH_SHORT).show();
                   shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());

                 if(rsvp.getVisibility()==View.VISIBLE)
                 {
                     if((yes.isChecked()==false) && ((no.isChecked()==false)) && (mayb.isChecked()==false) )
                     {
                         Toast.makeText(getActivity().getApplicationContext(),"Please Confirm your RSVP",Toast.LENGTH_SHORT).show();
                     }
                 }

                  else
                 {
                     Log.d("FB","Flag Value -->"+flag);
                     if(flag==1)
                     {
                         sp.AddtoPreferance("WED_CD", wed_cd.getText().toString());
//                      CheckRsvp oo = new CheckRsvp();
//                      oo.execute();


                         Intent wedchooser = new Intent(getActivity().getApplicationContext(), Wedding_home.class);
                         wedchooser.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(wedchooser);

                     }
                 }
              }
              return true;


          }
      });


    }


    @Override
    public void onClick(View v) {



        final AlertDialog.Builder ag = new AlertDialog.Builder(getActivity())
                .setTitle("Confirm RSVP")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getActivity().getApplicationContext(),"YES SELECTED",Toast.LENGTH_SHORT).show();

                        SendRSPV rsvp_obj = new SendRSPV();

                        Log.d("FB","Flag value"+flag_of_rsvp);

                       if(flag_of_rsvp ==1)
                       {
                           rsvp_obj.execute("YES");
                       }
                       else if(flag_of_rsvp ==2)
                       {
                           rsvp_obj.execute("NO");
                       }
                       else if(flag_of_rsvp ==3)
                        {
                            rsvp_obj.execute("MAYBE");
                        }

                        dialog.dismiss();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getActivity().getApplicationContext(), "NO SELECTED", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        yes.setChecked(false);
                        no.setChecked(false);
                        mayb.setChecked(false);

                    }
                }).setCancelable(false);

        if(v.getId()==R.id.Yes)
        {
           // Toast.makeText(getActivity(),"YES SELECTED",Toast.LENGTH_SHORT).show();
            flag_of_rsvp=1;
            ag.setMessage("Are you sure want to select YES");
            ag.show();
        }
       else  if(v.getId()==R.id.No)
        {
            flag_of_rsvp=2;
            //Toast.makeText(getActivity(),"NO SELECTED",Toast.LENGTH_SHORT).show();
            ag.setMessage("Are you sure want to select NO");
            ag.show();
        }
        else  if(v.getId()==R.id.Maybe)
        {
            flag_of_rsvp=3;
           // Toast.makeText(getActivity(),"MAYBE SELECTED",Toast.LENGTH_SHORT).show();
            ag.setMessage("Are you sure want to select May Be");

            ag.show();
        }
    }


    class CheckRsvp extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
            shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());

            sp.GetFromPreference("EMAIL");


            String url_chk = nt.GetFromPreference("B_URI") + "/Guest/CheckRSVP?WED_ID="+Wedding_id+"&USER_ID="+sp.GetFromPreference("EMAIL");
            String res_str = "NO_VAL";
            Log.d("FB","WEDDING ID----->"+Wedding_id);
           // Log.d("FB","WEDDING CODE----->"+Wedding_id);
            Log.d("FB","URL----->"+url_chk);
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet hgt = new HttpGet(url_chk);
            try {
                HttpResponse rspns = httpclient.execute(hgt);


                if (rspns.getStatusLine().getStatusCode() == 200) {
                    res_str = EntityUtils.toString(rspns.getEntity());
                }
            } catch (Exception e) {
                res_str = "ERROR " + e.getMessage();
                return res_str;
            }


            return res_str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.startsWith("NO_VAl")) {
                Log.d("FB", "NO_VAL (ASynch DOes not returned any value....)");
            } else if (s.startsWith("ERROR")) {
                Log.d("FB", s);
            } else {
                if (s.equalsIgnoreCase("NO"))
                {
                    //int pixel_2 = (int) (400 * (getActivity().getApplicationContext().getResources().getDisplayMetrics().density));
                    View vv = (View)getActivity().findViewById(R.id.rvsp);
                    vv.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    vv.setVisibility(View.VISIBLE);

                    Event_Lst el = new Event_Lst();
                    el.execute();

//                    FragmentManager f_manager = getFragmentManager();
//                    rsvp_frgmnt rsvp = new rsvp_frgmnt();
//                    FragmentTransaction t_manager_1 = f_manager.beginTransaction();
//                    //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
//                   // t_manager_1.remove((wed_menu_fragmnt) getFragmentManager().findFragmentByTag("WED_MENU"));
//                    t_manager_1.add(R.id.rel_lay_show_grp_1, rsvp, "RSVP");
//                    // t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
//                    // t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");
//
//                    // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
//                    //t_manager_1.addToBackStack(null);
//                    //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
//                    t_manager_1.commit();


                } else {

                    shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
                    if (s.equalsIgnoreCase("YES")) {
                        sp.AddtoPreferance("U_TYP","YES");

                    } else if (s.equalsIgnoreCase("ADMIN")) {
                        sp.AddtoPreferance("U_TYP","ADMIN");

                    } else if (s.equalsIgnoreCase("PRV")) {
                        sp.AddtoPreferance("U_TYP","PRV");

                    }
                }

            }
        }
    }
    class Event_Lst extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... params) {
            shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
            shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
            String url_chk = nt.GetFromPreference("B_URI") + "/Events/Eventall/"+sp.GetFromPreference("WED_CD");

            Log.d("FB","IN ASYNCH TASK");
            Log.d("FB","URL--->>"+url_chk);
            String res_str = "NO_VAL";
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet hgt = new HttpGet(url_chk);


            try
            {
                HttpResponse rspns = httpclient.execute(hgt);
                if (rspns.getStatusLine().getStatusCode() == 200)
                {
                    res_str = EntityUtils.toString(rspns.getEntity());
                    Log.d("FB","Response Successful..");
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

            Log.d("FB", "IN POST EXECUTE");


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
                    TableRow tr = null;
                    GradientDrawable gd = new GradientDrawable();
                    gd.setColor(000000); // Changes this drawbale to use a single color instead of a gradient
                    gd.setCornerRadius(5);
                    gd.setStroke(1, 0xFF000000);

                    tr = new TableRow(getActivity());
                    tr.setGravity(Gravity.CENTER);
                    TextView dy_cap = new TextView(getActivity());
                    TextView Event_cap = new TextView(getActivity());
                    dy_cap.setText("Day");
                    dy_cap.setPadding(2, 1, 2, 1);
                    //dy_cap.getBackground().clearColorFilter();
                    Event_cap.setText("Event");
                    Event_cap.setPadding(2, 1, 2, 1);
                   // Event_cap.getBackground().clearColorFilter();
                    tr.addView(dy_cap);
                    tr.addView(Event_cap);
                    table.addView(tr);
                    for(int i=0;i<jarr.length();i++)
                    {
                        job = jarr.getJSONObject(i);
                        String day = job.getString("day");
                        String wedd_name = job.getString("event_name");
                        //Pair<String,String> pr = new Pair<>(day,wedd_name);
                        tr = new TableRow(getActivity());
                        tr.setGravity(Gravity.CENTER);
                       // tr.setWeightSum(2);
                      //  tr.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        TextView dy = new TextView(getActivity());

                        dy.setText(day.substring(0,10));
                        dy.setPadding(15, 13, 15,12);
                       // dy.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, (int) 1f));
                        dy.setBackgroundDrawable(gd);

                        TextView evnt_nm = new TextView(getActivity());
                        evnt_nm.setPadding(15, 13, 15, 12);
                        evnt_nm.setBackgroundDrawable(gd);
                       // evnt_nm.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT, (int) 1f));
                        evnt_nm.setText(wedd_name);

                        tr.addView(dy);
                        tr.addView(evnt_nm);
                        table.addView(tr);

                        Log.e("FB","DAY"+day);
                        Log.e("FB","WEdding Name"+wedd_name);

                    }
                   // table.addView(tr);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }

    class SendRSPV extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {

            String rspnce="NO_VAL";
            shared_pref nt = new shared_pref("NETWORK",getActivity().getApplicationContext());
            shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());

            String rspnce_url = nt.GetFromPreference("B_URI")+"/Guest/SendRSVP";

            HttpPost post = new HttpPost(rspnce_url);
            List<NameValuePair> namevaluepair = new ArrayList<NameValuePair>(4);
            HttpClient httpclient = new DefaultHttpClient();
            namevaluepair.add(new BasicNameValuePair("WED_ID",Wedding_id));
            namevaluepair.add(new BasicNameValuePair("RSVP",params[0]));
            namevaluepair.add(new BasicNameValuePair("USER_ID", sp.GetFromPreference("EMAIL")));

            Log.d("FB","Executing....IN Async Task");
            try
            {
                post.setEntity( new UrlEncodedFormEntity(namevaluepair));
                HttpResponse responce = httpclient.execute(post);
                HttpEntity respentity = responce.getEntity();

                if (responce.getStatusLine().getStatusCode() == 200)
                {
                    rspnce = EntityUtils.toString(respentity);
                }
            }
            catch (Exception e)
            {
                rspnce = "ERROR "+e.getMessage();
                return rspnce;
            }

            return rspnce;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.startsWith("NO"))
            {
                Toast.makeText(getActivity().getApplicationContext(),"No DATA",Toast.LENGTH_SHORT).show();
                Log.d("FB", "NO (post Execute) --> "+s);
            }
            else if(s.startsWith("ERROR"))
            {
                Toast.makeText(getActivity().getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                Log.d("FB", "ERROR --> " + s);
            }
            else
            {
                Log.d("FB", "COMING IN SUCCESS-- --> " + s);
                Toast.makeText(getActivity().getApplicationContext(),"RSVP SENT"+s,Toast.LENGTH_SHORT).show();

                shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
                sp.AddtoPreferance("U_TYP", "YES");

                rsvp.setVisibility(View.GONE);
            }

        }
    }

}
