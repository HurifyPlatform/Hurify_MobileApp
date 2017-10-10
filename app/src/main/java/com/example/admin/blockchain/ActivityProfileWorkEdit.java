package com.example.admin.blockchain;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class ActivityProfileWorkEdit extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNavHeaderBg;
    private TextView txtValidName,txtValidLanguage,txtValidOverView;
    private EditText Name,Language,OverView;
    private Toolbar mtoolbar;
    private Button ProfileSubmit;
    String country1,category1,email,name,language,overview;
    String TAG="tag";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_work);
        init();
        profileShowWork();
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
        getSupportActionBar().setTitle("Work Profile Edit");
        // Navigation view header
        imgNavHeaderBg = (ImageView) findViewById(R.id.imageView);
       //

       // Log.d("email",email);


        ProfileSubmit.setOnClickListener(this);
    }

    private void init() {
        email = SharedPrefManager.getInstance(this).getUserEmail();
        txtValidName = (TextView) findViewById(R.id.validName);
        txtValidLanguage = (TextView) findViewById(R.id.validLanguage);
        txtValidOverView = (TextView) findViewById(R.id.validOverView);
        Name = (EditText) findViewById(R.id.Name);
        Language = (EditText) findViewById(R.id.Language);
        OverView = (EditText) findViewById(R.id.Overview);
        ProfileSubmit=  (Button) findViewById(R.id.ProfileEditWork);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ProfileEditWork) {
           // Log.d(TAG,"cliclked");
            name = Name.getText().toString().trim();
           // Log.d(TAG,name);
            if (name == null || name.isEmpty()) {
                txtValidName.setText("Name cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidName.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidName.setText("");
            }
            language = Language.getText().toString().trim();
            if (language == null || language.isEmpty()) {
                txtValidLanguage.setText("language cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidLanguage.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidLanguage.setText("");
            }
            overview= OverView.getText().toString().trim();
            if (overview == null || overview.isEmpty()) {
                txtValidOverView.setText("Hourly rate cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidOverView.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidOverView.setText("");
            }
            profileWork();
        }
    }


    private void profileShowWork() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/getprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Log.d("response", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        name=jsonObject1.getString("name");
                        overview=jsonObject1.getString("description");
                        language=jsonObject1.getString("languages_known");
                        country1=jsonObject1.getString("country");

                        Name.setText(name);
                        OverView.setText(overview);
                        Language.setText(language);

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
    }


    private void profileWork() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/editprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response1", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        String message=jsonObject.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        Intent savings=new Intent(ActivityProfileWorkEdit.this,NavDrawerActivityWork.class);
                        startActivity(savings);
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
                params.put("name", name);
                params.put("description", overview);
                params.put("languages_known", language);
                Log.d("json_uuidf", email);
                Log.d(TAG, name);
                Log.d(TAG, language);
                Log.d(TAG, overview);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);




        //  Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_LONG).show();

        //progressdialog.setMessage("User Registering.....");
        //progressdialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
         Intent intent=new Intent(this,NavDrawerActivityWork.class);
         intent.putExtra("TAG","home");
          startActivity(intent);


       // this.overridePendingTransition(swap_in_bottom,swap_out_bottom);
        finish();
        //  Toast.makeText(getApplicationContext(),"backKeyPressed",Toast.LENGTH_LONG).show();
    }





}

