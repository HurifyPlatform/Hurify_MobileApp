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

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    final String SiteKey = "6LfCuC4UAAAAAIY9lUBBDSDkFe6GQb5Hey9NdfwV";
    final String SecretKey  = "6LfCuC4UAAAAALAon5KII8bOlCRlQu6aTKM05ZyA";
   // final String SecretKey  = "6LfCuC4UAAAAALAon5KII8bOlCRlQu6aTKM05ZyB";
     String Token;
    String ipAddress;
    String AccountType,accounttype;
    String Type,type;
    String ServiceProviderType,serviceprovidertype;
    String flag="No";
    String flag1="No";
    String flag2="No";
    String flag3="No";
    int count=0;
    int count1=0;
    int count2=0;
    private ProgressDialog waitDialog;
    String ipAddress1,emailcheck,passwordcheck,confirmpasscheck;
    RelativeLayout coordinatorLayout;

    private GoogleApiClient mGoogleApiClient;
    String TAG="tag";
    Spinner spinner1,spinner2,spinner3;
    TextView Resendmail,recaptchatext,viewemail,viewpass,viewconfirmpass,viewaccounttype,viewserviceprovider,viewtype,loginhere;
    EditText Password,email,ConfrimPassword;
    Button login;

    Button btnRequest;
    ImageView image;
    TextView tvResult;
    GifDrawable gifFromResource;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ChooseProfileActivity.class));
            return;
        }
        waitDialog = new ProgressDialog(this);
      //  WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
       // String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
       // Log.d(TAG,ipAddress);
         ipAddress= getLocalIpAddress();
        Log.d(TAG,ipAddress);





       // spinner.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);

        //spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
       // ipAddress1= getWifiIPAddress();
       //Log.d(TAG,ipAddress1);

       // tvResult = (TextView)findViewById(R.id.result);
      //  btnRequest = (Button)findViewById(R.id.request);
       // image=(ImageView)findViewById(R.id.imageView);
       // btnRequest.setOnClickListener(RqsOnClickListener);
        image.setOnClickListener(RqsOnClickListener);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();

        mGoogleApiClient.connect();

        loginhere.setOnClickListener(this);
        Resendmail.setOnClickListener(this);
        login.setOnClickListener(this);

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
    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return  Formatter.formatIpAddress(ip);
    }

    View.OnClickListener RqsOnClickListener = new View.OnClickListener() {
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
                        image.setImageResource(R.mipmap.recaptcha2);
                        flag="Yes";
                        viewconfirmpass.setText("");
                    }else {
                        image.setImageResource(R.mipmap.recaptcha1);
                        flag="No";
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

    private void init() {
       // alreadyRegister = (TextView) findViewById(R.id.alreadyregister);
        Resendmail=(TextView) findViewById(R.id.txtResendMail);
        email = (EditText) findViewById(R.id.et_username);
        Password = (EditText) findViewById(R.id.et_password);
        ConfrimPassword = (EditText) findViewById(R.id.etConfirmPass);
        /*organization = (EditText) findViewById(R.id.etOrganization);*/
        viewemail = (TextView) findViewById(R.id.textViewUserIdMessage);
        viewpass = (TextView) findViewById(R.id.textViewUserPasswordMessage);
        viewconfirmpass= (TextView) findViewById(R.id.textviewconfirmpassword);


        loginhere= (TextView) findViewById(R.id.txtSignup);
        coordinatorLayout = (RelativeLayout) findViewById(R.id.coordinatorLayout);
        image =(ImageView)findViewById(R.id.recapchta);
        login = (Button) findViewById(R.id.btn_login);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txtSignup) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }

        if (view.getId() == R.id.txtResendMail) {
            startActivity(new Intent(getApplicationContext(), ReSendEmailVerification.class));
            return;
        }


        if (view.getId() == R.id.btn_login) {

           // final String accounttype= spinner1.getSelectedItem().toString();
           // Log.d("selected",accounttype);

            emailcheck=email.getText().toString();
            if (emailcheck == null || emailcheck.isEmpty()) {
                viewemail.setText(email.getHint() + " cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    email.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            }else
            {
                viewemail.setText("");
            }

            if (!validateEmail(emailcheck)) {
                Toast.makeText(this, "invalid email address, please retry.", LENGTH_SHORT).show();
                viewemail.setText("invalid email addresss");
                //emailAddress.setFocusable(true);
                return;
            }else
            {
                viewemail.setText("");
            }



            passwordcheck=Password.getText().toString();
            if (passwordcheck == null || passwordcheck.isEmpty()) {
                viewpass.setText("Password cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Password.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            }else
            {
                viewpass.setText("");
            }

            confirmpasscheck=ConfrimPassword.getText().toString();
            if (confirmpasscheck == null || confirmpasscheck.isEmpty()) {
                viewconfirmpass.setText("Confrim Password cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ConfrimPassword.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            }else
            {
                viewconfirmpass.setText("");
            }

            if(!passwordcheck.equals(confirmpasscheck))
            {
                viewpass.setText("Password and Confrim Password doesn't matched");
                return;
            }else
            {
                viewpass.setText("");
            }

            if(flag == "No")
            {
                Toast.makeText(getApplicationContext(),"Please Verify the recaptcha", Toast.LENGTH_LONG).show();
                viewconfirmpass.setText("Please Verify the recaptcha");
                return;
            }else
            {
                Log.d("log","verifyed recaptcha");
                viewconfirmpass.setText("");
            }

          /*  Log.d("flag",""+flag);
            Log.d("flag1",""+flag1);
            Log.d("flag2",""+flag2);
            Log.d("flag3",""+flag3);
            if(flag == "No")
            {
                Toast.makeText(getApplicationContext(),"Please Verify the recaptcha", Toast.LENGTH_LONG).show();
                recaptchatext.setText("Please Verify the recaptcha");
                return;
            }else
            {
                Log.d("log","verifyed recaptcha");
                recaptchatext.setText("");
            }
            if(flag1 == "No")
            {
                Toast.makeText(getApplicationContext(),"Please Verify the recaptcha", Toast.LENGTH_LONG).show();
                viewaccounttype.setText("Please Select Account Type");
                return;
            }
            if(flag2.equals("No"))
            {
                Toast.makeText(getApplicationContext(),"Please Verify the recaptcha", Toast.LENGTH_LONG).show();
                viewserviceprovider.setText("Please Select Service Provider Type");
                return;
            }else
            {
                viewserviceprovider.setText("");
            }
            if(flag3.equals("No"))
            {
                Toast.makeText(getApplicationContext(),"Please Verify the recaptcha", Toast.LENGTH_LONG).show();
                viewtype.setText("Please Select Type");
                return;
            }else
            {
                viewtype.setText("");
            }*/

            waitDialog.setMessage("Signing in...");
            waitDialog.show();

             RegisterUser();
        }


    }


    private void RegisterUser() {
           Log.d("how manty time","ohh");
       /* final String userName = name.getText().toString().trim();
        final String emailsome = email.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String confirmPass = confirmPassword.getText().toString().trim();
        final String phn = phoneNumber.getText().toString().trim();
        final String countryName = country.getSelectedItem().toString().trim();
        final String stateName = states.getSelectedItem().toString().trim();
        final String appPurposeName = purposeSdk.getSelectedItem().toString().trim();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "\n" + "https://platform.hurify.co/auth/signup", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                   // response="{\"success\":\"1\", \"message\": \"successful login\"}";
                    JSONObject jsonObject = new JSONObject(response);

                    int success= jsonObject.getInt("success");
                    if(success==1)
                    {
                        waitDialog.dismiss();
                        //image.setImageResource(R.mipmap.recaptcha2);
                       // flag="Yes";
                        Log.d("response", jsonObject.getString("message"));
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }else {
                       // image.setImageResource(R.mipmap.recaptcha1);
                       // flag="No";
                        waitDialog.dismiss();
                        Log.d("response", jsonObject.getString("message"));
                    }

                    Log.d("response", jsonObject+"");


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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }




    private boolean validateConfirmPassword(String Password, String ConfrimPassword) {
        return !Password.isEmpty() && !ConfrimPassword.isEmpty() && Password.equals(ConfrimPassword);
    }

    private boolean validateEmail(String address) {
        return VALID_EMAIL_ADDRESS_REGEX.matcher(address).find();
    }


}
