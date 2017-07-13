package com.example.namwan.firebaseauthfacebooklogin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView uidTextView;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        uidTextView = (TextView) findViewById(R.id.uidTextView);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            // User is signed in to Firebase, but we can only get
            // basic info like name, email, and profile photo url
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            String photoUrl = user.getPhotoUrl().toString();

            //Log.d("photoUrlTag",photoUrl);

            /*
            // Even a user's provider-specific profile information
            // only reveals basic information
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();
                // UID specific to the provider
                String profileUid = profile.getUid();
                // Name, email address, and profile photo Url
                String profileDisplayName = profile.getDisplayName();
                String profileEmail = profile.getEmail();
                Uri profilePhotoUrl = profile.getPhotoUrl();
            }
            */

            Picasso.with(this).load(photoUrl).into(profilePicture);
            nameTextView.setText(name);
            emailTextView.setText(email);
            uidTextView.setText(uid);
        }else {
            goLoginScreen();
        }
    }

    private void goLoginScreen(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
}
