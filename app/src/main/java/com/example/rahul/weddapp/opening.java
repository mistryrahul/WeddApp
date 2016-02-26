package com.example.rahul.weddapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class opening extends Fragment implements View.OnClickListener {


    Button view_demo_btn,contact_btn,continue_btn;
    communicator comm;
    View parent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parent = inflater.inflate(R.layout.fragment_opening,container,false);
        return parent;


    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (communicator) getActivity(); //attached to the main activity


        contact_btn = (Button) getActivity().findViewById(R.id.contact_btn);
        continue_btn = (Button) getActivity().findViewById(R.id.continue_btn);
        view_demo_btn =(Button) getActivity().findViewById(R.id.demo_btn);


        contact_btn.setOnClickListener(this);
        continue_btn.setOnClickListener(this);
        view_demo_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


       // Toast.makeText(getActivity(),"ID"+v.getId()+"id again->"+R.id.contact_btn,Toast.LENGTH_SHORT).show();

        if(v.getId() == R.id.continue_btn)
        {
          //  Toast.makeText(getActivity(),"RUNNING OK....",Toast.LENGTH_SHORT).show();


          comm.Respnce(1); // 1 for contact
        }
        if(v.getId() == R.id.contact_btn)
        {

         //   Toast.makeText(getActivity(),"RUNNING OK in ....Contact",Toast.LENGTH_SHORT).show();

            comm.Respnce(2); // 2 for continue
        }
        if(v.getId() == R.id.demo_btn)
        {
          //  Toast.makeText(getActivity(),"RUNNING OK...DEmo.",Toast.LENGTH_SHORT).show();

            comm.Respnce(3); // 3 for demo view
        }
    }
}
