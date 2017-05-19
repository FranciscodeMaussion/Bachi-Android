package com.frandemo.bachi;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CreateAlumnoActivity extends AppCompatActivity {
    private CreateAlumnoActivity helper = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_alumno);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText myEditText = (EditText) findViewById(R.id.dialog_alumno_fecha);
        myEditText.setInputType(InputType.TYPE_NULL);
        myEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DateDialog.show(this);
            }
        });
        myEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog.show(helper);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alumno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_yes:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void calendarViewShow(View view) {
        DateDialog.show(this);
    }
}
