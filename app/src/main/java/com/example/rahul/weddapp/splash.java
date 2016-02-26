package com.example.rahul.weddapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.transition.TransitionManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cz.msebera.android.httpclient.client.protocol.ResponseContentEncoding;
import cz.msebera.android.httpclient.entity.ContentType;

public class splash extends AppCompatActivity implements communicator {

    FragmentManager f_manager;
    Animation animationSlideInright;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent inttnt = getIntent();
        f_manager = getSupportFragmentManager();

        // for determnig the screen type
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        int scr_type=0;
        String scr_res = null;

        switch (density)
        {
            case DisplayMetrics.DENSITY_LOW:
                scr_type=120;
                scr_res="/320/240";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                scr_type=160;
                scr_res="/480/320";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                scr_type=240;
                scr_res="/800/480";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                scr_type=320;
                scr_res="/1280/720";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                scr_type=480;
                scr_res="/1920/1080";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                scr_type=640;
                scr_res="/2360/1600";
                break;
        }





        //Log.d("FB", "get EXTRA is NOT NULL "+inttnt.getStringExtra("LOGGED_IN"));

        shared_pref nt = new shared_pref("NETWORK",getApplicationContext());
        nt.AddtoPreferance("B_URI","http://192.168.1.10:8080/WeddingApp/Resources");  //Ip Used in every Web Service,(As Ip tends to change)
        //nt.AddtoPreferance("SCR_DISPLAY",String.valueOf(scr_type));
        nt.AddtoPreferance("SCR_RES",String.valueOf(scr_res));
//        shared_pref sp = new shared_pref("LOGIN",getApplicationContext());
//        sp.RemoveFieldfromPref("FRGMNT_SHOW");
//        sp.RemoveFieldfromPref("ACCESSTOKEN");
//        sp.RemoveFieldfromPref("PIC_URL");
//        sp.RemoveFieldfromPref("EMAIL");
//        sp.RemoveFieldfromPref("NAME");


        if((inttnt.getStringExtra("LOGGED_IN")!=null) )
        {
            Log.d("FB", "INTENT EXTRA--> "+inttnt.getStringExtra("LOGGED_IN"));
           // Log.d("FB", "INTENT EXTRA--> "+ inttnt.getExtras());

            if((inttnt.getStringExtra("LOGGED_IN") == "ERROR") ||(inttnt.getStringExtra("LOGGED_IN").equals("ERROR")) || (inttnt.getStringExtra("LOGGED_IN") == "LOGOUT") || (inttnt.getStringExtra("LOGGED_IN").equals("LOGOUT") ) || (inttnt.getStringExtra("LOGGED_IN")=="LOGGED_IN") || (inttnt.getStringExtra("LOGGED_IN").equals("LOGGED_IN")))
            {
            Log.d("FB", "get EXTRA is NOT NULL "+inttnt.getStringExtra("LOGGED_IN"));


//|| (inttnt.getStringExtra("LOGGED_IN").equals("ERROR")) || (inttnt.getStringExtra("LOGGED_IN").equals("LOGOUT") ) || (inttnt.getStringExtra("LOGGED_IN").equals("YES"))
     //        if ((inttnt.getStringExtra("LOGGED_IN") == "ERROR") || (inttnt.getStringExtra("LOGGED_IN") == "LOGOUT") || (inttnt.getStringExtra("LOGGED_IN")=="LOGGED_IN") ) {
            Log.d("FB", "IN NO ANIMATION PART(Splash)");

            inttnt.removeExtra("LOGGED_IN"); // removing exta from intent

            final View frgmnt_vu = (View) findViewById(R.id.frgmnt_group);
            frgmnt_vu.setVisibility(View.VISIBLE);


            opening frst_frgmnt = (opening) getSupportFragmentManager().findFragmentByTag("FIRST");

            if (frst_frgmnt == null) {
                frst_frgmnt = new opening();
                FragmentTransaction t_manager = f_manager.beginTransaction();
                // t_manager.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_bottom);
                //t_manager.add(R.id.frgmnt_group, frst_frgmnt, "FIRST");
                //t_manager.attach(frst_frgmnt);
                t_manager.remove(frst_frgmnt);
                //t_manager.addToBackStack("FIRST");
                t_manager.add(R.id.frgmnt_group, frst_frgmnt, "FIRST");
                t_manager.commit();

            } else {
                FragmentTransaction t_manager = f_manager.beginTransaction();
                // t_manager.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_bottom);
                t_manager.add(R.id.frgmnt_group, frst_frgmnt, "FIRST");
                //t_manager.addToBackStack("FIRST");
                t_manager.commit();

            }


             }
        }
        else
        {

            // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            // getActionBar().hide();
            //       shared_pref sp = new shared_pref("LOGIN",getApplicationContext());
            //        sp.RemoveFieldfromPref("ACCESSTOKEN");
            //        sp.RemoveFieldfromPref("PIC_URL");
            //       sp.RemoveFieldfromPref("EMAIL");
            //        sp.RemoveFieldfromPref("NAME");


            View imv = (View) findViewById(R.id.splash_rel);


            animationSlideInright = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim);
            imv.startAnimation(animationSlideInright);

