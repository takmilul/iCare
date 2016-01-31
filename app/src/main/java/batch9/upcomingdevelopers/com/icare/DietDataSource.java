package batch9.upcomingdevelopers.com.icare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class DietDataSource {

   private DatabaseHelper databaseHelper;
   private SQLiteDatabase database;
   private DietModel dietModel;

   public DietDataSource(Context context) {
      databaseHelper = new DatabaseHelper(context);
   }

   public void open() {
      database = databaseHelper.getWritableDatabase();
   }

   public void close() {
      databaseHelper.close();
      database.close();
   }

   public long insertDiet(DietModel dietModel) {
      this.open();

      ContentValues cv = new ContentValues();
      cv.put(DatabaseHelper.DIET_COL_TYPE, dietModel.getDietType());
      cv.put(DatabaseHelper.DIET_COL_MENU, dietModel.getDietMenu());
      cv.put(DatabaseHelper.DIET_COL_DATE, dietModel.getDietDate());
      cv.put(DatabaseHelper.DIET_COL_TIME, dietModel.getDietTime());
      cv.put(DatabaseHelper.DIET_COL_ALARM, dietModel.isDietAlarm());
      cv.put(DatabaseHelper.DIET_COL_REMINDER, dietModel.isDietReminder());
      cv.put(DatabaseHelper.DIET_COL_FOREIGN_ID, dietModel.getDietForeignKey());

      long inserted = database.insert(DatabaseHelper.DIET_TABLE_NAME, null, cv);
      this.close();

      return inserted;
   }

   public int updateDiet(int id, DietModel dietModel) {
      this.open();

      ContentValues cv = new ContentValues();
      cv.put(DatabaseHelper.DIET_COL_MENU, dietModel.getDietMenu());
      cv.put(DatabaseHelper.DIET_COL_DATE, dietModel.getDietDate());
      cv.put(DatabaseHelper.DIET_COL_TIME, dietModel.getDietTime());
      cv.put(DatabaseHelper.DIET_COL_ALARM, dietModel.isDietAlarm());
      cv.put(DatabaseHelper.DIET_COL_REMINDER, dietModel.isDietReminder());

      int updated = database.update(DatabaseHelper.DIET_TABLE_NAME, cv, DatabaseHelper.DIET_COL_ID + " = '" + id + "'", null);
      database.close();

      return updated;
   }

   public int delete(int id) {
      this.open();

      int deleted = database.delete(DatabaseHelper.DIET_TABLE_NAME, DatabaseHelper.DIET_COL_ID + " = '" + id + "'", null);
      database.close();
      this.close();

      return deleted;
   }

   public ArrayList<DietModel> getAllDiet(int id) {
      this.open();
      ArrayList<DietModel> userList = new ArrayList<>();

      Cursor cursor = database.query(DatabaseHelper.DIET_TABLE_NAME, new String[]{DatabaseHelper.DIET_COL_ID, DatabaseHelper.DIET_COL_TYPE, DatabaseHelper.DIET_COL_MENU, DatabaseHelper.DIET_COL_DATE, DatabaseHelper.DIET_COL_TIME, DatabaseHelper.DIET_COL_ALARM, DatabaseHelper.DIET_COL_REMINDER, DatabaseHelper.DIET_COL_FOREIGN_ID}, DatabaseHelper.DIET_COL_FOREIGN_ID + "= '" + id + "'", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ID));
            String dietType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TYPE));
            String dietMenu = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_MENU));
            String dietDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_DATE));
            String dietTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TIME));
            int dietAlarm = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ALARM));
            int dietRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_REMINDER));
            int dietForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_FOREIGN_ID));

            dietModel = new DietModel(mId, dietType, dietMenu, dietDate, dietTime, dietAlarm, dietRemainder, dietForeignKey);

            userList.add(dietModel);
            cursor.moveToNext();
         }
      }
      database.close();
      this.close();
      return userList;
   }

   public ArrayList<DietModel> getUpcomingDiet(int id, String date) {
      this.open();
      ArrayList<DietModel> userList = new ArrayList<>();

      Cursor cursor = database.query(DatabaseHelper.DIET_TABLE_NAME, new String[]{DatabaseHelper.DIET_COL_ID, DatabaseHelper.DIET_COL_TYPE, DatabaseHelper.DIET_COL_MENU, DatabaseHelper.DIET_COL_DATE, DatabaseHelper.DIET_COL_TIME, DatabaseHelper.DIET_COL_ALARM, DatabaseHelper.DIET_COL_REMINDER, DatabaseHelper.DIET_COL_FOREIGN_ID}, DatabaseHelper.DIET_COL_DATE + " > date('" + date + "') AND " + DatabaseHelper.DIET_COL_FOREIGN_ID + "= '" + id + "'", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ID));
            String dietType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TYPE));
            String dietMenu = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_MENU));
            String dietDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_DATE));
            String dietTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TIME));
            int dietAlarm = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ALARM));
            int dietRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_REMINDER));
            int dietForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_FOREIGN_ID));

            dietModel = new DietModel(mId, dietType, dietMenu, dietDate, dietTime, dietAlarm, dietRemainder, dietForeignKey);

            userList.add(dietModel);
            cursor.moveToNext();
         }
      }
      database.close();
      this.close();
      return userList;
   }

   public ArrayList<DietModel> getTodaysDiet(int id, String date) {
      this.open();
      ArrayList<DietModel> userList = new ArrayList<>();

      Cursor cursor = database.query(DatabaseHelper.DIET_TABLE_NAME, new String[]{DatabaseHelper.DIET_COL_ID, DatabaseHelper.DIET_COL_TYPE, DatabaseHelper.DIET_COL_MENU, DatabaseHelper.DIET_COL_DATE, DatabaseHelper.DIET_COL_TIME, DatabaseHelper.DIET_COL_ALARM, DatabaseHelper.DIET_COL_REMINDER, DatabaseHelper.DIET_COL_FOREIGN_ID}, DatabaseHelper.DIET_COL_DATE + " = date('" + date + "')  AND " + DatabaseHelper.DIET_COL_FOREIGN_ID + "= '" + id + "' ", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ID));
            String dietType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TYPE));
            String dietMenu = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_MENU));
            String dietDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_DATE));
            String dietTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TIME));
            int dietAlarm = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ALARM));
            int dietRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_REMINDER));
            int dietForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_FOREIGN_ID));

            dietModel = new DietModel(mId, dietType, dietMenu, dietDate, dietTime, dietAlarm, dietRemainder, dietForeignKey);

            userList.add(dietModel);
            cursor.moveToNext();
         }
      }
      database.close();
      this.close();
      return userList;
   }

   public DietModel singleDietInfo(int id) {
      this.open();

      Cursor cursor = database.query(DatabaseHelper.DIET_TABLE_NAME, new String[]{DatabaseHelper.DIET_COL_ID, DatabaseHelper.DIET_COL_TYPE, DatabaseHelper.DIET_COL_MENU, DatabaseHelper.DIET_COL_DATE, DatabaseHelper.DIET_COL_TIME, DatabaseHelper.DIET_COL_ALARM, DatabaseHelper.DIET_COL_REMINDER, DatabaseHelper.DIET_COL_FOREIGN_ID}, DatabaseHelper.DIET_COL_ID + " = '" + id + "'", null, null, null, null);
      cursor.moveToFirst();

      int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ID));
      String dietType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TYPE));
      String dietMenu = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_MENU));
      String dietDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_DATE));
      String dietTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TIME));
      int dietAlarm = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ALARM));
      int dietRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_REMINDER));
      int dietForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_FOREIGN_ID));

      dietModel = new DietModel(mId, dietType, dietMenu, dietDate, dietTime, dietAlarm, dietRemainder, dietForeignKey);

      database.close();
      return dietModel;
   }

   public ArrayList<DietModel> getAllDietAlarmAndReminders(){
      ArrayList<DietModel> alarmAndReminders = new ArrayList<>();
      this.open();

      Cursor cursor=database.query(DatabaseHelper.DIET_TABLE_NAME, null, DatabaseHelper.DIET_COL_ALARM + "=1 OR " + DatabaseHelper.DIET_COL_REMINDER + "=1", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ID));
            String dietType = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TYPE));
            String dietMenu = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_MENU));
            String dietDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_DATE));
            String dietTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIET_COL_TIME));
            int dietAlarm = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_ALARM));
            int dietRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_REMINDER));
            int dietForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DIET_COL_FOREIGN_ID));

            dietModel = new DietModel(mId, dietType, dietMenu, dietDate, dietTime, dietAlarm, dietRemainder, dietForeignKey);

            alarmAndReminders.add(dietModel);
            cursor.moveToNext();
         }
      }
      this.close();
      return alarmAndReminders;
   }
}
