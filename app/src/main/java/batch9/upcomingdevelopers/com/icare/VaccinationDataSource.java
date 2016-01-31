package batch9.upcomingdevelopers.com.icare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class VaccinationDataSource {
   private DatabaseHelper databaseHelper;
   private SQLiteDatabase database;
   private VaccinationModel vaccineModel;

   public VaccinationDataSource(Context context) {
      databaseHelper = new DatabaseHelper(context);
   }

   public void open() {
      database = databaseHelper.getWritableDatabase();
   }

   public void close() {
      databaseHelper.close();
      database.close();
   }

   public long insertVaccine(VaccinationModel vaccineModel) {
      this.open();

      ContentValues cv = new ContentValues();
      cv.put(DatabaseHelper.VACCINE_COL_NAME, vaccineModel.getVaccineName());
      cv.put(DatabaseHelper.VACCINE_COL_DATE, vaccineModel.getVaccineDate());
      cv.put(DatabaseHelper.VACCINE_COL_TIME, vaccineModel.getVaccineTime());
      cv.put(DatabaseHelper.VACCINE_COL_DETAIL, vaccineModel.getVaccineDetail());
      cv.put(DatabaseHelper.VACCINE_COL_REMINDER, vaccineModel.getVaccineReminder());
      cv.put(DatabaseHelper.VACCINE_COL_FOREIGN_ID, vaccineModel.getVaccineForeignKey());

      long inserted = database.insert(DatabaseHelper.VACCINE_TABLE_NAME, null, cv);
      this.close();

      return inserted;
   }

   public int updateVaccine(int id, VaccinationModel vaccineModel) {
      this.open();

      ContentValues cv = new ContentValues();
      cv.put(DatabaseHelper.VACCINE_COL_NAME, vaccineModel.getVaccineName());
      cv.put(DatabaseHelper.VACCINE_COL_DATE, vaccineModel.getVaccineDate());
      cv.put(DatabaseHelper.VACCINE_COL_TIME, vaccineModel.getVaccineTime());
      cv.put(DatabaseHelper.VACCINE_COL_DETAIL, vaccineModel.getVaccineDetail());
      cv.put(DatabaseHelper.VACCINE_COL_REMINDER, vaccineModel.getVaccineReminder());

      int updated = database.update(DatabaseHelper.VACCINE_TABLE_NAME, cv, DatabaseHelper.VACCINE_COL_ID + " = '" + id + "'", null);
      database.close();

      return updated;
   }

   public int delete(int id) {
      this.open();

      int deleted = database.delete(DatabaseHelper.VACCINE_TABLE_NAME, DatabaseHelper.VACCINE_COL_ID + " = '" + id + "'", null);
      database.close();
      this.close();

      return deleted;
   }

   public ArrayList<VaccinationModel> getAllVaccine(int id) {
      this.open();
      ArrayList<VaccinationModel> userList = new ArrayList<>();

      Cursor cursor = database.query(DatabaseHelper.VACCINE_TABLE_NAME, new String[]{DatabaseHelper.VACCINE_COL_ID, DatabaseHelper.VACCINE_COL_NAME, DatabaseHelper.VACCINE_COL_DATE, DatabaseHelper.VACCINE_COL_TIME, DatabaseHelper.VACCINE_COL_DETAIL, DatabaseHelper.VACCINE_COL_REMINDER, DatabaseHelper.VACCINE_COL_FOREIGN_ID}, DatabaseHelper.VACCINE_COL_FOREIGN_ID + " = '" + id + "'", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_ID));
            String vaccineName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_NAME));
            String vaccineDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DATE));
            String vaccineTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_TIME));
            String vaccineDetail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DETAIL));
            int vaccineRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_REMINDER));
            int vaccineForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_FOREIGN_ID));

            vaccineModel = new VaccinationModel(mId, vaccineName, vaccineDate, vaccineTime, vaccineDetail, vaccineRemainder, vaccineForeignKey);

            userList.add(vaccineModel);
            cursor.moveToNext();
         }
      }
      database.close();
      this.close();
      return userList;
   }

   public ArrayList<VaccinationModel> getCompletedVaccine(int id, String date) {
      this.open();
      ArrayList<VaccinationModel> userList = new ArrayList<>();

      Cursor cursor = database.query(DatabaseHelper.VACCINE_TABLE_NAME, new String[]{DatabaseHelper.VACCINE_COL_ID, DatabaseHelper.VACCINE_COL_NAME, DatabaseHelper.VACCINE_COL_DATE, DatabaseHelper.VACCINE_COL_TIME, DatabaseHelper.VACCINE_COL_DETAIL, DatabaseHelper.VACCINE_COL_REMINDER, DatabaseHelper.VACCINE_COL_FOREIGN_ID}, DatabaseHelper.VACCINE_COL_DATE + " < date('" + date + "') AND " +  DatabaseHelper.VACCINE_COL_FOREIGN_ID + " = '" + id + "'", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_ID));
            String vaccineName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_NAME));
            String vaccineDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DATE));
            String vaccineTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_TIME));
            String vaccineDetail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DETAIL));
            int vaccineRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_REMINDER));
            int vaccineForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_FOREIGN_ID));

            vaccineModel = new VaccinationModel(mId, vaccineName, vaccineDate, vaccineTime, vaccineDetail, vaccineRemainder, vaccineForeignKey);

            userList.add(vaccineModel);
            cursor.moveToNext();
         }
      }
      database.close();
      this.close();
      return userList;
   }

   public ArrayList<VaccinationModel> getUpcomingVaccine(int id, String date) {
      this.open();
      ArrayList<VaccinationModel> userList = new ArrayList<>();

      Cursor cursor = database.query(DatabaseHelper.VACCINE_TABLE_NAME, new String[]{DatabaseHelper.VACCINE_COL_ID, DatabaseHelper.VACCINE_COL_NAME, DatabaseHelper.VACCINE_COL_DATE, DatabaseHelper.VACCINE_COL_TIME, DatabaseHelper.VACCINE_COL_DETAIL, DatabaseHelper.VACCINE_COL_REMINDER, DatabaseHelper.VACCINE_COL_FOREIGN_ID}, DatabaseHelper.VACCINE_COL_DATE + " >= date('" + date + "') AND " +  DatabaseHelper.VACCINE_COL_FOREIGN_ID + " = '" + id + "'", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_ID));
            String vaccineName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_NAME));
            String vaccineDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DATE));
            String vaccineTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_TIME));
            String vaccineDetail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DETAIL));
            int vaccineRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_REMINDER));
            int vaccineForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_FOREIGN_ID));

            vaccineModel = new VaccinationModel(mId, vaccineName, vaccineDate, vaccineTime, vaccineDetail, vaccineRemainder, vaccineForeignKey);

            userList.add(vaccineModel);
            cursor.moveToNext();
         }
      }
      database.close();
      this.close();
      return userList;
   }

   public VaccinationModel singleVaccine(int id) {
      this.open();

      Cursor cursor = database.query(DatabaseHelper.VACCINE_TABLE_NAME, new String[]{DatabaseHelper.VACCINE_COL_ID, DatabaseHelper.VACCINE_COL_NAME, DatabaseHelper.VACCINE_COL_DATE, DatabaseHelper.VACCINE_COL_TIME, DatabaseHelper.VACCINE_COL_DETAIL, DatabaseHelper.VACCINE_COL_REMINDER, DatabaseHelper.VACCINE_COL_FOREIGN_ID}, DatabaseHelper.VACCINE_COL_ID + " = '" + id + "'", null, null, null, null);
      cursor.moveToFirst();

      int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_ID));
      String vaccineName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_NAME));
      String vaccineDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DATE));
      String vaccineTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_TIME));
      String vaccineDetail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DETAIL));
      int vaccineRemainder = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_REMINDER));
      int vaccineForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_FOREIGN_ID));

      vaccineModel = new VaccinationModel(mId, vaccineName, vaccineDate, vaccineTime, vaccineDetail, vaccineRemainder, vaccineForeignKey);

      database.close();
      return vaccineModel;
   }

   public ArrayList<VaccinationModel> getAllVaccineReminder(){
      ArrayList<VaccinationModel> vaccineReminderList = new ArrayList<>();

      this.open();
      Cursor cursor = database.query(DatabaseHelper.VACCINE_TABLE_NAME, new String[]{DatabaseHelper.VACCINE_COL_ID, DatabaseHelper.VACCINE_COL_NAME, DatabaseHelper.VACCINE_COL_DATE, DatabaseHelper.VACCINE_COL_TIME, DatabaseHelper.VACCINE_COL_DETAIL, DatabaseHelper.VACCINE_COL_FOREIGN_ID}, DatabaseHelper.VACCINE_COL_REMINDER + "=1", null, null, null, null);

      if (cursor != null && cursor.getCount() > 0) {

         cursor.moveToFirst();

         for (int i = 0; i < cursor.getCount(); i++) {

            int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_ID));
            String vaccineName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_NAME));
            String vaccineDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DATE));
            String vaccineTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_TIME));
            String vaccineDetail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_DETAIL));

            int vaccineForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.VACCINE_COL_FOREIGN_ID));

            vaccineModel = new VaccinationModel(mId, vaccineName, vaccineDate, vaccineTime, vaccineDetail, 1, vaccineForeignKey);

            vaccineReminderList.add(vaccineModel);
            cursor.moveToNext();
         }
      }
      database.close();
      return vaccineReminderList;
   }
}
