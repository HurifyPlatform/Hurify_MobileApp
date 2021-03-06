package com.example.admin.blockchain.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.blockchain.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.blockchain.R;
import com.example.admin.blockchain.RecyclerItemClickListener;
import com.example.admin.blockchain.SharedPrefManager;
import com.example.admin.blockchain.helper.JSONParser;
import com.example.admin.blockchain.helper.ProjectAppliedProviderInfoAdapter;
import com.example.admin.blockchain.modal.ProjectAppliedProviderInfoModel;

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
 * {@link MoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private List<ProjectAppliedProviderInfoModel> modelList=new ArrayList<>();
    private ProjectAppliedProviderInfoAdapter adapter;
    private JSONParser jsonParser;
    String email;
    TextView txtlisterror;

    private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
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
        View view=inflater.inflate(R.layout.fragment_movies, container, false);
        txtlisterror=(TextView)view.findViewById(R.id.text_list_error2);
        txtlisterror.setVisibility(View.INVISIBLE);
        jsonParser=new JSONParser();
        email = SharedPrefManager.getInstance(getActivity()).getUserEmail();
        //Log.d("www",email);

        recyclerView= (RecyclerView) view.findViewById(R.id.list_applied_project_provider);
        adapter=new ProjectAppliedProviderInfoAdapter(modelList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ProjectAppliedProviderInfoModel model = modelList.get(position);
               // Intent intent = new Intent(getActivity(),ActivityProjectFullDesc.class);
               // intent.putExtra("id",model.getId()+"");
               // startActivity(intent);
                Toast.makeText(getActivity(), model.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

       AppliedProjectListProvider();
        //profileHire1();
        return view;
    }









    private void AppliedProjectListProvider(){

        // RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/project/applied", new Response.Listener<String>() {


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
                           // String ExpertLevel=jsonObject3.optString("experience_level");
                            String DayValue=jsonObject3.optString("days");
                            String ProjectAbstract=jsonObject3.optString("project_description");
                            Log.d("Project Name",ProjectName);

                            Log.d("DayValue",DayValue);


                            ProjectAppliedProviderInfoModel model=new ProjectAppliedProviderInfoModel(id,ProjectName,DayValue,ProjectAbstract);
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
                    Toast.makeText(getContext(), "Server down try after some time", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<>();
                params.put("appliers_email", email);
              //  Log.d("email", email);
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


}
