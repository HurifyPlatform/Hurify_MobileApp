package com.example.admin.blockchain;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PostProjectConfirmHur extends AppCompatActivity implements View.OnClickListener {

    EditText HurAddress;
    Button SubmitHur;
    TextView errorHur,errorEdit;
    String HURADDRESS;
    Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postproject_hur);
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
        getSupportActionBar().setTitle("Hur Address");
        HurAddress = (EditText)findViewById(R.id.HurAddress);
        errorHur = (TextView) findViewById(R.id.errorHur);
        errorEdit = (TextView) findViewById(R.id.errorEdit);
        SubmitHur= (Button) findViewById(R.id.SubmitHur);
        SubmitHur.setOnClickListener(this);
    }

/*    public void buttonClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_username);
        final EditText etPassword = (EditText) alertLayout.findViewById(R.id.et_password);
        final CheckBox cbShowPassword = (CheckBox) alertLayout.findViewById(R.id.cb_show_password);

        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // to encode password in dots
                    etPassword.setTransformationMethod(null);
                } else {
                    // to display the password in normal text
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Login");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                Toast.makeText(getBaseContext(), "Username: " + user + " Password: " + pass, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }*/
    @Override
    public void onClick(View v) {

      if(v.getId()==R.id.SubmitHur) {
          HURADDRESS = HurAddress.getText().toString();
          if (HURADDRESS == null || HURADDRESS.isEmpty()) {
              errorEdit.setText(HurAddress.getHint() + " cannot be empty");
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                  HurAddress.setBackground(getDrawable(R.drawable.text_border_error));
              }
              return;
          } else {
              errorEdit.setText("");
          }

         // HurValue();

          LayoutInflater inflater = getLayoutInflater();
          View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_hurconfirm, null);
          final TextView message = (TextView) alertLayout.findViewById(R.id.textView15);
          final Button Submit = (Button) alertLayout.findViewById(R.id.Submit);
          final Button Cancle = (Button) alertLayout.findViewById(R.id.Cancle);
          final AlertDialog.Builder alert = new AlertDialog.Builder(this);
          alert.setTitle("Hur Information");
          message.setText("Hur Address Value 750");
          // this is set the view from XML inside AlertDialog
          alert.setView(alertLayout);
          final AlertDialog dialog = alert.create();
          dialog.show();
          // disallow cancel of AlertDialog on click of back button and outside touch

          Submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Toast.makeText(getBaseContext(), "Project Applied", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(PostProjectConfirmHur.this, NavDrawerActivityWork.class);
                  intent.putExtra("TAG","movies");
                  startActivity(intent);
                  dialog.dismiss();

              }
          });
          Cancle.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dialog.dismiss();
                  Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();

              }
          });

      }

    }

   /*private void HurValue() {


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
    }*/





}

