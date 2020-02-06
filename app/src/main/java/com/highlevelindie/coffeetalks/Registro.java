package com.highlevelindie.coffeetalks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.highlevelindie.coffeetalks.Model.user.User;

public class Registro extends AppCompatActivity implements View.OnClickListener{
    private EditText nick;
    private EditText pass;
    private EditText email;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        progressDialog = new ProgressDialog(this);

        nick = findViewById(R.id.nickname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        Button btnSignup = findViewById(R.id.btnlogin);
        btnSignup.setOnClickListener(this);

        Button btnLogin = findViewById(R.id.btnSignup);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignup:
                finish();
                break;
            case R.id.btnlogin:
                SignUpUser();
                break;
        }
    }

    private void SignUpUser() {
        String mail = email.getText().toString();
        String password = pass.getText().toString();
        String nickname = nick.getText().toString();
        if(TextUtils.isEmpty(mail)){
            Toast.makeText(this,"Introduce un valor en email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Introduce un valor en contraseña",Toast.LENGTH_LONG).show();
            return;
        }
        if(password.length()<5){
            Toast.makeText(this,"Introduce una contraseña mayor o igual a 6 caracteres",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(nickname)){
            Toast.makeText(this,"Introduce un valor en contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User(nickname,mail,password);
        boolean check = user.SignUp(this);

        progressDialog.setMessage("Registrando usuario " + nickname);
        progressDialog.show();

        if(check){
            finish();
        }else{
            Toast.makeText(this,"Algo ha fallado...",Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }
}
