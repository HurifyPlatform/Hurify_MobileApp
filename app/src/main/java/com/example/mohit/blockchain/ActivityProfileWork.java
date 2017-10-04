package com.example.mohit.blockchain;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;


public class ActivityProfileWork extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtValidName,txtValidCountry,txtValidLanguage,txtValidOverView;
    private EditText Name,Language,OverView;
    private Toolbar mtoolbar;
    private Button ProfileSubmit;
    private Spinner Country;
    String country1,category1,email,name,language,overview;
    String TAG="tag";
    String Account_Type;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_work);
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
        getSupportActionBar().setTitle("Work Profile");
        // Navigation view header
        imgNavHeaderBg = (ImageView) findViewById(R.id.imageView);
        Country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 country1 = (String) parent.getItemAtPosition(position);
                Log.v("TAG", country1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            Log.v("TAG", "item not selected");

            }
        });


        email = SharedPrefManager.getInstance(this).getUserEmail();
       // Log.d("email",email);


        ProfileSubmit.setOnClickListener(this);
    }

    private void init() {

        txtValidName = (TextView) findViewById(R.id.validName);
        txtValidLanguage = (TextView) findViewById(R.id.validLanguage);
        txtValidCountry = (TextView) findViewById(R.id.validCountry);
        txtValidOverView = (TextView) findViewById(R.id.validOverView);
        Name = (EditText) findViewById(R.id.Name);
        Language = (EditText) findViewById(R.id.Language);
        OverView = (EditText) findViewById(R.id.Overview);
        Country = (Spinner) findViewById(R.id.Country);
        ProfileSubmit=  (Button) findViewById(R.id.ProfileSubmit);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ProfileSubmit) {
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

            if(country1.equals("Choose Country"))
            {
                Toast.makeText(this, "country or state not selected !!!.", LENGTH_SHORT).show();
                txtValidCountry.setText("country not selected ");
                return;
            }else {
                txtValidCountry.setText("");
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



    private void profileWork() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/addprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response1", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        String message=jsonObject.getString("message");
                        CheckAccountType();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(getApplicationContext(), ActivityProfileHireShow.class));
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
                params.put("country", country1);
                Log.d("json_uuidf", email);
                Log.d(TAG, name);
                Log.d(TAG, language);
                Log.d(TAG, overview);
                Log.d(TAG, country1);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        //progressdialog.setMessage("User Registering.....");
        //progressdialog.show();
    }

    private void CheckAccountType()
    {
        Log.d(TAG,"idiot");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/checkacc", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    // success=1;

                    // s="some";
                    if(success == 1)
                    {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        Account_Type =jsonObject1.getString("account_type");
                        //waitDialog.dismiss();
                        switch(Account_Type)
                        {
                            case "Service Provider":
                                Intent intent1=new Intent(ActivityProfileWork.this,NavDrawerActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                                finish();

                                break;
                            case "Service Seeker":
                                Intent intent2=new Intent(ActivityProfileWork.this,NavDrawerActivityWork.class);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);
                                finish();

                                break;
                            default:System.out.println("Not in 10, 20 or 30");
                                // etc...
                        }
                    }else {
                        //waitDialog.dismiss();
                        Log.d("response", jsonObject+"");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //waitDialog.hide();
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
                //String email1 = SharedPrefManager.getInstance(LoginActivity.this).getUserEmail();
                Log.d("json_uuidf", email);
                // Log.d("mohit", email1);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }



}