            final View frgmnt_vu = (View) findViewById(R.id.frgmnt_group);
            frgmnt_vu.setVisibility(View.VISIBLE);
            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(4000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                        opening frst_frgmnt = new opening();
                        FragmentTransaction t_manager = f_manager.beginTransaction();
                        t_manager.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_bottom);
                        t_manager.add(R.id.frgmnt_group, frst_frgmnt, "FIRST");
                        //t_manager.addToBackStack("FIRST");
                        t_manager.commit();

                    }
                }

            };

            timer.start();

        }



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
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

    @Override
    public void Respnce(int data) {

        //Toast.makeText(getApplicationContext(),"COMING HERE ATLEAAST",Toast.LENGTH_SHORT).show();

        if (data == 1) {

         //Log.d("FB","INTERNET->"+Misc.getConnectivityStatus(getApplicationContext()));

         if(Misc.getConnectivityStatus(getApplicationContext())!=0) {
            // redirection code to user_nav fragment
            //fb login code
            shared_pref sp = new shared_pref("LOGIN", getApplicationContext());
            if ((sp.GetFromPreference("ACCESSTOKEN") == "NO_VAL")  || (sp.GetFromPreference("ACCESSTOKEN").equals("NO_VAL"))) {
                View frgmnt_vu_3 = (View) findViewById(R.id.frgmnt_group_3);
                frgmnt_vu_3.setVisibility(View.VISIBLE);
                fblogin fb = new fblogin();
                FragmentTransaction t_manager = f_manager.beginTransaction();
                t_manager.setCustomAnimations(R.anim.fb_opening_anim, R.anim.fb_closing_anim, R.anim.fb_opening_anim, R.anim.fb_closing_anim);// add animation
                t_manager.add(R.id.frgmnt_group_3, fb, "THIRD");
                        //t_manager.replace(R.id.frgmnt_group_3,fb,"THIRD");  ///check
                t_manager.addToBackStack("FIRST");
                t_manager.remove((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                t_manager.commit();
            } else {
                Intent user_nav_intnt = new Intent(getApplicationContext(), User_nav.class);
                user_nav_intnt.putExtra("LOGGED_IN", "YES");
                startActivity(user_nav_intnt);
            }
          }
            else
          {
              Toast.makeText(getApplicationContext(),"No INTERNET, Make sure Internet is On",Toast.LENGTH_LONG).show();

          }


        }
        if (data == 2) {
            View frgmnt_vu_2 = (View) findViewById(R.id.frgmnt_group_2);
            frgmnt_vu_2.setVisibility(View.VISIBLE);
            second_fragment second_fragment = new second_fragment();
            FragmentTransaction t_manager = f_manager.beginTransaction();
            t_manager.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
            t_manager.add(R.id.frgmnt_group_2, second_fragment, "SECOND");
                      // t_manager.replace(R.id.frgmnt_group_2, second_fragment, "SECOND"); //check del that
            t_manager.addToBackStack("FIRST");
            t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
            t_manager.commit();
        }
        if (data == 3) {

        }


    }
}
