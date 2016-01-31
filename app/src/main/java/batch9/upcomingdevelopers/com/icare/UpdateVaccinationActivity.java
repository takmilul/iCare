package batch9.upcomingdevelopers.com.icare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class UpdateVaccinationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private int reminder;
    private TextView detailUpdateVaccineTv;
    private EditText nameUpdateVaccineEt;
    private TextView dateUpdateVaccineTv;
    private TextView timeUpdateVaccineTv;
    private Switch reminderUpdateVaccineSwitch;
    private VaccinationDataSource vaccinationDataSource;
    private int vaccineId;
    private VaccinationModel vaccinationModel;

    String name;
    String date;
    String time;
    String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vaccination);

        vaccineId = getIntent().getIntExtra(DatabaseHelper.VACCINE_COL_ID, 0);
        vaccinationDataSource = new VaccinationDataSource(getApplicationContext());

        nameUpdateVaccineEt = (EditText) findViewById(R.id.nameUpdateVaccineEt);
        dateUpdateVaccineTv = (TextView) findViewById(R.id.datePickerUpdateVaccineTv);
        timeUpdateVaccineTv = (TextView) findViewById(R.id.timePickerUpdateVaccineTv);
        detailUpdateVaccineTv = (TextView) findViewById(R.id.detailUpdateVaccineEt);
        reminderUpdateVaccineSwitch = (Switch) findViewById(R.id.reminderUpdateVaccineSwitch);

        vaccinationModel = vaccinationDataSource.singleVaccine(vaccineId);

        nameUpdateVaccineEt.setHint(vaccinationModel.getVaccineName());
        dateUpdateVaccineTv.setHint( vaccinationModel.getVaccineDate());
        timeUpdateVaccineTv.setHint( vaccinationModel.getVaccineTime());
        detailUpdateVaccineTv.setHint( vaccinationModel.getVaccineDetail());

        reminder = vaccinationModel.getVaccineReminder();

        if (reminder == 0) {
            reminderUpdateVaccineSwitch.setChecked(false);
        } else {
            reminderUpdateVaccineSwitch.setChecked(true);
        }

        reminderUpdateVaccineSwitch.setOnCheckedChangeListener(this);
    }

    public void datePicker(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment(dateUpdateVaccineTv);
        datePickerFragment.show(getSupportFragmentManager(), "date");
    }

    public void timePicker(View view) {
        TimePickerFragment startTimePickerFragment = new TimePickerFragment(timeUpdateVaccineTv);
        startTimePickerFragment.show(getSupportFragmentManager(), "startTime");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.okBtn) {
            name = nameUpdateVaccineEt.getText().toString().replaceAll("( )+", " ").trim();
            date = dateUpdateVaccineTv.getText().toString();
            time = timeUpdateVaccineTv.getText().toString();
            detail = detailUpdateVaccineTv.getText().toString().replaceAll("( )+", " ").trim();

            if (name.length() != 0) {
                vaccinationModel.setVaccineName(name);
            }
            if (date.length() != 0) {
                vaccinationModel.setVaccineDate(date);
            }
            if (time.length() != 0) {
                vaccinationModel.setVaccineTime(time);
            }
            if(detail.length() != 0){
                vaccinationModel.setVaccineDetail(detail);
            }

            vaccinationModel.setVaccineReminder(reminder);

            int confirmation = vaccinationDataSource.updateVaccine(vaccineId, vaccinationModel);
            if (confirmation > 0) {
                Toast.makeText(getBaseContext(), "Vaccine Updated Successfully", Toast.LENGTH_SHORT).show();

                int hashedId = AddVaccinationActivity.hashToFarFromZero(vaccineId);
                if (AddDietActivity.alarmManager!= null) {
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

                    AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), hashedId, intent, PendingIntent.FLAG_NO_CREATE);
                    AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                    if (AddDietActivity.alarmIntent != null)
                        AddDietActivity.alarmIntent.cancel();
                }

                if(reminder == 1) {
                    Calendar calendar = Calendar.getInstance();
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

                    if(AddDietActivity.alarmManager == null)
                        AddDietActivity.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    Date reminderDate = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m a");
                    try {
                        reminderDate = sdf.parse( vaccinationModel.getVaccineDate() + " " + vaccinationModel.getVaccineTime());
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
                    }
                    calendar.setTime(reminderDate);
                    intent.putExtra("user", ViewProfileActivity.userName);

                    intent.putExtra("isReminder", 2);
                    intent.putExtra("vaccine", vaccinationModel.getVaccineName());
                    intent.putExtra("details", vaccinationModel.getVaccineDetail());
                    intent.putExtra("id", vaccineId);

                    AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), hashedId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AddDietActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - AlarmManager.INTERVAL_DAY, AddDietActivity.alarmIntent);
                }
            } else {
                Toast.makeText(getBaseContext(), "Vaccine is not Updated", Toast.LENGTH_SHORT).show();
            }
            Intent viewVaccinationIntent = new Intent(getApplicationContext(), ViewVaccinationChartActivity.class);
            startActivity(viewVaccinationIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            reminder = 1;
            Toast.makeText(getBaseContext(), "Reminder will be shown before 24 hour",Toast.LENGTH_LONG).show();
        } else {
            reminder = 0;
        }

    }
}
