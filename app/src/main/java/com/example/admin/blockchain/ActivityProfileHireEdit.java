package com.example.admin.blockchain;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;


public class ActivityProfileHireEdit extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtValidName,txtValidLanguage,txtValidJobTitle,txtValidHourRate,txtValidCategory,txtValidSkills,txtValidOverView,Categories;
    private EditText Name,Language,JobTitle,HourRate,Skills,OverView;
    private Toolbar mtoolbar;
    private Button ProfileSubmit;
    StringBuilder sb = new StringBuilder(14);
    String country1,category1,email,name,country,language,jobtitle,hourrate,category,skills,overview;
    String TAG="tag";
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String item = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_hire);
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
        getSupportActionBar().setTitle("Hire Profile Edit");
        // Navigation view header
        imgNavHeaderBg = (ImageView) findViewById(R.id.imageView);
        listItems = getResources().getStringArray(R.array.category_item);
        checkedItems = new boolean[listItems.length];

        Categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityProfileHireEdit.this);
               // View mView = getLayoutInflater().inflate(R.layout.category_popup, null);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                            item="";
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        item="";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        Categories.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            item="";
                            Categories.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });



       // Log.d("email",email);

        profileShowHire();
        ProfileSubmit.setOnClickListener(this);
    }

    private void init() {
        email = SharedPrefManager.getInstance(this).getUserEmail();
        txtValidName = (TextView) findViewById(R.id.validName);
        txtValidLanguage = (TextView) findViewById(R.id.validLanguage);
        txtValidJobTitle = (TextView) findViewById(R.id.validJobTitle);
        txtValidHourRate = (TextView) findViewById(R.id.validHourRate);
        txtValidCategory = (TextView) findViewById(R.id.validCategories);
        txtValidSkills = (TextView) findViewById(R.id.validSkill);
        txtValidOverView = (TextView) findViewById(R.id.validOverView);
        Name = (EditText) findViewById(R.id.Name);
        Language = (EditText) findViewById(R.id.Language);
        JobTitle = (EditText) findViewById(R.id.JobTitle);
        HourRate = (EditText) findViewById(R.id.HourRate);
        Skills = (EditText) findViewById(R.id.Skills);
        OverView = (EditText) findViewById(R.id.Overview);
        Categories= (TextView) findViewById(R.id.Categories);
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

            if(item.equals(""))
            {
                Toast.makeText(this, "country or state not selected !!!.", LENGTH_SHORT).show();
                txtValidCategory.setText("Categories not selected");
                return;
            }else {
                txtValidCategory.setText("");
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
            jobtitle = JobTitle.getText().toString().trim();
            if (jobtitle == null || jobtitle.isEmpty()) {
                txtValidJobTitle.setText("Job title cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidJobTitle.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidJobTitle.setText("");
            }
            hourrate = HourRate.getText().toString().trim();
            if (hourrate == null || hourrate.isEmpty()) {
                txtValidHourRate.setText("Hourly rate cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidHourRate.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidHourRate.setText("");
            }
            skills = Skills.getText().toString().trim();
            if (skills == null || skills.isEmpty()) {
                txtValidSkills.setText("Hourly rate cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidSkills.setBackground(getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidSkills.setText("");
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
            profileHire();
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }


    private void profileShowHire() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/getprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("psh", jsonObject+"");
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

                        Name.setText(name);
                        HourRate.setText(hourrate);
                        Categories.setText(category);
                        OverView.setText(overview);
                        Language.setText(language);
                        Skills.setText(skills);
                        JobTitle.setText(jobtitle);
                        item=category;
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


    private void profileHire() {

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
                        Intent savings=new Intent(ActivityProfileHireEdit.this,NavDrawerActivity.class);
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
                params.put("salary_per_hour", hourrate);
                params.put("description", overview);
                params.put("categories", item);
                params.put("languages_known", language);
                params.put("job_title", jobtitle);
                params.put("skills", skills);
                Log.d("json_uuidf", email);
                Log.d(TAG, name);
                Log.d(TAG, language);
                Log.d(TAG, jobtitle);
                Log.d(TAG, hourrate);
                Log.d(TAG, skills);
                Log.d(TAG, overview);
                Log.d(TAG, item);
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
        Intent intent=new Intent(this,NavDrawerActivity.class);
        intent.putExtra("TAG","home");
        startActivity(intent);


        // this.overridePendingTransition(swap_in_bottom,swap_out_bottom);
        finish();
        //  Toast.makeText(getApplicationContext(),"backKeyPressed",Toast.LENGTH_LONG).show();
    }


}

