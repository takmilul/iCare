package batch9.upcomingdevelopers.com.icare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateMedicalHistoryActivity extends AppCompatActivity {
    ImageView prescriptionUpdateMedicalHistoryImage;
    EditText doctorNameUpdateMedicalHistoryEt;
    EditText detailsUpdateMedicalHistoryEt;
    TextView datePickerUpdateMedicalHistoryTv;

    MedicalHistoryDataSource medicalHistoryDataSource;
    MedicalHistoryModel medicalHistoryModel;
    int historyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medical_history);

        prescriptionUpdateMedicalHistoryImage = (ImageView) findViewById(R.id.prescriptionUpdateMedicalHistoryImage);
        doctorNameUpdateMedicalHistoryEt = (EditText) findViewById(R.id.doctorNameUpdateMedicalHistoryEt);
        detailsUpdateMedicalHistoryEt = (EditText) findViewById(R.id.detailsUpdateMedicalHistoryEt);
        datePickerUpdateMedicalHistoryTv = (TextView) findViewById(R.id.datePickerUpdateMedicalHistoryTv);

        historyId = getIntent().getIntExtra(DatabaseHelper.MEDICAL_HISTORY_COL_ID, 0);

        medicalHistoryDataSource = new MedicalHistoryDataSource(getApplicationContext());
        medicalHistoryModel = medicalHistoryDataSource.getSingleMedicalHistory(historyId);

        Bitmap bitmap = BitmapFactory.decodeFile(medicalHistoryModel.getMedicalHistoryImageFilePath());

        prescriptionUpdateMedicalHistoryImage.setImageBitmap(bitmap);
        doctorNameUpdateMedicalHistoryEt.setHint(medicalHistoryModel.getMedicalHistoryDoctorName());
        detailsUpdateMedicalHistoryEt.setHint(medicalHistoryModel.getMedicalHistoryDetails());
        datePickerUpdateMedicalHistoryTv.setHint(medicalHistoryModel.getMedicalHistoryDate());
    }

    public void pickDate(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment(datePickerUpdateMedicalHistoryTv);
        datePickerFragment.show(getSupportFragmentManager(), "date");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.okBtn){
            String path = medicalHistoryModel.getMedicalHistoryImageFilePath();
            String doctorName = doctorNameUpdateMedicalHistoryEt.getText().toString();
            String details = detailsUpdateMedicalHistoryEt.getText().toString();
            String date = datePickerUpdateMedicalHistoryTv.getText().toString();

            Calendar chosenTime = Calendar.getInstance();
            Calendar currentTime = Calendar.getInstance();
            currentTime.set(Calendar.HOUR_OF_DAY, 0);
            currentTime.set(Calendar.MINUTE, 0);
            currentTime.set(Calendar.SECOND, 0);
            currentTime.set(Calendar.MILLISECOND, 0);

            Date dateAndTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m:s a");
            try {
                dateAndTime = sdf.parse(date + " " + "0:0:0 AM");
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
            }
            chosenTime.setTime(dateAndTime);

            if (doctorName.length() == 0) {
                doctorName = medicalHistoryModel.getMedicalHistoryDoctorName();
            }
            if (details.length() == 0) {
                details = medicalHistoryModel.getMedicalHistoryDetails();
            }
            if (date.length() == 0) {
                date = medicalHistoryModel.getMedicalHistoryDate();
            } else if(currentTime.before(chosenTime)){
                Toast.makeText(getApplicationContext(), "Future date is not allowed!", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }

            medicalHistoryModel = new MedicalHistoryModel(path, doctorName, details, date, 0);
            int confirmation = medicalHistoryDataSource.update(historyId, medicalHistoryModel);
            if (confirmation > 0) {
                Toast.makeText(getBaseContext(), "Medical History Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Medical History is not Updated", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), ViewMedicalHistoryActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
