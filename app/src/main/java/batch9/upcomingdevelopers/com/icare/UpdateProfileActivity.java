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

public class UpdateProfileActivity extends AppCompatActivity implements TextWatcher {
   private boolean nameCheck = true;
   private boolean ageCheck = true;
   private boolean heightCheck = true;
   private boolean weightCheck = true;
   private int confirmation;
   private String userName;
   private String userAge;
   private String userHeight;
   private String userWeight;
   private EditText nameUpdateProfileEt;
   private EditText ageUpdateProfileEt;
   private EditText heightUpdateProfileEt;
   private EditText weightUpdateProfileEt;
   private UserProfileModel userProfileModel;
   private ArrayList<String> user = new ArrayList<>();
   private UserProfileDataSource profileDataSource;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_update_profile);
      nameUpdateProfileEt = (EditText) findViewById(R.id.nameUpdateProfileEt);
      ageUpdateProfileEt = (EditText) findViewById(R.id.ageUpdateProfileEt);
      heightUpdateProfileEt = (EditText) findViewById(R.id.heightUpdateProfileEt);
      weightUpdateProfileEt = (EditText) findViewById(R.id.weightUpdateProfileEt);
      profileDataSource = new UserProfileDataSource(getApplicationContext());
      userProfileModel = profileDataSource.singleProfile(ViewProfileActivity.userId);

      ArrayList<UserProfileModel> allUser = profileDataSource.getAllProfile();
      if (allUser.size() > 0) {
         for (UserProfileModel profileModel : allUser) {
            user.add(profileModel.getUserName());
         }
      }

      nameUpdateProfileEt.setHint(userProfileModel.getUserName());
      ageUpdateProfileEt.setHint(userProfileModel.getUserAge());
      heightUpdateProfileEt.setHint( userProfileModel.getUserHeight());
      weightUpdateProfileEt.setHint(userProfileModel.getUserWeight());

      nameUpdateProfileEt.addTextChangedListener(this);
      ageUpdateProfileEt.addTextChangedListener(this);
      heightUpdateProfileEt.addTextChangedListener(this);
      weightUpdateProfileEt.addTextChangedListener(this);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.ok_action_bar_button, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == R.id.okBtn) {

         userName = nameUpdateProfileEt.getText().toString().replaceAll("( )+", " ").trim();
         userAge = ageUpdateProfileEt.getText().toString();
         userHeight = heightUpdateProfileEt.getText().toString();
         userWeight = weightUpdateProfileEt.getText().toString();
         if (userName.length() == 0) {
            userName = userProfileModel.getUserName();
         }
         if (userAge.length() == 0) {
            userAge = userProfileModel.getUserAge();
         }
         if (userHeight.length() == 0) {
            userHeight = userProfileModel.getUserHeight();
         }
         if (userWeight.length() == 0) {
            userWeight = userProfileModel.getUserWeight();
         }
         if(nameCheck && ageCheck && heightCheck && weightCheck){
            userProfileModel = new UserProfileModel(userName, userAge, userHeight, userWeight);
            confirmation = profileDataSource.update(ViewProfileActivity.userId, userProfileModel);
            if (confirmation > 0) {
               Toast.makeText(getBaseContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
               Toast.makeText(getBaseContext(), "Profile is not Updated", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getBaseContext(), ViewProfileActivity.class);
            startActivity(intent);
         }
         else {
            Toast.makeText(getApplicationContext(), "Properly fill all the fields", Toast.LENGTH_LONG).show();
         }
      }
      return super.onOptionsItemSelected(item);
   }
   
   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      
   }
   
   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count) {
      View view = getCurrentFocus();
      if (view == nameUpdateProfileEt) {
         String name = s.toString().replaceAll("( )+", " ").trim();
         if (name.length() > 0) {
            if (user.size() > 0) {
               for (String userList : user) {
                  if (name.compareTo(userList) == 0) {
                     nameUpdateProfileEt.setError("User exist. Add a different user");
                     nameCheck = false;
                     //Toast.makeText(getApplicationContext(),"matched: false",Toast.LENGTH_SHORT).show();
                     break;
                  } else {
                     nameCheck = true;
                     //Toast.makeText(getApplicationContext(),"not matched: true",Toast.LENGTH_SHORT).show();
                  }
               }
            } else {
               nameCheck = true;
            }
         }
      }
      if (view == ageUpdateProfileEt) {
         String age = s.toString();
         if (age.length() > 0) {
            if (Integer.parseInt(age) > 200) {
               ageUpdateProfileEt.setError("Age must not be greater than 200");
               ageCheck = false;
            } else {
               ageCheck = true;
            }
         }
      }
      if (view == heightUpdateProfileEt) {
         String height = s.toString();
         if (height.length() > 0) {
            if (Integer.parseInt(height) > 120) {
               heightUpdateProfileEt.setError("Height must not be greater than 120 Inch");
               heightCheck = false;
            } else {
               heightCheck = true;
            }
         }
      }
      if (view == weightUpdateProfileEt) {
         String weight = s.toString();
         if (weight.length() > 0) {
            if (Integer.parseInt(weight) > 350) {
               weightUpdateProfileEt.setError("Weight must not be greater than 350 Kg");
               weightCheck = false;
            } else {
               weightCheck = true;
            }
         }
      }
   }
   
   @Override
   public void afterTextChanged(Editable s) {

   }
}
