package com.example.mohit.blockchain;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by admin on 14/02/17.
 */

public class SharedPrefManager {
    private static SharedPrefManager mInstance;

    private static Context mCtx;
    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USER_EMAIL="useremail";
    private static final String KEY_USER_DEVICE_NAME="devicename";

  //  private static final String KEY_PASSWORD="pass";

    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

   public boolean userLogIn(String email)
   {
       SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
       SharedPreferences.Editor editor=sharedPreferences.edit();

       editor.putString(KEY_USER_EMAIL, email);
       // editor.putString(KEY_PASSWORD,pass);

       editor.apply();
       return true;


   }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL, null)!=null)
        {
             return true;
        }
        return false;
    }


    public boolean logout()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUserEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);

    }


}