package com.example.mohit.blockchain.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.mohit.blockchain.ActivityProfileHireEdit;
import com.example.mohit.blockchain.ActivityProfileWorkEdit;
import com.example.mohit.blockchain.R;
import com.example.mohit.blockchain.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileWorkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileWorkFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private ImageView imgNavHeaderBg;
    private TextView txtName,txtCountry,txtLanguage,txtOverView;
    private Toolbar mtoolbar;
    private Button ProfileEdit;
    String country1,category1,email,name,language,overview;
    String TAG="tag";
    Context mContext;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileWorkFragment() {
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
    public static ProfileWorkFragment newInstance(String param1, String param2) {
        ProfileWorkFragment fragment = new ProfileWorkFragment();
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
        mContext = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_work, container, false);
        txtName = (TextView) view.findViewById(R.id.Name);
        txtLanguage = (TextView) view.findViewById(R.id.Language);
        txtCountry = (TextView) view.findViewById(R.id.Country);
        txtOverView = (TextView) view.findViewById(R.id.Overview);
        ProfileEdit=  (Button) view.findViewById(R.id.ProfileEdit);
        imgNavHeaderBg = (ImageView) view.findViewById(R.id.imageView);



        email = SharedPrefManager.getInstance(getActivity()).getUserEmail();
        //email="lakshmi.spandana@mobodexter.com";
        // Log.d("email",email);


        ProfileEdit.setOnClickListener(this);
        profileWorkShow();
        return view;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ProfileEdit) {

            Toast.makeText(getActivity(), "Go to next Activity", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getActivity(), ActivityProfileWorkEdit.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // profileHire();
        }else {
            Toast.makeText(getActivity(), "Not Go to next Activity", Toast.LENGTH_LONG).show();
        }

    }



    private void profileWorkShow() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://platform.hurify.co/auth/getprofile", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("response", jsonObject+"");
                    int success= jsonObject.getInt("success");
                    if(success == 1)
                    {
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        name=jsonObject1.getString("name");
                        overview=jsonObject1.getString("description");
                        language=jsonObject1.getString("languages_known");
                        country1=jsonObject1.getString("country");
                        txtName.setText(name);
                        txtCountry.setText(country1);
                        txtOverView.setText(overview);
                        txtLanguage.setText(language);
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(mContext, "Server down try after some time", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
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
