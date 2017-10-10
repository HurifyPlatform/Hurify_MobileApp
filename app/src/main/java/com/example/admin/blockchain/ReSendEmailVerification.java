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
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class ReSendEmailVerification extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    EditText email;
    Button Send;
    TextView text,error;
    String emailcheck;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resend_email);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        email=(EditText) findViewById(R.id.etVerifyEmail);
        Send= (Button) findViewById(R.id.btnDeviceName);
        text= (TextView) findViewById(R.id.textView3);
        error= (TextView) findViewById(R.id.error);
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
        Send.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         Intent intent=new Intent(ReSendEmailVerification.this,MainActivity.class);
         startActivity(intent);
        finish();
        //  Toast.makeText(getApplicationContext(),"backKeyPressed",Toast.LENGTH_LONG).show();
    }



    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btnDeviceName)
        {

            emailcheck=email.getText().toString();
            if (emailcheck == null || emailcheck.isEmpty()) {
                text.setText(email.getHint() + " cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    email.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            }else
            {
                text.setText("");
            }

            if (!validateEmail(emailcheck)) {
                Toast.makeText(this, "invalid email address, please retry.", LENGTH_SHORT).show();
                text.setText("invalid email addresss");
                //emailAddress.setFocusable(true);
                return;
            }else
            {
                text.setText("");
            }

            VerifyEmail();

        }

    }


    private void VerifyEmail() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/resendmail", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success= jsonObject.getInt("success");
                    String s=jsonObject.getString("message");

                    if(success == 1)
                    {
                        Log.d("response", s);
                        Intent intent = new Intent(ReSendEmailVerification.this,MainActivity.class);
                        startActivity(intent);

                    }else {
                        error.setText(s);
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
                params.put("email", emailcheck);
                Log.d("json_uuidf", emailcheck);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private boolean validateEmail(String address) {
        return VALID_EMAIL_ADDRESS_REGEX.matcher(address).find();
    }
}
