package com.example.admin.blockchain.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.blockchain.ActivityProjectFullDesc;
import com.example.admin.blockchain.R;
import com.example.admin.blockchain.RecyclerItemClickListener;
import com.example.admin.blockchain.helper.JSONParser;
import com.example.admin.blockchain.helper.ProjectInfoAdapter;
import com.example.admin.blockchain.modal.ProjectInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PhotosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private List<ProjectInfoModel> modelList=new ArrayList<>();
    private ProjectInfoAdapter adapter;
    private Button filter,filter1;
    private JSONParser jsonParser;
    TextView txtlisterror;
    StringBuilder sb = new StringBuilder(14);
    String[] listItems;
    String[] listItems1;
    boolean[] checkedItems;
    boolean[] checkedItems1;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<Integer> mUserItems1 = new ArrayList<>();
    String item = "";
    String item1 = "";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PhotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotosFragment newInstance(String param1, String param2) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_photos, container, false);
        txtlisterror=(TextView)view.findViewById(R.id.text_list_error1);
        txtlisterror.setVisibility(View.INVISIBLE);
        jsonParser=new JSONParser();
        listItems = getResources().getStringArray(R.array.category_item);
        checkedItems = new boolean[listItems.length];
        listItems1 = getResources().getStringArray(R.array.exp_item);
        checkedItems1 = new boolean[listItems1.length];
        filter=(Button)view.findViewById(R.id.filter);
        filter1=(Button)view.findViewById(R.id.filter1);
       if(item.equals("") && item1.equals(""))
        {
            Log.d("hey","Hello");
            profileHire();
        }
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //View mView = getActivity().getLayoutInflater().inflate(R.layout.filter_popup, null);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
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
                       // Categories.setText(item);
                      //  Log.d(TAG, item);
                        Log.d("pbfI", item);
                        Log.d("pbfI1", item1);
                        modelList.clear();
                        profileHire();
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Log.d("pbfI", item);
                        Log.d("pbfI1", item1);
                        modelList.clear();
                        profileHire();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            item="";
                           // Categories.setText("");
                           // Log.d("TAG1", item);
                        }
                        Log.d("nebfI", item);
                        Log.d("nebfI1", item1);
                        modelList.clear();
                        profileHire();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
                mDialog.getButton(mDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.filterColor));
                mDialog.getButton(mDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.filterColor));
                mDialog.getWindow().setBackgroundDrawableResource(R.color.white);
               // profileHire();

            }
        });


        filter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //View mView = getActivity().getLayoutInflater().inflate(R.layout.filter_popup, null);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setMultiChoiceItems(listItems1, checkedItems1, new DialogInterface.OnMultiChoiceClickListener() {
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
                            mUserItems1.add(position);
                        }else{
                            mUserItems1.remove((Integer.valueOf(position)));
                            item1="";
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        item1="";
                        for (int i = 0; i < mUserItems1.size(); i++) {
                            item1 = item1 + listItems1[mUserItems1.get(i)];
                            if (i != mUserItems1.size() - 1) {
                                item1 = item1 + ", ";
                            }
                        }
                        // Categories.setText(item);
                       Log.d("pbf1I", item);
                        Log.d("pbf1I1", item1);
                        modelList.clear();
                        profileHire();
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Log.d("nbf1I", item);
                        Log.d("nbf1I1", item1);
                        modelList.clear();
                        profileHire();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems1.length; i++) {
                            checkedItems1[i] = false;
                            mUserItems1.clear();
                            item1="";
                            // Categories.setText("");
                           // Log.d("TAG1", item1);

                        }
                        Log.d("nebf1I", item);
                        Log.d("nebf1I1", item1);
                        modelList.clear();
                        profileHire();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });


        recyclerView= (RecyclerView) view.findViewById(R.id.list);
        adapter=new ProjectInfoAdapter(modelList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ProjectInfoModel model = modelList.get(position);
                Intent intent = new Intent(getActivity(),ActivityProjectFullDesc.class);
                intent.putExtra("id",model.getId()+"");
                startActivity(intent);
                Toast.makeText(getActivity(), model.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));






        Log.d("item", item);
        Log.d("item1", item1);

        //profileHire1();
        return view;
    }

    private void profileHire1() {

        Log.d("some", item);
        Log.d("some", item1);
        Toast.makeText(getActivity(),item+"some",Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(),item1+"some1",Toast.LENGTH_LONG).show();

    }


    private void profileHire(){

        // RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/project/findproject", new Response.Listener<String>() {


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
                            String ProjectName=jsonObject3.optString("project_name");
                            String Technology=jsonObject3.optString("technology");
                            String Category=jsonObject3.optString("category");
                            String Price=jsonObject3.optString("price");
                            String ExpertLevel=jsonObject3.optString("experience_level");
                            String EstTime=jsonObject3.optString("estimated_time");
                            String ProjectAbstract=jsonObject3.optString("project_description");
                            String DayValue=jsonObject3.optString("days");
                            Log.d("Project Name",ProjectName);
                            Log.d("Technology",Technology);
                            Log.d("Category",Category);
                            Log.d("Price",Price);
                            Log.d("ExpertLevel",ExpertLevel);
                            Log.d("EstTime",EstTime);
                            Log.d("ProjectAbstract",ProjectAbstract);
                            Log.d("DayValue",DayValue);


                            ProjectInfoModel model=new ProjectInfoModel(id,ProjectName,DayValue,Technology,Category,ProjectAbstract,Price,ExpertLevel,EstTime);
                            modelList.add(model);
                            adapter.notifyDataSetChanged();
                        }


                    } else {
                        txtlisterror.setText("No Work Till Now");
                        txtlisterror.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"nothing",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error.getMessage()==null) {
                    Toast.makeText(getActivity(), "Server down try after some time", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<>();
                params.put("category", item);
                params.put("experience", item1);
                Log.d("category", item +"I");
                Log.d("experience", item1 +"I1");
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.main, menu);
        final CheckBox checkBox = (CheckBox) menu.findItem(R.id.delete).getActionView();
        final CheckBox checkBox1 = (CheckBox) menu.findItem(R.id.delete1).getActionView();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    checkBox.setChecked(false);
                }else {
                    checkBox.setChecked(true);
                }
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    checkBox1.setChecked(false);
                }else {
                    checkBox1.setChecked(true);
                }
            }
        });


        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
}
