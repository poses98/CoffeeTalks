package com.highlevelindie.coffeetalks.Model.user;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.highlevelindie.coffeetalks.ChatList;
import com.highlevelindie.coffeetalks.ChatView;

public class DaoUser extends AppCompatActivity {
    boolean check;

    public boolean SignUp(final String nickname, String email, String password, Activity v){
        final Activity activity = v;
        final User user = new User(nickname,email,password);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Setting the nickname as displayname for the new user
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nickname).build();
                    task.getResult().getUser().updateProfile(profileUpdates);
                    activity.finish();
                }else{
                    check = false;
                }
            }
        });

        return check;
    }

    public boolean SignIn(String email,String password,Activity v){
        final Activity activity = v;
        final User user = new User(email,password);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                activity.startActivity(new Intent(activity, ChatList.class));
                activity.finish();
            }
        });
        return false;
    }
}
