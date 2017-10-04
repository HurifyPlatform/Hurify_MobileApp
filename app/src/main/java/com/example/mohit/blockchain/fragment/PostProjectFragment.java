package com.example.mohit.blockchain.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.mohit.blockchain.ActivityProfileHire;
import com.example.mohit.blockchain.NavDrawerActivityWork;
import com.example.mohit.blockchain.PostProjectConfirmHur;
import com.example.mohit.blockchain.R;
import com.example.mohit.blockchain.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostProjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostProjectFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String TAG="tag";
    private ImageView imgNavHeaderBg;
    private TextView txtValidName,txtValidTechnology,txtValidCategory,txtValidPrice,txtValidExprience,txtValidEstTime,txtValidAbstract,txtValidDescription,Categories;
    private EditText Name,Technology,Price,EstTime,Abstract,Description;
    private Toolbar mtoolbar;
    private Button PostProject;
    private Spinner Exprience;
    String ExpValue;
    String email,projectname,technology,price,estTime,abstra,description;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    String item = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public PostProjectFragment() {
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
    public static PostProjectFragment newInstance(String param1, String param2) {
        PostProjectFragment fragment = new PostProjectFragment();
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
        View view= inflater.inflate(R.layout.fragment_post_project_work, container, false);
        txtValidName = (TextView) view.findViewById(R.id.validName);
        txtValidTechnology = (TextView) view.findViewById(R.id.validTechnology);
        txtValidExprience = (TextView) view.findViewById(R.id.validExprience);
        txtValidCategory = (TextView) view.findViewById(R.id.validCategories);
        txtValidPrice = (TextView) view.findViewById(R.id.validPrice);
        txtValidEstTime = (TextView) view.findViewById(R.id.validEstTime);
        txtValidAbstract = (TextView) view.findViewById(R.id.validAbstract);
        txtValidDescription = (TextView) view.findViewById(R.id.validDescription);
        Name = (EditText) view.findViewById(R.id.Project_Name);
        Technology = (EditText) view.findViewById(R.id.Technology);
        Price = (EditText) view.findViewById(R.id.Price);
        EstTime = (EditText) view.findViewById(R.id.Estimate_Time);
        Abstract = (EditText) view.findViewById(R.id.Abstract);
        Description = (EditText) view.findViewById(R.id.Description);
        Exprience = (Spinner) view.findViewById(R.id.Experience);
        Categories= (TextView) view.findViewById(R.id.Categories);
        PostProject=  (Button) view.findViewById(R.id.PostProject);
        imgNavHeaderBg = (ImageView) view.findViewById(R.id.imageView);
        Exprience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ExpValue = (String) parent.getItemAtPosition(position);
                Log.v("TAG", ExpValue);
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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
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

        email = SharedPrefManager.getInstance(getActivity()).getUserEmail();
        //email="cse.mohit93@gmail.com";
        PostProject.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.PostProject) {
             Log.d(TAG,"cliclked");
            projectname = Name.getText().toString().trim();
            // Log.d(TAG,name);
            if (projectname == null || projectname.isEmpty()) {
                txtValidName.setText("Name cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidName.setBackground(getActivity().getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidName.setText("");
            }

            if(ExpValue.equals("Choose Exprience Level"))
            {
               // Toast.makeText(getActivity(), "Exprience not selected !!!.", LENGTH_SHORT).show();
                txtValidExprience.setText("Exprience Level not selected ");
                return;
            }else {
                txtValidExprience.setText("");
            }

            if(item.equals(""))
            {
                //Toast.makeText(this, "country or state not selected !!!.", LENGTH_SHORT).show();
                txtValidCategory.setText("Categories not selected");
                return;
            }else {
                txtValidCategory.setText("");
            }
            technology = Technology.getText().toString().trim();
            if (technology == null || technology.isEmpty()) {
                txtValidTechnology.setText("Technology cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidTechnology.setBackground(getActivity().getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidTechnology.setText("");
            }
            price = Price.getText().toString().trim();
            if (price == null || price.isEmpty()) {
                txtValidPrice.setText("Price cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidPrice.setBackground(getActivity().getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidPrice.setText("");
            }
            estTime = EstTime.getText().toString().trim();
            if (estTime == null || estTime.isEmpty()) {
                txtValidEstTime.setText("Estimate time cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidEstTime.setBackground(getActivity().getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidEstTime.setText("");
            }
            abstra = Abstract.getText().toString().trim();
            if (abstra == null || abstra.isEmpty()) {
                txtValidAbstract.setText("Abstract cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidAbstract.setBackground(getActivity().getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidAbstract.setText("");
            }
            description= Description.getText().toString().trim();
            if (description == null || description.isEmpty()) {
                txtValidDescription.setText("Description cannot be empty");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    txtValidDescription.setBackground(getActivity().getDrawable(R.drawable.text_border_error));
                }
                return;
            } else {
                txtValidDescription.setText("");
            }
            postProject();
            //profilehire();
        }

    }

    private void profilehire() {

        Log.d(TAG, projectname);
        Log.d(TAG, ExpValue);
        Log.d(TAG, technology);
        Log.d(TAG, price);
        Log.d(TAG, estTime);
        Log.d(TAG, abstra);
        Log.d(TAG, description);
        Log.d(TAG, item);
    }


    private void postProject() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/project/createproject", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response1", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        String message=jsonObject.getString("message");

                        Intent intent = new Intent(getActivity(), PostProjectConfirmHur.class);
                        startActivity(intent);

                       // Intent intent = new Intent(getActivity(), NavDrawerActivityWork.class);
                        //intent.putExtra("TAG","movies");
                        //startActivity(intent);
                       /* Fragment fragment = new PostedProjectsFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_work, fragment, "home");
                        fragmentTransaction.commitAllowingStateLoss();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();*/
                        //startActivity(new Intent(getApplicationContext(), ActivityProfileHireShow.class));
                    }else {
                        Toast.makeText(getActivity(), "not success", Toast.LENGTH_LONG).show();
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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                //String uname=sessionManager.getCurrentUsername();

                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("project_name", projectname);
                params.put("technology", technology);
                params.put("price", price);
                params.put("category", item);
                params.put("experience_level", ExpValue);
                params.put("estimated_time", estTime);
                params.put("project_description", description);
                params.put("abstract", abstra);
                Log.d("json_uuidf", email);
                Log.d(TAG, projectname);
                Log.d(TAG, technology);
                Log.d(TAG, price);
                Log.d(TAG, item);
                Log.d(TAG, ExpValue);
                Log.d(TAG, estTime);
                Log.d(TAG, description);
                Log.d(TAG, abstra);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);




        //progressdialog.setMessage("User Registering.....");
        //progressdialog.show();
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
