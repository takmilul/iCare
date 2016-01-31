package batch9.upcomingdevelopers.com.icare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserProfileDataSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private UserProfileModel userProfileModel;

    public UserProfileDataSource(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        database.close();
    }

    public long insert(UserProfileModel userProfileModel){
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.PROFILE_COL_USER_NAME,userProfileModel.getUserName());
        cv.put(DatabaseHelper.PROFILE_COL_USER_AGE,userProfileModel.getUserAge());
        cv.put(DatabaseHelper.PROFILE_COL_USER_HEIGHT,userProfileModel.getUserHeight());
        cv.put(DatabaseHelper.PROFILE_COL_USER_WEIGHT, userProfileModel.getUserWeight());

        long inserted = database.insert(DatabaseHelper.PROFILE_TABLE_NAME,null,cv);
        this.close();

        return inserted;
    }

    public int update(int id, UserProfileModel userProfileModel) {
        this.open();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.PROFILE_COL_USER_NAME, userProfileModel.getUserName());
        cv.put(DatabaseHelper.PROFILE_COL_USER_AGE, userProfileModel.getUserAge());
        cv.put(DatabaseHelper.PROFILE_COL_USER_HEIGHT, userProfileModel.getUserHeight());
        cv.put(DatabaseHelper.PROFILE_COL_USER_WEIGHT, userProfileModel.getUserWeight());


        int updated = database.update(DatabaseHelper.PROFILE_TABLE_NAME, cv, DatabaseHelper.PROFILE_COL_ID + " = '" + id + "'", null);
        database.close();

        return updated;
    }

    public int delete(int id) {
        this.open();

        int deleted=   database.delete(DatabaseHelper.PROFILE_TABLE_NAME, DatabaseHelper.PROFILE_COL_ID + " = '" + id + "'", null);
        database.close();
        this.close();

        return deleted;
    }

    public ArrayList<UserProfileModel> getAllProfile(){
        this.open();
        ArrayList<UserProfileModel> userList=new ArrayList<>();

        Cursor cursor=database.query(DatabaseHelper.PROFILE_TABLE_NAME, null, null, null, null, null, null);

        if (cursor!=null && cursor.getCount()>0){

            cursor.moveToFirst();

            for (int i=0; i<cursor.getCount(); i++){

                int id=cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_ID));
                String name=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_NAME));
                String age=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_AGE));
                String height=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_HEIGHT));
                String weight=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_WEIGHT));
                userProfileModel=new UserProfileModel(id,name,age,height,weight) ;
                userList.add(userProfileModel);
                cursor.moveToNext();
            }
        }
        database.close();
        this.close();
        return userList;
    }

    public UserProfileModel singleProfile(int id){
        this.open();

        Cursor cursor=database.query(DatabaseHelper.PROFILE_TABLE_NAME, new String []{DatabaseHelper.PROFILE_COL_ID, DatabaseHelper.PROFILE_COL_USER_NAME, DatabaseHelper.PROFILE_COL_USER_AGE, DatabaseHelper.PROFILE_COL_USER_HEIGHT, DatabaseHelper.PROFILE_COL_USER_WEIGHT}, DatabaseHelper.PROFILE_COL_ID + " = '" + id + "'",null,null,null,null);
        cursor.moveToFirst();

        int mId=cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_ID));
        String name=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_NAME));
        String age=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_AGE));
        String height=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_HEIGHT));
        String weight=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_WEIGHT));

        userProfileModel=new UserProfileModel(mId,name,age,height,weight) ;
        database.close();
        return userProfileModel;
    }

    public Map<Integer, String> getAllUserNames(){
        Map<Integer, String> userNames = new HashMap<>();
        this.open();

        Cursor cursor=database.query(DatabaseHelper.PROFILE_TABLE_NAME, new String[]{DatabaseHelper.PROFILE_COL_ID, DatabaseHelper.PROFILE_COL_USER_NAME}, null, null, null, null, null);
        if (cursor!=null && cursor.getCount()>0){

            cursor.moveToFirst();

            for (int i=0; i<cursor.getCount(); i++){

                int id=cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_ID));
                String name=cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROFILE_COL_USER_NAME));
                userNames.put(id, name);
                cursor.moveToNext();
            }
        }

        this.close();

        return userNames;
    }
}
