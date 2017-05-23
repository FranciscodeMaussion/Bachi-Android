package com.frandemo.bachi;

import android.app.DatePickerDialog;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateAlumnoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText myEditText;
    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_alumno);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.activity_alumno_create));
        myEditText = (EditText) findViewById(R.id.dialog_alumno_fecha);
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
                    showDatePickerDialog(v);
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
                EditText nombre = (EditText) findViewById(R.id.dialog_alumno_nombre);
                EditText fecha = (EditText) findViewById(R.id.dialog_alumno_fecha);
                EditText escolar = (EditText) findViewById(R.id.dialog_alumno_escolar);
                Alumno newAlumno = new
                        Alumno(nombre.getText().toString(),
                        fecha.getText().toString(),
                        Integer.parseInt(escolar.getText().toString()));
                mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mFirebaseDatabaseReference.child("alumnos")
                        .push().setValue(newAlumno);
                //TODO close activity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DateDialog newFragment = new DateDialog();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        /* store the values selected into a Calendar instance*/
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(c.getTime());
        myEditText.setText(date);
    }
}
