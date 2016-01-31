package batch9.upcomingdevelopers.com.icare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateDietActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
   private int alarm;
   private int reminder;
   private int dietId;
   private DietModel dietModel;
   private TextView typeUpdateDietTv;
   private EditText menuUpdateDietEt;
   private TextView dateUpdateDietTv;
   private TextView timeUpdateDietTv;
   private Switch alarmUpdateDietSwitch;
   private Switch reminderUpdateDietSwitch;
   private DietDataSource dietDataSource;

   String menu;
   String date;
   String time;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_update_diet);

      dietId = getIntent().getIntExtra(DatabaseHelper.DIET_COL_ID, 0);
      dietDataSource = new DietDataSource(getApplicationContext());
      typeUpdateDietTv = (TextView) findViewById(R.id.typeUpdateDietTv);
      menuUpdateDietEt = (EditText) findViewById(R.id.menuUpdateDietEv);
      dateUpdateDietTv = (TextView) findViewById(R.id.datePickerUpdateDietTv);
      timeUpdateDietTv = (TextView) findViewById(R.id.timePickerUpdateDietTv);
      alarmUpdateDietSwitch = (Switch) findViewById(R.id.alarmUpdateDietSwitch);
      reminderUpdateDietSwitch = (Switch) findViewById(R.id.reminderUpdateDietSwitch);

      dietModel = dietDataSource.singleDietInfo(dietId);

      typeUpdateDietTv.setHint(dietModel.getDietType());
      menuUpdateDietEt.setHint(dietModel.getDietMenu());
      dateUpdateDietTv.setHint(dietModel.getDietDate());
      timeUpdateDietTv.setHint( dietModel.getDietTime());

      alarm = dietModel.isDietAlarm();
      reminder = dietModel.isDietReminder();

      if (alarm == 0) {
         alarmUpdateDietSwitch.setChecked(false);
      } else {
         alarmUpdateDietSwitch.setChecked(true);
      }
      if (reminder == 0) {
         reminderUpdateDietSwitch.setChecked(false);
      } else {
         reminderUpdateDietSwitch.setChecked(true);
      }

      alarmUpdateDietSwitch.setOnCheckedChangeListener(this);
      reminderUpdateDietSwitch.setOnCheckedChangeListener(this);
   }

   public void datePicker(View view) {
      DatePickerFragment datePickerFragment = new DatePickerFragment(dateUpdateDietTv);
      datePickerFragment.show(getSupportFragmentManager(), "date");
   }

   public void timePicker(View view) {
      TimePickerFragment startTimePickerFragment = new TimePickerFragment(timeUpdateDietTv);
      startTimePickerFragment.show(getSupportFragmentManager(), "startTime");
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
      return true;
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {

      if (item.getItemId() == R.id.okBtn) {
         menu = menuUpdateDietEt.getText().toString().replaceAll("( )+", " ").trim();
         date = dateUpdateDietTv.getText().toString();
         time = timeUpdateDietTv.getText().toString();
         if(time.length()==0)
            time = dietModel.getDietTime();

         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
         long timeLong = System.currentTimeMillis();
         Date currentDate = new Date(timeLong);
         String dates = dateFormat.format(currentDate);
         Date dietTime = new Date();
         try {
            dietTime = timeFormat.parse(date + " " + time);
         } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
         }

         if (date.compareTo(dates) >= 0) {
            boolean check = true;
            if (dietTime.before(currentDate))
               check = false;
            if (check) {

               if (menu.length() != 0) {
                  dietModel.setDietMenu(menu);
               }
               if (date.length() != 0) {
                  dietModel.setDietDate(date);
               }
               if (time.length() != 0) {
                  dietModel.setDietTime(time);
               }
               dietModel.setDietAlarm(alarm);
               dietModel.setDietReminder(reminder);
               int confirmation = dietDataSource.updateDiet(dietId, dietModel);

               if (confirmation > 0) {
                  Toast.makeText(getBaseContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                  if (AddDietActivity.alarmManager != null) {
                     Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

                     AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), dietId, intent, PendingIntent.FLAG_NO_CREATE);
                     AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                     if (AddDietActivity.alarmIntent != null)
                        AddDietActivity.alarmIntent.cancel();

                     AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), -(dietId), intent, PendingIntent.FLAG_NO_CREATE);
                     AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                     if (AddDietActivity.alarmIntent != null)
                        AddDietActivity.alarmIntent.cancel();
                  }

                  Calendar calendar = Calendar.getInstance();
                  Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                  if (alarm == 1 || reminder == 1) {
                     if (AddDietActivity.alarmManager == null)
                        AddDietActivity.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                     Date date = new Date();
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m a");
                     try {
                        date = sdf.parse(dietModel.getDietDate() + " " + dietModel.getDietTime());
                     } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
                     }
                     calendar.setTime(date);
                     intent.putExtra("user", ViewProfileActivity.userName);
                  }

                  if (alarm == 1) {
                     AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), dietId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                     AddDietActivity.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, AddDietActivity.alarmIntent);
                  }

                  if (reminder == 1) {
                     intent.putExtra("isReminder", 1);
                     intent.putExtra("type", dietModel.getDietType());
                     intent.putExtra("menu", dietModel.getDietMenu());
                     intent.putExtra("id", dietId);

                     AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), -(dietId), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                     AddDietActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AddDietActivity.alarmIntent);
                  }
               } else {
                  Toast.makeText(getBaseContext(), "Profile is not Updated", Toast.LENGTH_SHORT).show();
               }

               Intent intent = new Intent(getApplicationContext(), ViewDietChartActivity.class);
               startActivity(intent);
            } else {
               Toast.makeText(getBaseContext(), "Please provide a valid time", Toast.LENGTH_LONG).show();
            }

         } else {
            Toast.makeText(getBaseContext(), "Please provide a valid date", Toast.LENGTH_LONG).show();
         }





            /*if (menu.length() != 0) {
                dietModel.setDietMenu(menu);
            }
            if (date.length() != 0) {
                dietModel.setDietDate(date);
            }
            if (time.length() != 0) {
                dietModel.setDietTime(time);
            }

            dietModel.setDietAlarm(alarm);
            dietModel.setDietReminder(reminder);

            int confirmation = dietDataSource.updateDiet(dietId, dietModel);
            if (confirmation > 0) {
                Toast.makeText(getBaseContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();



                if (AddDietActivity.alarmManager!= null) {
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

                    AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), dietId, intent, PendingIntent.FLAG_NO_CREATE);
                    AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                    if(AddDietActivity.alarmIntent != null)
                        AddDietActivity.alarmIntent.cancel();

                    AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), -(dietId), intent, PendingIntent.FLAG_NO_CREATE);
                    AddDietActivity.alarmManager.cancel(AddDietActivity.alarmIntent);
                    if(AddDietActivity.alarmIntent != null)
                        AddDietActivity.alarmIntent.cancel();
                }

                Calendar calendar = Calendar.getInstance();
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                if(alarm == 1 || reminder == 1){
                    if(AddDietActivity.alarmManager == null)
                        AddDietActivity.alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m a");
                    try {
                        date = sdf.parse( dietModel.getDietDate()+ " " + dietModel.getDietTime());
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
                    }
                    calendar.setTime(date);
                    intent.putExtra("user", ViewProfileActivity.userName);
                }

                if(alarm == 1){
                    AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), dietId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AddDietActivity.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, AddDietActivity.alarmIntent);
                }

                if(reminder == 1){
                    intent.putExtra("isReminder", 1);
                    intent.putExtra("type", dietModel.getDietType());
                    intent.putExtra("menu", dietModel.getDietMenu());
                    intent.putExtra("id", dietId);

                    AddDietActivity.alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), -(dietId), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AddDietActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AddDietActivity.alarmIntent);
                }
            } else {
                Toast.makeText(getBaseContext(), "Profile is not Updated", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), ViewDietChartActivity.class);
            startActivity(intent);*/
      }

      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
      if (buttonView == alarmUpdateDietSwitch) {
         if (isChecked) {
            alarm = 1;
         } else {
            alarm = 0;
         }
      } else {
         if (isChecked) {
            reminder = 1;
         } else {
            reminder = 0;
         }
      }
   }
}
