package batch9.upcomingdevelopers.com.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewHealthCentersActivity extends AppCompatActivity {
    ListView hospitalListView;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_health_centers);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        hospitalListView = (ListView) findViewById(R.id.hospitalListView);
        ArrayList<HealthCenters> healthCenterList = HealthCenters.getAllHealthCenters();
        HospitalAdapter adapter = new HospitalAdapter(getBaseContext(), healthCenterList);
        hospitalListView.setAdapter(adapter);

        intent = new Intent(this, MapsActivity.class);
        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HealthCenters healthCenters = (HealthCenters) parent.getItemAtPosition(position);
                intent.putExtra("name", healthCenters.getName());
                intent.putExtra("lat", healthCenters.getLat());
                intent.putExtra("lon", healthCenters.getLon());

                startActivity(intent);
            }
        });
    }


}
