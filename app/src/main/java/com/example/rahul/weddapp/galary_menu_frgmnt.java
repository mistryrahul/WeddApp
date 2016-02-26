package com.example.rahul.weddapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Admin on 16-12-2015.
 */
public class galary_menu_frgmnt extends Fragment
{
    FragmentManager f_manager;
    Button CreateGlary;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.galary_menu_frgmnt,container,false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
       View parent = getView();
        CreateGlary = (Button)parent.findViewById(R.id.crtgal);

        CreateGlary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                f_manager = getFragmentManager();
                multiple_img_upload_frgmnt gmf =new multiple_img_upload_frgmnt();


                FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                t_manager_1.addToBackStack("WED_MENU");
                t_manager_1.replace(R.id.containerview, gmf, "MULTI_PIC_UPLOAD");
                // t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
                // t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");

                // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                //t_manager_1.addToBackStack(null);
                //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                t_manager_1.commit();

            }
        });

    }
}


