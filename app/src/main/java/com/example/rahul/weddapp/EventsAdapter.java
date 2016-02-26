package com.example.rahul.weddapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    static JSONArray event_list;
    static Context ctx_ori;
    private LayoutInflater inflate;
    public EventsAdapter()
    {

    }


    public EventsAdapter(Context ctx,JSONArray jsonarry)
    {
        ctx_ori =ctx;
        event_list = jsonarry;
        inflate = LayoutInflater.from(ctx);

    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.events_viewholder,parent,false);
        EventViewHolder holder = new EventViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
    /// code to set data in the recycler vieew adapter....

        try {
            JSONObject job = (JSONObject) event_list.get(position);

           // Log.d("FB","JSON OBJ--->"+job.getString("day"));


            holder.evnt_cd.setText(job.getString("event_code"));
            holder.Evntnm.setText(job.getString("event_name"));
            holder.EvntAddrs.setText(job.getString("venue"));
            holder.year.setText(Misc.getYear(job.getString("day")));
            holder.day.setText(Misc.getdayMonth(job.getString("day")));



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return event_list.length();
    }

    public static class  EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView Evntnm;
        TextView EvntAddrs;
        TextView year;
        TextView day;
        TextView evnt_cd;

        public EventViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            Evntnm = (TextView)itemView.findViewById(R.id.evnt_nm);
            EvntAddrs = (TextView)itemView.findViewById(R.id.evnt_loc);
            year = (TextView)itemView.findViewById(R.id.year);
            day = (TextView)itemView.findViewById(R.id.day);
            evnt_cd =(TextView)itemView.findViewById(R.id.evnt_cd);


        }

        @Override
        public void onClick(View v) {

            TextView tv_evnt_cd = (TextView)v.findViewById(R.id.evnt_cd);
            int flag=0;
            String json_resp=null;
            JSONObject job_1 = null;

            //int evnt_cd = Integer.parseInt(tv_evnt_cd.getText().toString());
          //  Log.d("FB","On CLICK WOrking");
         //   Log.d("FB","TextView->"+tv_evnt_cd.getText());
          try {

              Log.d("FB","event list length"+event_list.length());

              for (int y = 0; y < event_list.length(); y++) {

               //   Log.d("FB","In Loop");

                  job_1 = event_list.getJSONObject(y);

                 // Log.d("FB","OK TILL HERE.....(1)");
                  json_resp = job_1.toString();

                //  Log.d("FB","OK TILL HERE.....(2)");
                  //Log.d("FB",json_resp);

                 // Log.d("FB",job_1.getString("event_code")+"  --iteration-->"+y);

                   if(job_1.getString("event_code").equals(tv_evnt_cd.getText().toString()))
                   {
                        flag=2;
                       Toast.makeText(ctx_ori,"CLICKED ON "+job_1.getString("event_name"),Toast.LENGTH_SHORT).show();
                         break;

                   }

              }

              if(flag==2)
              {
                  communicator_2 comm= new event_viewer_frgmnt();
                  flag=0;
                  comm.Responce(1,json_resp);
                  Log.d("FB", "COMING");
                 // Log.d("FB",job_1.getString("event_cd"));
              }
          }
          catch (Exception e)
          {
              Log.d("FB", "ERROR " + e.getMessage());
             // e.printStackTrace();
          }

        }
    }

}





