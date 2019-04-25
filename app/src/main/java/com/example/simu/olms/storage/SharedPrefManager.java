package com.example.simu.olms.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.simu.olms.model.StudentInfo;

public class SharedPrefManager {

     public static final String SHARED_PREF_NAME = "my_shared_preff";

     private static SharedPrefManager mInstance;
     private Context context;

    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUser(StudentInfo stuInfo){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id",stuInfo.getId());
        editor.putString("name",stuInfo.getName());
        editor.putString("dob",stuInfo.getDob());
        editor.putString("phn",stuInfo.getPhone());
        editor.putString("email",stuInfo.getEmail());

        editor.apply();
    }

    public void userType(String userType){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("user",userType);
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("id","1") != "1";
    }

    public StudentInfo getStudentInfo(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new StudentInfo(
                sharedPreferences.getString("id",null),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("dob",null),
                sharedPreferences.getString("phn",null),
                sharedPreferences.getString("email",null)
        );

    }

    public void clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
