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

public class ReminderActivity extends AppCompatActivity {
    Ringtone ringtone;
    TextView typeReminderActivityTV;
    TextView menuReminderActivityTV;
    TextView userReminderActivityTV;
    Intent intent;
    private Window wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wind = getWindow();

        wind.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
        wind.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        wind.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
        wind.addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_reminder);
        setFinishOnTouchOutside(false);



        intent = getIntent();

        typeReminderActivityTV = (TextView) findViewById(R.id.typeReminderActivityTV);
        menuReminderActivityTV = (TextView) findViewById(R.id.menuReminderActivityTV);
        userReminderActivityTV = (TextView) findViewById(R.id.userReminderActivityTV);

        String userName = "User: " + intent.getStringExtra("user");
        String menuType = intent.getStringExtra("type") + " Menu";

        userReminderActivityTV.setText(userName);
        typeReminderActivityTV.setText(menuType);
        menuReminderActivityTV.setText(intent.getStringExtra("menu"));

        Uri toneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getBaseContext(), toneUri);
        ringtone.play();
    }

    public void cancelReminder(View view) {
        ringtone.stop();
        finish();
    }
}
