package com.example.fishlinghu.timemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class CompletionActivity extends AppCompatActivity {

    private TextView textView_congrat;
    private Button button_save;
    private Spinner spinner_category;
    private EditText editText_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion);

        // set the congratulation text
        textView_congrat = findViewById(R.id.textView_congrat);
        textView_congrat.setText("Congrats! You just focused for n minutes");

        // hide other category input by default
        editText_category = findViewById(R.id.editText_category);
        editText_category.setVisibility(View.GONE);

        // add default categories to the spinner
        spinner_category = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        // add categories that have been added by the user
        // adapter.add("Temp!"); fucking BUG
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editText_category.getText().toString().isEmpty()) {
                    // use the user defined category
                    String new_category = editText_category.getText().toString();
                    Toast.makeText(getApplicationContext(), new_category, Toast.LENGTH_LONG).show();
                } else {
                    // use the selected category
                }
                // store the tomato with selected category
                startActivity(new Intent(CompletionActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
