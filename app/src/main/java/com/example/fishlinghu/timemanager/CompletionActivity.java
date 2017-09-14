package com.example.fishlinghu.timemanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CompletionActivity extends AppCompatActivity {

    private String[] Month = {
            "",
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
    };

    private DatabaseReference reference;
    private FirebaseUser GoogleUser;
    private String AccountEmail;

    private TextView textView_congrat;
    private Button button_save;
    private Spinner spinner_category;
    private EditText editText_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion);

        GoogleUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        AccountEmail = GoogleUser.getEmail();

        // set the congratulation text
        textView_congrat = findViewById(R.id.textView_congrat);
        textView_congrat.setText("Congrats! You just focused for n minutes");

        // hide other category input by default
        editText_category = findViewById(R.id.editText_category);
        editText_category.setVisibility(View.GONE);

        // add default categories to the spinner
        spinner_category = findViewById(R.id.spinner_category);
        String[] category_array = getResources().getStringArray(R.array.category_array);
        ArrayList<String> category_arrayList = new ArrayList<String>(Arrays.asList(category_array));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, category_arrayList);
        // add categories that have been added by the user
        adapter.add("Temp!");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_category.setAdapter(adapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_category = spinner_category.getSelectedItem().toString();
                if (Objects.equals("Other", selected_category)) {
                    // let user create a new category
                    editText_category.setVisibility(View.VISIBLE);
                } else {
                    editText_category.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                String category;
                if (!editText_category.getText().toString().isEmpty()) {
                    // use the user defined category
                    category = editText_category.getText().toString();
                    // Toast.makeText(getApplicationContext(), category, Toast.LENGTH_LONG).show();
                } else {
                    // use the selected category
                    category = spinner_category.getSelectedItem().toString();
                }
                // store the tomato with selected category
                int duration = getIntent().getIntExtra("duration", 0);
                Tomato new_tomato = new Tomato(category, duration);

                org.joda.time.LocalDate joda_date = new org.joda.time.LocalDate();
                int month_idx = joda_date.getMonthOfYear();
                int day_of_month = joda_date.getDayOfMonth();

                String tomato_key = reference.child("users")
                        .child( AccountEmail.replace(".",",") )
                        .child("tomatoes")
                        .child(Month[month_idx])
                        .child(String.valueOf(day_of_month)).push().getKey();
                reference.child("users")
                        .child( AccountEmail.replace(".",",") )
                        .child("tomatoes")
                        .child(Month[month_idx])
                        .child(String.valueOf(day_of_month))
                        .child(tomato_key)
                        .setValue(new_tomato);

                startActivity(new Intent(CompletionActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
