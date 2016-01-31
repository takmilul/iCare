package batch9.upcomingdevelopers.com.icare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class BootReceiver extends BroadcastReceiver {
    DietDataSource dietDataSource;
    VaccinationDataSource vaccinationDataSource;
    UserProfileDataSource userProfileDataSource;
    ArrayList<DietModel> dietAlarmAndReminderList;
    ArrayList<VaccinationModel> vaccinationReminderList;
    Map<Integer, String> userNameMap;

    @Override
    public void onReceive(Context context, Intent bootIntent) {
        if (bootIntent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            dietDataSource = new DietDataSource(context.getApplicationContext());
            vaccinationDataSource = new VaccinationDataSource(context.getApplicationContext());

            dietAlarmAndReminderList = dietDataSource.getAllDietAlarmAndReminders();
            vaccinationReminderList = vaccinationDataSource.getAllVaccineReminder();

            if(dietAlarmAndReminderList.size() == 0 && vaccinationReminderList.size() == 0)
                return;


            userProfileDataSource = new UserProfileDataSource(context.getApplicationContext());
            userNameMap = userProfileDataSource.getAllUserNames();

            if(AddDietActivity.alarmManager == null)
                AddDietActivity.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Calendar alarmTime = Calendar.getInstance();
            Calendar currentTime = Calendar.getInstance();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d K:m a");

            if(dietAlarmAndReminderList.size() > 0) {
                for (DietModel dietModel : dietAlarmAndReminderList) {
                    try {
                        date = sdf.parse(dietModel.getDietDate() + " " + dietModel.getDietTime());
                    } catch (ParseException e) {
                        Toast.makeText(context.getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
                    }
                    alarmTime.setTime(date);

                    Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("user", userNameMap.get(dietModel.getDietForeignKey()));

                    int dietId = dietModel.getDietId();

                    if (dietModel.isDietAlarm() == 1) {
                        int hour = alarmTime.get(Calendar.HOUR_OF_DAY);
                        int minute = alarmTime.get(Calendar.MINUTE);

                        currentTime.setTimeInMillis(System.currentTimeMillis());
                        if (alarmTime.before(currentTime)) {
                            alarmTime.setTimeInMillis(System.currentTimeMillis());
                            alarmTime.set(Calendar.HOUR_OF_DAY, hour);
                            alarmTime.set(Calendar.MINUTE, minute);
                            alarmTime.set(Calendar.SECOND, 0);

                            currentTime.setTimeInMillis(System.currentTimeMillis());
                            if (alarmTime.before(currentTime)) {
                                alarmTime.add(Calendar.HOUR_OF_DAY, 24);
                            }
                        }
                        AddDietActivity.alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), dietId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AddDietActivity.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, AddDietActivity.alarmIntent);
                    }

                    if (dietModel.isDietReminder() == 1) {
                        intent.putExtra("isReminder", 1);
                        intent.putExtra("type", dietModel.getDietType());
                        intent.putExtra("menu", dietModel.getDietMenu());
                        intent.putExtra("id", dietId);

                        AddDietActivity.alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), -(dietId), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        AddDietActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), AddDietActivity.alarmIntent);
                    }
                }
            }

            if(vaccinationReminderList.size() > 0){
                for(VaccinationModel vaccinationModel: vaccinationReminderList){
                    try {
                        date = sdf.parse(vaccinationModel.getVaccineDate() + " " + vaccinationModel.getVaccineTime());
                    } catch (ParseException e) {
                        Toast.makeText(context.getApplicationContext(), "Can not parse Date from String", Toast.LENGTH_LONG).show();
                    }
                    alarmTime.setTime(date);


                    Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
                    intent.putExtra("user", userNameMap.get(vaccinationModel.getVaccineForeignKey()));
                    intent.putExtra("isReminder", 2);
                    intent.putExtra("vaccine", vaccinationModel.getVaccineName());
                    intent.putExtra("details", vaccinationModel.getVaccineDetail());

                    int vaccineId = vaccinationModel.getVaccineId();
                    intent.putExtra("id", vaccineId);

                    AddDietActivity.alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), AddVaccinationActivity.hashToFarFromZero(vaccineId), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AddDietActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis() - AlarmManager.INTERVAL_DAY, AddDietActivity.alarmIntent);
                }
            }
        }
    }
}
