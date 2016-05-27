package com.example.yang.cashdash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    public static final String DASH_PREFERENCES = "dashPreferences";
    public String savedUser, savedPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button log_in_button = (Button) findViewById(R.id.log_in_button);
        log_in_button.setOnClickListener(logIn);
        Button register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(register);

        SharedPreferences palPreferences = getSharedPreferences(DASH_PREFERENCES, Context.MODE_PRIVATE);
        savedUser = palPreferences.getString("username", "");
        savedPass = palPreferences.getString("password", "");
        Log.d("ABCD", savedUser);
        Log.d("ABCD", savedPass);
    }
    private View.OnClickListener logIn = new View.OnClickListener() {
        public void onClick(View v) {
            String user = ((EditText)findViewById(R.id.VenmoEditText)).getText().toString();
            String pw = ((EditText)findViewById(R.id.pwEditText)).getText().toString();

            if (user == null || user.isEmpty()){
                ((TextView)findViewById(R.id.error_text)).setText("Please enter your Venmo username");
                findViewById(R.id.error_text).setVisibility(View.VISIBLE);
            } else if (pw == null || pw.isEmpty()) {
                ((TextView)findViewById(R.id.error_text)).setText("Please enter your password");
                findViewById(R.id.error_text).setVisibility(View.VISIBLE);
            } else if (!user.equals(savedUser) || !pw.equals(savedPass)) {
                ((TextView)findViewById(R.id.error_text)).setText("Please enter correct credential");
                findViewById(R.id.error_text).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.error_text).setVisibility(View.GONE);
                Intent log = new Intent(getBaseContext(), CashDashActivity.class);
                log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(log);
            }
        }
    };
    private View.OnClickListener register = new View.OnClickListener() {
        public void onClick(View v) {
            Intent log = new Intent(getBaseContext(), RegisterActivity.class);
            log.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(log);
        }
    };




}
