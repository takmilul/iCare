package batch9.upcomingdevelopers.com.icare;

import android.content.Intent;
import android.os.Bundle;
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

public class UpdateDoctorActivity extends AppCompatActivity {
    private EditText nameUpdateDoctorEt;
    private EditText detailUpdateDoctorEt;
    private TextView appointmentUpdateDoctorTv;
    private EditText phoneUpdateDoctorEt;
    private EditText emailUpdateDoctorEt;
    private DoctorDataSource doctorDataSource;
    private int doctorId;
    private DoctorModel doctorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);

        doctorId = getIntent().getIntExtra(DatabaseHelper.DOCTOR_COL_ID, 0);
        doctorDataSource = new DoctorDataSource(getApplicationContext());

        nameUpdateDoctorEt = (EditText) findViewById(R.id.nameUpdateDoctorEt);
        detailUpdateDoctorEt = (EditText) findViewById(R.id.detailUpdateDoctorEt);
        appointmentUpdateDoctorTv = (TextView) findViewById(R.id.datePickerUpdateDoctorTv);
        phoneUpdateDoctorEt = (EditText) findViewById(R.id.phoneUpdateDoctorEt);
        emailUpdateDoctorEt = (EditText) findViewById(R.id.emailUpdateDoctorEt);

        doctorModel = doctorDataSource.singleDoctorInfo(doctorId);

        nameUpdateDoctorEt.setHint( doctorModel.getDoctorName());
        detailUpdateDoctorEt.setHint( doctorModel.getDoctorDetail());
        appointmentUpdateDoctorTv.setHint( doctorModel.getDoctorAppointment());
        phoneUpdateDoctorEt.setHint( doctorModel.getDoctorPhone());
        emailUpdateDoctorEt.setHint( doctorModel.getDoctorEmail());
    }

    public void datePicker(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment(appointmentUpdateDoctorTv);
        datePickerFragment.show(getSupportFragmentManager(), "date");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.okBtn) {
            String name = nameUpdateDoctorEt.getText().toString();
            String detail = detailUpdateDoctorEt.getText().toString();
            String appointment = appointmentUpdateDoctorTv.getText().toString();
            String phone = phoneUpdateDoctorEt.getText().toString();
            String email = emailUpdateDoctorEt.getText().toString();

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

            if (name.length() == 0) {
                name = doctorModel.getDoctorName();
            }
            if (detail.length() == 0) {
                detail = doctorModel.getDoctorDetail();
            }
            if (phone.length() == 0) {
                phone = doctorModel.getDoctorPhone();
            }
            if (email.length() == 0) {
                email = doctorModel.getDoctorEmail();
            }
            if (appointment.length() == 0) {
                appointment = doctorModel.getDoctorAppointment();
            }else if(chosenTime.before(currentTime)){
                Toast.makeText(getApplicationContext(), "Past date is not allowed!", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }

            doctorModel = new DoctorModel(name, detail, appointment, phone, email, 0);
            int confirmation = doctorDataSource.updateDoctor(doctorId, doctorModel);

            if (confirmation > 0) {
                Toast.makeText(getBaseContext(), "Doctor Info Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Doctor Info is not Updated", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), ViewDoctorsChartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
