package batch9.upcomingdevelopers.com.icare;

import android.Manifest.permission;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewDoctorsChartActivity extends AppCompatActivity implements OnItemLongClickListener, OnItemClickListener {
   private ListView alertDialogLv;
   private ListView doctorsChartLv;
   private ArrayList<DoctorModel> doctorModelArrayList;
   private DoctorDataSource doctorDataSource;
   private DoctorAdapter doctorAdapter;
   final String[] list={"CALL", "SMS", "MAIL"};

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_view_doctors_chart);

      doctorsChartLv = (ListView) findViewById(R.id.doctorsChartLv);

      doctorDataSource = new DoctorDataSource(getApplicationContext());
      doctorModelArrayList = doctorDataSource.getAllDoctor(ViewProfileActivity.userId);
      if ((doctorModelArrayList.size() < 1)) {
         final AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Warning");
         builder.setMessage("No Doctor Info has been added. Please add a new Doctor Info.");
         builder.setIcon(R.drawable.info);
         builder.setPositiveButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
               finish();
            }
         });
         builder.setNegativeButton("Add", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               Intent intent = new Intent(getBaseContext(), AddDoctorActivity.class);
               startActivity(intent);

               builder.setCancelable(false);
               dialog.cancel();
            }
         });
         builder.setCancelable(false);
         builder.create().show();
      }
      else {
         doctorAdapter = new DoctorAdapter(getApplicationContext(), doctorModelArrayList);
         doctorsChartLv.setAdapter(doctorAdapter);
      }
      doctorsChartLv.setOnItemLongClickListener(this);
      doctorsChartLv.setOnItemClickListener(this);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_view_diet_chart, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {

      int id = item.getItemId();
      if (id == R.id.addDietBtn) {
         Intent intent = new Intent(getBaseContext(), AddDoctorActivity.class);
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Warning");
      builder.setIcon(R.drawable.warning);
      builder.setMessage("Update or Delete");
      builder.setCancelable(true);

      builder.setPositiveButton("Cancel", new OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
         }
      });

      builder.setNeutralButton("Delete",
              new OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {

                    int confirmation = doctorDataSource.delete(doctorModelArrayList.get(position).getDoctorId());
                    if (confirmation > 0) {
                       doctorModelArrayList.remove(position);
                       doctorAdapter.notifyDataSetChanged();
                       Toast.makeText(getApplicationContext(), "Doctor's Info deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                       Toast.makeText(getApplicationContext(), "delete Unsuccessfully", Toast.LENGTH_SHORT).show();
                    }

                    dialog.cancel();
                 }
              });

      builder.setNegativeButton("Update",
              new OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(), UpdateDoctorActivity.class);
                    intent.putExtra(DatabaseHelper.DOCTOR_COL_ID, doctorModelArrayList.get(position).getDoctorId());
                    startActivity(intent);
                    dialog.cancel();
                 }
              });

      builder.create().show();
      return true;
   }

   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      final String phone = ((TextView)view.findViewById(R.id.phoneViewDoctorTv)).getText().toString();
      final String email = ((TextView)view.findViewById(R.id.emailViewDoctorTv)).getText().toString();
      alertDialogLv = new ListView(this);
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.alert_dialog_listview_row, R.id.alertDialogTv, list);
      alertDialogLv.setAdapter(adapter);
      alertDialogLv.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(list[position] == "CALL"){
               Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
               if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                  return;
               }
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivityForResult(intent, 0);
            }

            if(list[position] == "SMS"){
               Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivityForResult(intent, 1);
            }

            if(list[position] == "MAIL"){
               Intent intent = new Intent(Intent.ACTION_SEND);
               intent.setType("message/rfc822");
               intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
               intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
               intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               Intent newIntent = Intent.createChooser(intent, "Send Email");
               newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivityForResult(newIntent, 2);
            }
         }
      });

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setView(alertDialogLv);
      builder.setCancelable(true);
      builder.create().show();


      /*Dialog dialog = builder.create();
      dialog.show();
      dialog.getWindow().setLayout(800,470);
      WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
      lp.copyFrom(alertDialogBuilder.getWindow().getAttributes());
      lp.height = 100;
      lp.width = 50;
      alertDialogBuilder.getWindow().setAttributes(lp);*/
   }
}
