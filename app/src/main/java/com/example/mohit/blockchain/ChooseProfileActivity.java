package com.example.mohit.blockchain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifDrawable;

import static android.widget.Toast.LENGTH_SHORT;

public class ChooseProfileActivity extends AppCompatActivity implements View.OnClickListener
       {

        Button Hire,Work;
private Toolbar mToolbar;
           String usertype,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        Hire= (Button) findViewById(R.id.button_hire);
        Work= (Button) findViewById(R.id.button_work);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
         @Override
      public void onClick(View v) {
        onBackPressed();
        }
        });
        getSupportActionBar().setTitle("Resend Email");
        email = SharedPrefManager.getInstance(this).getUserEmail();
        Hire.setOnClickListener(this);
        Work.setOnClickListener(this);

    }


           @Override
           public void onClick(View v) {

               if(v.getId()==R.id.button_hire)
               {
                   usertype="Service Seeker";
                   Toast.makeText(getApplicationContext(),"some",Toast.LENGTH_LONG).show();
                   UserType();
                   Intent intent=new Intent(ChooseProfileActivity.this,ActivityProfileWork.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
                   finish();
                   return;
               }

               if(v.getId()==R.id.button_work)
               {
                  usertype="Service Provider";
                   Toast.makeText(getApplicationContext(),"some",Toast.LENGTH_LONG).show();
                   UserType();
                   Intent intent=new Intent(ChooseProfileActivity.this,ActivityProfileHire.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
                   finish();

               }

           }


           private void UserType() {

               StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/profiletype", new Response.Listener<String>() {


                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject jsonObject = new JSONObject(response);
                           int success= jsonObject.getInt("success");
                           // success=1;
                           String s=jsonObject.getString("message");
                           // s="some";
                           if(success == 1)
                           {

                               Log.d("response", s);
                           }else {

                               Log.d("response", s);
                           }

                           Log.d("response", jsonObject+"");


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                       if(error.getMessage()==null) {
                           Toast.makeText(getApplicationContext(), "Server down try after some time", Toast.LENGTH_LONG).show();
                       }else {
                           Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                       }
                   }
               }) {

                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {


                       //String uname=sessionManager.getCurrentUsername();

                       Map<String, String> params = new HashMap<>();
                       params.put("email", email);
                       params.put("account_type", usertype);
                       Log.d("json_uuidf", usertype);

                       return params;
                   }
               };
               RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
               stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                       DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
               requestQueue.add(stringRequest);

           }
       }
