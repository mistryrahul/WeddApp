package com.example.rahul.weddapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28-09-2015.
 */
public class fblogin extends Fragment {

    private TextView mTextDetails;
    private static CallbackManager callbackmanager;
    public  static AccessToken accesstoken;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private Profile pendingUpdateForUser;

    private Profile profile;

    public fblogin()
    {

    }

    private FacebookCallback<LoginResult> mcallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult)
        {
            final AccessToken accesstoken = loginResult.getAccessToken();

            /////////////////////
            GraphRequest request = GraphRequest.newMeRequest(
                    accesstoken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            // Application code
                            shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
                             sp.AddtoPreferance("ACCESSTOKEN",accesstoken.getToken());

                            sp.AddtoPreferance("BACK_REG","NO"); // back ground registration Checking...


                       //    Log.d("RESPONSE_R_R", "response" + object.toString());
                            Intent intent=new Intent(getActivity().getApplicationContext(), User_nav.class);
                            try {

                                if(object.has("picture"))
                                {
                                    //intent.putExtra("pic_url", object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    sp.AddtoPreferance("PIC_URL",object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                }
                                //else
                                {
                                    //intent.putExtra("pic_url","NO");
                                   // sp.AddtoPreferance("PIC_URL", "NO_VAL");

                                }
                                //new LoadImage().execute(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                //intent.putExtra("pic_url",object.getJSONObject("picture").getString("url"));
                                if(object.has("name"))
                                {
                                    //intent.putExtra("name", object.getString("name"));
                                    sp.AddtoPreferance("NAME", object.getString("name"));

                                }
                                //else
                                {
                                    //intent.putExtra("name", "NO");
                                    //sp.AddtoPreferance("NAME", "NO_VAL");
                                }
                                if(object.has("email"))
                                {
                                    //intent.putExtra("email", object.getString("email"));
                                    sp.AddtoPreferance("EMAIL", object.getString("email"));
                                }
                               // else
                                {
                                    //intent.putExtra("email","NO");
                                   // sp.AddtoPreferance("EMAIL", "NO_VAL");
                                }
                                if(object.has("gender"))
                                {
                                   // intent.putExtra("gender", object.getString("gender"));
                                    sp.AddtoPreferance("GENDER", object.getString("gender"));

                                }
                                else
                                {
                                    //intent.putExtra("gender","NO");
                                    //sp.AddtoPreferance("GENDER", "NO_VAL");
                                    Log.d("FB","FRIEND LIST-->"+object.getString("me/friends"));
                                }

                                startActivity(intent);

                                //finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday,picture.width(150)");
            request.setParameters(parameters);
            request.executeAsync();





            ///////////////////////
            //if (profile != null)
            // {
            //mTextDetails.setText("hi" + profile.getName());
            //profile.getProfilePictureUri(640,480);

            //ArrayList<String> grnt_prmsn_lst = (ArrayList<String>) loginResult.getRecentlyGrantedPermissions();
            //ArrayList<String> grnt_prmsn_lst_1 = new ArrayList<>(loginResult.getRecentlyGrantedPermissions());


            // Intent myintent = new Intent(getActivity().getApplicationContext(),registration.class);
            // myintent.putExtra("Profile_data",profile);
            // startActivity(myintent);
            // }

        }

        @Override
        public void onCancel() {
            System.out.println("cancelled....");
        }



        @Override
        public void onError(FacebookException e) {

            System.out.println("Error....");
            e.printStackTrace();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fblogin_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackmanager = CallbackManager.Factory.create();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LoginButton loginbutton =(LoginButton) view.findViewById(R.id.login_button);
        loginbutton.setVisibility(View.VISIBLE);
       // mTextDetails = (TextView) view.findViewById(R.id.Tvmsg);

        List<String> prmssn = new ArrayList<String>();
        prmssn.add("public_profile");
        prmssn.add("email");
       // prmssn.add("user_friends");
        loginbutton.setReadPermissions(prmssn);
        loginbutton.setFragment(this);


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginButton lb = (LoginButton) v.findViewById(R.id.login_button);
                lb.setVisibility(View.GONE);
                String txt = lb.getText().toString();
                Log.e("FB", txt);

                Toast.makeText(getActivity().getApplicationContext(), "FB" + txt, Toast.LENGTH_SHORT);

                if (txt.startsWith("out",4)) {
                    Toast.makeText(getActivity().getApplicationContext(), "LOG OUT", Toast.LENGTH_SHORT);
                    shared_pref sp = new shared_pref("LOGIN",getActivity().getApplicationContext());
                    sp.RemoveFieldfromPref("ACCESSTOKEN");
                    sp.RemoveFieldfromPref("PIC_URL");
                    sp.RemoveFieldfromPref("EMAIL");
                    sp.RemoveFieldfromPref("NAME");
                    sp.RemoveFieldfromPref("BACK_REG");




                }

            }
        });


        loginbutton.registerCallback(callbackmanager, mcallback);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackmanager.onActivityResult(requestCode, resultCode, data);



    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
