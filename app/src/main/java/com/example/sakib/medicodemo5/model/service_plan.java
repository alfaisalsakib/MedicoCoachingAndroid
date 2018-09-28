package com.example.sakib.medicodemo5.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.sakib.medicodemo5.MainActivity;
import com.example.sakib.medicodemo5.R;
import com.github.barteksc.pdfviewer.PDFView;

public class service_plan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_plan);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("serviceplan.pdf").load();
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if ((KeyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent intent = new Intent(service_plan.this, MainActivity.class);
            startActivity(intent);
            service_plan.this.finish();
            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }
}
