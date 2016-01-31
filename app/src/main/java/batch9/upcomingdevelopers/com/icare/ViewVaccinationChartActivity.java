package batch9.upcomingdevelopers.com.icare;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewVaccinationChartActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ListView completedVaccinationChartLv;
    private ListView upcomingVaccinationChartLv;
    private TextView completedVaccinationChartTv;
    private TextView upcomingVaccinationChartTv;
//    private ArrayList<VaccinationModel> vaccinationModelArrayList;
    private VaccinationDataSource vaccinationDataSource;
//    private VaccinationAdapter vaccinationAdapter;
    private VaccinationAdapter completedVaccinationAdapter;
    private VaccinationAdapter upcomingVaccinationAdapter;
    private ArrayList<VaccinationModel> completedVaccinationArrayList;
    private ArrayList<VaccinationModel> upcomingVaccinationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vaccination_chart);
        completedVaccinationChartTv = (TextView) findViewById(R.id.completedVaccinationChartTv);
        upcomingVaccinationChartTv = (TextView) findViewById(R.id.upcomingVaccinationChartTv);
        completedVaccinationChartLv = (ListView) findViewById(R.id.completedVaccinationChartLv);
        upcomingVaccinationChartLv = (ListView) findViewById(R.id.upcomingVaccinationChartLv);


        long time = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(time);
        vaccinationDataSource = new VaccinationDataSource(getApplicationContext());
        completedVaccinationArrayList = vaccinationDataSource.getCompletedVaccine(ViewProfileActivity.userId, date);
        upcomingVaccinationArrayList = vaccinationDataSource.getUpcomingVaccine(ViewProfileActivity.userId, date);

        if ((upcomingVaccinationArrayList.size() < 1) && (completedVaccinationArrayList.size() < 1)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning");
            builder.setMessage("No vaccination has been added. Please add a new vaccination.");
            builder.setIcon(R.drawable.info);
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            builder.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(getBaseContext(), AddVaccinationActivity.class);
                    startActivity(intent);

                    builder.setCancelable(false);
                    dialog.cancel();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
        if (upcomingVaccinationArrayList.size() > 0) {
            upcomingVaccinationChartTv.setText("Upcoming Chart");
            upcomingVaccinationAdapter = new VaccinationAdapter(getBaseContext(), upcomingVaccinationArrayList);
            upcomingVaccinationChartLv.setAdapter(upcomingVaccinationAdapter);
        }

        if (completedVaccinationArrayList.size() > 0) {
            completedVaccinationChartTv.setText("Completed Chart");
            completedVaccinationAdapter = new VaccinationAdapter(getBaseContext(), completedVaccinationArrayList);
            completedVaccinationChartLv.setAdapter(completedVaccinationAdapter);
        }

       /* else {
            Toast.makeText(getApplicationContext(), "Please Add Your Vaccine", Toast.LENGTH_SHORT).show();
        }*/
        upcomingVaccinationChartLv.setOnItemLongClickListener(this);
        completedVaccinationChartLv.setOnItemLongClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.addBtn) {
            Intent intent = new Intent(getBaseContext(), AddVaccinationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setIcon(android.R.drawable.sym_def_app_icon);
        builder.setMessage("Update or Delete");
        builder.setCancelable(true);

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNeutralButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int vaccineId = 0;
                        int confirmation;

                        if (parent == completedVaccinationChartLv) {
                            vaccineId = completedVaccinationArrayList.get(position).getVaccineId();
                            confirmation = vaccinationDataSource.delete(vaccineId);
                            if (confirmation > 0) {
                                completedVaccinationArrayList.remove(position);
                                completedVaccinationAdapter.notifyDataSetChanged();
                                if (completedVaccinationArrayList.size() < 1)
                                    completedVaccinationChartTv.setText("");
                                Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Delete Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (parent == upcomingVaccinationChartLv) {
                            vaccineId = upcomingVaccinationArrayList.get(position).getVaccineId();
                            confirmation = vaccinationDataSource.delete(vaccineId);
                            if (confirmation > 0) {
                                upcomingVaccinationArrayList.remove(position);
                                upcomingVaccinationAdapter.notifyDataSetChanged();
                                if (upcomingVaccinationArrayList.size() < 1)
                                    upcomingVaccinationChartTv.setText("");
                                Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Delete Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }

                        dialog.cancel();


                        int hashedId = AddVaccinationActivity.hashToFarFromZero(vaccineId);
                        if (AddDietActivity.alarmManager!= null) {
                            Intent intent = new Intent(ViewVaccinationChartActivity.this.getApplicationContext(), AlarmReceiver.class);

                            AddDietActivity.alarmIntent = PendingIntent.getBroadcast(ViewVaccinationChartActivity.this.getApplicationContext(), hashedId, intent, PendingIntent.FLAG_NO_CREATE);
                            AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                            if (AddDietActivity.alarmIntent != null)
                                AddDietActivity.alarmIntent.cancel();
                        }
                    }
                });

        builder.setNegativeButton("Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), UpdateVaccinationActivity.class);
                        if (parent == completedVaccinationChartLv)
                            intent.putExtra(DatabaseHelper.VACCINE_COL_ID, completedVaccinationArrayList.get(position).getVaccineId());
                        if (parent == upcomingVaccinationChartLv)
                            intent.putExtra(DatabaseHelper.VACCINE_COL_ID, upcomingVaccinationArrayList.get(position).getVaccineId());
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder.create().show();
        return false;
    }
}
