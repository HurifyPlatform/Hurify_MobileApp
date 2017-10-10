package com.example.admin.blockchain;

import android.content.Intent;
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


public class ActivityProjectFullDesc extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNavHeaderBg;
    private TextView txtName,txtDays,txtTechnology,txtPrice,txtCategories,txtEstimateTime,txtDescription,txtExprience;
    private EditText Name,Language,JobTitle,HourRate,Skills,OverView;
    private Toolbar mtoolbar;
    private Button Apply;
    String email,name,category,data,technology,price,exprience,estTime,description,days;
    String TAG="tag";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_full_desc);
        init();
        profileShowHire();
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
        getSupportActionBar().setTitle("Project Details");
        // Navigation view header
        imgNavHeaderBg = (ImageView) findViewById(R.id.imageView);



       //

       // Log.d("email",email);


        Apply.setOnClickListener(this);

        Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
        profileShowHire();
    }

    private void init() {
        email = SharedPrefManager.getInstance(this).getUserEmail();
        data = getIntent().getExtras().getString("id");
        Log.d("id",data);
        txtName = (TextView) findViewById(R.id.Name);
        txtDays = (TextView) findViewById(R.id.days);
        txtTechnology = (TextView) findViewById(R.id.Technology);
        txtPrice = (TextView) findViewById(R.id.Price);
        txtCategories = (TextView) findViewById(R.id.Categories);
        txtEstimateTime = (TextView) findViewById(R.id.EstTime);
        txtDescription = (TextView) findViewById(R.id.Description);
        txtExprience = (TextView) findViewById(R.id.Experience);
        Apply=  (Button) findViewById(R.id.Apply);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.Apply) {
            Log.d("tag","clicked");
            Intent intent = new Intent(ActivityProjectFullDesc.this,ActivityBidValue.class);
            intent.putExtra("id",data);
            startActivity(intent);

          //  profileHire();
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }


    private void profileShowHire() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/project/getprojectdetails", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                   // Log.d("response", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        name=jsonObject1.getString("project_name");
                        technology=jsonObject1.getString("technology");
                        category=jsonObject1.getString("category");
                        price=jsonObject1.getString("price");
                        exprience=jsonObject1.getString("experience_level");
                        estTime=jsonObject1.getString("estimated_time");
                        description=jsonObject1.getString("project_description");
                        days=jsonObject1.getString("days");

                        txtName.setText(name);
                        txtTechnology.setText(technology);
                     //   Categories.setText(category);
                        txtCategories.setText(category);
                        txtPrice.setText(price);
                        txtExprience.setText(exprience);
                        txtEstimateTime.setText(estTime);
                        txtDescription.setText(description);
                        txtDays.setText(days);
                      //  item=category;
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
                params.put("id", data);
                //Log.d("json_uuidf", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



}

