package batch9.upcomingdevelopers.com.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    private TextView showName;
    private TextView showHeight;
    private TextView showWeight;
    private TextView showAge;

    static String name;
    static String age;
    static String height;
    static String weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        showName = (TextView) findViewById(R.id.showName);
        showAge = (TextView) findViewById(R.id.showAge);
        showHeight = (TextView) findViewById(R.id.showHeight);
        showWeight = (TextView) findViewById(R.id.showWeight);


        Intent intent = getIntent();
        if(intent.getBooleanExtra("isDashboardActivityStarting", false)){
            name = intent.getStringExtra("name");
            age = intent.getStringExtra("age");
            height = intent.getStringExtra("height");
            weight = intent.getStringExtra("weight");
        }

        showName.setText(name);
        showAge.setText(age);
        showHeight.setText(height);
        showWeight.setText(weight);

    }

    public void viewDietChart(View view) {
        Intent intent = new Intent(getBaseContext(), ViewDietChartActivity.class);
        startActivity(intent);
    }

    public void viewDoctor(View view) {
        Intent intent = new Intent(getBaseContext(), ViewDoctorsChartActivity.class);
        startActivity(intent);
    }

    public void viewVaccinationChart(View view) {
        Intent intent = new Intent(getBaseContext(), ViewVaccinationChartActivity.class);
        startActivity(intent);
    }

    public void viewMedicalHistory(View view) {
        Intent intent = new Intent(getBaseContext(), ViewMedicalHistoryActivity.class);
        startActivity(intent);
    }

    public void viewHospitalList(View view) {
        Intent intent = new Intent(getBaseContext(), ViewHealthCentersActivity.class);
        startActivity(intent);
    }

    public void generalInfo(View view) {
        Intent intent = new Intent(getBaseContext(), GeneralInfoActivity.class);
        startActivity(intent);
    }
}
