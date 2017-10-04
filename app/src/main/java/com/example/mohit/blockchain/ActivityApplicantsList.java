package com.example.mohit.blockchain;

import android.content.Intent;
import android.os.Build;
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
import com.example.mohit.blockchain.helper.ProjectAppliedProviderInfoAdapter;
import com.example.mohit.blockchain.modal.ProjectAppliedInfoModel;
import com.example.mohit.blockchain.modal.ProjectAppliedProviderInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityApplicantsList extends AppCompatActivity {

    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtBidValue,txtlisterror;
    private EditText BidValue;
    private Toolbar mtoolbar;

    private RecyclerView recyclerView;
    private List<ProjectAppliedInfoModel> modelList=new ArrayList<>();
    private ProjectAppliedInfoAdapter adapter;
    private Button BidSubmit;
    private Spinner Country;
    String Account_Type;
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
        setContentView(R.layout.activity_applicants_list);
        txtlisterror=(TextView)findViewById(R.id.text_list_error);
        txtlisterror.setVisibility(View.INVISIBLE);
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
        getSupportActionBar().setTitle("Applicants List");


        email = SharedPrefManager.getInstance(this).getUserEmail();
        data = getIntent().getExtras().getString("id");

        Log.d("id",data);
        //Log.d("www",email);

        recyclerView= (RecyclerView) findViewById(R.id.list_applied_project);
        adapter=new ProjectAppliedInfoAdapter(modelList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ProjectAppliedInfoModel model = modelList.get(position);
                 Intent intent = new Intent(ActivityApplicantsList.this,ActivityApplicantsProfile.class);
                 intent.putExtra("id",model.getProjectName());
                 startActivity(intent);
                Toast.makeText(getApplicationContext(), model.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        AppliedProjectList();

        // Navigation view header


    }



    private void AppliedProjectList(){

        // RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/project/getappliedusers", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                //response="{\"success\":\"1\", \"message\": {\"phantomPower\": {\"HIVEPIE-26862\": [{\"socket\": \"1\", \"PowerSavingPerMonth\": \"11.07852\", \"CostSavingPerMonth\": \"1.325376\"}, {\"socket\": \"2\", \"PowerSavingPerMonth\": \"114.319999999999999\", \"CostSavingPerMonth\": \"0.6626879999999999\"}, {\"socket\": \"3\", \"PowerSavingPerMonth\": \"1117.1987654321\", \"CostSavingPerMonth\": \"1.10448\"}, {\"socket\": \"4\", \"PowerSavingPerMonth\": \"23425.1250490876\", \"CostSavingPerMonth\": \"0.773136\"}]}}}";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("res",response+"");
                    int success = jsonObject.getInt("success");
                    Log.d("phantom success",success+"");
                    if (success == 1) {

                        JSONArray jsonArray=jsonObject.optJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject3=jsonArray.getJSONObject(i);
                            //String socket=jsonObject3.optString("socket");
                            int id=jsonObject3.getInt("id");
                            String ProjectName=jsonObject3.optString("appliers_email");
                            String ExpertLevel=jsonObject3.optString("experience_level");
                            String DayValue=jsonObject3.optString("days");
                            String BidValue=jsonObject3.optString("bid_value");
                            Log.d("Project Name",ProjectName);

                            Log.d("DayValue",DayValue);


                            ProjectAppliedInfoModel model=new ProjectAppliedInfoModel(id,ProjectName,DayValue,BidValue,ExpertLevel);
                            modelList.add(model);
                            adapter.notifyDataSetChanged();
                        }


                    } else {
                        txtlisterror.setText("No One Applied");
                        txtlisterror.setVisibility(View.VISIBLE);

                       /* View v = getLayoutInflater().inflate(R.layout.activity_applicants_list_not_there, null);
                        mtoolbar = (Toolbar)v.findViewById(R.id.toolbar);
                        txtlisterror=(TextView)v.findViewById(R.id.text_list_error);
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
                        getSupportActionBar().setTitle("Applicants List");
                        txtlisterror.setText("No One Applied For This Project");*/
                        Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_LONG).show();
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
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<>();
                params.put("project_id", data);
                Log.d("id", data);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }




}

