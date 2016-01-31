package batch9.upcomingdevelopers.com.icare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by acer on 11-Jan-16.
 */
public class MedicalHistoryDataSource {
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    MedicalHistoryModel medicalHistoryModel;

    public MedicalHistoryDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public long insert(MedicalHistoryModel medicalHistoryModel){
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_IMAGE, medicalHistoryModel.getMedicalHistoryImageFilePath());
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_DOCTOR, medicalHistoryModel.getMedicalHistoryDoctorName());
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_DETAILS, medicalHistoryModel.getMedicalHistoryDetails());
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_DATE, medicalHistoryModel.getMedicalHistoryDate());
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_FOREIGN_ID, medicalHistoryModel.getMedicalHistoryForeignKey());

        long inserted = database.insert(DatabaseHelper.MEDICAL_HISTORY_TABLE_NAME, null, cv);

        this.close();
        return inserted;
    }

    public int update(int id, MedicalHistoryModel medicalHistoryModel) {
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_IMAGE, medicalHistoryModel.getMedicalHistoryImageFilePath());
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_DOCTOR, medicalHistoryModel.getMedicalHistoryDoctorName());
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_DETAILS, medicalHistoryModel.getMedicalHistoryDetails());
        cv.put(DatabaseHelper.MEDICAL_HISTORY_COL_DATE, medicalHistoryModel.getMedicalHistoryDate());

        int updated = database.update(DatabaseHelper.MEDICAL_HISTORY_TABLE_NAME, cv, DatabaseHelper.MEDICAL_HISTORY_COL_ID + " = '" + id + "'", null);

        this.close();;
        return updated;
    }

    public int delete(int id) {
        this.open();

        int deleted = database.delete(DatabaseHelper.MEDICAL_HISTORY_TABLE_NAME, DatabaseHelper.MEDICAL_HISTORY_COL_ID + " = '" + id + "'", null);
        this.close();

        return deleted;
    }

    public ArrayList<MedicalHistoryModel> getAllMedicalHistory(int id) {
        this.open();
        ArrayList<MedicalHistoryModel> medicalHistoryList = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.MEDICAL_HISTORY_TABLE_NAME, new String[]{DatabaseHelper.MEDICAL_HISTORY_COL_ID, DatabaseHelper.MEDICAL_HISTORY_COL_IMAGE, DatabaseHelper.MEDICAL_HISTORY_COL_DOCTOR, DatabaseHelper.MEDICAL_HISTORY_COL_DETAILS, DatabaseHelper.MEDICAL_HISTORY_COL_DATE, DatabaseHelper.MEDICAL_HISTORY_COL_FOREIGN_ID}, DatabaseHelper.MEDICAL_HISTORY_COL_FOREIGN_ID + " = '" + id + "'", null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_ID));
                String medicalHistoryImageFilePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_IMAGE));;
                String medicalHistoryDoctorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_DOCTOR));;
                String medicalHistoryDetails = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_DETAILS));;
                String medicalHistoryDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_DATE));;
                int medicalHistoryForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_FOREIGN_ID));;

                medicalHistoryModel = new MedicalHistoryModel(mId, medicalHistoryImageFilePath, medicalHistoryDoctorName, medicalHistoryDetails, medicalHistoryDate, medicalHistoryForeignKey);

                medicalHistoryList.add(medicalHistoryModel);
                cursor.moveToNext();
            }
        }

        this.close();
        return medicalHistoryList;
    }

    public MedicalHistoryModel getSingleMedicalHistory(int id) {
        this.open();

        Cursor cursor = database.query(DatabaseHelper.MEDICAL_HISTORY_TABLE_NAME, new String[]{DatabaseHelper.MEDICAL_HISTORY_COL_ID, DatabaseHelper.MEDICAL_HISTORY_COL_IMAGE, DatabaseHelper.MEDICAL_HISTORY_COL_DOCTOR, DatabaseHelper.MEDICAL_HISTORY_COL_DETAILS, DatabaseHelper.MEDICAL_HISTORY_COL_DATE, DatabaseHelper.MEDICAL_HISTORY_COL_FOREIGN_ID}, DatabaseHelper.MEDICAL_HISTORY_COL_ID + " = '" + id + "'", null, null, null, null);
        cursor.moveToFirst();


        String medicalHistoryImageFilePath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_IMAGE));;
        String medicalHistoryDoctorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_DOCTOR));;
        String medicalHistoryDetails = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_DETAILS));;
        String medicalHistoryDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_DATE));;
        int medicalHistoryForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.MEDICAL_HISTORY_COL_FOREIGN_ID));;

        medicalHistoryModel = new MedicalHistoryModel(id, medicalHistoryImageFilePath, medicalHistoryDoctorName, medicalHistoryDetails, medicalHistoryDate, medicalHistoryForeignKey);

        this.close();
        return medicalHistoryModel;
    }
}
