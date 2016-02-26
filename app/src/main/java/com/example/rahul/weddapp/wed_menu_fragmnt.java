package com.example.rahul.weddapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Admin on 10-11-2015.
 */
public class wed_menu_fragmnt extends Fragment
{
   // private communicator comm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.wed_menu_fragment,container,false);
        return parent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        comm = (communicator) getActivity();
        /*Button keyppl = (Button)getActivity().findViewById(R.id.Kppl);
        Button Invite = (Button)getActivity().findViewById(R.id.Invite);
        Button Evnt = (Button)getActivity().findViewById(R.id.Evnt);
        Button picgal = (Button)getActivity().findViewById(R.id.picgal);
        Button toast = (Button)getActivity().findViewById(R.id.toast);
        Button Msg = (Button)getActivity().findViewById(R.id.msg);
        Button Hlpdsk = (Button)getActivity().findViewById(R.id.hlpdsk);
        Button Viewwedd = (Button)getActivity().findViewById(R.id.ViewWedd);

        keyppl.setOnClickListener(this);
        Evnt.setOnClickListener(this);
        picgal.setOnClickListener(this);
        toast.setOnClickListener(this);
        Msg.setOnClickListener(this);
        Hlpdsk.setOnClickListener(this);
        Viewwedd.setOnClickListener(this);
        Invite.setOnClickListener(this);
*/    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

   /* @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.Kppl)
        {
          //comm.Respnce(1); // key people
        }
        else if(v.getId()==R.id.Evnt)
        {
         //   comm.Respnce(2); //Event
        }
        else if(v.getId()==R.id.Invite)
        {
         //   comm.Respnce(3); //Invite
        }
        else if(v.getId()==R.id.picgal)
        {
        //    comm.Respnce(4); //PicGalary
        }
        else if(v.getId()==R.id.toast)
        {
          //  comm.Respnce(5); //Toast
        }
        else if(v.getId()==R.id.msg)
        {
          //  comm.Respnce(6); //Message broadcast
        }
        else if(v.getId()==R.id.hlpdsk)
        {
         //   comm.Respnce(7); //helpdesk no
        }
        else if(v.getId()==R.id.ViewWedd)
        {
          //  comm.Respnce(8); //View/Edit Wedd Profile.
        }
    }*/
}
