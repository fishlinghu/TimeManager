package com.example.fishlinghu.timemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseUser GoogleUser;
    private String AccountEmail;

    private EditText EditTextName;

    private Button ButtonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        GoogleUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        EditTextName = findViewById(R.id.editText_Name);

        ButtonDone = findViewById(R.id.button_done);

        AccountEmail = GoogleUser.getEmail();

        ButtonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                User NewUserData = new User(
                        EditTextName.getText().toString(),
                        GoogleUser.getPhotoUrl().toString()
                );

                reference.child("users").child( AccountEmail.replace(".",",") ).setValue(NewUserData);

                Toast.makeText(v.getContext(), "Account created", Toast.LENGTH_LONG).show();

                startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                finish();

            }
        });
    }
}
