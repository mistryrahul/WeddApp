package com.example.rahul.weddapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.bus.ActivityResultBus;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.bus.ActivityResultEvent;



//public class Wedding_home extends AppCompatActivity implements communicator {
public class Wedding_home extends AppCompatActivity {

    FragmentManager f_manager;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        wed_menu_fragmnt crt_wed_frg = ((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
//        if (crt_wed_frg != null) {
//            f_manager = getSupportFragmentManager();
//            FragmentTransaction fragmnt_trnctn = f_manager.beginTransaction();
//            fragmnt_trnctn.replace(R.id.containerview, crt_wed_frg, "WED_MENU").commit();
//
//        } else {
//            crt_wed_frg = new wed_menu_fragmnt();
//            f_manager = getSupportFragmentManager();
//            FragmentTransaction fragmnt_trnctn = f_manager.beginTransaction();
//            fragmnt_trnctn.replace(R.id.containerview, crt_wed_frg, "WED_MENU").commit();
//
//        }
         if(crt_wed_frg==null)
         {
             crt_wed_frg = new wed_menu_fragmnt();
            // f_manager = getSupportFragmentManager();
           // FragmentTransaction fragmnt_trnctn = f_manager.beginTransaction();
           // fragmnt_trnctn.replace(R.id.containerview, crt_wed_frg, "WED_MENU").commit();

         }

             f_manager = getSupportFragmentManager();
             FragmentTransaction fragmnt_trnctn = f_manager.beginTransaction();
             fragmnt_trnctn.replace(R.id.containerview, crt_wed_frg, "WED_MENU").commit();



        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


               /* Log.d("FB", "Menu Item-->" + menuItem.getItemId());
                Log.d("FB","key People-->"+R.id.keyppl);
                Log.d("FB","Pic Galary-->"+R.id.picgal);
                Log.d("FB","Events-->"+R.id.evnts);
                Log.d("FB","Invite-->"+R.id.invite);
                Log.d("FB","Toast-->"+R.id.toast);
                Log.d("FB","Message-->"+R.id.message);
                Log.d("FB","Help Desk-->"+R.id.hlpdsk);*/

                if (menuItem.getItemId() == R.id.keyppl) {
                    Toast.makeText(getApplicationContext(), "IN KEYPPL", Toast.LENGTH_SHORT).show();
                    Log.d("FB", "KEY PPL");
                }
                if (menuItem.getItemId() == R.id.picgal) {
                    Toast.makeText(getApplicationContext(), "IN PIC GAL", Toast.LENGTH_SHORT).show();
                    // Log.d("FB", "IN PIC GAL");

                    f_manager = getSupportFragmentManager();
                    galary_menu_frgmnt gmf = new galary_menu_frgmnt();

                    FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                    //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                    t_manager_1.addToBackStack("WED_MENU");
                    t_manager_1.replace(R.id.containerview, gmf, "GALARY_MN");
                    // t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
                    // t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");

                    // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                    //t_manager_1.addToBackStack(null);
                    //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                    t_manager_1.commit();


                }
                if (menuItem.getItemId() == R.id.wedding) {
                    // Toast.makeText(getApplicationContext(), "IN Wedding", Toast.LENGTH_SHORT).show();
                    // Log.d("FB", "IN WEDDING");

                    f_manager = getSupportFragmentManager();
                    edit_wed_frgmnt edt_wed_frg = new edit_wed_frgmnt();
                    FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                    //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                    //t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
                    //t_manager_1.add(R.id.rel_lay_show_grp_2, edt_wed_frg, "EDT_WED");
                    t_manager_1.replace(R.id.containerview, edt_wed_frg, "EDT_WED");
                    t_manager_1.addToBackStack("WED_MENU");
                    // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                    //t_manager_1.addToBackStack(null);
                    //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                    t_manager_1.commit();

                }
                if (menuItem.getItemId() == R.id.evnts) {
                    Toast.makeText(getApplicationContext(), "IN Events", Toast.LENGTH_SHORT).show();
                    Log.d("FB", "EVENTS");


                    f_manager = getSupportFragmentManager();
                    event_viewer_frgmnt edt_wed_frg = new event_viewer_frgmnt();
                    FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                    //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                    t_manager_1.addToBackStack("WED_MENU");
                    t_manager_1.replace(R.id.containerview, edt_wed_frg, "EVNT_VWER_PRNT");
                    // t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
                    // t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");

                    // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                    //t_manager_1.addToBackStack(null);
                    //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                    t_manager_1.commit();

                }
                if (menuItem.getItemId() == R.id.invite) {
                    //Toast.makeText(getApplicationContext(), "IN Invite", Toast.LENGTH_SHORT).show();
                    Log.d("FB", "Invite");

                    f_manager = getSupportFragmentManager();
                    invite_main_frgmnt invt_mn = new invite_main_frgmnt();

                    FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                    //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                    t_manager_1.addToBackStack("WED_MENU");
                    t_manager_1.replace(R.id.containerview, invt_mn, "INVITE_MN");
                    // t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
                    // t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");

                    // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                    //t_manager_1.addToBackStack(null);
                    //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                    t_manager_1.commit();


                }
                if (menuItem.getItemId() == R.id.toast) {
                    Toast.makeText(getApplicationContext(), "IN Toast", Toast.LENGTH_SHORT).show();
                    Log.d("FB", "In Toast");
                }
                if (menuItem.getItemId() == R.id.message) {
                    Toast.makeText(getApplicationContext(), "IN Message", Toast.LENGTH_SHORT).show();
                    Log.d("FB", "In Message");
                }
                if (menuItem.getItemId() == R.id.hlpdsk) {
                    // Toast.makeText(getApplicationContext(), "IN HelpDesk", Toast.LENGTH_SHORT).show();
                    // Log.d("FB", "In HelpDesk");
                }
                if (menuItem.getItemId() == R.id.home_scrn) {

                    wed_menu_fragmnt crt_wed_frg = ((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));

                    if(crt_wed_frg==null)
                    {
                        crt_wed_frg = new wed_menu_fragmnt();
                    }
                    f_manager = getSupportFragmentManager();
                    FragmentTransaction fragmnt_trnctn = f_manager.beginTransaction();
                    fragmnt_trnctn.replace(R.id.containerview, crt_wed_frg, "WED_MENU").commit();

                 /*   if (crt_wed_frg != null) {
                        f_manager = getSupportFragmentManager();
                        FragmentTransaction fragmnt_trnctn = f_manager.beginTransaction();
                        fragmnt_trnctn.replace(R.id.containerview, crt_wed_frg, "WED_MENU").commit();

                    } else {
                        crt_wed_frg = new wed_menu_fragmnt();
                        f_manager = getSupportFragmentManager();
                        FragmentTransaction fragmnt_trnctn = f_manager.beginTransaction();
                        fragmnt_trnctn.replace(R.id.containerview, crt_wed_frg, "WED_MENU").commit();

                    }*/

                }


                return false;
            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.wed_home_tool_bar);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();

      /*   wed_menu_fragmnt crt_wed_frg = ((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU")) ;
        if(crt_wed_frg!=null)
        {
//            Log.d("FB","COMING HERE............IN NOT NULL.");
//            crt_wed_frg = (wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU");
//            FragmentTransaction t_manager_1 = f_manager.beginTransaction();
//            //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
//            //t_manager_1.add(R.id.Linlay2, crt_wed_frg, "WED_MENU");
//            // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
//            //t_manager_1.addToBackStack(null);
//            //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
//            t_manager_1.remove(crt_wed_frg);
//            t_manager_1.add(R.id.Linlay2, crt_wed_frg, "WED_MENU");
//            t_manager_1.commit();

        }
        else if(crt_wed_frg==null)
        {
            Log.d("FB","COMING HERE in NULL NULL NULL.............");
            crt_wed_frg = new wed_menu_fragmnt();
            FragmentTransaction t_manager_1 = f_manager.beginTransaction();
            //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
            t_manager_1.add(R.id.Linlay2, crt_wed_frg, "WED_MENU");
            // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
            //t_manager_1.addToBackStack(null);
            //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
            t_manager_1.commit();

        }
*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActivityResultBus.getInstance().postQueue(
                new ActivityResultEvent(requestCode, resultCode, data));
    }


    /*@Override
    public void Respnce(int data) {
        if (data == 1) {
            Toast.makeText(getApplicationContext(), "KEY PEOPLE", Toast.LENGTH_LONG).show();
        }
        if (data == 2) {
            Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_LONG).show();
            // Toast.makeText(getApplicationContext(),"Key People...",Toast.LENGTH_LONG).show();
            f_manager = getSupportFragmentManager();
            add_event_frgmnt edt_wed_frg = new add_event_frgmnt();
            FragmentTransaction t_manager_1 = f_manager.beginTransaction();
            //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
            t_manager_1.addToBackStack("WED_MENU");
            t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
            t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");

            // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
            //t_manager_1.addToBackStack(null);
            //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
            t_manager_1.commit();
        }
        if (data == 3) {
            Toast.makeText(getApplicationContext(), "Invite", Toast.LENGTH_LONG).show();
        }
        if (data == 4) {
            Toast.makeText(getApplicationContext(), "Pic Galary", Toast.LENGTH_LONG).show();
        }
        if (data == 5) {
            Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_LONG).show();
        }
        if (data == 6) {
            Toast.makeText(getApplicationContext(), "MESSAGE", Toast.LENGTH_LONG).show();
        }
        if (data == 7) {
            Toast.makeText(getApplicationContext(), "HelpDesh..", Toast.LENGTH_LONG).show();
        }
        if (data == 8) {
            Toast.makeText(getApplicationContext(), "View Weddding", Toast.LENGTH_LONG).show();

            f_manager = getSupportFragmentManager();
            edit_wed_frgmnt edt_wed_frg = new edit_wed_frgmnt();
            FragmentTransaction t_manager_1 = f_manager.beginTransaction();
            //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
            t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
            t_manager_1.add(R.id.rel_lay_show_grp_2, edt_wed_frg, "EDT_WED");
            t_manager_1.addToBackStack("WED_MENU");
            // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
            //t_manager_1.addToBackStack(null);
            //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
            t_manager_1.commit();


        }

    }*/


}