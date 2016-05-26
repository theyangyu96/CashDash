package com.example.yang.cashdash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.parse.*;

public class RegisterActivity extends AppCompatActivity {
    android.widget.EditText usernameEditText;
    android.widget.EditText passwordEditText;
    android.widget.EditText passwordAgainEditText;
    android.widget.EditText nameEditText;
    android.widget.EditText numberEditText;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //  Parse.initialize(new Parse.Configuration.Builder(myContext)
        //         .applicationId("YOUR_APP_ID")
        //         .server("http://YOUR_PARSE_SERVER:1337/parse")

        //  .build()
        // );
        usernameEditText = (EditText) findViewById(R.id.VenmoEditText);
        passwordEditText = (EditText) findViewById(R.id.pwEditText);
        passwordAgainEditText = (EditText) findViewById(R.id.pwConfirmEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        numberEditText = (EditText) findViewById(R.id.numberEditText);

        Button mActionButton = (Button) findViewById(R.id.log_in_button);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //  signup();
            }
        });
    }

    private void signup() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();
        String errorValue;
        String name = nameEditText.getText().toString().trim();
        String number= numberEditText.getText().toString().trim();



        boolean validationError = false;
        //StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
       /* if (username.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        if (!password.equals(passwordAgain)) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
        }
        validationErrorMessage.append(getString(R.string.error_text));
        if (validationError) {
            Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toa
                    st.LENGTH_LONG)
                    .show();
            return;
        }*/

        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);
        user.put("number",number);
        user.put("name",name);

        signup();

           user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    // Handle the response
                }
          });
        //  Intent intent = new Intent(RegisterActivity.this, DispatchActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
        //       Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity(intent);
    }
}




  /*  public void setUsername(String username){
        this.username=username;
    }

    public void setPassword(String password){
        this.password=password;
    }

    ParseUser user = new ParseUser();
    user.setUsername("my name");
    user.setPassword("my pass");
    user.setEmail("email@example.com");

// other fields can be set just like with ParseObject
    user.put("phone", "650-253-0000");

    user.signUpInBackground(new SignUpCallback() {
        public void done(ParseException e) {
            if (e == null) {
                // Hooray! Let them use the app now.
            } else {
                // Sign up didn't succeed. Look at the ParseException
                // to figure out what went wrong
            }
        }
    }*/

