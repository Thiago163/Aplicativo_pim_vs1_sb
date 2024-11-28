package com.example.pim_mundo_verde.component;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SENHA = "senha";

    // SQL para criar a tabela de usuários
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                    COLUMN_SENHA + " TEXT);";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);  // Cria a tabela
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
