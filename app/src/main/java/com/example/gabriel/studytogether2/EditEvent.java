package com.example.gabriel.studytogether2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditEvent extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date = null;
    private DatabaseAccess dbAccess = null;


    EditText tv_name;// = (EditText) findViewById(R.id.et_name);

    EditText et_startHour;// = (EditText) findViewById(R.id.et_start_hour);
    EditText et_startMinute;// = (EditText) findViewById(R.id.et_start_minute);
    Spinner spinner_start;// = (Spinner) findViewById(R.id.spinner_start_ampm);

    EditText et_endHour;// = (EditText) findViewById(R.id.et_end_hour);
    EditText et_endMinute;// = (EditText) findViewById(R.id.et_end_minute);
    Spinner spinner_end;// = (Spinner) findViewById(R.id.spinner_end_ampm);

    CheckBox repeat;// = (CheckBox) findViewById(R.id.cb_repeat);
    CheckBox sunday;// = (CheckBox) findViewById(R.id.cb_sun);
    CheckBox monday;// = (CheckBox) findViewById(R.id.cb_mon);
    CheckBox tuesday;// = (CheckBox) findViewById(R.id.cb_tue);
    CheckBox wednesday;// = (CheckBox) findViewById(R.id.cb_wed);
    CheckBox thursday;// = (CheckBox) findViewById(R.id.cb_thu);
    CheckBox friday;// = (CheckBox) findViewById(R.id.cb_fri);
    CheckBox saturday;// = (CheckBox) findViewById(R.id.cb_sat);

    EditText et_date;// = (EditText) findViewById(R.id.et_date);

    EditText et_notes;// = (EditText) findViewById(R.id.et_notes);



    private int startTimeHours, startTimeMinute, endTimeHour, endTimeMinute, year, month, day;
    private String name, notes;
    private boolean sun, mon, tues, wed, thurs, fri, sat, rep;

    private boolean busy = true;

    private boolean validDate = false;

    private WeekViewEvent wve;

    boolean editExisting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        editExisting = getIntent().getExtras().getBoolean("EDIT_EXISTING");

        dbAccess = new DatabaseAccess();

        tv_name = (EditText) findViewById(R.id.et_name);

        et_startHour = (EditText) findViewById(R.id.et_start_hour);
        et_startMinute = (EditText) findViewById(R.id.et_start_minute);
        spinner_start = (Spinner) findViewById(R.id.spinner_start_ampm);

        et_endHour = (EditText) findViewById(R.id.et_end_hour);
        et_endMinute = (EditText) findViewById(R.id.et_end_minute);
        spinner_end = (Spinner) findViewById(R.id.spinner_end_ampm);

        repeat = (CheckBox) findViewById(R.id.cb_repeat);
        sunday = (CheckBox) findViewById(R.id.cb_sun);
        monday = (CheckBox) findViewById(R.id.cb_mon);
        tuesday = (CheckBox) findViewById(R.id.cb_tue);
        wednesday = (CheckBox) findViewById(R.id.cb_wed);
        thursday = (CheckBox) findViewById(R.id.cb_thu);
        friday = (CheckBox) findViewById(R.id.cb_fri);
        saturday = (CheckBox) findViewById(R.id.cb_sat);

        et_date = (EditText) findViewById(R.id.et_date);

        et_notes = (EditText) findViewById(R.id.et_notes);

        //startSpinner = (Spinner) findViewById(R.id.spinner_start_ampm);
        //endSpinner = (Spinner) findViewById(R.id.spinner_end_ampm);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_am_pm,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_start.setAdapter(adapter);
        spinner_end.setAdapter(adapter);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        if (editExisting) {
            populateFields(getIntent().getExtras());
            //tv_name.setText(getIntent().getExtras().getString("NAME"));


        }
    }

    private void populateFields(Bundle bundle) {
        tv_name.setText(bundle.getString("NAME"));

        int startHour = bundle.getInt("START_HOUR");
        int startMinute = bundle.getInt("START_MINUTE");
        int endHour = bundle.getInt("END_HOUR");
        int endMinute = bundle.getInt("END_MINUTE");

        if (startHour > 12) {
            spinner_start.setSelection(1);
            startHour -= 12;
        }

        et_startHour.setText("" + startHour);
        et_startMinute.setText("" + startMinute);

        if (endHour > 12) {
            spinner_end.setSelection(1);
            endHour -= 12;
        }

        //calendar.set;

        et_endHour.setText("" + endHour);
        et_endMinute.setText("" + endMinute);

        int tempYear = bundle.getInt("YEAR");
        int tempMonth = bundle.getInt("MONTH");
        int tempDay = bundle.getInt("DAY");

        calendar.set(Calendar.YEAR, tempYear);
        calendar.set(Calendar.MONTH, tempMonth);
        calendar.set(Calendar.DAY_OF_MONTH, tempDay);

        updateLabel();

        //et_date.setText(tempMonth + "/" + tempDay + "/" + tempYear);
        //et_notes.setText("to be implemented with database");

        //RadioButton rb = (RadioButton) findViewById(R.id.radio_busy);
        //RadioButton rf = (RadioButton) findViewById(R.id.radio_free);

    }

    public void showDatePickerDialog(View v) {
        if (editExisting) {
            new DatePickerDialog(EditEvent.this, date,
                    getIntent().getExtras().getInt("YEAR"),
                    getIntent().getExtras().getInt("MONTH"),
                    getIntent().getExtras().getInt("DAY")).show();
        } else {
            new DatePickerDialog(EditEvent.this, date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void updateLabel() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sfd = new SimpleDateFormat(dateFormat, Locale.US);
        EditText date = (EditText) findViewById(R.id.et_date);
        date.setText(sfd.format(calendar.getTime()));
    }

    public void submitEvent(View view) {
        populateDateVariables();

        if (validateVariables()) {

            wve = new WeekViewEvent(100, name, year, month, day, startTimeHours, startTimeMinute, year, month, day, endTimeHour, endTimeMinute);

            if (busy) {
                wve.setColor(Color.rgb(239, 147, 147));
            }

            EditEnvelope ee = new EditEnvelope();

            String tempBusy = "N";
            if (busy)
                tempBusy = "Y";


            if (editExisting) {
               // ee.updateEvent(wve);
                DBMediumUpdate dbmu = new DBMediumUpdate();
                dbmu.update(name, "" + year + "-" + month + "-" + day, "M", String.format("%02d", startTimeHours) + ":" + String.format("%02d", startTimeMinute) + ":" + "00", String.format("%02d", endTimeHour) + ":" + String.format("%02d", endTimeMinute) + ":" + "00", tempBusy, notes, getIntent().getExtras().getLong("ID"));
            } else {
              //  ee.addEvent(wve);
                DBMediumInsert dbmi = new DBMediumInsert();
                dbmi.insert(name, "" + year + "-" + month + "-" + day, "M", String.format("%02d", startTimeHours) + ":" + String.format("%02d", startTimeMinute) + ":" + "00", String.format("%02d", endTimeHour) + ":" + String.format("%02d", endTimeMinute) + ":" + "00", tempBusy, notes);
                //dbAccess.insertNewWeekViewEvent(name, "" + year + "-" + month + "-" + day, "M", String.format("%02d", startTimeHours) + ":" + String.format("%02d", startTimeMinute) + ":" + "00", String.format("%02d", endTimeHour) + ":" + String.format("%02d", endTimeMinute) + ":" + "00", tempBusy, notes);
            }
            // Intent intent = new Intent(this, MainActivity.class);

            //startActivity(intent);

            finish();
        } else {
            Context context = view.getContext();
            CharSequence text = "Invalid input";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private boolean validateVariables() {
        int tempStartHours = startTimeHours;
        int tempEndHours = endTimeHour;

        //Spinner spinner_start = (Spinner) findViewById(R.id.spinner_start_ampm);
        //Spinner spinner_end = (Spinner) findViewById(R.id.spinner_end_ampm);


        if (spinner_start.getSelectedItem().toString().equals("PM")) {
            tempStartHours -= 12;
        }
        if (tempStartHours % 12 == 0)
            tempStartHours -= 12;

        if (spinner_end.getSelectedItem().toString().equals("PM")) {
            tempEndHours -= 12;
        }
        if (tempEndHours % 12 == 0)
            tempEndHours -= 12;


        if (tempStartHours < 0 || tempStartHours > 12 ||
                tempEndHours < 0 || tempEndHours > 12) {
            return false;
        }

        if (startTimeMinute < 0 || startTimeMinute > 59 ||
                endTimeMinute < 0 || endTimeMinute > 59) {
            return false;
        }

        if (!validDate) {
            return false;
        }

        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_busy:
                if (checked)
                    busy = true;
                    break;
            case R.id.radio_free:
                if (checked)
                    busy = false;
                    break;
        }
    }

    private void populateDateVariables(){
        // TODO: CURRENTLY NOT CHECKING FOR EMPTY FIELDS
        // TODO: POSSIBLE FIXES:
        // TODO:    1. MAKE FIELDS REQUIRED.
        // TODO:    2. CHECK FOR NULL AT EACH ASSIGNMENT.

        try {

            name = tv_name.getText().toString();

            startTimeHours = Integer.parseInt(et_startHour.getText().toString());
            startTimeMinute = Integer.parseInt(et_startMinute.getText().toString());
            if (spinner_start.getSelectedItem().toString().equals("PM")) {
                startTimeHours += 12;
            }
            if (startTimeHours % 12 == 0)
                startTimeHours -= 12;

            endTimeHour = Integer.parseInt(et_endHour.getText().toString());
            endTimeMinute = Integer.parseInt(et_endMinute.getText().toString());
            if (spinner_end.getSelectedItem().toString().equals("PM")) {
                endTimeHour += 12;
            }
            if (endTimeHour % 12 == 0)
                endTimeHour -= 12;

            parseDate(et_date.getText().toString());

            rep = repeat.isChecked();
            sun = sunday.isChecked();
            mon = monday.isChecked();
            tues = tuesday.isChecked();
            wed = wednesday.isChecked();
            thurs = thursday.isChecked();
            fri = friday.isChecked();
            sat = saturday.isChecked();

            notes = et_notes.getText().toString();
        } catch (Exception e) {
           /* Context context = getBaseContext();
            CharSequence text = "Invalid input";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();*/

           //toast will be shown later
        }
    }

    private void parseDate(String date) {
        String[] split_date = date.split("/");
        if (split_date.length < 3) {
            validDate = false;
        } else {
            month = Integer.parseInt(split_date[0]);
            day = Integer.parseInt(split_date[1]);
            year = Integer.parseInt("20" + split_date[2]);
            validDate = true;
        }
    }
}
