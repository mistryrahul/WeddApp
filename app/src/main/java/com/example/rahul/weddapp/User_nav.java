package com.example.rahul.weddapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.EGLExt;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.bus.ActivityResultBus;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.bus.ActivityResultEvent;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class User_nav extends AppCompatActivity  {
    FragmentManager f_manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_user_nav);

        //

    }

    @Override
    protected void onResume() {
        super.onResume();

        f_manager = getSupportFragmentManager();

        final shared_pref sp = new shared_pref("LOGIN",getApplicationContext());
        final Intent intent = getIntent();
        Button viewPro = (Button) findViewById(R.id.btnviewpro);
        Button cont = (Button) findViewById(R.id.btncont);

        //viewPro.setOnClickListener(this);
        //cont.setOnClickListener(this);

        //Log.d("FB","In Resume--- intent->"+intent.getStringExtra("LOGGED_IN")+" SP BACK->"+sp.GetFromPreference("BACK_REG")+"New SP-->"+sp.GetFromPreference("FRGMNT_SHOW"));

       //&& ((sp.GetFromPreference("BACK_REG")=="YES") || (sp.GetFromPreference("BACK_REG").equals("YES")))
            if( (intent.getExtras() != null) && ((sp.GetFromPreference("BACK_REG")=="YES") || (sp.GetFromPreference("BACK_REG").equals("YES")))  ) {


                if ((intent.getStringExtra("LOGGED_IN") == "YES") || (intent.getStringExtra("LOGGED_IN").equals("YES"))) {

                    intent.removeExtra("LOGGED_IN"); // removing exta from intent

                    //Log.d("FB", "In Resume (1st) --- intent->" + intent.getStringExtra("LOGGED_IN") + " SP->" + sp.GetFromPreference("NAME"));

                    Toast.makeText(getApplicationContext(), "ALREADY LOGGED in WITH FACEBOOK", Toast.LENGTH_LONG).show();


                  //  sp.AddtoPreferance("BACK_REG", "YES"); // Updating Back Ground Registration as already Registered with Back ground

                    User_nav_menu_fragment navmenu = (User_nav_menu_fragment)getSupportFragmentManager().findFragmentByTag("NAV_MENU") ;

                    if(navmenu==null)
                    {
                        navmenu = new User_nav_menu_fragment();
                        FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                        t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                        t_manager_1.remove(navmenu);
                        t_manager_1.add(R.id.downmenu, navmenu, "NAV_MENU");
                        // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                        //t_manager_1.addToBackStack(null);
                        //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                        t_manager_1.commit();
                        Log.d("FB","nav menu null");
                    }
                   else
                    {
                        FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                        t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                        t_manager_1.add(R.id.downmenu, navmenu, "NAV_MENU");
                        // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                        //t_manager_1.addToBackStack(null);
                        //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                        t_manager_1.commit();
                        Log.d("FB", "nav menu NOT null");
                    }



                }

//&& (!sp.GetFromPreference("FRGMNT_SHOW").equals("YES"))
//&& ((!sp.GetFromPreference("FRGMNT_SHOW").equals("YES")))
              //  Log.d("FB","Show VAlue"+shared_pref.getFrag_shw());
            } else if(( (intent.getExtras() == null) || (sp.GetFromPreference("BACK_REG")=="NO")  || (sp.GetFromPreference("BACK_REG").equals("NO")) )&& shared_pref.getFrag_shw()==0 )
            {
               // sp.RemoveFieldfromPref("FRGMNT_SHOW");  // for handling Pic Selection in Fragment (Browse Pic)

               // Log.d("FB","In Resume (2nd) --- intent->"+intent.getStringExtra("LOGGED_IN")+" SP->"+sp.GetFromPreference("FRGMNT_SHOW"));
             //   Log.d("FB","Evaluate-->"+(sp.GetFromPreference("FRGMNT_SHOW")=="NO_VAL"));
             //   Log.d("FB","Evaluate--2>"+(sp.GetFromPreference("FRGMNT_SHOW").equals("NO_VAL")));

                final String[] sts_cd = {null};
                class SaveRectoServer extends AsyncTask<Context, Void, String> {
                    private Context contxt;
                    private String resp = null;
                    private ProgressDialog dialog = null;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        dialog = ProgressDialog.show(User_nav.this, "Please Wait", "Process in Progress", true);
                        dialog.setCancelable(false);


                    }

                    @Override
                    protected String doInBackground(Context... params) {
                        shared_pref nt = new shared_pref("NETWORK",getApplicationContext());
                        HttpClient httpclient = new DefaultHttpClient();
                        String uri = nt.GetFromPreference("B_URI")+"/users/byemail/"+sp.GetFromPreference("EMAIL");
                        //Log.d("HASH_KEY_GEN",URLEncoder.encode(uri));
                        //contxt = params[0];

                        HttpGet httpget = new HttpGet(uri);
                        //HttpPost httpPost = new HttpPost(uri);
                        try {
                            HttpResponse responce = httpclient.execute(httpget);
                            //  Log.d("FB","IN RESPONCE...");
                            //Log.d("FB","RESPONCE-->"+responce.toString());

                            if (responce.getStatusLine().getStatusCode() ==200 ) {
                                resp = EntityUtils.toString(responce.getEntity());
                              //  Log.d("FB","IN OK RESPONCE");
                            } else {
                                resp = "NO_REC";
                                //   Log.d("HASH_KEY_GEN","RUNNING in ERROR");
                            }
                            return resp;
                        } catch (ConnectTimeoutException e) {
                            resp = "Error..Host Unreachable";
                            return resp;
                        } catch (Exception e) {
                            resp = "Error..Please try again";
                            return resp;
                        }

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);

                        dialog.dismiss();
                        Log.d("FB","DIALOG SHOULD STOP HERE");

                        if (s.startsWith("Error")) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                            Log.d("FB", "ERROR-->"+s);

                            LoginManager.getInstance().logOut(); // loging out from facebook also.
                            shared_pref sp = new shared_pref("LOGIN",getApplicationContext());
                            sp.RemoveFieldfromPref("ACCESSTOKEN");
                            sp.RemoveFieldfromPref("PIC_URL");
                            sp.RemoveFieldfromPref("EMAIL");
                            sp.RemoveFieldfromPref("NAME");


                            Intent intntt = new Intent(getApplicationContext(),splash.class);
                            intntt.putExtra("LOGGED_IN","ERROR");
                            User_nav.this.finish(); // finishing this Activity
                            intntt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intntt);

                        } else {
                            //Toast.makeText(contxt, "Request Sent..We will Contact you Soon", Toast.LENGTH_SHORT).show();
                           // sts_cd[0] = s;

                            Log.d("FB","Evaluate-(1)->"+(!s.equals("NO_VAL")));
                            Log.d("FB","Evaluate-(2)->"+(s!="NO_VAL"));

                              //&& sp.GetFromPreference("BACK_REG").equals("NO")
                             //&& sp.GetFromPreference("BACK_REG")=="NO"
                             //&& sp.GetFromPreference("BACK_REG")=="NO"
                            //&& sp.GetFromPreference("BACK_REG").equals("NO")
                            if ((s.equals("NO_VAL")  ) || ((s=="NO_VAL") ) || ((s.equals("NO_REC"))  ) || ((s=="NO_VAL")) )
                            {

                                Toast.makeText(getApplicationContext(), "USER NOT REGISTEREED with WEDD SITE......", Toast.LENGTH_LONG).show();
//
                                WedRegFrgagment(); // for registraton Page



//                                Log.d("FB","VALUE-->"+s);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "ALREADY REGISTIEREED with WEDD SITE......", Toast.LENGTH_LONG).show();

                                sp.AddtoPreferance("BACK_REG", "YES"); // Updating Back Ground Registration as already Registered with Back ground

                                User_nav_menu_fragment navmenu = (User_nav_menu_fragment)getSupportFragmentManager().findFragmentByTag("NAV_MENU");

                                   if(navmenu==null)
                                   {
                                       navmenu = new User_nav_menu_fragment();
                                       FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                                       //t_manager.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                                       t_manager_1.remove(navmenu);
                                       t_manager_1.add(R.id.downmenu, navmenu, "NAV_MENU");
                                       // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                                       //t_manager_1.addToBackStack(null);
                                       //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                                       t_manager_1.commit();
                                   }
                                else
                                   {
                                       FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                                       //t_manager.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                                       t_manager_1.add(R.id.downmenu, navmenu, "NAV_MENU");
                                       // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                                       //t_manager_1.addToBackStack(null);
                                       //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                                       t_manager_1.commit();
                                   }



                            }



                        }
                        //second_fragment sf =(second_fragment) getActivity().getSupportFragmentManager().findFragmentByTag("SECOND");
                        // getActivity().getSupportFragmentManager().beginTransaction().remove(sf).attach((opening) getActivity().getSupportFragmentManager().findFragmentByTag("FIRST")).commit();

                        // InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        // imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    }
                }


                if(sp.GetFromPreference("EMAIL")!="NO_VAL") {
                    SaveRectoServer bb = new SaveRectoServer();
                    bb.execute(getApplicationContext());
                }
                else
                {
                    FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                    //t_manager_1.add(R.id.downmenu, navmenu, "NAV_MENU");
                    t_manager_1.detach((User_nav_menu_fragment) getSupportFragmentManager().findFragmentByTag("NAV_MENU"));
                    t_manager_1.addToBackStack("NAV_MENU");
                    t_manager_1.commit();
                    Toast.makeText(User_nav.this, "Complete Your Profile First", Toast.LENGTH_SHORT).show();
                    WedRegFrgagment();
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void WedRegFrgagment() // for showing Wed the registration page
    {

        signup_wed_fb fb_swh = new signup_wed_fb();
        FragmentTransaction t_manager = f_manager.beginTransaction();
        //t_manager.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
        t_manager.add(R.id.rel_lay_show_grp_1, fb_swh, "FB_SHW");
        // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
        //t_manager.addToBackStack(null);
        //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
        t_manager.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.d("FB", "USER_NAV ON ACTIVITY RESULT GETS CALLED.....");

        ActivityResultBus.getInstance().postQueue(
                new ActivityResultEvent(requestCode, resultCode, data));
        //Log.d("FB", "On Activity Result ELSE PART");

//        f_manager = getSupportFragmentManager();
//
//
//        create_wed_frgmnt frst_frgmnt = (create_wed_frgmnt) f_manager.findFragmentByTag("ADD_WEDD");
//
//        if (frst_frgmnt == null) {
//            frst_frgmnt = new create_wed_frgmnt();
//            FragmentTransaction t_manager = f_manager.beginTransaction();
//            // t_manager.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_bottom);
//            //t_manager.add(R.id.frgmnt_group, frst_frgmnt, "FIRST");
//            //t_manager.attach(frst_frgmnt);
//            t_manager.remove(frst_frgmnt);
//            //t_manager.addToBackStack("FIRST");
//            t_manager.add(R.id.rel_lay_show_grp_1, frst_frgmnt, "ADD_WEDD");
//            t_manager.commit();
//
//        } else {
//            FragmentTransaction t_manager = f_manager.beginTransaction();
//            // t_manager.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_bottom);
//            // t_manager.add(R.id.rel_lay_show_grp_1, frst_frgmnt, "ADD_WEDD");
//            t_manager.attach(frst_frgmnt);
//            //t_manager.addToBackStack("FIRST");
//            t_manager.commit();
//
//        }
//
//
    }
}
