package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

import android.database.sqlite.SQLiteException;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myusers.db";
    private static final String USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("Database Operations", "Creating a Table.");
        try {
            String CREATE_USERS_TABLE = "CREATE TABLE " + USERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_FOLLOWED + " BOOLEAN" + ")";
            db.execSQL(CREATE_USERS_TABLE);

            for (int i = 0; i < 20; i++) {
                String name = "Name" + Math.round(Math.random() * 100000000);
                String description = "Description" + Math.round(Math.random() * 100000000);
                int randomNum = new Random().nextInt(2);
                String followed = "false";
                if (randomNum == 1) {
                    followed = "true";
                }
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, name);
                values.put(COLUMN_DESCRIPTION, description);
                values.put(COLUMN_FOLLOWED, followed);
                db.insert(USERS, null, values);

                Log.i("Database Operations", "Table created successfully.");
                db.close();
            }
        } catch (SQLiteException e) {
            Log.i("Database Operations", "Error creating table", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS);
        onCreate(db);
    }

    //Add a user record
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESCRIPTION, user.getDescription());
        values.put(COLUMN_FOLLOWED, user.getFollowed());
        db.insert(USERS, null, values);
        db.close();
    }

    //Get all users
    public ArrayList<User> getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + USERS;
        Cursor cursor = db.rawQuery(query, null);
        boolean follow = false;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            if(cursor.getInt(3) == 1) {
                follow = true;
            }
            User user = new User(id, name, description, follow);
            users.add(user);
        }
        db.close();
        return users;
    }

    //Read a user records
    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USERS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_FOLLOWED}, COLUMN_NAME + "=?", new String[]{username}, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getInt(3) > 0);

        db.close();
        return user;
    }

    // Update user record
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOLLOWED, user.getFollowed());
        String clause ="id=?";
        String[] args = {String.valueOf(user.getId())};
        db.update(USERS, values,COLUMN_ID +  clause, args);
        db.close();
    }
}
