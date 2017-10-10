package com.example.admin.blockchain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ActivityProfileWorkShow extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNavHeaderBg;
    private TextView txtName,txtCountry,txtLanguage,txtOverView;
    private Toolbar mtoolbar;
    private Button ProfileEdit;
    String country1,category1,email,name,language,overview;
    String TAG="tag";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_show_work);
        init();
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("Work Profile Show");
        // Navigation view header
        imgNavHeaderBg = (ImageView) findViewById(R.id.imageView);



        //email = SharedPrefManager.getInstance(this).getUserEmail();
        email="lakshmi.spandana@mobodexter.com";
       // Log.d("email",email);


        ProfileEdit.setOnClickListener(this);
        profileWorkShow();
    }

    private void init() {

        txtName = (TextView) findViewById(R.id.Name);
        txtLanguage = (TextView) findViewById(R.id.Language);
        txtCountry = (TextView) findViewById(R.id.Country);
        txtOverView = (TextView) findViewById(R.id.Overview);
        ProfileEdit=  (Button) findViewById(R.id.ProfileEdit);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ProfileEdit) {

            Toast.makeText(getApplicationContext(), "Go to next Activity", Toast.LENGTH_LONG).show();
                // profileHire();
            }else {
                Toast.makeText(getApplicationContext(), "Not Go to next Activity", Toast.LENGTH_LONG).show();
            }

    }



    private void profileWorkShow() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/getprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        name=jsonObject1.getString("name");
                        overview=jsonObject1.getString("description");
                        language=jsonObject1.getString("languages_known");
                        country1=jsonObject1.getString("country");
                        txtName.setText(name);
                        txtCountry.setText(country1);
                        txtOverView.setText(overview);
                        txtLanguage.setText(language);
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_LONG).show();
                    }




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
                Log.d("json_uuidf", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


        //progressdialog.setMessage("User Registering.....");
        //progressdialog.show();
    }



}

