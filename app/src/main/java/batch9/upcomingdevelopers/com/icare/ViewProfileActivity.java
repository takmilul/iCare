package batch9.upcomingdevelopers.com.icare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewProfileActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

   private ListView viewProfile;
   private UserProfileDataSource dataSource;
   private ArrayList<UserProfileModel> userList;
   private UserProfileModel userInfo;
   static int userId;
   private ArrayList<String> profileName;
   private ArrayAdapter<String> profileAdapter;


   static String userName = "";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_view_profile);
      viewProfile = (ListView) findViewById(R.id.listViewViewProfile);

      dataSource = new UserProfileDataSource(getApplicationContext());
      userList = dataSource.getAllProfile();
      profileName = new ArrayList<>();

      if (userList.size() < 1) {
         final AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Warning");
         builder.setMessage("No profile has been added. Please add a new profile.");
         builder.setIcon(R.drawable.info);
         builder.setPositiveButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
               finish();
            }
         });
         builder.setNegativeButton("Add", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


               Intent intent = new Intent(getBaseContext(), AddProfileActivity.class);
               startActivity(intent);

               builder.setCancelable(false);
               dialog.cancel();
            }
         });
         builder.setCancelable(false);
         builder.create().show();
      }
      else {
         for (int i = 0; i < userList.size(); i++) {
            profileName.add(userList.get(i).getUserName());
         }

         profileAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, profileName);
         viewProfile.setAdapter(profileAdapter);
      }

      viewProfile.setOnItemClickListener(this);
      viewProfile.setOnItemLongClickListener(this);
   }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_add, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {

      int id = item.getItemId();
      if (id == R.id.addBtn) {
         Intent intent = new Intent(getBaseContext(), AddProfileActivity.class);
         startActivity(intent);
         return true;
      }

      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      userId = userList.get(position).getUserId();
      userName = userList.get(position).getUserName();
      userInfo = dataSource.singleProfile(userId);
      Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
      intent.putExtra("isDashboardActivityStarting", true);
      intent.putExtra("name", userName);
      intent.putExtra("age", userInfo.getUserAge());
      intent.putExtra("height", userInfo.getUserHeight());
      intent.putExtra("weight", userInfo.getUserWeight());
      startActivity(intent);
   }

   @Override
   public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
      userId = userList.get(position).getUserId();
      AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
      builder1.setTitle("Warning");
      builder1.setIcon(R.drawable.warning);
      builder1.setMessage("Update or Delete");
      builder1.setCancelable(true);

      builder1.setPositiveButton("Cancel", new OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
         }
      });

      builder1.setNeutralButton("Delete",
            new OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {

                  int confirmation = dataSource.delete(ViewProfileActivity.userId);
                  if (confirmation > 0) {
                     profileName.remove(position);
                     profileAdapter.notifyDataSetChanged();
                     Toast.makeText(getApplicationContext(), "User profile deleted successfully", Toast.LENGTH_SHORT).show();
                  } else {
                     Toast.makeText(getApplicationContext(), "delete Unsuccessfully", Toast.LENGTH_SHORT).show();
                  }

                  dialog.cancel();
               }
            });

      builder1.setNegativeButton("Update",
            new OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                  Intent intent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                  startActivity(intent);
                  dialog.cancel();
               }
            });

      builder1.create().show();
      return true;
   }
}
