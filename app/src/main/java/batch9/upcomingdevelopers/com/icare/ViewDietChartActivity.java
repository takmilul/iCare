package batch9.upcomingdevelopers.com.icare;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

public class ViewDietChartActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

   private TextView todaysDietChartTv;
   private TextView upcomingDietChartTv;
   private ListView todaysDietChartLv;
   private ListView upcomingDietChartLv;
   private ArrayList<DietModel> upcomingDietModelArrayList;
   private ArrayList<DietModel> todaysDietModelArrayList;
   private DietModel dietModel;
   private DietDataSource dietDataSource;
   private DietAdapter todaysDietAdapter;
   private DietAdapter upcomingDietAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_view_diet_chart);
      todaysDietChartTv = (TextView) findViewById(R.id.todaysDietChartTv);
      upcomingDietChartTv = (TextView) findViewById(R.id.upcomingDietChartTv);
      todaysDietChartLv = (ListView) findViewById(R.id.todaysDietChartLv);
      upcomingDietChartLv = (ListView) findViewById(R.id.upcomingDietChartLv);

      long time = System.currentTimeMillis();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String date = dateFormat.format(time);
      dietDataSource = new DietDataSource(getApplicationContext());
      todaysDietModelArrayList = dietDataSource.getTodaysDiet(ViewProfileActivity.userId, date);
      upcomingDietModelArrayList = dietDataSource.getUpcomingDiet(ViewProfileActivity.userId, date);

      if ((todaysDietModelArrayList.size() < 1) && (upcomingDietModelArrayList.size() < 1)) {
         final AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Warning");
         builder.setMessage("No Diet has been added. Please add a new Diet.");
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

               Intent intent = new Intent(getBaseContext(), AddDietActivity.class);
               startActivity(intent);

               builder.setCancelable(false);
               dialog.cancel();
            }
         });
         builder.setCancelable(false);
         builder.create().show();
      }
      if (todaysDietModelArrayList.size() > 0) {
         todaysDietChartTv.setText("Today's Chart");
         todaysDietAdapter = new DietAdapter(getBaseContext(), todaysDietModelArrayList);
         todaysDietChartLv.setAdapter(todaysDietAdapter);
      }
      if (upcomingDietModelArrayList.size() > 0) {
         upcomingDietChartTv.setText("Upcoming Chart");
         upcomingDietAdapter = new DietAdapter(getBaseContext(), upcomingDietModelArrayList);
         upcomingDietChartLv.setAdapter(upcomingDietAdapter);
      }
      todaysDietChartLv.setOnItemLongClickListener(this);
      upcomingDietChartLv.setOnItemLongClickListener(this);
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
         Intent intent = new Intent(getBaseContext(), AddDietActivity.class);
         startActivity(intent);
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
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

      builder.setNeutralButton("Delete",new OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {

                    int dietId = 0;
                    int confirmation;

                    if (parent == todaysDietChartLv) {
                       dietId = todaysDietModelArrayList.get(position).getDietId();
                       confirmation = dietDataSource.delete(dietId);
                       if (confirmation > 0) {
                          todaysDietModelArrayList.remove(position);
                          todaysDietAdapter.notifyDataSetChanged();
                          if (todaysDietModelArrayList.size() < 1)
                             todaysDietChartTv.setText("");
                          Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                       } else {
                          Toast.makeText(getApplicationContext(), "Delete Unsuccessful", Toast.LENGTH_SHORT).show();
                       }
                    }
                    if (parent == upcomingDietChartLv) {
                       dietId = upcomingDietModelArrayList.get(position).getDietId();
                       confirmation = dietDataSource.delete(dietId);
                       if (confirmation > 0) {
                          upcomingDietModelArrayList.remove(position);
                          upcomingDietAdapter.notifyDataSetChanged();
                          if (upcomingDietModelArrayList.size() < 1)
                             upcomingDietChartTv.setText("");
                          Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                       } else {
                          Toast.makeText(getApplicationContext(), "Delete Unsuccessful", Toast.LENGTH_SHORT).show();
                       }
                    }

                    dialog.cancel();

                    if (AddDietActivity.alarmManager != null) {
                       Intent intent = new Intent(ViewDietChartActivity.this.getApplicationContext(), AlarmReceiver.class);

                       AddDietActivity.alarmIntent = PendingIntent.getBroadcast(ViewDietChartActivity.this.getApplicationContext(), dietId, intent, PendingIntent.FLAG_NO_CREATE);
                       AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                       if (AddDietActivity.alarmIntent != null)
                          AddDietActivity.alarmIntent.cancel();

                       AddDietActivity.alarmIntent = PendingIntent.getBroadcast(ViewDietChartActivity.this.getApplicationContext(), -(dietId), intent, PendingIntent.FLAG_NO_CREATE);
                       AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                       if (AddDietActivity.alarmIntent != null)
                          AddDietActivity.alarmIntent.cancel();
                    }
                 }
              }
              );

      builder.setNegativeButton("Update",new OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(getApplicationContext(), UpdateDietActivity.class);
                    if (parent == todaysDietChartLv)
                       intent.putExtra(DatabaseHelper.DIET_COL_ID, todaysDietModelArrayList.get(position).getDietId());
                    if (parent == upcomingDietChartLv)
                       intent.putExtra(DatabaseHelper.DIET_COL_ID, upcomingDietModelArrayList.get(position).getDietId());
                    startActivity(intent);
                    dialog.cancel();
                 }
              }
      );

      builder.create().show();
      return false;
   }
}

