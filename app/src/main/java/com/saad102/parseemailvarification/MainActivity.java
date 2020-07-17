package com.saad102.parseemailvarification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextMail, mEditTextUserName, mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextMail = findViewById(R.id.edtMail);
        mEditTextUserName = findViewById(R.id.edtUserName);
        mEditTextPassword = findViewById(R.id.edtPassword);

        ParseInstallation.getCurrentInstallation().saveInBackground();




    }
    public void signUpIsPressed(View view){
        try {
            // Reset errors
            // Sign up with Parse
            ParseUser user = new ParseUser();
            user.setUsername(mEditTextUserName.getText().toString());
            user.setPassword(mEditTextPassword.getText().toString());
            user.setEmail(mEditTextMail.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseUser.logOut();
                        alertDisplayer("Account Created Successfully!", "Please verify your email before Login", false);
                    } else {
                        ParseUser.logOut();
                        alertDisplayer("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void alertDisplayer(String title,String message, final boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}