package com.example.mohit.blockchain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohit.blockchain.helper.JSONParser;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.format.Formatter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * Created by admin on 13/02/17.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener
{
    final String SiteKey = "6LfCuC4UAAAAAIY9lUBBDSDkFe6GQb5Hey9NdfwV";
    final String SecretKey  = "6LfCuC4UAAAAALAon5KII8bOlCRlQu6aTKM05ZyA";
    private GoogleApiClient mGoogleApiClient;
    private TextView textViewRegister;
    public TextView forgotpass1;
    TextView viewemail,viewpass;
    private EditText email,pass;
    private JSONParser jsonParser;
    private Button signIn;
    String emailcheck,passwordcheck;
    ImageView recapchta;
    private ProgressDialog progressDialog;
    String ipAddress;
    String TAG="tag";
    String Token,Account_Type,email1;
    String flag="No";
    int flag1=0;
    private ProgressDialog waitDialog;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Log.d("some","this clicked");
            //
            //onPause();
            CheckAccountProfileUpdate();
            //CheckAccountType();
           // finish();
            //return;
            // startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        init();
        waitDialog = new ProgressDialog(this);
        ipAddress= getLocalIpAddress();
        Log.d(TAG,ipAddress);
        recapchta.setOnClickListener(RqsOnClickListener1);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(LoginActivity.this)
                .addOnConnectionFailedListener(LoginActivity.this)
                .build();

        mGoogleApiClient.connect();


        signIn.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        forgotpass1.setOnClickListener(this);


    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        // Log.i(TAG, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }


    View.OnClickListener RqsOnClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //tvResult.setText("");

            SafetyNet.SafetyNetApi.verifyWithRecaptcha(mGoogleApiClient, SiteKey)
                    .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                        @Override
                        public void onResult(SafetyNetApi.RecaptchaTokenResult result) {
                            Status status = result.getStatus();

                            if ((status != null) && status.isSuccess()) {

                                // tvResult.setText("isSuccess()\n");
                                // Indicates communication with reCAPTCHA service was
                                // successful. Use result.getTokenResult() to get the
                                // user response token if the user has completed
                                // the CAPTCHA.

                                if (!result.getTokenResult().isEmpty()) {

                                    Token=result.getTokenResult();
                                    Log.d("token",Token);
                                    RegisterBackground();

                                    // tvResult.append("!result.getTokenResult().isEmpty()");
                                    // Log.d(TAG,result+"");
                                    // User response token must be validated using the
                                    // reCAPTCHA site verify API.
                                }else{
                                    //tvResult.append("result.getTokenResult().isEmpty()");
                                    Log.e("MY_APP_TAG", "result.getTokenResult().isEmpty()");

                                }
                            } else {

                                Log.e("MY_APP_TAG", "Error occurred " +
                                        "when communicating with the reCAPTCHA service.");



                                // Use status.getStatusCode() to determine the exact
                                // error code. Use this code in conjunction with the
                                // information in the "Handling communication errors"
                                // section of this document to take appropriate action
                                // in your app.
                            }
                        }
                    });

        }
    };

    private void RegisterBackground() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.google.com/recaptcha/api/siteverify", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success= jsonObject.getString("success");
                    if(success.equals("true"))
                    {
                        recapchta.setImageResource(R.mipmap.recaptcha2);
                        flag="Yes";
                        viewpass.setText("");
                    }else {
                        recapchta.setImageResource(R.mipmap.recaptcha1);
                        flag="No";
                    }

                    Log.d("response1", jsonObject+"");


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
                params.put("secret", SecretKey);
                params.put("response", Token);
                params.put("remoteip", ipAddress);
                Log.d("json_uuidf", Token);
                Log.d("json_uuidf", ipAddress);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "onConnected()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,
                "onConnectionSuspended: " + i,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,
                "onConnectionFailed():\n" + connectionResult.getErrorMessage(),
                Toast.LENGTH_LONG).show();
    }

    private void init()
    {
        textViewRegister=(TextView)findViewById(R.id.txtSignup);
        forgotpass1=(TextView)findViewById(R.id.txtForgotPass);
        viewemail=(TextView)findViewById(R.id.textViewUserIdMessage);
        viewpass=(TextView)findViewById(R.id.textViewUserPasswordMessage);
        signIn=(Button) findViewById(R.id.btn_login);
        email=(EditText)findViewById(R.id.et_username);
        pass=(EditText)findViewById(R.id.et_password);
        recapchta=(ImageView)findViewById(R.id.recapchta);

    }


    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.txtForgotPass)
        {   emailcheck = email.getText().toString();
            if (emailcheck == null || emailcheck.isEmpty()) {
                viewemail.setText(email.getHint() + " cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    email.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                viewemail.setText("");
            }

            if (!validateEmail(emailcheck)) {
                Toast.makeText(this, "invalid email address, please retry.", LENGTH_SHORT).show();
                viewemail.setText("invalid email addresss");
                //emailAddress.setFocusable(true);
                return;
            } else {
                viewemail.setText("");
            }
            Intent intent = new Intent(LoginActivity.this, ForgotPass.class);
            intent.putExtra("Email", emailcheck);
            startActivity(intent);
            return;
        }
        if(v.getId()==R.id.txtSignup) {
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        if(v.getId()==R.id.btn_login) {

            emailcheck = email.getText().toString();
            if (emailcheck == null || emailcheck.isEmpty()) {
                viewemail.setText(email.getHint() + " cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    email.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                viewemail.setText("");
            }

            if (!validateEmail(emailcheck)) {
                Toast.makeText(this, "invalid email address, please retry.", LENGTH_SHORT).show();
                viewemail.setText("invalid email addresss");
                //emailAddress.setFocusable(true);
                return;
            } else {
                viewemail.setText("");
            }

            passwordcheck = pass.getText().toString();
            if (passwordcheck == null || passwordcheck.isEmpty()) {
                viewpass.setText("Password cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    pass.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                viewpass.setText("");
            }
            if(flag == "No")
            {
                viewpass.setText("Please Verify the recaptcha");
                Toast.makeText(getApplicationContext(),"Please Verify the recaptcha", Toast.LENGTH_LONG).show();
                return;
            }else
            {
                viewpass.setText("");
                Log.d("log","verifyed recaptcha");
            }
            waitDialog.setMessage("Signing in...");
            waitDialog.show();
            UserLogin();


        }

    }

    private void UserLogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/login", new Response.Listener<String>() {


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
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogIn(
                                        emailcheck

                                );
                       // flag1=1;
                        CheckAccountProfileUpdate1();

                        //Log.d("response", s);

                    }else {
                        waitDialog.hide();
                        Log.d("responselogine", s);
                    }

                    Log.d("responselogin", jsonObject+"");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                waitDialog.hide();
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
                params.put("password", passwordcheck);
                Log.d("json_uuidf", emailcheck);
                Log.d("json_uuidf", passwordcheck);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }






    private void CheckAccountProfileUpdate()
    {
        Log.d(TAG,"idiot");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/checkprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("profile update", jsonObject+"");
                    int success= jsonObject.getInt("success");

                    // success=1;

                    // s="some";
                    if(success == 1)
                    {
                      CheckAccountType();
                    }else {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        String message=jsonObject1.getString("account_type");
                        switch(message)
                        {
                            case "Service Provider":
                                Intent intent1=new Intent(LoginActivity.this,ActivityProfileHire.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                                finish();

                                break;
                            case "Service Seeker":
                                Intent intent2=new Intent(LoginActivity.this,ActivityProfileWork.class);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);
                                finish();

                                break;
                            default:System.out.println("Not in 10, 20 or 30");
                                // etc...
                        }
                        Log.d("responsepu", jsonObject+"");
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                waitDialog.hide();
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
                String email1 = SharedPrefManager.getInstance(LoginActivity.this).getUserEmail();
               // Log.d("json_uuidf", emailcheck);
                Log.d("pfuemail", email1);
                params.put("email", email1);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }





    private void CheckAccountProfileUpdate1()
    {
        Log.d(TAG,"idiot");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/checkprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("profile update", jsonObject+"");
                    int success= jsonObject.getInt("success");

                    // success=1;

                    // s="some";
                    if(success == 1)
                    {
                        CheckAccountType1();
                    }else {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        String message=jsonObject1.getString("account_type");
                        waitDialog.dismiss();
                        switch(message)
                        {
                            case "Service Provider":
                                Intent intent1=new Intent(LoginActivity.this,ActivityProfileHire.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                                finish();

                                break;
                            case "Service Seeker":
                                Intent intent2=new Intent(LoginActivity.this,ActivityProfileWork.class);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);
                                finish();

                                break;
                            default:System.out.println("Not in 10, 20 or 30");
                                // etc...
                        }
                        Log.d("responsepu", jsonObject+"");
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                waitDialog.hide();
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
                String email1 = SharedPrefManager.getInstance(LoginActivity.this).getUserEmail();
                 Log.d("json_uuidf", emailcheck);
                Log.d("pfuemail", emailcheck);
                params.put("email", emailcheck);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }



    private void CheckAccountType1()
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
                        waitDialog.dismiss();
                        switch(Account_Type)
                        {
                            case "Service Provider":
                                Intent intent1=new Intent(LoginActivity.this,NavDrawerActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                                finish();

                                break;
                            case "Service Seeker":
                                Intent intent2=new Intent(LoginActivity.this,NavDrawerActivityWork.class);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);
                                finish();

                                break;
                            default:System.out.println("Not in 10, 20 or 30");
                                // etc...
                        }
                    }else {
                        waitDialog.dismiss();
                        Intent intent=new Intent(LoginActivity.this,ChooseProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        // Log.d("response", jsonObject+"");
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                waitDialog.hide();
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

                String email1 = SharedPrefManager.getInstance(LoginActivity.this).getUserEmail();
                params.put("email", emailcheck);
                 Log.d("json_uuidf", emailcheck);
                //Log.d("CAemail", email1);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


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
                            waitDialog.dismiss();
                            switch(Account_Type)
                            {
                                case "Service Provider":
                                    Intent intent1=new Intent(LoginActivity.this,NavDrawerActivity.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent1);
                                    finish();

                                    break;
                                case "Service Seeker":
                                    Intent intent2=new Intent(LoginActivity.this,NavDrawerActivityWork.class);
                                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent2);
                                    finish();

                                    break;
                                default:System.out.println("Not in 10, 20 or 30");
                                    // etc...
                            }
                        }else {
                                waitDialog.dismiss();
                                Intent intent=new Intent(LoginActivity.this,ChooseProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                           // Log.d("response", jsonObject+"");
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    waitDialog.hide();
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

                    String email1 = SharedPrefManager.getInstance(LoginActivity.this).getUserEmail();
                    params.put("email", email1);
                  //  Log.d("json_uuidf", emailcheck);
                    Log.d("CAemail", email1);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);


    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart invoked");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop invoked");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart invoked");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy invoked");
    }



    private boolean validateEmail(String address) {
        return VALID_EMAIL_ADDRESS_REGEX.matcher(address).find();
    }

}
