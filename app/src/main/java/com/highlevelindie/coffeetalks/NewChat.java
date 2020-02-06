package com.highlevelindie.coffeetalks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.highlevelindie.coffeetalks.Model.chat.Chat;
import com.highlevelindie.coffeetalks.Model.chat.Message;

public class NewChat extends AppCompatActivity implements View.OnClickListener{

    private Button cancelar;
    private Button crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        cancelar = findViewById(R.id.cancelar);
        crear = findViewById(R.id.crear_canal);

        cancelar.setOnClickListener(this);
        crear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelar:
                finish();
                break;
            case R.id.crear_canal:
                CrearCanal();
                finish();
                break;

        }
    }

    private void CrearCanal() {
        EditText et = (EditText)findViewById(R.id.nombre_canal);
        String nombre = et.getText().toString();

        Chat chat = new Chat();
        chat.setTitle(nombre);
        chat.setlastmsg("SE HA CREADO EL CANAL DE CHAT: " + nombre);
        // Creamos una instancia de la base de datos
        FirebaseDatabase.getInstance()
                .getReference() // Cogemos la referencia
                .child("chats") // Accedemos al nodo que nos interesa
                .push() // Realizamos un push que genera automáticamente la clave del nodo
                .setValue(chat);

    }
}
