package com.project.gvcall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth auth;

    EditText codeBox;
    Button joinBtn, shareBtn;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        auth = FirebaseAuth.getInstance();

        codeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.createBtn);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        URL serverURL;

        try {
            serverURL = new URL("httpsS://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL).setWelcomePageEnabled(false).build();

            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder().
                        setRoom(codeBox.getText().toString()).setWelcomePageEnabled(false).build();

                JitsiMeetActivity.launch(DashboardActivity.this,options);
            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(DashboardActivity.this, "Home",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        auth.signOut();
                        finish();
                        startActivity(new Intent(DashboardActivity.this,
                                LoginActivity.class));
                        break;
                }
                return true;
            }
        });
    }
}