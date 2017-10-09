package com.example.gabriel.studytogether2;

import android.app.DatePickerDialog;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

public class EditEvent extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Spinner startSpinner = (Spinner) findViewById(R.id.spinner_start_ampm);
        Spinner endSpinner = (Spinner) findViewById(R.id.spinner_end_ampm);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_am_pm,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startSpinner.setAdapter(adapter);
        endSpinner.setAdapter(adapter);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

    }

    public void showDatePickerDialog(View v) {
        new DatePickerDialog(EditEvent.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
