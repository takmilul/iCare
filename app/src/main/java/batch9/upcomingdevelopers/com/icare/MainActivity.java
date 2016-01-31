package batch9.upcomingdevelopers.com.icare;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
   }

   public void addProfile(View view) {
      Intent intent = new Intent(getBaseContext(), AddProfileActivity.class);
      startActivity(intent);
   }

   public void viewProfile(View view) {
      Intent intent = new Intent(getBaseContext(), ViewProfileActivity.class);
      startActivity(intent);
   }

   public void viewHospitalList(View view) {
      Intent intent = new Intent(getBaseContext(), ViewHealthCentersActivity.class);
      startActivity(intent);
   }

   public void generalInfo(View view) {
      Intent intent = new Intent(getBaseContext(), GeneralInfoActivity.class);
      startActivity(intent);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_update_diet, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == R.id.action_settings) {
         finishAffinity();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

}
