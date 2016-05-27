package com.example.yang.cashdash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    public static final String DASH_PREFERENCES = "dashPreferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(register);
        Button confirm_button = (Button) findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(confirm);
        ScrollView restOfScreen = (ScrollView) findViewById(R.id.scroll);
        restOfScreen.setOnClickListener(cancel);

    }

    private View.OnClickListener register = new View.OnClickListener() {
        public void onClick(View v) {
            String user = ((EditText)findViewById(R.id.VenmoEditText)).getText().toString();
            String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();
            String number = ((EditText)findViewById(R.id.numberEditText)).getText().toString();
            String pw = ((EditText)findViewById(R.id.pwEditText)).getText().toString();
            String pwConfirm = ((EditText)findViewById(R.id.pwConfirmEditText)).getText().toString();
            if (!validate(user, name, number, pw, pwConfirm)) {
                ((TextView)findViewById(R.id.error_text)).setText("Please fill out the entire form!");
                (findViewById(R.id.error_text)).setVisibility(View.VISIBLE);
            } else if (!pw.equals(pwConfirm)) {
                ((TextView)findViewById(R.id.error_text)).setText("The passwords don't match!");
                (findViewById(R.id.error_text)).setVisibility(View.VISIBLE);
            } else {
                Button register_button = (Button) findViewById(R.id.register_button);
                register_button.setVisibility(View.GONE);
                Button confirm_button = (Button) findViewById(R.id.confirm_button);
                confirm_button.setVisibility(View.VISIBLE);
            }
        }
    };

    private View.OnClickListener confirm = new View.OnClickListener() {
        public void onClick(View v) {
            String user = ((EditText)findViewById(R.id.VenmoEditText)).getText().toString();
            String pw = ((EditText)findViewById(R.id.pwEditText)).getText().toString();
            SharedPreferences dashPreferences = getSharedPreferences(DASH_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = dashPreferences.edit();
            editor.putString("username", user);
            editor.putString("password", pw);
            editor.commit();
            Intent log = new Intent(getBaseContext(), CashDashActivity.class);
            log.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(log);
        }
    };

    private View.OnClickListener cancel = new View.OnClickListener() {
        public void onClick(View v) {
            Button register_button = (Button) findViewById(R.id.register_button);
            register_button.setVisibility(View.VISIBLE);
            Button confirm_button = (Button) findViewById(R.id.confirm_button);
            confirm_button.setVisibility(View.GONE);
        }
    };


    private boolean validate(String... param) {
        for (int i = 0; i<param.length; i++){
            if (param[i] == null || param[i].isEmpty())
                return false;
        }
        return true;
    }

}
