package batch9.upcomingdevelopers.com.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ViewMedicalHistoryActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    ListView listViewViewMedicalHistory;
    MedicalHistoryDataSource medicalHistoryDataSource;
    ArrayList<MedicalHistoryModel> medicalHistoryModelList;
    MedicalHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_history);

        listViewViewMedicalHistory = (ListView) findViewById(R.id.listViewViewMedicalHistory);
        medicalHistoryDataSource = new MedicalHistoryDataSource(getApplicationContext());
        medicalHistoryModelList = medicalHistoryDataSource.getAllMedicalHistory(ViewProfileActivity.userId);
        if ((medicalHistoryModelList.size() < 1)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning");
            builder.setMessage("No Medical History has been added. Please add a new Medical History.");
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

                    Intent intent = new Intent(getBaseContext(), AddMedicalHistoryActivity.class);
                    startActivity(intent);

                    builder.setCancelable(false);
                    dialog.cancel();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
        else {
            adapter = new MedicalHistoryAdapter(getBaseContext(), medicalHistoryModelList);
            listViewViewMedicalHistory.setAdapter(adapter);
            listViewViewMedicalHistory.setOnItemLongClickListener(this);
        }

    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
        final MedicalHistoryModel medicalHistoryModel = medicalHistoryModelList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setIcon(R.drawable.warning);
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


                        int confirmation = medicalHistoryDataSource.delete(medicalHistoryModel.getMedicalHistoryId());
                        if (confirmation > 0) {
                            medicalHistoryModelList.remove(position);
                            adapter.notifyDataSetChanged();

                            if(medicalHistoryModel.getMedicalHistoryImageFilePath().length() > 0) {
                                File photoFile = new File(medicalHistoryModel.getMedicalHistoryImageFilePath());
                                boolean confirmDelete = photoFile.delete();
                                if (confirmDelete) {
                                    Toast.makeText(getApplicationContext(), "Image File Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed To Delete Image File", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Toast.makeText(getApplicationContext(), "Medical History deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "delete Unsuccessfully", Toast.LENGTH_SHORT).show();
                        }

                        dialog.cancel();
                    }
                });

        builder.setNegativeButton("Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), UpdateMedicalHistoryActivity.class);
                        intent.putExtra(DatabaseHelper.MEDICAL_HISTORY_COL_ID, medicalHistoryModel.getMedicalHistoryId());
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder.create().show();
        return false;
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
            Intent intent = new Intent(getBaseContext(), AddMedicalHistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
