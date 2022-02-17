package com.shustov.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle bundle = getIntent().getExtras();

        String s = bundle.getString("Text");
        setTitle(bundle.getString("Text"));

        createTestLog("Second Activity", "Получено сообщение от главной активности: " + s);
        createTestLog("Second Activity", "create");
    }


    private void createTestLog(String activityName, String message) {
        Log.d(
                getString(R.string.loggerTagTest),
                activityName + ": " + message
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        createTestLog("Second Activity", "start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        createTestLog("Second Activity", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        createTestLog("Second Activity", "destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        createTestLog("Second Activity", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        createTestLog("Second Activity", "resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        createTestLog("Second Activity", "restart");
    }

}