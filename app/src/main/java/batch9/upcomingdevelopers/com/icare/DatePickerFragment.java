package batch9.upcomingdevelopers.com.icare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


   private TextView datePicker;
   private String date1;

   public DatePickerFragment(){}
   public DatePickerFragment(TextView datePicker) {
      this.datePicker = datePicker;
   }

   @NonNull
   @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
      Calendar justDate = Calendar.getInstance();
      int year = justDate.get(Calendar.YEAR);
      int month = justDate.get(Calendar.MONTH);
      int day = justDate.get(Calendar.DAY_OF_MONTH);

      return new DatePickerDialog(getContext(), this, year, month, day);
   }

   @Override
   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

      SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      Calendar calendar = Calendar.getInstance();
      calendar.set(year,monthOfYear,dayOfMonth);
      date1 = formatDate.format(calendar.getTime());
      datePicker.setText(date1);
   }
}
