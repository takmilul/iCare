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

public class AlarmActivity extends AppCompatActivity {
    Ringtone ringtone;
    TextView userAlarmActivityTV;
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

        setContentView(R.layout.activity_alarm);
        setFinishOnTouchOutside(false);



        intent = getIntent();

        userAlarmActivityTV = (TextView) findViewById(R.id.userAlarmActivityTV);

        String userName = "User: " + intent.getStringExtra("user");
        userAlarmActivityTV.setText(userName);

        Uri toneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getBaseContext(), toneUri);
        ringtone.play();
    }

    public void cancelAlarm(View view) {
        ringtone.stop();
        finish();
    }
}
