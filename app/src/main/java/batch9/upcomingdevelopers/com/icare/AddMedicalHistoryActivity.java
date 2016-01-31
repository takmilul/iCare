package batch9.upcomingdevelopers.com.icare;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddMedicalHistoryActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    File currentPhoto;
    ImageView captureAddMedicalHistoryImage;
    ImageView prescriptionAddMedicalHistoryImage;
    TextView datePickerAddMedicalHistoryTv;
    EditText doctorNameAddMedicalHistoryEt;
    EditText detailsAddMedicalHistoryEt;
    MedicalHistoryDataSource medicalHistoryDataSource;

    static String path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_history);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        currentPhoto = null;

        prescriptionAddMedicalHistoryImage = (ImageView) findViewById(R.id.prescriptionAddMedicalHistoryImage);
        if(savedInstanceState != null && currentPhoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhoto.getAbsolutePath());
            prescriptionAddMedicalHistoryImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            prescriptionAddMedicalHistoryImage.setImageBitmap(bitmap);
        }
        captureAddMedicalHistoryImage = (ImageView) findViewById(R.id.captureAddMedicalHistoryImage);
        datePickerAddMedicalHistoryTv = (TextView) findViewById(R.id.datePickerAddMedicalHistoryTv);
        doctorNameAddMedicalHistoryEt = (EditText) findViewById(R.id.doctorNameAddMedicalHistoryEt);
        detailsAddMedicalHistoryEt = (EditText) findViewById(R.id.detailsAddMedicalHistoryEt);

        medicalHistoryDataSource = new MedicalHistoryDataSource(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhoto.getAbsolutePath());
            prescriptionAddMedicalHistoryImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            prescriptionAddMedicalHistoryImage.setImageBitmap(bitmap);

        } else if(requestCode == REQUEST_TAKE_PHOTO){
            //Toast.makeText(this, "Result not returned", Toast.LENGTH_LONG).show();
            if(currentPhoto.delete()){
                //Toast.makeText(this, "Deleted previously created file", Toast.LENGTH_LONG).show();
                currentPhoto = null;
            } else {
                Toast.makeText(this, "Can't delete previously created file", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void captureImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this, "Can not create Image File", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        } else {
            Toast.makeText(this, "Can not resolve Activity", Toast.LENGTH_LONG).show();
        }

    }

    private File createImageFile() throws IOException {
        // Create an unique image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Delete previously created file if created
        if(currentPhoto != null){
            if(currentPhoto.delete()) { // Save a file for latter use
                currentPhoto = image;
            } else {
                Toast.makeText(this, "Can not delete previously created file", Toast.LENGTH_LONG).show();
            }
        } else {
            // Save a file for latter use
            currentPhoto = image;
        }
        path = currentPhoto.getAbsolutePath();
        return image;
    }

    public void datePicker(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment(datePickerAddMedicalHistoryTv);
        datePickerFragment.show(getSupportFragmentManager(), "date");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("path", path);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        long confirmation;
        if (item.getItemId() == R.id.okBtn) {

            String imageFilePath = "";
            if(currentPhoto != null)
                imageFilePath = currentPhoto.getAbsolutePath();
            String name = doctorNameAddMedicalHistoryEt.getText().toString();
            String details = detailsAddMedicalHistoryEt.getText().toString();
            String date = datePickerAddMedicalHistoryTv.getText().toString();

            if(imageFilePath.length() == 0 || name.length() == 0 ||details.length() == 0|| date.equals("Pick Date")){
                Toast.makeText(getApplicationContext(), "All information and photo are required!", Toast.LENGTH_LONG).show();
                return true;
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
                dateAndTime = sdf.parse(date + " " + "0:0:0 AM");
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
            }
            chosenTime.setTime(dateAndTime);
            if(currentTime.before(chosenTime)){
                Toast.makeText(getApplicationContext(), "Future date is not allowed!", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }

            MedicalHistoryModel medicalHistoryModel = new MedicalHistoryModel(imageFilePath, name, details, date, ViewProfileActivity.userId);
            confirmation = medicalHistoryDataSource.insert(medicalHistoryModel);

            if (confirmation > 0) {
                Toast.makeText(getBaseContext(), "Data inserted successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), ViewMedicalHistoryActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), "Data not inserted", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
