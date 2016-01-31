package batch9.upcomingdevelopers.com.icare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddVaccinationActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener {

    private EditText addVaccineNameEt;
    private EditText addVaccineDetailEt;
    private TextView datePicker;
    private TextView timePicker;
    private Switch addVaccineReminderSwitch;
    private int reminder = 0;
    private VaccinationDataSource vaccinationDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vaccination);
        addVaccineNameEt = (EditText) findViewById(R.id.nameAddVaccineEv);
        addVaccineDetailEt = (EditText) findViewById(R.id.detailAddVaccineEv);
        datePicker = (TextView) findViewById(R.id.datePickerAddVaccineTv);
        timePicker = (TextView) findViewById(R.id.timePickerAddVaccineTv);
        addVaccineReminderSwitch = (Switch) findViewById(R.id.reminderAddVaccineSwitch);
        addVaccineReminderSwitch.setOnCheckedChangeListener(this);

        vaccinationDataSource = new VaccinationDataSource(getApplicationContext());
    }


    public void datePicker(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment(datePicker);
        datePickerFragment.show(getSupportFragmentManager(), "date");
    }

    public void timePicker(View view) {
        TimePickerFragment startTimePickerFragment = new TimePickerFragment(timePicker);
        startTimePickerFragment.show(getSupportFragmentManager(), "startTime");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        long confirmation;
        if (item.getItemId() == R.id.okBtn) {
            String name = addVaccineNameEt.getText().toString().replaceAll("( )+", " ").trim();
            String pickedDate = datePicker.getText().toString();
            String time = timePicker.getText().toString();
            String detail = addVaccineDetailEt.getText().toString().replaceAll("( )+", " ").trim();


            if(name.length()>0 && !pickedDate.equals("Pick Date") && !time.equals("Pick Time") && detail.length()>0){

                VaccinationModel vaccinationModel = new VaccinationModel(name, pickedDate, time, detail, reminder, ViewProfileActivity.userId);
                confirmation = vaccinationDataSource.insertVaccine(vaccinationModel);

                if (confirmation > 0) {
                    Toast.makeText(getBaseContext(), "Data inserted successfully", Toast.LENGTH_LONG).show();

                    if(reminder == 1){
                        Calendar calendar = Calendar.getInstance();
                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

                        if(AddDietActivity.alarmManager == null)
                            AddDietActivity.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m a");
                        try {
                            date = sdf.parse(pickedDate + " " + time);
                        } catch (ParseException e) {
                            Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
                        }
                        calendar.setTime(date);
                        intent.putExtra("user", ViewProfileActivity.userName);

                        intent.putExtra("isReminder", 2);
                        intent.putExtra("vaccine", name);
                        intent.putExtra("details", detail);

                        int rowId = (int) confirmation;
                        intent.putExtra("id", rowId);

                        AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), hashToFarFromZero(rowId), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AddDietActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - AlarmManager.INTERVAL_DAY, AddDietActivity.alarmIntent);
                    }
                    Intent intent = new Intent(getBaseContext(), ViewVaccinationChartActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Data not inserted", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getBaseContext(), "Please provide all data", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static int hashToFarFromZero(int i){
        final int MIN = -2147483648;
        final int MAX = 2147483647;
        int k;
        if(i%2 == 0){
            k = MAX - ((i/2) - 1);
        }else{
            k = MIN + (i/2);
        }
        return k;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            reminder = 1;
            Toast.makeText(getBaseContext(), "Remider will be shown before 24 hour",Toast.LENGTH_LONG).show();
        } else {
            reminder = 0;
        }
    }
}
