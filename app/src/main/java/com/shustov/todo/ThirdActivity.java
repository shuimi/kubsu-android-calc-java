package com.shustov.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent mIntent = getIntent();
        setTitle(mIntent.getStringExtra("in_param"));

        ((Button) findViewById(R.id.buttonThird1)).setOnClickListener(y -> {
            Intent intent = new Intent();
            intent.putExtra("param", ((EditText) findViewById(R.id.editTextThird1)).getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("param", ((EditText) findViewById(R.id.editTextThird1)).getText().toString());
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }
}