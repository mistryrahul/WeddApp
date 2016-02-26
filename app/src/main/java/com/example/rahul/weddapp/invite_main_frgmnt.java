package com.example.rahul.weddapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Admin on 16-12-2015.
 */
public class invite_main_frgmnt  extends Fragment implements View.OnClickListener
{
    //ImageButton message;
    //ImageButton gmail;
    //ImageButton facebook;
    //ImageButton Whatsapp;
    ImageView imvu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_main_frgmnt,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        View parent = getView();
        //message = (ImageButton)parent.findViewById(R.id.msg_img_btn);
        //gmail =(ImageButton)parent.findViewById(R.id.gmail_img_btn);
        //facebook = (ImageButton)parent.findViewById(R.id.fb_img_btn);
        //Whatsapp =(ImageButton)parent.findViewById(R.id.whatsapp_img_btn);
        imvu = (ImageView)parent.findViewById(R.id.invtatn_imgvu);
        imvu.setOnClickListener(this);
        ///message.setOnClickListener(this);
        //gmail.setOnClickListener(this);
        //facebook.setOnClickListener(this);
        //Whatsapp.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.invtatn_imgvu)
        {
            Toast.makeText(getActivity().getApplicationContext(),"Invitaion Code Copied in Clipboard",Toast.LENGTH_SHORT).show();

            shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
            String msg="NO_VAL";
            if((!sp.GetFromPreference("BRD_NM").equals("NO_VAL")) && (!sp.GetFromPreference("GRM_NM").equals("NO_VAL")) && (!sp.GetFromPreference("WED_CD").equals("NO_VAL")))
            {
                 msg =sp.GetFromPreference("BRD_NM")+" and "+sp.GetFromPreference("GRM_NM")+" getting married, "+
                        "You are invited to witness this Auspicious Event,"+"\n"+
                        "please download the app to get all the information regarding the events. The link is "+"\n"+
                        "http://FULLLinkOfApptoDownload"+"\n"+
                        "Please use the wedding code-"+sp.GetFromPreference("WED_CD")+" while registering in the App";
            }

            if(!msg.equals("NO_VAL"))
            {
                Misc.setClipboard(getActivity().getApplicationContext(),msg);

                Intent watsapp = new Intent();
                watsapp.setAction(Intent.ACTION_SEND);
                watsapp.putExtra(Intent.EXTRA_TEXT, msg);
                watsapp.setType("text/plain");
                startActivity(watsapp);
            }

        }

    }
}
