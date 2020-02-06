package com.highlevelindie.coffeetalks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.highlevelindie.coffeetalks.Model.chat.Chat;
import com.highlevelindie.coffeetalks.Model.chat.Message;

public class ChatList extends AppCompatActivity {
    private FirebaseListAdapter<Chat> myAdapter;
    private String channel_no,channel_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        listMessages();
        addListListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        channel_no = "";
        channel_name = "";
    }

    private void addListListener() {
        final ListView listOfChats = (ListView)findViewById(R.id.chat_list);
        listOfChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView chatName = view.findViewById(R.id.chat_name);
                channel_name = chatName.getText().toString().trim();
                LaunchActivity();
            }
        });
    }

    private void LaunchActivity() {
        FirebaseDatabase.getInstance().getReference().child("chats").addListenerForSingleValueEvent
                (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot s:dataSnapshot.getChildren()){
                    if(channel_name.equals(s.child("title").getValue().toString())){
                        channel_no = s.getKey().toString();
                    }

                }
                Intent intent = new Intent(ChatList.this,ChatView.class);
                intent.putExtra("channel_name",channel_no);
                startActivity(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newchat:
                startActivity(new Intent(this,NewChat.class));
                break;
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            default:

        }
        return super.onOptionsItemSelected(item);

    }
    public void listMessages(){
        // Recogemos en el objeto listOfMessages el elemento de ListView de la vista
        ListView listOfMessages = (ListView)findViewById(R.id.chat_list);
        // Le ponemos de contexto a Firebase la actividad
        Firebase.setAndroidContext(this);
        // Creamos un objeto Firebase al que le pasamos la URL de la base de datos
        Firebase mDatabase = new Firebase("https://coffeetalks-e4e57.firebaseio.com/");
        // Inicializamos la clase anónima FireBaseListAdapter pasando como parámetros la actividad, la clase
        // del modelo, el layout que tendran los items de la lista y por último la referencia de la
        // base de datos
        myAdapter = new FirebaseListAdapter<Chat>(this, Chat.class,R.layout.chats_layout,mDatabase.child("chats")) {

            @Override
            protected void populateView(View view, Chat chat, int i) {

                // Cogemos las referencias del layout que le hemos puesto para los items en objetos
                // del tipo TextView
                TextView messageText = (TextView)view.findViewById(R.id.message_text);
                TextView channelName = (TextView)view.findViewById(R.id.chat_name);
                TextView messageTime = (TextView)view.findViewById(R.id.message_time);

                // Asignamos su valor mediante setText
                messageText.setText(chat.getlastmsg());
                channelName.setText(chat.getTitle());

                // Formateamos la fecha antes de mostrarla
                messageTime.setText(DateFormat.format("HH:mm",
                        chat.getTimestamp()));
            }
        };
        // Asignamos el adapter a la lista
        listOfMessages.setAdapter(myAdapter);
    }


}
