package com.example.admin.blockchain;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;


public class ActivityProfileHire extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtValidName,txtValidCountry,txtValidLanguage,txtValidJobTitle,txtValidHourRate,txtValidCategory,txtValidSkills,txtValidOverView,Categories;
    private EditText Name,Language,JobTitle,HourRate,Skills,OverView;
    private Toolbar mtoolbar;
    private Button ProfileSubmit;
    private Spinner Country;
    ImageView ChoosePhoto,UploadPhoto,ChooseDocument,UploadDocument;
    String Account_Type;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
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
        setContentView(R.layout.activity_profile_hire);
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
        getSupportActionBar().setTitle("Hire Profile");
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
        listItems = getResources().getStringArray(R.array.category_item);
        checkedItems = new boolean[listItems.length];

        Categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityProfileHire.this);
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

        email = SharedPrefManager.getInstance(this).getUserEmail();
       // email="cse.mohit93@gmail.com";
       // Log.d("email",email);


        ProfileSubmit.setOnClickListener(this);
        ChoosePhoto.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                UploadPhoto.setImageBitmap(bitmap);
                PhotoUpload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {

        txtValidName = (TextView) findViewById(R.id.validName);
        txtValidLanguage = (TextView) findViewById(R.id.validLanguage);
        txtValidCountry = (TextView) findViewById(R.id.validCountry);
        txtValidJobTitle = (TextView) findViewById(R.id.validJobTitle);
        txtValidHourRate = (TextView) findViewById(R.id.validHourRate);
        txtValidCategory = (TextView) findViewById(R.id.validCategories);
        txtValidSkills = (TextView) findViewById(R.id.validSkill);
        txtValidOverView = (TextView) findViewById(R.id.validOverView);
        ChoosePhoto=(ImageView)findViewById(R.id.Choose_photo);
        UploadPhoto=(ImageView)findViewById(R.id.Upload_photo);
        ChooseDocument=(ImageView)findViewById(R.id.Choose_document);
        UploadDocument=(ImageView)findViewById(R.id.Upload_document);
        Name = (EditText) findViewById(R.id.Name);
        Language = (EditText) findViewById(R.id.Language);
        JobTitle = (EditText) findViewById(R.id.JobTitle);
        HourRate = (EditText) findViewById(R.id.HourRate);
        Skills = (EditText) findViewById(R.id.Skills);
        OverView = (EditText) findViewById(R.id.Overview);
        Country = (Spinner) findViewById(R.id.Country);
        Categories= (TextView) findViewById(R.id.Categories);
        ProfileSubmit=  (Button) findViewById(R.id.ProfileSubmit);

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.Choose_photo)
        {
            showFileChooser();

        }



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




    private void PhotoUpload()
    {
        Log.d(TAG,"idiot");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.100.130:1800/auth/upload1", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response", response+"");

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
               // String image = getStringImage(bitmap);
                String image="some";
                String name="mo";

                Map<String, String> params = new Hashtable<String, String>();
                params.put("image", image);
               // params.put("name", name);
                //String email1 = SharedPrefManager.getInstance(LoginActivity.this).getUserEmail();
               // Log.d("json_uuidf", email);
                Log.d("mohit", image);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }



    private void profileHire() {


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
                        Log.d("profile status",message);
                        CheckAccountType();
                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
                params.put("salary_per_hour", hourrate);
                params.put("description", overview);
                params.put("categories", item);
                params.put("languages_known", language);
                params.put("job_title", jobtitle);
                params.put("skills", skills);
                params.put("country", country1);
                Log.d("json_uuidf", email);
                Log.d(TAG, name);
                Log.d(TAG, language);
                Log.d(TAG, jobtitle);
                Log.d(TAG, hourrate);
                Log.d(TAG, skills);
                Log.d(TAG, overview);
                Log.d(TAG, country1);
                Log.d(TAG, item);
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
                    Log.d("Checkacc", jsonObject+"");
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
                                Intent intent1=new Intent(ActivityProfileHire.this,NavDrawerActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                                finish();

                                break;
                            case "Service Seeker":
                                Intent intent2=new Intent(ActivityProfileHire.this,NavDrawerActivityWork.class);
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
        requestQueue.add(stringRequest);


    }


}

