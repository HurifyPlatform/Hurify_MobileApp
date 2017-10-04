package com.example.mohit.blockchain;

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

public class ForgotPass extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mtoolbar;
    EditText VerificationCode,Password;
    Button Reset;
    TextView txtEmailStatus,verifyCodeMessage,passwordMessage;
    String emailcheck,code,password;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
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
        getSupportActionBar().setTitle("Reset Password");
        VerificationCode=(EditText)findViewById(R.id.editTextForgotPasswordCode);
        Password=(EditText)findViewById(R.id.editTextForgotPasswordPass);
        Reset=(Button)findViewById(R.id.button2);
        verifyCodeMessage=(TextView)findViewById(R.id.textViewForgotPasswordCodeMessage);
        passwordMessage=(TextView)findViewById(R.id.textViewForgotPasswordUserIdMessage);
        txtEmailStatus=(TextView)findViewById(R.id.textViewForgotPasswordMessage);
        emailcheck = getIntent().getStringExtra("Email");
        SentEmail();
        Reset.setOnClickListener(this);

    }

    private void SentEmail() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/forgot", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    //response="{\"success\":\"1\", \"message\":\"succeful Login\"}";
                    JSONObject jsonObject = new JSONObject(response);
                    int success= jsonObject.getInt("success");
                    // success=1;
                    String s=jsonObject.getString("message");
                    // s="some";
                    if(success == 1)
                    {
                        txtEmailStatus.setText("Set your new password with the verification code that was sent to you"+emailcheck);
                        Log.d("response", s);

                    }else {
                        //waitDialog.hide();
                        txtEmailStatus.setText(s);
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
               // waitDialog.hide();
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


    private void ResetPassword() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/reset", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    //response="{\"success\":\"1\", \"message\":\"succeful Login\"}";
                    JSONObject jsonObject = new JSONObject(response);
                    int success= jsonObject.getInt("success");
                    // success=1;
                    String s=jsonObject.getString("message");
                    // s="some";
                    if(success == 1)
                    {
                        //txtEmailStatus.setText(s);
                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgotPass.this, LoginActivity.class);
                        startActivity(intent);
                        Log.d("response", s);

                    }else {
                        //waitDialog.hide();
                        txtEmailStatus.setText(s);
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
                // waitDialog.hide();
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
                params.put("token", code);
                params.put("password", password);
                Log.d("json_uuidf", code);
                Log.d("json_uuidf", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.button2)
        {
            password = Password.getText().toString();
            if (password == null || password.isEmpty()) {
                passwordMessage.setText("Password Cannot be Empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    passwordMessage.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                passwordMessage.setText("");
            }

            code = VerificationCode.getText().toString();
            if (code == null || code.isEmpty()) {
                verifyCodeMessage.setText("Verification Code Cannot be Empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    verifyCodeMessage.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                verifyCodeMessage.setText("");
            }
            ResetPassword();
        }

    }
}
