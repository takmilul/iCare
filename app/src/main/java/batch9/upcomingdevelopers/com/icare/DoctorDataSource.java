package batch9.upcomingdevelopers.com.icare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DoctorDataSource {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private DoctorModel doctorModel;

    public DoctorDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        database.close();
    }

    public long insertDoctor(DoctorModel doctorModel){
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.DOCTOR_COL_NAME,doctorModel.getDoctorName());
        cv.put(DatabaseHelper.DOCTOR_COL_DETAIL,doctorModel.getDoctorDetail());
        cv.put(DatabaseHelper.DOCTOR_COL_APPOINTMENT,doctorModel.getDoctorAppointment());
        cv.put(DatabaseHelper.DOCTOR_COL_PHONE,doctorModel.getDoctorPhone());
        cv.put(DatabaseHelper.DOCTOR_COL_EMAIL,doctorModel.getDoctorEmail());
        cv.put(DatabaseHelper.DOCTOR_COL_FOREIGN_ID, doctorModel.getDoctorForeignKey());

        long inserted = database.insert(DatabaseHelper.DOCTOR_TABLE_NAME,null,cv);
        this.close();

        return inserted;
    }

    public int updateDoctor(int id, DoctorModel doctorModel) {
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.DOCTOR_COL_NAME, doctorModel.getDoctorName());
        cv.put(DatabaseHelper.DOCTOR_COL_DETAIL, doctorModel.getDoctorDetail());
        cv.put(DatabaseHelper.DOCTOR_COL_APPOINTMENT, doctorModel.getDoctorAppointment());
        cv.put(DatabaseHelper.DOCTOR_COL_PHONE, doctorModel.getDoctorPhone());
        cv.put(DatabaseHelper.DOCTOR_COL_EMAIL, doctorModel.getDoctorEmail());

        int updated = database.update(DatabaseHelper.DOCTOR_TABLE_NAME, cv, DatabaseHelper.DOCTOR_COL_ID + " = '" + id + "'", null);
        database.close();

        return updated;
    }

    public int delete(int id) {
        this.open();

        int deleted = database.delete(DatabaseHelper.DOCTOR_TABLE_NAME, DatabaseHelper.DOCTOR_COL_ID + " = '" + id + "'", null);
        database.close();
        this.close();

        return deleted;
    }

    public ArrayList<DoctorModel> getAllDoctor(int id) {
        this.open();
        ArrayList<DoctorModel> userList = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.DOCTOR_TABLE_NAME, new String[]{DatabaseHelper.DOCTOR_COL_ID, DatabaseHelper.DOCTOR_COL_NAME, DatabaseHelper.DOCTOR_COL_DETAIL, DatabaseHelper.DOCTOR_COL_APPOINTMENT, DatabaseHelper.DOCTOR_COL_PHONE, DatabaseHelper.DOCTOR_COL_EMAIL, DatabaseHelper.DOCTOR_COL_FOREIGN_ID}, DatabaseHelper.DOCTOR_COL_FOREIGN_ID + " = '" + id + "'", null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_ID));
                String doctorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_NAME));
                String doctorDetail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_DETAIL));
                String doctorAppointment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_APPOINTMENT));
                String doctorPhone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_PHONE));
                String doctorEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_EMAIL));
                int doctorForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_FOREIGN_ID));

                doctorModel = new DoctorModel(mId, doctorName, doctorDetail, doctorAppointment, doctorPhone, doctorEmail,  doctorForeignKey);

                userList.add(doctorModel);
                cursor.moveToNext();
            }
        }
        database.close();
        this.close();
        return userList;
    }

    public DoctorModel singleDoctorInfo(int id) {
        this.open();

        Cursor cursor = database.query(DatabaseHelper.DOCTOR_TABLE_NAME, new String[]{DatabaseHelper.DOCTOR_COL_ID, DatabaseHelper.DOCTOR_COL_NAME, DatabaseHelper.DOCTOR_COL_DETAIL, DatabaseHelper.DOCTOR_COL_APPOINTMENT, DatabaseHelper.DOCTOR_COL_PHONE, DatabaseHelper.DOCTOR_COL_EMAIL, DatabaseHelper.DOCTOR_COL_FOREIGN_ID}, DatabaseHelper.DOCTOR_COL_ID + " = '" + id + "'", null, null, null, null);
        cursor.moveToFirst();

        int mId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_ID));
        String doctorName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_NAME));
        String doctorDetail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_DETAIL));
        String doctorAppointment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_APPOINTMENT));
        String doctorPhone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_PHONE));
        String doctorEmail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_EMAIL));
        int doctorForeignKey = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DOCTOR_COL_FOREIGN_ID));

        doctorModel = new DoctorModel(mId, doctorName, doctorDetail, doctorAppointment, doctorPhone, doctorEmail,  doctorForeignKey);

        database.close();
        return doctorModel;
    }
}
