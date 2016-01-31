package batch9.upcomingdevelopers.com.icare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "USER_INFORMATION";
    static final int DATABASE_VERSION = 5;

    static final String PROFILE_TABLE_NAME = "USER_PROFILE";
    static final String PROFILE_COL_ID = "User_Id";
    static final String PROFILE_COL_USER_NAME = "User_Name";
    static final String PROFILE_COL_USER_AGE = "User_Age";
    static final String PROFILE_COL_USER_HEIGHT = "User_Height";
    static final String PROFILE_COL_USER_WEIGHT = "User_Weight";


    static final String DIET_TABLE_NAME = "DIET_INFO";
    static final String DIET_COL_ID = "Diet_Id";
    static final String DIET_COL_TYPE = "Diet_Type";
    static final String DIET_COL_MENU = "Diet_Menu";
    static final String DIET_COL_DATE = "Diet_Date";
    static final String DIET_COL_TIME = "Diet_Time";
    static final String DIET_COL_ALARM = "Diet_Alarm";
    static final String DIET_COL_REMINDER = "Diet_Reminder";
    static final String DIET_COL_FOREIGN_ID = "Diet_Foreign_Id";


    static final String VACCINE_TABLE_NAME = "VACCINE_INFO";
    static final String VACCINE_COL_ID = "Vaccine_Id";
    static final String VACCINE_COL_NAME = "Vaccine_Name";
    static final String VACCINE_COL_DATE = "Vaccine_Date";
    static final String VACCINE_COL_TIME = "Vaccine_Time";
    static final String VACCINE_COL_DETAIL = "Vaccine_Detail";
    static final String VACCINE_COL_REMINDER = "Vaccine_Reminder";
    static final String VACCINE_COL_FOREIGN_ID = "Vaccine_Foreign_Id";


    static final String DOCTOR_TABLE_NAME = "DOCTOR_INFO";
    static final String DOCTOR_COL_ID = "Doctor_Id";
    static final String DOCTOR_COL_NAME = "Vaccine_Name";
    static final String DOCTOR_COL_DETAIL = "Doctor_Detail";
    static final String DOCTOR_COL_APPOINTMENT = "Doctor_Appointment";
    static final String DOCTOR_COL_PHONE = "Doctor_Phone";
    static final String DOCTOR_COL_EMAIL = "Doctor_Email";
    static final String DOCTOR_COL_FOREIGN_ID = "Doctor_Foreign_Id";


    static final String MEDICAL_HISTORY_TABLE_NAME = "MEDICAL_HISTORY_INFO";
    static final String MEDICAL_HISTORY_COL_ID = "Medical_History_Id";
    static final String MEDICAL_HISTORY_COL_IMAGE = "Medical_History_Image";
    static final String MEDICAL_HISTORY_COL_DOCTOR = "Medical_History_Doctor";
    static final String MEDICAL_HISTORY_COL_DETAILS = "Medical_History_Details";
    static final String MEDICAL_HISTORY_COL_DATE = "Medical_History_Date";
    static final String MEDICAL_HISTORY_COL_FOREIGN_ID = "Medical_History_Foreign_Id";


    private static final String CREATE_PROFILE_TABLE = "CREATE TABLE " + PROFILE_TABLE_NAME + "(" +
            PROFILE_COL_ID + " INTEGER PRIMARY KEY, " +
            PROFILE_COL_USER_NAME + " TEXT, " +
            PROFILE_COL_USER_AGE + " TEXT, " +
            PROFILE_COL_USER_HEIGHT + " TEXT, " +
            PROFILE_COL_USER_WEIGHT + " TEXT);";


    private static final String CREATE_DIET_TABLE = "CREATE TABLE " + DIET_TABLE_NAME + "(" +
            DIET_COL_ID + " INTEGER PRIMARY KEY, " +
            DIET_COL_TYPE + " TEXT, " +
            DIET_COL_MENU + " TEXT, " +
            DIET_COL_DATE + " TEXT, " +
            DIET_COL_TIME + " TEXT, " +
            DIET_COL_ALARM + " INTEGER, " +
            DIET_COL_REMINDER + " INTEGER, " +
            DIET_COL_FOREIGN_ID + " INTEGER NOT NULL REFERENCES " +
            PROFILE_TABLE_NAME + "(" + PROFILE_COL_ID + ") ON DELETE CASCADE);";


    private static final String CREATE_VACCINE_TABLE = "CREATE TABLE " + VACCINE_TABLE_NAME + "(" +
            VACCINE_COL_ID + " INTEGER PRIMARY KEY, " +
            VACCINE_COL_NAME + " TEXT, " +
            VACCINE_COL_DATE + " TEXT, " +
            VACCINE_COL_TIME + " TEXT, " +
            VACCINE_COL_DETAIL + " TEXT, " +
            VACCINE_COL_REMINDER + " INTEGER, " +
            VACCINE_COL_FOREIGN_ID + " INTEGER NOT NULL REFERENCES " +
            PROFILE_TABLE_NAME + "(" + PROFILE_COL_ID + ") ON DELETE CASCADE);";


    private static final String CREATE_DOCTOR_TABLE = "CREATE TABLE " + DOCTOR_TABLE_NAME + "(" +
            DOCTOR_COL_ID + " INTEGER PRIMARY KEY, " +
            DOCTOR_COL_NAME + " TEXT, " +
            DOCTOR_COL_DETAIL + " TEXT, " +
            DOCTOR_COL_APPOINTMENT + " TEXT, " +
            DOCTOR_COL_PHONE + " TEXT, " +
            DOCTOR_COL_EMAIL + " TEXT, " +
            DOCTOR_COL_FOREIGN_ID + " INTEGER NOT NULL REFERENCES " +
            PROFILE_TABLE_NAME + "(" + PROFILE_COL_ID + ") ON DELETE CASCADE);";


    private static final String CREATE_MEDICAL_HISTORY_TABLE = "CREATE TABLE " + MEDICAL_HISTORY_TABLE_NAME + "(" +
            MEDICAL_HISTORY_COL_ID + " INTEGER PRIMARY KEY, " +
            MEDICAL_HISTORY_COL_IMAGE + " TEXT, " +
            MEDICAL_HISTORY_COL_DOCTOR + " TEXT, " +
            MEDICAL_HISTORY_COL_DETAILS + " TEXT, " +
            MEDICAL_HISTORY_COL_DATE + " TEXT, " +
            MEDICAL_HISTORY_COL_FOREIGN_ID + " INTEGER NOT NULL REFERENCES " +
            PROFILE_TABLE_NAME + "(" + PROFILE_COL_ID + ") ON DELETE CASCADE);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROFILE_TABLE);
        db.execSQL(CREATE_DIET_TABLE);
        db.execSQL(CREATE_VACCINE_TABLE);
        db.execSQL(CREATE_DOCTOR_TABLE);
        db.execSQL(CREATE_MEDICAL_HISTORY_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + PROFILE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + DIET_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + VACCINE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + DOCTOR_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + MEDICAL_HISTORY_TABLE_NAME);
    }
}
