package com.tma.sparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tma.sparking.utils.Constants;
import com.tma.sparking.utils.OnPhoneNumberAvailable;
import com.tma.sparking.utils.PhoneInformation;
import com.tma.sparking.utils.SharedPreferenceUtils;

public class LoginActivity extends AppCompatActivity implements OnPhoneNumberAvailable {
    private EditText mEditTextPhoneNumber;

    private PhoneInformation mPhoneInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = (Button) findViewById(R.id.sign_in_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferenceUtils.putBoolean(Constants.LOGGED, true);
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });

        mEditTextPhoneNumber = (EditText) findViewById(R.id.phone_number);

        mPhoneInformation = new PhoneInformation(this);
        mPhoneInformation.getPhoneNumber();
    }

    @Override
    public void onPhoneNumberAvailable(String phoneNumber) {
        mEditTextPhoneNumber.setText(phoneNumber);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPhoneInformation.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

