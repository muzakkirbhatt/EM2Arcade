package com.example.mbhatt1.em2arcade;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GameDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "user";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CONNECTHS = "connecths";
    private static final String MEMORYHS = "memoryhs";
    private static final String BLACKJACKHS = "blackjackhs";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // build sql create statement
        String sqlCreate = "create table " + TABLE + "( " + ID + " integer primary key autoincrement, " + USERNAME + " text unique, " + EMAIL + " text unique," + PASSWORD + " text," + CONNECTHS + " text," + MEMORYHS + " text," + BLACKJACKHS + " text )";
        db.execSQL(sqlCreate);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE);
        onCreate(db);
    }

    public boolean findUsername(String username, String type) {
        boolean toReturn = false;
        String sqlQuery = "select * from " + TABLE;
        if (type.equalsIgnoreCase("username")) {
            sqlQuery = "select " + USERNAME + " from " + TABLE;
        } else if (type.equalsIgnoreCase("email")) {
            sqlQuery = "select " + EMAIL + " from " + TABLE;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        while (cursor.moveToNext()) {
            String text = "";
            if (type.equalsIgnoreCase("username")) {
                text = cursor.getString(0);
            } else if (type.equalsIgnoreCase("email")) {
                text = cursor.getString(0);
            }
            if (username.equalsIgnoreCase(text)) {
                toReturn = true;
            }
        }
        db.close();
        return toReturn;
    }

    public boolean registerUser(User user) {
        boolean test = false;
        if (!this.findUsername(user.getUsername(), "username") && !this.findUsername(user.getEmail(), "email")) {
            SQLiteDatabase db = this.getWritableDatabase();
            String sqlInsert = "insert into " + TABLE + " values( null, '" + user.getUsername() + "', '" + user.getEmail() + "' , '" + user.getPassword() + "' , '" + user.getConnectHS() + "' , '" + user.getMemoryHS() + "', '" + user.getBlackjackHS() + "' )";
            db.execSQL(sqlInsert);
            db.close();
            return true;
        } else return false;
    }

    public User loginUser(String username, String password, String type) {
        if (this.findUsername(username, type)) {

            String sqlQuery = "";
            if (type.equalsIgnoreCase("username")) {
                sqlQuery = "select * from " + TABLE + " where " + USERNAME + " = '" + username + "'";
            }
            if (type.equalsIgnoreCase("email")) {
                sqlQuery = "select * from " + TABLE + " where " + EMAIL + " = '" + username + "'";
            }
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sqlQuery, null);

            User user = null;
            if (cursor.moveToFirst())
                user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            if (user.getPassword().equals(password)) {
                return user;
            } else return null;
        } else return null;
    }


    public void deleteByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = "delete from " + TABLE + " where " + USERNAME + " = '" + username + "'";
        db.execSQL(sqlDelete);
        db.close();
    }

    public void updateHS(User user, String game) {
        String sqlUpdate = "update " + TABLE + " set ";

        if (game.equalsIgnoreCase("connect four")) {
            sqlUpdate = CONNECTHS + " = '" + user.getConnectHS() + "'";
        } else if (game.equalsIgnoreCase("memory game")) {
            sqlUpdate = MEMORYHS + " = '" + user.getMemoryHS() + "'";
        } else if (game.equalsIgnoreCase("black jack")) {
            sqlUpdate = BLACKJACKHS + " = '" + user.getBlackjackHS() + "'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        sqlUpdate = " where " + USERNAME + " = '" + user.getUsername() + "'";
        db.execSQL(sqlUpdate);
        db.close();
    }

    public void updateByUsername(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdate = "update " + TABLE + " set " + USERNAME + " = '" + user.getUsername() + "', " + EMAIL + " = '" + user.getEmail() + "', " + PASSWORD + " = '" + user.getPassword() + "'" + "', " + CONNECTHS + " = '" + user.getConnectHS() + "', " + MEMORYHS + " = '" + user.getMemoryHS() + "', " + BLACKJACKHS + " = '" + user.getBlackjackHS() + "' where " + USERNAME + " = " + "'" + user.getUsername() + "'";
        db.execSQL(sqlUpdate);
        db.close();
    }


    public User selectByUsername(String username) {
        String sqlQuery = "select * from " + TABLE + " where " + USERNAME + " = '" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        User user = null;
        if (cursor.moveToFirst())
            user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return user;
    }

    public ArrayList<User> selectAll() {
        String sqlQuery = "select * from " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            User user = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
            users.add(user);
        }
        db.close();
        return users;
    }
}

