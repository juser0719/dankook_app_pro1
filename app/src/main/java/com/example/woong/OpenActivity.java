package com.example.woong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class OpenActivity extends AppCompatActivity {

    private Switch open;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        Intent intent = getIntent();
        open = (Switch)findViewById(R.id.switch2);
        auth = FirebaseAuth.getInstance();
        open.setOnCheckedChangeListener(new openSwitchListener());
    }
    class openSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {//open

                Toast.makeText(OpenActivity.this, "open!!", Toast.LENGTH_SHORT).show();
            }
            else {    //close
                Toast.makeText(OpenActivity.this, "closed!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
