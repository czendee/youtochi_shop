package com.tochi.RobotJUEGO;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin("SI");
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin("SI");
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     */
    private void attemptLogin(String va){
        Intent intent = new Intent(this, LoginActivity.class);

        EditText editText = (EditText) findViewById(R.id.password);
        String message = editText.getText().toString();

//    	ImageButton  mybutton = (ImageButton) findViewById(R.id.imageButton3);


        System.out.println("goToLogin2 - 1");


        RequestTaskValidaUsuarioAdministrador th=new RequestTaskValidaUsuarioAdministrador(this);//pass activity



        System.out.println("goToMonitorConfigWeb - 3");


        System.out.println("goToAddEdificio - 3");

        th.extraMessageUser=message;
        th.nextActivity= this;
        th.DuenoId=message;

        th.execute();

        //to test assume it is OK
    }
}
