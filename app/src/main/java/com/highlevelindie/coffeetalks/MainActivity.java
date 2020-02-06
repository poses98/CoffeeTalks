package com.highlevelindie.coffeetalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.highlevelindie.coffeetalks.Model.user.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText emailTxt;
    private EditText passTxt;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTxt = findViewById(R.id.email);
        passTxt = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        Button btnSignUp = findViewById(R.id.btnSignup);
        Button btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(this, ChatList.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnlogin:
                LogInUser();
                break;
            case R.id.btnSignup:
                startActivity(new Intent(this,Registro.class));
        }
    }

    private void LogInUser() {
        String email = emailTxt.getText().toString();
        String password = passTxt.getText().toString();
        User user = new User(email,password);
        user.SignIn(this);
    }
}
