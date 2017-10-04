package com.example.mohit.blockchain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohit.blockchain.helper.ProjectAppliedInfoAdapter;
import com.example.mohit.blockchain.modal.ProjectAppliedInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityApplicantsProfile extends AppCompatActivity implements View.OnClickListener {

    private TextView txtName,txtCountry,txtLanguage,txtJobTitle,txtHourRate,txtCategory,txtSkills,txtOverView;
    private Button ProfileReject,ProfileApply;
    private Toolbar mtoolbar;
    private ImageView imgNavHeaderBg;
    String email,name,country,language,jobtitle,hourrate,category,skills,overview,exp,data;
    String TAG="tag";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_profile);
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
        getSupportActionBar().setTitle("Applicant Profile");
        // Navigation view header
        imgNavHeaderBg = (ImageView) findViewById(R.id.imageView);

        //email = SharedPrefManager.getInstance(this).getUserEmail();
        //email="ashish@mobodexter.com";
        // Log.d("email",email);


        ProfileReject.setOnClickListener(this);
        ProfileApply.setOnClickListener(this);
        profileShowHire();
    }

    private void init() {
        data = getIntent().getExtras().getString("id");
        txtName = (TextView) findViewById(R.id.Name);
        txtLanguage = (TextView) findViewById(R.id.Language);
        txtCountry = (TextView) findViewById(R.id.Country);
        txtJobTitle = (TextView) findViewById(R.id.JobTitle);
        txtHourRate = (TextView) findViewById(R.id.HourRate);
        txtCategory = (TextView) findViewById(R.id.Categories);
        txtSkills = (TextView) findViewById(R.id.Skills);
        txtOverView = (TextView) findViewById(R.id.Overview);

        ProfileReject=  (Button) findViewById(R.id.Cancle);
        ProfileApply=  (Button) findViewById(R.id.Submit);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.Cancle) {
            Toast.makeText(getApplicationContext(), "Project Not Approved", Toast.LENGTH_LONG).show();
            // profileHire();
            return;
        }
        if (v.getId() == R.id.Submit) {
            Toast.makeText(getApplicationContext(), "Project Approved", Toast.LENGTH_LONG).show();
            // profileHire();
            return;
        }
    }

    private void profileShowHire() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/getfreelancerprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("res",response+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        name=jsonObject1.getString("name");
                        hourrate=jsonObject1.getString("salary_per_hour");
                        overview=jsonObject1.getString("description");
                        category=jsonObject1.getString("categories");
                        language=jsonObject1.getString("languages_known");
                        jobtitle=jsonObject1.getString("job_title");
                        skills=jsonObject1.getString("skills");
                        country=jsonObject1.getString("country");



                        txtName.setText(name);
                        txtHourRate.setText(hourrate);
                        txtCategory.setText(category);
                        txtCountry.setText(country);
                        txtOverView.setText(overview);
                        txtLanguage.setText(language);
                        txtSkills.setText(skills);
                        txtJobTitle.setText(jobtitle);

                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_LONG).show();
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
                params.put("email", data);
                Log.d("id", data);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }






}

