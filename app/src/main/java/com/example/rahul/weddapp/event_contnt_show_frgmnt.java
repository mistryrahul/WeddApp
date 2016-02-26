package com.example.rahul.weddapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;


/**
 * Created by Admin on 30-11-2015.
 */
public class event_contnt_show_frgmnt extends Fragment implements View.OnClickListener {

    //private static final LatLng davao = new LatLng(22.572093, 88.351049);
    private GoogleMap map = null;
    private LinearLayout Dscrptn_Linlay;
    private LinearLayout VnueAddrs_Linlay;
    private LinearLayout TravInfo_Linlay;
    private LinearLayout Drscode_Linlay;
    private LinearLayout Accomodatn_Linlay;


    private TextView Venue_nm;
    private TextView Evnt_Nm;
    private TextView Day_imgb;
    private TextView year_imgb;
    private TextView evnt_code;
    private static JSONObject jsobnew;
    private TextView Accomodatn_txtvu;
    private TextView Dscrptn_txtvu;
    private TextView VnueAddrs_txtvu;
    private TextView TravInfo_txtvu;
    private TextView Drscode_txtvu;

    private Button delete_btn;
    private Button edit_btn;
    private Button add_to_cal_btn;

    SupportMapFragment fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_contnt_show_frgmnt, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentByTag("GOOGLE_MAP");
//        if (fragment == null) {
//            fragment = SupportMapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.g_map, fragment, "GOOGLE_MAP").commit();
//        }

    }

    @Override
    public void onResume() {
        super.onResume();
        View parentview = getView();
        Bundle args = this.getArguments();

        delete_btn = (Button)parentview.findViewById(R.id.DeleteEvnt);
        edit_btn = (Button)parentview.findViewById(R.id.edit);
        add_to_cal_btn = (Button)parentview.findViewById(R.id.Addtocalender);

        Dscrptn_Linlay = (LinearLayout) parentview.findViewById(R.id.dscrptn_linlay);
        VnueAddrs_Linlay = (LinearLayout) parentview.findViewById(R.id.VenueAddrs_linlay);
        TravInfo_Linlay = (LinearLayout) parentview.findViewById(R.id.TravInfo_linlay);
        Drscode_Linlay = (LinearLayout) parentview.findViewById(R.id.DrsCode_linlay);
        Accomodatn_Linlay = (LinearLayout) parentview.findViewById(R.id.Accomodatn_linlay);

        Venue_nm = (TextView)parentview.findViewById(R.id.Venue);
        Evnt_Nm = (TextView)parentview.findViewById(R.id.evntNm);
        Day_imgb = (TextView)parentview.findViewById(R.id.day_imgbox);
        year_imgb = (TextView)parentview.findViewById(R.id.year_imgbox);
        evnt_code = (TextView)parentview.findViewById(R.id.event_id);

        Dscrptn_Linlay.setOnClickListener(this);
        VnueAddrs_Linlay.setOnClickListener(this);
        TravInfo_Linlay.setOnClickListener(this);
        Drscode_Linlay.setOnClickListener(this);
        Accomodatn_Linlay.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);
        add_to_cal_btn.setOnClickListener(this);

        Dscrptn_txtvu = (TextView) parentview.findViewById(R.id.dscrptn_txtvu);
        VnueAddrs_txtvu = (TextView) parentview.findViewById(R.id.VnuAddrs_txtvu);
        TravInfo_txtvu = (TextView) parentview.findViewById(R.id.TravInfo_txtvu);
        Drscode_txtvu = (TextView) parentview.findViewById(R.id.DrsCode_txtvu);
        Accomodatn_txtvu = (TextView) parentview.findViewById(R.id.Accomodatn_txtvu);

        try {

            Log.d("FB","Key--"+(args.getString("json_obj")!=null));

            if ((args.getString("json_obj")!=null)) {
//           if(map==null)
//           {
//               map = fragment.getMap();
//               map.addMarker(new MarkerOptions().position(new LatLng(22.572093, 88.351049)));
//           }
                Log.d("FB","ENTERING .............");

                String json = args.getString("json_obj");
                jsobnew = new JSONObject(json);
                Dscrptn_txtvu.setText(jsobnew.getString("description"));
                VnueAddrs_txtvu.setText(jsobnew.getString("location"));
                TravInfo_txtvu.setText(jsobnew.getString("travel_info"));
                Drscode_txtvu.setText(jsobnew.getString("dress_code"));
                Accomodatn_txtvu.setText(jsobnew.getString("accomodation"));
                Venue_nm.setText(jsobnew.getString("venue"));
                Evnt_Nm.setText(jsobnew.getString("event_name"));
                Day_imgb.setText(Misc.getdayMonth(jsobnew.getString("day")));
                year_imgb.setText(Misc.getYear(jsobnew.getString("day")));
                evnt_code.setText(jsobnew.getString("event_code"));
                Log.d("FB","JSON RUNNING....");
                Log.d("FB", "EVENT NAME->" + jsobnew.getString("event_name"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("FB","ERROR "+e.getMessage());
        }

    }

    @Override
    public void onClick(View v) {


        Log.d("FB","ON CLICK WORKING.........");
        Log.d("FB","EVENT_CODE"+v.getId());

        if (v.getId() == R.id.dscrptn_linlay) {

            Log.d("FB","DESCRIPTION");
            if(Dscrptn_txtvu.getVisibility()==View.VISIBLE)
            {
                Dscrptn_txtvu.setVisibility(View.GONE);
                Dscrptn_Linlay.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                Log.d("FB","INVISIBLE");

            }
            else
            {
                Dscrptn_txtvu.setVisibility(View.VISIBLE);
                Log.d("FB", "VISIBLE");
            }


        } else if (v.getId() == R.id.VenueAddrs_linlay) {

            Log.d("FB","VENUE ADDRESS");
            if(VnueAddrs_txtvu.getVisibility()==View.VISIBLE)
            {
                VnueAddrs_txtvu.setVisibility(View.GONE);
                VnueAddrs_Linlay.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                Log.d("FB", "INVISIBLE");
            }
            else
            {
                VnueAddrs_txtvu.setVisibility(View.VISIBLE);
                Log.d("FB", "VISIBLE");
            }

        } else if (v.getId() == R.id.TravInfo_linlay) {

            Log.d("FB","TRAVELINFO");
            if(TravInfo_txtvu.getVisibility()==View.VISIBLE)
            {
                TravInfo_txtvu.setVisibility(View.GONE);
                TravInfo_Linlay.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                Log.d("FB", "INVISIBLE");
            }
            else
            {
                TravInfo_txtvu.setVisibility(View.VISIBLE);
                Log.d("FB", "VISIBLE");
            }

        } else if (v.getId() == R.id.DrsCode_linlay) {

            Log.d("FB","DRESSCODE");
            if(Drscode_txtvu.getVisibility()==View.VISIBLE)
            {
                Drscode_txtvu.setVisibility(View.GONE);
                Drscode_Linlay.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                Log.d("FB", "INVISIBLE");
            }
            else
            {
                Drscode_txtvu.setVisibility(View.VISIBLE);
                Log.d("FB", "VISIBLE");
            }

        }
        else if (v.getId() == R.id.Accomodatn_linlay) {

            Log.d("FB","DRESSCODE");
            if(Accomodatn_txtvu.getVisibility()==View.VISIBLE)
            {
                Accomodatn_txtvu.setVisibility(View.GONE);
                Accomodatn_Linlay.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                Log.d("FB", "INVISIBLE");
            }
            else
            {
                Accomodatn_txtvu.setVisibility(View.VISIBLE);
                Log.d("FB", "VISIBLE");
            }

        }

        else if(v.getId()== R.id.DeleteEvnt)
        {
            Log.d("FB","DELETE EVENT.... STARTED>...");
            ////////
            class delete_event extends AsyncTask<String,Void,String>
            {

                @Override
                protected String doInBackground(String... params) {

                    shared_pref nt = new shared_pref("NETWORK",getActivity().getApplicationContext());
                    String uri = nt.GetFromPreference("B_URI")+"/Events/Deleteevnt/"+params[0];
                    Log.d("FB","Delet Event-->"+uri);
                    String rersp="NO";

                    try {
                        HttpClient client = new DefaultHttpClient();
                        HttpDelete del = new HttpDelete(uri);
                        HttpResponse respnce = client.execute(del);
                        if (respnce.getStatusLine().getStatusCode() == 200)
                        {
                            rersp = EntityUtils.toString(respnce.getEntity());
                            //Log.d("FB","IN ALIVE (RESPONCE STRING)->"+res_str);
                        }

                       }
                      catch (IOException e)
                      {
                        e.printStackTrace();
                        rersp="ERROR "+e.getMessage();
                        return rersp;
                      }

                    return rersp;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    if(s.startsWith("NO"))
                    {
                      Log.d("FB","NO SUCH Event EXIST");
                    }
                    else if(s.startsWith("ERROR"))
                    {
                        Log.d("FB",s);
                    }
                    else if(s.startsWith("YES"))
                    {

                        android.support.v4.app.FragmentManager f_manager;
                        f_manager = getFragmentManager();
                        //wed_menu_fragmnt wed_mnu_frg = new wed_menu_fragmnt();
                        event_viewer_frgmnt edt_wed_frg = new event_viewer_frgmnt();
                        FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                        //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                        //t_manager_1.addToBackStack("WED_MENU");
                        t_manager_1.addToBackStack("WED_MENU");
                        t_manager_1.remove((event_contnt_show_frgmnt) getFragmentManager().findFragmentByTag("SHW_EVNT"));
                        //t_manager_1.add(R.id.rel_lay_show_grp_2, wed_mnu_frg, "WED_MENU");
                        t_manager_1.replace(R.id.containerview,edt_wed_frg , "EVNT_VWER_PRNT");
                        t_manager_1.commit();

                    }


                }
            }

            final AlertDialog.Builder delet = new AlertDialog.Builder(getContext());
                                delet.setMessage("Are you Sure want To delete this Event");
                                delet.setCancelable(false);
                                delet.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        delete_event de = new delete_event();
                                        de.execute(evnt_code.getText().toString());
                                        dialog.cancel();
                                    }
                                });

                                delet.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.cancel();
                                    }
                                });

                      AlertDialog alrt = delet.create();
                      alrt.show();

            /////////


        }
        else if(v.getId()==R.id.Addtocalender)
        {
            final ContentValues event = new ContentValues();
            event.put(CalendarContract.Events.CALENDAR_ID, 1);

            event.put(CalendarContract.Events.TITLE, Evnt_Nm.getText().toString());
            event.put(CalendarContract.Events.DESCRIPTION, "INVITED...Need to visit");
            event.put(CalendarContract.Events.EVENT_LOCATION, Venue_nm.getText().toString());

            String inp = Day_imgb.getText().toString().substring(4,6)+"-"+Day_imgb.getText().toString().substring(0,3)+"-"+year_imgb.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date dt = sdf.parse(inp);
                Log.d("FB","FULL DATE--->>"+dt);
                Log.d("FB","DAY-"+Day_imgb.getText().toString().substring(4,6));
                Log.d("FB","MON-"+Day_imgb.getText().toString().substring(0,3));
                Log.d("FB","Year-"+year_imgb.getText().toString());

                Log.d("FB","DATE is-->"+(dt.getYear()+1900)+"/"+dt.getMonth()+1+"/"+dt.getDate());

                Calendar begintime = Calendar.getInstance();
                Calendar enddate = Calendar.getInstance();
                begintime.set((dt.getYear()+1900),(dt.getMonth()+1),dt.getDate(),10,30);

                enddate.set((dt.getYear()+1900),(dt.getMonth()+1),dt.getDate(),11,00);

                event.put(CalendarContract.Events.DTSTART,begintime.getTimeInMillis());
                event.put(CalendarContract.Events.DTEND, enddate.getTimeInMillis());
                event.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
                event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true
                String timeZone = TimeZone.getDefault().getID();
                event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

                Uri baseUri;
                if (Build.VERSION.SDK_INT >= 8) {
                    baseUri = Uri.parse("content://com.android.calendar/events");
                } else {
                    baseUri = Uri.parse("content://calendar/events");
                }


                getActivity().getApplicationContext().getContentResolver().insert(baseUri, event);
                Toast.makeText(getActivity().getApplicationContext(),"Event Added to Calender",Toast.LENGTH_SHORT).show();

                Log.d("FB","event added to calander");


            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*Calendar begintime = Calendar.getInstance();

            int yr = Integer.parseInt(year_imgb.getText().toString());
            String mon= Day_imgb.getText().toString().substring(0,3);
            int day = Integer.parseInt(Day_imgb.getText().toString().substring(4,6));
            int mnth = 0;
            if(mon.startsWith("JAN"))
            {
                mnth=1;
            }
            else if(mon.startsWith("FEB"))
            {
               mnth=2;
            }
            else if(mon.startsWith("MAR"))
            {
                mnth=3;
            }
            else if(mon.startsWith("APR"))
            {
                mnth=4;
            }
            else if(mon.startsWith("MAY"))
            {
                mnth=5;
            }
            else if(mon.startsWith("JUN"))
            {
                mnth=6;
            }
            else if(mon.startsWith("JUL"))
            {
                mnth=7;
            }
            else if(mon.startsWith("AUG"))
            {
                mnth=8;
            }
            else if(mon.startsWith("SEP"))
            {
                mnth=9;
            }
            else if(mon.startsWith("OCT"))
            {
                mnth=10;
            }
            else if(mon.startsWith("NOV"))
            {
                mnth=11;
            }
            else if(mon.startsWith("DEC"))
            {
                mnth=12;
            }*/
            /*begintime.set(yr, mnth, day, 10, 30);
            Calendar enddate = Calendar.getInstance();
            enddate.set(yr, mnth, day, 11, 00);
            event.put(CalendarContract.Events.DTSTART, String.valueOf(begintime));
            event.put(CalendarContract.Events.DTEND, String.valueOf(enddate));
            event.put(CalendarContract.Events.ALL_DAY, 0);   // 0 for false, 1 for true
            event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true

            String timeZone = TimeZone.getDefault().getID();
            event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

            Uri baseUri;
            if (Build.VERSION.SDK_INT >= 8) {
                baseUri = Uri.parse("content://com.android.calendar/events");
            } else {
                baseUri = Uri.parse("content://calendar/events");
            }

            Context context = null;
            context.getContentResolver().insert(baseUri, event);
*/

        }
        else if(v.getId()==R.id.edit)
        {
            shared_pref.Update_flag = 1;
            Toast.makeText(getActivity().getApplicationContext(),"Event Name Cannot be changed",Toast.LENGTH_LONG).show();
            android.support.v4.app.FragmentManager f_manager;
            f_manager = getFragmentManager();
            //wed_menu_fragmnt wed_mnu_frg = new wed_menu_fragmnt();
            //event_viewer_frgmnt edt_wed_frg = new event_viewer_frgmnt();
            add_event_frgmnt add_evnt_frg = new add_event_frgmnt();

            Bundle args = new Bundle();
            try {
            //    Log.d("FB", "EVENT_NAME in some where...->" + jsobnew.getString("event_name"));

                args.putString("EVENT_NAME", jsobnew.getString("event_name"));
                args.putString("DESCRIPTION",jsobnew.getString("description"));
                args.putString("DAY",jsobnew.getString("day"));
                args.putString("DRESSCODE",jsobnew.getString("dress_code"));
                args.putString("VENUE",jsobnew.getString("venue"));
                args.putString("LAT",jsobnew.getString("lat"));
                args.putString("LNG",jsobnew.getString("lng"));
                args.putString("LOCATION",jsobnew.getString("location"));
                args.putString("TRAV_INFO",jsobnew.getString("travel_info"));
                args.putString("ACCOMODATION",jsobnew.getString("accomodation"));
                args.putString("EVENT_CODE",jsobnew.getString("event_code"));

                add_evnt_frg.setArguments(args);
                FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                //t_manager_1.addToBackStack("WED_MENU");
                t_manager_1.remove((event_contnt_show_frgmnt) getFragmentManager().findFragmentByTag("SHW_EVNT"));
                t_manager_1.addToBackStack("SHW_EVNT");
                //t_manager_1.add(R.id.rel_lay_show_grp_2, wed_mnu_frg, "WED_MENU");
                t_manager_1.replace(R.id.containerview, add_evnt_frg, "EVNT_VWER");
                t_manager_1.commit();


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),"JSON PARSING ERROR "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),"ERROR "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }




        }

    }
}
