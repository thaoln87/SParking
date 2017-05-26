package com.tma.sparking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tma.sparking.utils.Constants;
import com.tma.sparking.utils.PhoneInformation;
import com.tma.sparking.utils.SharedPreferenceUtils;

public class LoginActivity extends AppCompatActivity {
    private static final String OWN_PHONE_NUMBER = "0909.123.456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = (Button)findViewById(R.id.sign_in_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtils.putBoolean(Constants.LOGGED, true);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });

        EditText editTextPhoneNumber = (EditText)findViewById(R.id.phone_number);
        editTextPhoneNumber.setText(OWN_PHONE_NUMBER);
    }
}

