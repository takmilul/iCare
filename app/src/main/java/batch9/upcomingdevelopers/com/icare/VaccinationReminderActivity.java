package batch9.upcomingdevelopers.com.icare;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class VaccinationReminderActivity extends AppCompatActivity {
    TextView userVaccinationReminderActivityTV;
    TextView vaccineVaccinationReminderActivityTV;
    TextView detailsVaccinationReminderActivityTV;
    Intent intent;
    Ringtone ringtone;
    private Window wind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wind = getWindow();

        wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
        wind.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_vaccination_reminder);



        intent = getIntent();

        userVaccinationReminderActivityTV = (TextView) findViewById(R.id.userVaccinationReminderActivityTV);
        vaccineVaccinationReminderActivityTV = (TextView) findViewById(R.id.vaccineVaccinationReminderActivityTV);
        detailsVaccinationReminderActivityTV = (TextView) findViewById(R.id.detailsVaccinationReminderActivityTV);

        String userName = "User: " + intent.getStringExtra("user");
        String vaccineName = "Vaccine: " + intent.getStringExtra("vaccine");

        userVaccinationReminderActivityTV.setText(userName);
        vaccineVaccinationReminderActivityTV.setText(vaccineName);
        detailsVaccinationReminderActivityTV.setText(intent.getStringExtra("details"));

        Uri toneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getBaseContext(), toneUri);
        ringtone.play();
    }

    public void cancelVaccinationReminder(View view) {
        ringtone.stop();
        finish();
    }
}
