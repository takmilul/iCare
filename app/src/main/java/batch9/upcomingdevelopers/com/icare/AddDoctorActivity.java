package batch9.upcomingdevelopers.com.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDoctorActivity extends AppCompatActivity {

    private EditText nameAddDoctorEt;
    private EditText detailAddDoctorEt;
    private EditText phoneAddDoctorEt;
    private EditText emailAddDoctorEt;
    private TextView appointmentAddDoctorTv;
    private DoctorDataSource doctorDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        nameAddDoctorEt = (EditText) findViewById(R.id.nameAddDoctorEt);
        detailAddDoctorEt = (EditText) findViewById(R.id.detailAddDoctorEt);
        phoneAddDoctorEt = (EditText) findViewById(R.id.phoneAddDoctorEt);
        emailAddDoctorEt = (EditText) findViewById(R.id.emailAddDoctorEt);
        appointmentAddDoctorTv = (TextView) findViewById(R.id.datePickerAddDoctorTv);

        doctorDataSource = new DoctorDataSource(getApplicationContext());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void datePicker(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment(appointmentAddDoctorTv);
        datePickerFragment.show(getSupportFragmentManager(), "date");
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
            String name = nameAddDoctorEt.getText().toString();
            String detail = detailAddDoctorEt.getText().toString();
            String appointment = appointmentAddDoctorTv.getText().toString();
            String phone = phoneAddDoctorEt.getText().toString();
            String email = emailAddDoctorEt.getText().toString();

            if(name.length() == 0||detail.length() == 0||appointment.equals("Pick Appointment Date")||phone.length()==0|| email.length() == 0){
                Toast.makeText(getApplicationContext(), "All information are required!", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }


            Calendar chosenTime = Calendar.getInstance();
            Calendar currentTime = Calendar.getInstance();
            currentTime.set(Calendar.HOUR_OF_DAY, 0);
            currentTime.set(Calendar.MINUTE, 0);
            currentTime.set(Calendar.SECOND, 0);
            currentTime.set(Calendar.MILLISECOND, 0);


            Date dateAndTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m:s a");
            try {
                dateAndTime = sdf.parse(appointment + " " + "0:0:0 AM");
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
            }
            chosenTime.setTime(dateAndTime);

            if(chosenTime.before(currentTime)){
                Toast.makeText(getApplicationContext(), "Past date is not allowed!", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }

            DoctorModel doctorModel = new DoctorModel(name, detail, appointment, phone, email, ViewProfileActivity.userId);
            confirmation = doctorDataSource.insertDoctor(doctorModel);

            if (confirmation > 0) {
                Toast.makeText(getBaseContext(), "Data inserted successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), ViewDoctorsChartActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Data not inserted", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
