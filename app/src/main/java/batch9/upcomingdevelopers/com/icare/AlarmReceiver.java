package batch9.upcomingdevelopers.com.icare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    DietDataSource dietDataSource;
    VaccinationDataSource vaccinationDataSource;
    DietModel dietModel;
    VaccinationModel vaccinationModel;

    @Override
    public void onReceive(Context context, Intent intent) {

//        int id = intent.getIntExtra("id", 0);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(intent.getLongExtra("calender", 0L));
//
//        Calendar nextDate = Calendar.getInstance();
//        nextDate.setTimeInMillis(System.currentTimeMillis());
//        nextDate.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
//        nextDate.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
//
//        nextDate.add(Calendar.HOUR_OF_DAY, 24);
//
//        if(AddDietActivity.alarmManager == null)
//            AddDietActivity.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        Intent nextAlarmIntent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
//
//        nextAlarmIntent.putExtra("calender", nextDate.getTimeInMillis());
//        nextAlarmIntent.putExtra("id", id);
//        AddDietActivity.alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), id, nextAlarmIntent, 0);
//
//        AddDietActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, nextDate.getTimeInMillis(), AddDietActivity.alarmIntent);

        dietDataSource = new DietDataSource(context.getApplicationContext());
        vaccinationDataSource = new VaccinationDataSource(context.getApplicationContext());

        int isReminder = intent.getIntExtra("isReminder", 0);
        if(isReminder == 2){
            int id = intent.getIntExtra("id", 0);
            vaccinationModel = vaccinationDataSource.singleVaccine(id);
            vaccinationModel.setVaccineReminder(0);
            vaccinationDataSource.updateVaccine(id, vaccinationModel);

            Intent playVaccinationReminderIntent = new Intent(context, VaccinationReminderActivity.class);

            playVaccinationReminderIntent.putExtra("user", intent.getStringExtra("user"));
            playVaccinationReminderIntent.putExtra("vaccine", intent.getStringExtra("vaccine"));
            playVaccinationReminderIntent.putExtra("details", intent.getStringExtra("details"));

            playVaccinationReminderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(playVaccinationReminderIntent);
        } else if(isReminder == 1) {
            int id = intent.getIntExtra("id", 0);
            dietModel = dietDataSource.singleDietInfo(id);
            dietModel.setDietReminder(0);
            dietDataSource.updateDiet(id, dietModel);

            Intent playReminderIntent = new Intent(context, ReminderActivity.class);

            playReminderIntent.putExtra("user", intent.getStringExtra("user"));
            playReminderIntent.putExtra("type", intent.getStringExtra("type"));
            playReminderIntent.putExtra("menu", intent.getStringExtra("menu"));

            playReminderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(playReminderIntent);
        } else if(isReminder == 0){
            Intent playAlarmIntent = new Intent(context, AlarmActivity.class);

            playAlarmIntent.putExtra("user", intent.getStringExtra("user"));

            playAlarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(playAlarmIntent);

        }

    }
}
