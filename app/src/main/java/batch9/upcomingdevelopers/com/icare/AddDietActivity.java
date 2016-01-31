package batch9.upcomingdevelopers.com.icare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDietActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

   private EditText menuAddDietEt;
   private TextView datePickerAddDietTv;
   private TextView timePickerAddDietTv;
   private Switch alarmAddDietSwitch;
   private Switch reminderAddDietSwitch;
   private Spinner dietTypeSpnr;
   private String type;
   private int alarmAddDiet = 0;
   private int reminderAddDiet = 0;
   private String[] dietTypeList;
   private DietDataSource dietDataSource;

   public static AlarmManager alarmManager;
   public static PendingIntent alarmIntent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_diet);
      dietTypeSpnr = (Spinner) findViewById(R.id.dietTypeAddDietSpinner);
      menuAddDietEt = (EditText) findViewById(R.id.menuAddDietEv);
      datePickerAddDietTv = (TextView) findViewById(R.id.datePickerAddDietEt);
      timePickerAddDietTv = (TextView) findViewById(R.id.timePickerAddDietTv);
      alarmAddDietSwitch = (Switch) findViewById(R.id.alarmAddDietSwitch);
      reminderAddDietSwitch = (Switch) findViewById(R.id.reminderAddDietSwitch);
      dietTypeSpnr.setOnItemSelectedListener(this);

      if(alarmManager == null)
         alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

      alarmAddDietSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
               alarmAddDiet = 1;
            } else {
               alarmAddDiet = 0;
            }
         }
      });

      reminderAddDietSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
               reminderAddDiet = 1;
            } else {
               reminderAddDiet = 0;
            }
         }
      });

      dietDataSource = new DietDataSource(getApplicationContext());
      dietTypeList = new String[]{"Breakfast", "Lunch", "Dinner", "Snacks"};

      ArrayAdapter<String> dietType = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, dietTypeList);
      dietType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      dietTypeSpnr.setAdapter(dietType);
   }

   public void datePicker(View view) {
      DatePickerFragment datePickerFragment = new DatePickerFragment(datePickerAddDietTv);
      datePickerFragment.show(getSupportFragmentManager(), "date");
   }

   public void timePicker(View view) {
      TimePickerFragment startTimePickerFragment = new TimePickerFragment(timePickerAddDietTv);
      startTimePickerFragment.show(getSupportFragmentManager(), "startTime");
   }

   @Override
   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      type = dietTypeList[position];
   }

   @Override
   public void onNothingSelected(AdapterView<?> parent) {

   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      long idOfRow = 0;
      if (item.getItemId() == R.id.okBtn) {
         String menu = menuAddDietEt.getText().toString().replaceAll("( )+", " ").trim();
         String dateText = datePickerAddDietTv.getText().toString();
         String time = timePickerAddDietTv.getText().toString();

         if(menu.length()>0 && dateText.length()>0 && time.length()>0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            long timeLong = System.currentTimeMillis();
            Date currentDate = new Date(timeLong);
            String dates = dateFormat.format(currentDate);
            Date dietDate = new Date();

            try {
               dietDate = timeFormat.parse(dateText + " " + time);
            } catch (ParseException e) {
               Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
            }

            if (dateText.compareTo(dates) >= 0) {
               boolean check = true;
               if (dietDate.before(currentDate))
                  check = false;
               if (check) {
                  DietModel dietModel = new DietModel(type, menu, dateText, time, alarmAddDiet, reminderAddDiet, ViewProfileActivity.userId);
                  idOfRow = dietDataSource.insertDiet(dietModel);

                  if (idOfRow > 0) {
                     Toast.makeText(getBaseContext(), "Data inserted successfully", Toast.LENGTH_LONG).show();
                     int rowId = (int) idOfRow;
                     Calendar calendar = Calendar.getInstance();
                     Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                     if (alarmAddDiet == 1 || reminderAddDiet == 1) {
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m a");
                        try {
                           date = sdf.parse(datePickerAddDietTv.getText().toString() + " " + timePickerAddDietTv.getText().toString());
                        } catch (ParseException e) {
                           Toast.makeText(getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
                        }
                        calendar.setTime(date);
                        intent.putExtra("user", ViewProfileActivity.userName);
                     }

                     if (alarmAddDiet == 1) {
                        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), rowId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
                     }

                     if (reminderAddDiet == 1) {
                        intent.putExtra("isReminder", 1);
                        intent.putExtra("type", type);
                        intent.putExtra("menu", menu);
                        intent.putExtra("id", rowId);

                        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), -(rowId), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
                     }

                     Intent intent1 = new Intent(getBaseContext(), ViewDietChartActivity.class);
                     startActivity(intent1);
                  }
                  else {
                     Toast.makeText(getBaseContext(), "Data not inserted", Toast.LENGTH_LONG).show();
                  }
               } else {
                  Toast.makeText(getBaseContext(), "Please provide a valid time", Toast.LENGTH_LONG).show();
               }

            } else {
               Toast.makeText(getBaseContext(), "Please provide a valid date", Toast.LENGTH_LONG).show();
            }
         }
         else {
            Toast.makeText(getBaseContext(), "Please provide all information", Toast.LENGTH_LONG).show();
         }
      }
      return super.onOptionsItemSelected(item);
   }
}
