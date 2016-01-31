package batch9.upcomingdevelopers.com.icare;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private String time;
    private TextView timePicker;

    public TimePickerFragment() {
    }

    public TimePickerFragment(TextView timePicker){
        this.timePicker = timePicker;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar justTime = Calendar.getInstance();
        int hour = justTime.get(Calendar.HOUR_OF_DAY);
        int minute = justTime.get(Calendar.MINUTE);
        return new TimePickerDialog(getContext(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(hourOfDay>=12){
        time = String.valueOf(hourOfDay-12) + ":" + String.valueOf(minute) + " PM";
        }
        else {
        time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + " AM";
        }
        timePicker.setText(time);
    }
}
