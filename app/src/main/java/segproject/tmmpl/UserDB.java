package segproject.tmmpl;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;


public class UserDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
//    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_AVATAR_ID = "avatar_id";



    public UserDB (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " +
                TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_USERNAME
                + " TEXT," + COLUMN_AVATAR_ID + " STRING" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User product ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, product.getUsername());
        values.put(COLUMN_AVATAR_ID, product.getAvatarId());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public User findUser(String username){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select*FROM" + TABLE_USERS + "WHERE"+ COLUMN_USERNAME + "=\""+ username + "\"";
        Cursor cursor = db.rawQuery(query, null);

        User user = new User();

        if (cursor.moveToFirst()){
            user.setID(Integer.parseInt(cursor.getString(0)));
            user.setUsername(cursor.getString(1));
            user.setAvatarId(cursor.getString(2));
            cursor.close();

        } else{
            user = null;

        }

        db.close();
        return user;

    }


    public boolean deleteUser(String username){
        boolean result= false;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select*FROM"+TABLE_USERS+ "WHERE"+ COLUMN_USERNAME + "=\"" + username + "\"" ;
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            String idSTR = cursor.getString(0);
            db.delete(TABLE_USERS, COLUMN_ID + "="+idSTR, null);
            cursor.close();
            result= true;
        }
        db.close();
        return  result;

    }

}
