package batch9.upcomingdevelopers.com.icare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddProfileActivity extends AppCompatActivity implements TextWatcher {

   private EditText userNameEt;
   private EditText userAgeEt;
   private EditText userHeightEt;
   private EditText userWeightEt;
   private UserProfileDataSource dataSource;
   private boolean nameCheck;
   private boolean ageCheck;
   private boolean heightCheck;
   private boolean weightCheck;
   private ArrayList<String> user = new ArrayList<>();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_profile);

      userNameEt = (EditText) findViewById(R.id.nameAddProfileEt);
      userAgeEt = (EditText) findViewById(R.id.ageAddProfileEt);
      userHeightEt = (EditText) findViewById(R.id.heightAddProfileEt);
      userWeightEt = (EditText) findViewById(R.id.weightAddProfileEt);
      dataSource = new UserProfileDataSource(getApplicationContext());

      ArrayList<UserProfileModel> allUser = dataSource.getAllProfile();
      if (allUser.size() != 0) {
         for (UserProfileModel profileModel : allUser) {
            user.add(profileModel.getUserName());
         }
      }

      userNameEt.addTextChangedListener(this);
      userAgeEt.addTextChangedListener(this);
      userHeightEt.addTextChangedListener(this);
      userWeightEt.addTextChangedListener(this);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == R.id.okBtn) {

         String userName = userNameEt.getText().toString().replaceAll("( )+", " ").trim();
         String userAge = userAgeEt.getText().toString();
         String userHeight = userHeightEt.getText().toString();
         String userWeight = userWeightEt.getText().toString();
         UserProfileModel userProfileModel = new UserProfileModel(userName, userAge, userHeight, userWeight);

         if (userName.length() > 0 && userAge.length() > 0 && userHeight.length() > 0 && userWeight.length() > 0) {

            if (nameCheck && ageCheck && heightCheck && weightCheck) {
               long confirmation = dataSource.insert(userProfileModel);
               if(confirmation > 0) {
                  Intent intent = new Intent(getBaseContext(), ViewProfileActivity.class);
                  startActivity(intent);
               } else {
                  Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_LONG).show();
               }
            } else {
               Toast.makeText(getApplicationContext(), "Properly fill all the fields", Toast.LENGTH_LONG).show();
            }
         } else {
            Toast.makeText(getApplicationContext(), "All Fields Required", Toast.LENGTH_LONG).show();
         }
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {

   }

   @Override
   public void afterTextChanged(Editable s) {
      View view = getCurrentFocus();
      if (view == userNameEt) {
            String name = s.toString().replaceAll("( )+", " ").trim();
            if (user.size() > 0) {
               for (String userList : user) {
                  if (name.compareTo(userList) == 0) {
                     userNameEt.setError("User exist. Add a different user");

                     nameCheck = false;
                     break;
                  } else {
                     nameCheck = true;
                  }
               }
            } else {
               nameCheck = true;
            }

      }
      if (view == userAgeEt) {
         String age = s.toString();
         if (age.length() > 0) {
            if (Integer.parseInt(age) > 200) {
               userAgeEt.setError("Age must not be greater than 200");
               ageCheck = false;
            } else {
               ageCheck = true;
            }
         }
      }
      if (view == userHeightEt) {
         String height = s.toString();
         if (height.length() > 0) {
            if (Integer.parseInt(height) > 120) {
               userHeightEt.setError("Height must not be greater than 120 Inch");
               heightCheck = false;
            } else {
               heightCheck = true;
            }
         }
      }
      if (view == userWeightEt) {
         String weight = s.toString();
         if (weight.length() > 0) {
            if (Integer.parseInt(weight) > 350) {
               userWeightEt.setError("Weight must not be greater than 350 Kg");
               weightCheck = false;
            } else {
               weightCheck = true;
            }
         }
      }
   }
}
