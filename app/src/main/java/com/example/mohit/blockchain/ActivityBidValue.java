package com.example.mohit.blockchain;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.mohit.blockchain.fragment.MoviesFragment;
import com.example.mohit.blockchain.fragment.PostedProjectsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;


public class ActivityBidValue extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtBidValue,txterror,txtExpError;
    private EditText BidValue;
    private Toolbar mtoolbar;
    private Button BidSubmit;
    private Spinner ExpLevel;
    String exp;
    StringBuilder sb = new StringBuilder(14);
    String bidvalue,data,email;
    String TAG="tag";
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String item = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_value);
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
        getSupportActionBar().setTitle("Bid Value");


        ExpLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              //  exp =  parent.getItemAtPosition(position)+"";
                //Log.v("TAG", exp);

                exp= (String) parent.getItemAtPosition(position);
                Log.d("some",exp);
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("TAG", "item not selected");

            }
        });
        // Navigation view header



        BidSubmit.setOnClickListener(this);
    }

    private void init() {

        txtBidValue=(TextView) findViewById(R.id.biderror);
        txtExpError=(TextView) findViewById(R.id.txtexp_error);
        txterror=(TextView) findViewById(R.id.script);
        BidValue = (EditText) findViewById(R.id.bid_value);
        BidSubmit=  (Button) findViewById(R.id.bid_value_button);
        ExpLevel = (Spinner) findViewById(R.id.exp_level);
        data = getIntent().getExtras().getString("id");
        Log.d("id bid",data);
        email = SharedPrefManager.getInstance(this).getUserEmail();


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bid_value_button) {
           // Log.d(TAG,"cliclked");
            bidvalue = BidValue.getText().toString().trim();
           // Log.d(TAG,name);
            if (bidvalue == null || bidvalue.isEmpty()) {
                txtBidValue.setText("Bid Value cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtBidValue.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtBidValue.setText("");
            }


            if(exp.equals("Choose Exprience Level"))
            {
                Toast.makeText(this, "experience level not selected!!!.", LENGTH_SHORT).show();
                txtExpError.setText("experience level not selected");
                return;
            }else {
                txtExpError.setText("");
            }

            profileHire();
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }



    private void profileHire() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/project/apply", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response1", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    String message=jsonObject.getString("message");
                    if(success == 1)
                    {

                        Log.d("profile status",message);
                        Intent intent = new Intent(ActivityBidValue.this,NavDrawerActivity.class);
                        intent.putExtra("TAG","movies");
                        startActivity(intent);

                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(getApplicationContext(), ActivityProfileHireShow.class));
                    }else {

                        txterror.setText(message);
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
                params.put("appliers_email", email);
                params.put("project_id", data);
                params.put("bid_value",bidvalue);
                params.put("experience_level",exp);
                Log.d("appliers_email", email);
                Log.d("project_id", data);
                Log.d("bid_value",bidvalue);
                Log.d("experience_level",exp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        //progressdialog.setMessage("User Registering.....");
        //progressdialog.show();
    }



}

