package com.example.rahul.weddapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Admin on 26-11-2015.
 */
public class event_viewer_frgmnt extends Fragment implements communicator_2 {

    RecyclerView eventrecycler;
    Button Addevnt;
    EventsAdapter adpter;
    JSONArray main_json_array;
    static FragmentManager f_manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.event_viewer_frgmnt, container, false);
        eventrecycler = (RecyclerView) parent.findViewById(R.id.recevntvwer);
        Addevnt = (Button) parent.findViewById(R.id.evntcrt);
        f_manager = getFragmentManager();

        class getEventList extends AsyncTask<String, Void, String> {
            ProgressDialog dialog = null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(getContext());
                //dialog = ProgressDialog.show(User_nav.this,"Please wait..","Please Wait..",true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                //WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
                //wmlp.gravity = Gravity.TOP | Gravity.LEFT;
               // wmlp.x = 100;
               // wmlp.y = 100;

                dialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Log.d("FB","IN DO IT BACK GROUND");

                String resp_str = "NO_VAL";

                shared_pref nt = new shared_pref("NETWORK", getActivity().getApplicationContext());
                HttpClient httpclient = new DefaultHttpClient();
                String uri = nt.GetFromPreference("B_URI") + "/Events/Eventall/" + params[0];
                Log.d("FB","RUNNING URL__>"+uri);

                //String uri_alive = nt.GetFromPreference("B_URI")+"/Bridegrooms/Subscription/"+s;
                //Log.d("HASH_KEY_GEN",URLEncoder.encode(uri));
                //contxt = params[0];
                String json_string = null;
                HttpGet httpget = new HttpGet(uri);
                //HttpPost httpPost = new HttpPost(uri);
                try {
                    HttpResponse responce = httpclient.execute(httpget);
                    //  Log.d("FB","IN RESPONCE...");
                    //Log.d("FB","RESPONCE-->"+responce.toString());

                    if (responce.getStatusLine().getStatusCode() == 200) {


                        String resp = EntityUtils.toString(responce.getEntity());
                        main_json_array = new JSONArray(resp);
                        //main_json_array
                        resp_str = "YES";
                    }
                } catch (Exception e) {
                    resp_str = "ERROR " + e.getMessage();
                   // Log.d("FB","ERROR----->"+e.getMessage());
                    return resp_str;

                }


                return resp_str;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("FB", "ON POST EXECUTE");

                dialog.dismiss();

                 if(s.startsWith("ERROR "))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Error happened....",Toast.LENGTH_SHORT).show();
                }
                else if(s.startsWith("NO_VAL"))
                {
                    Toast.makeText(getActivity().getApplicationContext(),"No value fetched....",Toast.LENGTH_SHORT).show();
                }
                else if(s.startsWith("YES"))
                {
                   // Toast.makeText(getActivity().getApplicationContext(),"Fetched successfully....",Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(getActivity().getApplicationContext(),main_json_array.toString(),Toast.LENGTH_SHORT).show();

                    adpter = new EventsAdapter(getActivity().getApplicationContext(),main_json_array);
                    eventrecycler.setAdapter(adpter);
                    eventrecycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

                }



            }
        }

        shared_pref sp = new shared_pref("LOGIN",getActivity());
        getEventList obj = new getEventList();
        obj.execute(sp.GetFromPreference("WED_CD"));

        Log.d("FB", "EXECUTED THAT");



        return parent;
    }


    @Override
    public void onResume() {
        super.onResume();
        Addevnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_manager = getFragmentManager();
                add_event_frgmnt edt_wed_frg = new add_event_frgmnt();
                FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                t_manager_1.addToBackStack("WED_MENU");
                t_manager_1.replace(R.id.containerview, edt_wed_frg,"EVNT_VWER");
                // t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
                // t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");

                // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                //t_manager_1.addToBackStack(null);
                //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                t_manager_1.commit();
            }
        });

    }

    @Override
    public void Responce(int pos, String data) {

        Log.d("FB","IN RESPONCE");
        if(pos==1)
        {
            Log.d("FB","IN RESPONCE/pos");

            if((data!=null)) {
                Log.d("FB", "IN RESPONCE/POS/DATA");
                Bundle args = new Bundle();
                args.putString("json_obj",data);

                Log.d("FB","JSON STRING-->"+data);


                Log.d("FB", "f_manager" + ((f_manager) == null));
                //f_manager = getFragmentManager();
                //event_viewer_frgmnt edt_wed_frg = new event_viewer_frgmnt();
                event_contnt_show_frgmnt cntnt_shw_frgmnt = new event_contnt_show_frgmnt();
                cntnt_shw_frgmnt.setArguments(args);
                FragmentTransaction t_manager_1 = f_manager.beginTransaction();
                //t_manager_1.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                t_manager_1.addToBackStack("EVNT_VWER_PRNT");
                t_manager_1.replace(R.id.containerview, cntnt_shw_frgmnt,"SHW_EVNT");
                // t_manager_1.remove((wed_menu_fragmnt) getSupportFragmentManager().findFragmentByTag("WED_MENU"));
                // t_manager_1.add(R.id.Linlay2, edt_wed_frg, "EVNT_VWER");

                // t_manager.add(R.id.rel_lay_show_grp_2,fb,"FB");
                //t_manager_1.addToBackStack(null);
                //t_manager.detach((opening) getSupportFragmentManager().findFragmentByTag("FIRST"));
                t_manager_1.commit();
            }
        }
    }
}
