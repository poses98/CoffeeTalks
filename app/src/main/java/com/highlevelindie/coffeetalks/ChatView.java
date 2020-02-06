package com.highlevelindie.coffeetalks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.highlevelindie.coffeetalks.Model.chat.Message;



public class ChatView extends AppCompatActivity implements View.OnClickListener{
    private FirebaseListAdapter<Message> myAdapter;
    private EditText message;
    private String channel_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Button send = findViewById(R.id.send);
        send.setOnClickListener(this);
        message = findViewById(R.id.message);
        channel_name = this.getIntent().getStringExtra("channel_name");
        listMessages();
    }
    @Override
    public void onClick(View v) {
        if(!message.getText().toString().isEmpty()){
            sendMessage();
        }
    }
    public void listMessages(){
        // Recogemos en el objeto listOfMessages el elemento de ListView de la vista
        ListView listOfMessages = (ListView)findViewById(R.id.message_list);
        // Le ponemos de contexto a Firebase la actividad
        Firebase.setAndroidContext(this);
        // Creamos un objeto Firebase al que le pasamos la URL de la base de datos
        Firebase mDatabase = new Firebase("https://coffeetalks-e4e57.firebaseio.com/");
        // Inicializamos la clase anónima FireBaseListAdapter pasando como parámetros la actividad, la clase
        // del modelo, el layout que tendran los items de la lista y por último la referencia de la
        // base de datos
        myAdapter = new FirebaseListAdapter<Message>(ChatView.this, Message.class,R.layout.message_list,mDatabase.child("messages").child(channel_name)) {

            @Override
            protected void populateView(View view, Message message, int i) {
                RelativeLayout layout = view.findViewById(R.id.layout);

                if(message.getName().equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())){
                    layout.setBackground(getResources().getDrawable(R.drawable.buttonshape_green));
                }else{
                    layout.setBackground(getResources().getDrawable(R.drawable.buttonshape_blue));
                }

                // Cogemos las referencias del layout que le hemos puesto para los items en objetos
                // del tipo TextView
                TextView messageText = (TextView)view.findViewById(R.id.message_text);
                TextView messageUser = (TextView)view.findViewById(R.id.message_user);
                TextView messageTime = (TextView)view.findViewById(R.id.message_time);

                // Asignamos su valor mediante setText
                messageText.setText(message.getMessage());
                messageUser.setText(message.getName());

                // Formateamos la fecha antes de mostrarla
                messageTime.setText(DateFormat.format("HH:mm",
                       message.getTime()));
            }
        };
        // Asignamos el adapter a la lista
        listOfMessages.setAdapter(myAdapter);
    }

    private void sendMessage() {
        // Creamos una variable de tipo String que contiene el mensaje a enviar
        final String msg = message.getText().toString();
        // Creamos una instancia de la base de datos
        FirebaseDatabase.getInstance()
                .getReference() // Cogemos la referencia
                .child("messages") // Accedemos al nodo que nos interesa
                .child(channel_name)
                .push() // Realizamos un push que genera automáticamente la clave del nodo
                .setValue(new Message(FirebaseAuth.getInstance()  // Por último le pasamos el modelo
                                .getCurrentUser()
                                .getDisplayName(),msg)
                );
        message.setText(""); // Reseteamos el input field para que se vacíe
    }

    @Override
    public void onBackPressed() {
        Log.d("backpressed","se ha presionado");
        finish();

    }
}

//  ANTIGUOS MÉTODOS
/*
        mDatabase.child("messages").child(channel_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot s : dataSnapshot.getChildren()){
                        Message log_message = new Message();
                        log_message.setName(s.child("name").getValue().toString());
                        log_message.setMessage(s.child("message").getValue().toString());
                        map.put(""+(map.size()),log_message);
                    }
                    map.put(""+(map.size()),final_message);
                    mDatabase.child("messages").child(channel_name).setValue(map);
                }else{
                    map.put(""+(map.size()),final_message);
                    mDatabase.child("messages").child(channel_name).setValue(map);
                }
                listMessages();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        final ListView listView = (ListView) findViewById(R.id.message_list);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final ArrayList<String> messagelst = new ArrayList<String>();

        mDatabase.child("messages").child(channel_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot msg : dataSnapshot.getChildren()) {
                        Message log_message = new Message();
                        log_message.setName(msg.child("name").getValue().toString());
                        log_message.setMessage(msg.child("message").getValue().toString());
                        String defmen = log_message.getName()+":\n"+log_message.getMessage();
                        messagelst.add(defmen);
                    }
                    listView.setAdapter(new ArrayAdapter(ChatView.this, R.layout.message_list, messagelst));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

         */