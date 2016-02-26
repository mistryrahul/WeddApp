package com.example.rahul.weddapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.EntityBuilder;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.ByteArrayBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Admin on 16-11-2015.
 */
public class add_event_frgmnt  extends Fragment
{
    private TextView dtv;
    private String evnt_cd;
    private Button btn;
    private TextView date_selctd;
    private EditText hh,mm,venue,venu_add,lat,lng,trav_inf,descrptn,accomo,drs_cd,event_nm_et;
    Spinner evnt_name;
    private int year_mn,month_mn,day_mn,hour_mn,min_mn;
    private int month_st,year_st,day_st;
    private Button save_button;
    private final int DATE_PICKER_ID=1,TIME_PICKER_ID=2;
    Bundle args;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_event_frgmnt,container,false);
        args = this.getArguments();
        return view;
    }
    //to check event name already exist or not
    class ChkEvntNameDate extends AsyncTask<Pair<String,String>,Void,String>
    {
        final shared_pref sp = new shared_pref("LOGIN", getActivity().getApplicationContext());
        String widget_nm;

        @Override
        protected String doInBackground(Pair<String, String>... params) {

            String url="";
            String resp_str="NO_VAL";
             shared_pref nt = new shared_pref("NETWORK",getActivity().getApplicationContext());
            // date format is mm/dd/yyyy
            Log.d("FB","IN ASYNC TASK");
            Log.d("FB","Pair 1"+params[0].first);
            Log.d("FB","Pair 2"+params[0].second);

            if(params[0].first.equalsIgnoreCase("NAME"))
            {
                widget_nm=params[0].first;
                url=nt.GetFromPreference("B_URI")+"/Events/Checkevent?wed_cd="+sp.GetFromPreference("WED_CD")+"&event_nm="+params[0].second+"&wed_dt=";
            }
            else if(params[0].first.equalsIgnoreCase("DATE"))
            {
                widget_nm=params[0].first;
                url=nt.GetFromPreference("B_URI")+"/Events/Checkevent?wed_cd="+sp.GetFromPreference("WED_CD")+"&event_nm=&wed_dt="+params[0].second;
            }
             Log.d("FB","URL->"+url);
            try {
                HttpClient httpclient_1 = new DefaultHttpClient();
                HttpGet hgt = new HttpGet(url);
                HttpResponse rspns = httpclient_1.execute(hgt);
                if (rspns.getStatusLine().getStatusCode() == 200) {
                    resp_str = EntityUtils.toString(rspns.getEntity());
                    //Log.d("FB","IN ALIVE (RESPONCE STRING)->"+res_str);
                }
                Log.d("FB","RESPONCE STRING-->"+resp_str);


            }
            catch (IOException e) {
                resp_str = "ERROR "+e.getMessage();
                return resp_str;
                //e.printStackTrace();
            }


            return resp_str;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("FB", "RESPONCE STRING in postExecute-->"+s);


            if(s.startsWith("NO_VAL"))
            {
                Toast.makeText(getActivity(),"NO VALUE",Toast.LENGTH_SHORT).show();
            }
            else if(s.startsWith("ERROR "))
            {
                Toast.makeText(getActivity(),"ERROR",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Log.d("FB","Value got from DB-->"+s);
                if(s.startsWith("YES"))
                {
                    if(widget_nm.equalsIgnoreCase("NAME") )
                    {

                        event_nm_et.setText("");
                        new AlertDialog.Builder(getActivity())
                                       .setTitle("Error")
                                       .setMessage("Event Already Created,Select Another Event")
                                       .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create().show();

                        evnt_name.setSelection(0);

                        //event_nm_et.setVisibility(View.VISIBLE);
                        //event_nm_et.setError("ALready Exist.select another");

                    }
                    else  //if(widget_nm.equalsIgnoreCase("DATE"))
                    {
                        //date_selctd.setText("");
                        date_selctd.setText("");
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error")
                                .setMessage("There is an Event on this Date")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();

                    }
                }
            }

        }
    }



    @Override
    public void onResume() {
        super.onResume();
        View parent = getView();
        dtv = (TextView)parent.findViewById(R.id.datetime);
        btn= (Button)parent.findViewById(R.id.Calender);
        evnt_name =(Spinner)parent.findViewById(R.id.spinnerEvntNm);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.event_lst,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        evnt_name.setPrompt("Event Name");
        evnt_name.setAdapter(adapter);
        date_selctd = (TextView)parent.findViewById(R.id.datetime);
        hh = (EditText)parent.findViewById(R.id.hh);
        mm = (EditText)parent.findViewById(R.id.mm);
        venue = (EditText)parent.findViewById(R.id.Venue);
        venu_add = (EditText)parent.findViewById(R.id.VenueAddrs);
        lat = (EditText)parent.findViewById(R.id.LAT);
        lng = (EditText)parent.findViewById(R.id.LONG);
        trav_inf = (EditText)parent.findViewById(R.id.EdtTxtTravInf);
        descrptn = (EditText)parent.findViewById(R.id.EdtTxtDscrptn);
        accomo = (EditText)parent.findViewById(R.id.EdtTxtAccomo);
        drs_cd = (EditText)parent.findViewById(R.id.EdtTxtDrsCd);
        event_nm_et = (EditText)parent.findViewById(R.id.edtxtEvntNm);
        save_button = (Button)parent.findViewById(R.id.save);

        Calendar cal = Calendar.getInstance();
        year_mn = cal.get(Calendar.YEAR);
        month_mn =(cal.get(Calendar.MONTH)+1);
        day_mn = cal.get(Calendar.DAY_OF_MONTH);

        //Log.d("FB","MONTH->"+cal.get(Calendar.MONTH));

         if(shared_pref.Update_flag==1)
           {

               /*
                   <item>Mehandi</item>
        Sangeet
        Wedding
        Reception
        Others
               */
                if(args!=null)
                {
                    Log.d("FB","ARGS VAL-->"+(args!=null));

                    if(args.getString("EVENT_NAME").equalsIgnoreCase("WEDDING"))
                    {
                        evnt_name.setSelection(1);
                    }
                    else if(args.getString("EVENT_NAME").equalsIgnoreCase("SANGEET"))
                    {
                        evnt_name.setSelection(0);
                    }
                    else if(args.getString("EVENT_NAME").equalsIgnoreCase("Reception"))
                    {
                        evnt_name.setSelection(2);
                    }
                    else
                    {
                        evnt_name.setSelection(3);
                        event_nm_et.setText(args.getString("EVENT_NAME"));
                    }

                    Misc.getdayMonth(args.getString("DAY")).replace(" ", "/");
                    Misc.getYear(args.getString("DAY"));

                    date_selctd.setText(Misc.getdayMonth(args.getString("DAY")).replace(" ", "/") + "/" + Misc.getYear(args.getString("DAY")));
                    //hh.setText(args.getString("hh"));
                    venue.setText(args.getString("VENUE"));
                    venu_add.setText(args.getString("LOCATION"));
                    lat.setText(args.getString("LAT"));
                    lng.setText(args.getString("LNG"));
                    trav_inf.setText(args.getString("TRAV_INFO"));
                    descrptn.setText(args.getString("DESCRIPTION"));
                    accomo.setText(args.getString("ACCOMODATION"));
                    drs_cd.setText(args.getString("DRESSCODE"));
                    evnt_cd = args.getString("EVENT_CODE");
                    save_button.setText("Update");
                }




           }
        


        // Check if this event is already created in this wedding
        evnt_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //////////////////////////////

                ///////////////////////////////
                Toast.makeText(getActivity().getApplicationContext(), "Item Selected-> " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

                event_nm_et.setVisibility(View.INVISIBLE);

                if (parent.getItemAtPosition(position).toString().startsWith("Others")) {
                    Log.d("FB", "In Others...");
                    event_nm_et.setVisibility(View.VISIBLE);
                } else if (parent.getItemAtPosition(position).toString().startsWith("--Select")) {

                } else {
                    ChkEvntNameDate obj = new ChkEvntNameDate();
                    //obj.execute(<"NAME",parent.getItemAtPosition(position).toString()>);
                    Pair<String, String> pobj = new Pair<String, String>("NAME", parent.getItemAtPosition(position).toString());
                    obj.execute(pobj);
                    Log.d("FB", "In rest 4...");
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        event_nm_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                 Toast.makeText(getActivity().getApplicationContext(),"FOCUS CHANGED",Toast.LENGTH_SHORT).show();
                 //event_nm_et.setVisibility(View.VISIBLE);
                 ChkEvntNameDate obj = new ChkEvntNameDate();
                 Pair<String, String> pobj = new Pair<String, String>("NAME", event_nm_et.getText().toString());
                 obj.execute(pobj);
            }

        });

       date_selctd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               date_selctd.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Toast.makeText(getActivity().getApplicationContext(),"FOCUS CHANGED (rio 1)",Toast.LENGTH_SHORT).show();
                Pair<String,String> pobj = new Pair<String, String>("DATE",date_selctd.getText().toString());
                ChkEvntNameDate obj = new ChkEvntNameDate();
                obj.execute(pobj);
            }
        });
       //To check whether there is any other Event or not in this Date
        date_selctd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                     //Toast.makeText(getActivity().getApplicationContext(),"Date Changed",Toast.LENGTH_LONG).show();
                ChkEvntNameDate obj = new ChkEvntNameDate();
                //obj.execute(<"NAME",parent.getItemAtPosition(position).toString()>);
                Pair<String,String> pobj = new Pair<String, String>("DATE",date_selctd.getText().toString());
                obj.execute(pobj);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().showDialog(DATE_PICKER_ID);

                startDialog();


            }
        });
         // for saving the data in the back end
         save_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

              // Toast.makeText(getActivity().getApplicationContext(),"Save Clicked",Toast.LENGTH_LONG).show();
                if((evnt_name.getSelectedItem().toString().startsWith("--Select Event--")))
                {
                    event_nm_et.setVisibility(View.VISIBLE);
                    event_nm_et.setError("Please select an Event");
                }
                 else if((evnt_name.getSelectedItem().toString().startsWith("Others")) && ( (event_nm_et.getText().toString()=="") || (event_nm_et.getText().toString().equals("")) ) )
                 {
                     event_nm_et.setError("Please select an Event");
                 }
                 else if((hh.getText().toString()=="") || (hh.getText().toString().equals("")))
                {
                    hh.setError("Please select hour");
                }
                else if((mm.getText().toString()=="") || (mm.getText().toString().equals("")))
                {
                    mm.setError("Please select Minute");
                }
                else if((venue.getText().toString()=="") || (venue.getText().toString().equals("")))
                {
                    venue.setError("Please select Venue");
                }
                else if((venu_add.getText().toString()=="") || (venu_add.getText().toString().equals("")))
                {
                    venu_add.setError("Please select Venue Address");
                }
                else if((trav_inf.getText().toString()=="") || (trav_inf.getText().toString().equals("")))
                {
                    trav_inf.setError("can not be null");
                }
                else if((descrptn.getText().toString()=="") || (descrptn.getText().toString().equals("")))
                {
                    trav_inf.setError("can not be null");
                }
                else if((accomo.getText().toString()=="") || (accomo.getText().toString().equals("")))
                {
                    accomo.setError("can not be null");
                }
                else if((drs_cd.getText().toString()=="") || (drs_cd.getText().toString().equals("")))
                {
                    drs_cd.setError("can not be null");
                }
                 else
                {
                    Toast.makeText(getActivity(),"ALLs Well",Toast.LENGTH_SHORT).show();
                    final String _accomo,_evnt_nm,_loc_addrd,_trav_info,_day,_descrptn,_dres_cd,_lat,_lng,_venu,_wed_cd;     //local variables
                    _accomo  = accomo.getText().toString();
                    if(((String)evnt_name.getSelectedItem()).startsWith("Others") )
                    {
                       _evnt_nm = event_nm_et.getText().toString();
                    }
                    else
                    {
                        _evnt_nm=((String)evnt_name.getSelectedItem());
                    }
                    shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
                    _venu = venue.getText().toString();
                    _loc_addrd = venu_add.getText().toString();
                    _trav_info = trav_inf.getText().toString();
                    _day = date_selctd.getText().toString(); //+" "+hh.getText().toString()+":"+mm.getText().toString()
                    _dres_cd =drs_cd.getText().toString();
                    _descrptn = descrptn.getText().toString();
                    _lat =lat.getText().toString();
                    _lng =lng.getText().toString();
                    _wed_cd = sp.GetFromPreference("WED_CD");

                    class SaveEventtoBackend extends AsyncTask<String,Void,String>
                    {

                        @Override
                        protected String doInBackground(String... params) {
                            String resp = "NO_VAL";

                            if(params[0].equalsIgnoreCase("SAVE"))
                            {

                                shared_pref nt = new shared_pref("NETWORK",getActivity().getApplicationContext());
                                String url = nt.GetFromPreference("B_URI")+"/Events/AddeventNew"; ///////////////
                                Log.d("FB", "IN SAVE PART");
                                HttpClient httpclient = new DefaultHttpClient();
                                HttpPost post = new HttpPost(url);
                                // EntityBuilder entity = EntityBuilder.create();
                                // MultipartEntityBuilder entity = MultipartEntityBuilder.create();
                                List<NameValuePair> namevaluepair = new ArrayList<NameValuePair>(11);

                                namevaluepair.add(new BasicNameValuePair("ACCOMO",_accomo));
                                namevaluepair.add(new BasicNameValuePair("EVNT_NM",_evnt_nm));
                                namevaluepair.add(new BasicNameValuePair("LOC_ADDRS",_loc_addrd));
                                namevaluepair.add(new BasicNameValuePair("TRAV_INFO",_trav_info));
                                namevaluepair.add(new BasicNameValuePair("DAY",_day));
                                namevaluepair.add(new BasicNameValuePair("DSCRPTN",_descrptn));
                                namevaluepair.add(new BasicNameValuePair("DRS_CD",_dres_cd));
                                namevaluepair.add(new BasicNameValuePair("LAT",_lat));
                                namevaluepair.add(new BasicNameValuePair("LNG",_lng));
                                namevaluepair.add(new BasicNameValuePair("VNU",_venu));
                                namevaluepair.add(new BasicNameValuePair("WED_CD",_wed_cd));

                                try {
                                    post.setEntity( new UrlEncodedFormEntity(namevaluepair));
                                    HttpResponse responce = httpclient.execute(post);
                                    HttpEntity respentity = responce.getEntity();

                                    if (responce.getStatusLine().getStatusCode() == 201) {
                                        resp = EntityUtils.toString(respentity);
                                       }
                                    }
                                catch(Exception e)
                                {
                                    resp = "ERROR "+e.getMessage();
                                    return resp;
                                }

                            }
                            else if(params[0].equalsIgnoreCase("Update"))
                            {
                                shared_pref nt = new shared_pref("NETWORK",getActivity().getApplicationContext());
                                String url = nt.GetFromPreference("B_URI")+"/Events/EventUpdateNew"; ///////////////
                                Log.d("FB", "IN UPDATE PART");
                                HttpClient httpclient = new DefaultHttpClient();
                                HttpPost post = new HttpPost(url);
                                // EntityBuilder entity = EntityBuilder.create();
                                // MultipartEntityBuilder entity = MultipartEntityBuilder.create();
                                List<NameValuePair> namevaluepair = new ArrayList<NameValuePair>(11);

                                namevaluepair.add(new BasicNameValuePair("ACCOMO",_accomo));
                                namevaluepair.add(new BasicNameValuePair("EVNT_NM",_evnt_nm));
                                namevaluepair.add(new BasicNameValuePair("LOC_ADDRS",_loc_addrd));
                                namevaluepair.add(new BasicNameValuePair("TRAV_INFO",_trav_info));
                                namevaluepair.add(new BasicNameValuePair("DAY",_day));
                                namevaluepair.add(new BasicNameValuePair("DSCRPTN",_descrptn));
                                namevaluepair.add(new BasicNameValuePair("DRS_CD",_dres_cd));
                                namevaluepair.add(new BasicNameValuePair("LAT",_lat));
                                namevaluepair.add(new BasicNameValuePair("LNG",_lng));
                                namevaluepair.add(new BasicNameValuePair("VNU",_venu));
                                namevaluepair.add(new BasicNameValuePair("EVNT_CD",evnt_cd));

                                try {
                                    post.setEntity( new UrlEncodedFormEntity(namevaluepair));
                                    HttpResponse responce = httpclient.execute(post);
                                    HttpEntity respentity = responce.getEntity();

                                    if (responce.getStatusLine().getStatusCode() == 200) {
                                        resp = EntityUtils.toString(respentity);
                                        Log.d("FB","RESPONCE-->"+resp);
                                    }
                                }
                                catch(Exception e)
                                {
                                    resp = "ERROR "+e.getMessage();
                                    Log.d("FB","In App"+resp);
                                    return resp;

                                }

                            }



                            return resp;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);

                           //Log.d("FB","RESPONCE_____>------>---->---->"+s);

                            if(s.startsWith("ERROR"))
                            {
                                Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                            }
                            else if(s.startsWith("NO_VAL"))
                            {
                                //Toast.makeText(getActivity().getApplicationContext(),"NO_VAL",Toast.LENGTH_SHORT).show();
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
                                t_manager_1.remove((add_event_frgmnt) getFragmentManager().findFragmentByTag("EVNT_VWER"));
                                //t_manager_1.add(R.id.rel_lay_show_grp_2, wed_mnu_frg, "WED_MENU");
                                t_manager_1.replace(R.id.containerview,edt_wed_frg , "EVNT_VWER_PRNT");
                                t_manager_1.commit();
                            }
                            else
                            {
                                Toast.makeText(getActivity().getApplicationContext(),"CREATED SUCCESSFULLY "+s,Toast.LENGTH_SHORT).show();
                                android.support.v4.app.FragmentManager f_manager;
                                f_manager = getFragmentManager();
                                //wed_menu_fragmnt wed_mnu_frg = new wed_menu_fragmnt();
                                event_viewer_frgmnt edt_wed_frg = new event_viewer_frgmnt();
                                FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                                //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                                //t_manager_1.addToBackStack("WED_MENU");
                                t_manager_1.addToBackStack("WED_MENU");
                                t_manager_1.remove((add_event_frgmnt) getFragmentManager().findFragmentByTag("EVNT_VWER"));
                                //t_manager_1.add(R.id.rel_lay_show_grp_2, wed_mnu_frg, "WED_MENU");
                                t_manager_1.replace(R.id.containerview,edt_wed_frg , "EVNT_VWER_PRNT");
                                t_manager_1.commit();

                            }
                        }
                    }
                    SaveEventtoBackend objj = new SaveEventtoBackend();
                    objj.execute(save_button.getText().toString());

                }



             }
         });


        hh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ((hh.getText().toString() == "") || (hh.getText().toString().equals(""))) {

                }
                else if(s.length()<=2)
                {
                    if (Integer.parseInt(hh.getText().toString()) > 24) {
                        hh.setError("Set Proper time");
                    }
                }
                else if(s.length()>2)
                {
                    hh.setError("Set Proper time");
                }
            }
        });
        mm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if((mm.getText().toString()=="")|| (mm.getText().toString().equals(""))) {

                }
                else if(s.length()<=2)
                {
                    if (Integer.parseInt(mm.getText().toString()) > 59) {
                        mm.setError("Set Proper time");
                    }
                }
                else if(s.length()>2)
                {
                    mm.setError("Set Proper time");
                }
            }
        });


    }
       public void startDialog()
       {
           DatePickerDialog datepicker = new DatePickerDialog(getContext(),R.style.AppTheme,datepickerListener,year_mn,month_mn,day_mn);
           datepicker.setCancelable(false);
           datepicker.setTitle("Select The Date");
           datepicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "DONE", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

                   Toast.makeText(getActivity(),"DONE CLICKED",Toast.LENGTH_SHORT).show();
                   dialog.cancel();
                   dtv.setText(getMonth(month_st) + "/" + day_st + "/" + year_st);
               //    Log.d("FB", "DATE--" + getMonth(month_st) + "/" + day_st+"/"+year_st);

               }
           });
           datepicker.show();
       }

    DatePickerDialog.OnDateSetListener datepickerListener = new DatePickerDialog.OnDateSetListener()
    {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            year_st=year;
            month_st =(monthOfYear+1);
            day_st = dayOfMonth;



            //  Log.d("FB", "SELECTED DATE-->" +month_mn + "-" + day_mn + "-" + year_mn); //
        }


    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String getMonth(int x)
    {
        String mon=null;
        if(x==1)
        {
          mon = "JAN";
        }
        if(x==2)
        {
            mon = "FEB";
        }
        if(x==3)
        {
            mon = "MAR";
        }
        if(x==4)
        {
            mon = "APR";
        }
        if(x==5)
        {
            mon = "MAY";
        }
        if(x==6)
        {
            mon = "JUN";
        }
        if(x==7)
        {
            mon = "JUL";
        }
        if(x==8)
        {
            mon = "AUG";
        }
        if(x==9)
        {
            mon = "SEP";
        }
        if(x==10)
        {
            mon = "OCT";
        }
        if(x==11)
        {
            mon = "NOV";
        }
        if(x==12)
        {
            mon = "DEC";
        }
        return mon;
    }

}



