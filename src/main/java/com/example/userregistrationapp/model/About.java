package com.example.userregistrationapp.model;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.userregistrationapp.R;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class About extends AppCompatActivity {
    private EditText name, age;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        name = findViewById(R.id.edit1);
        age = findViewById(R.id.edit2);

    }

    public void onResume()
    {
        super.onResume();

        // Fetching the stored data
        // from the SharedPreference
        @SuppressLint("WrongConstant") SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_APPEND);

        String s1 = sh.getString("name", "");
        int a = sh.getInt("age", 0);

        // Setting the fetched data
        // in the EditTexts
        name.setText(s1);
        age.setText(String.valueOf(a));
    }

    // Store the data in the SharedPreference
    // in the onPause() method
    // When the user closes the application
    // onPause() will be called
    // and data will be stored
    @Override
    protected void onPause()
    {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        SharedPreferences.Editor myEdit
                = sharedPreferences.edit();
        myEdit.putString("name",
                name.getText().toString());
        myEdit.putInt("age",
                Integer.parseInt(
                        age.getText().toString()));
        myEdit.commit();
    }
}




