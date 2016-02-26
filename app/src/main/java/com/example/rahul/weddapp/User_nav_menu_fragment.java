package com.example.rahul.weddapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Admin on 17-10-2015.
 */
public class User_nav_menu_fragment extends Fragment implements  View.OnClickListener{

    Button continu;
    Button viewpro;
    Button crtwedd;
    FragmentManager f_manager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_nav_menu_fragment, container, false);

        continu = (Button) view.findViewById(R.id.btncont);
        viewpro = (Button) view.findViewById(R.id.btnviewpro);
        crtwedd = (Button) view.findViewById(R.id.btncrtwedd);

        continu.setOnClickListener(this);
        viewpro.setOnClickListener(this);
        crtwedd.setOnClickListener(this);

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.btncont) {

            Toast.makeText(getActivity().getApplicationContext(), "CONTINUE IS PRESSED", Toast.LENGTH_SHORT).show();
            wed_chooser_frgmnt wcf = new wed_chooser_frgmnt();

            f_manager = getFragmentManager();
            FragmentTransaction t_manager = f_manager.beginTransaction();
            t_manager.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
            t_manager.remove((User_nav_menu_fragment) getFragmentManager().findFragmentByTag("NAV_MENU"));
            t_manager.add(R.id.rel_lay_show_grp_1,wcf,"WED_SEL");  //delete that
            //t_manager.replace(R.id.rel_lay_show_grp_1, wcf, "WED_SEL");
            t_manager.addToBackStack("NAV_MENU");
            t_manager.commit();


        }
        if (v.getId() == R.id.btnviewpro)
        {

            Toast.makeText(getActivity().getApplicationContext(),"ViewProfile IS PRESSED",Toast.LENGTH_SHORT).show();
            signup_wed_fb fb_swh = new signup_wed_fb();

            f_manager =getFragmentManager();
            FragmentTransaction t_manager = f_manager.beginTransaction();
            //t_manager.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);

            t_manager.remove((User_nav_menu_fragment) getFragmentManager().findFragmentByTag("NAV_MENU"));
              //manager.add(R.id.rel_lay_show_grp_1, fb_swh, "FB_SHW");
            t_manager.addToBackStack("NAV_MENU");
            t_manager.add(R.id.rel_lay_show_grp_1, fb_swh, "FB_SHW");
            //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
            t_manager.commit();

        }
        if(v.getId() == R.id.btncrtwedd)
        {

            Toast.makeText(getActivity().getApplicationContext(),"Create Wedding IS PRESSED",Toast.LENGTH_SHORT).show();
            f_manager = getFragmentManager();
            create_wed_frgmnt cwf = new create_wed_frgmnt();

            FragmentTransaction t_manager = f_manager.beginTransaction();
            t_manager.remove((User_nav_menu_fragment) getFragmentManager().findFragmentByTag("NAV_MENU"));
                // t_manager.add(R.id.rel_lay_show_grp_1, cwf, "ADD_WEDD");
            t_manager.addToBackStack("NAV_MENU");
            t_manager.add(R.id.rel_lay_show_grp_1, cwf, "ADD_WEDD");
            //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
            t_manager.commit();

        }

    }
}
