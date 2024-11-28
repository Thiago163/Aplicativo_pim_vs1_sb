package com.example.pim_mundo_verde.component;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {

    // Nome do banco de dados
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela
    private static final String TABLE_USERS = "users";

    // Colunas da tabela
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_SENHA = "senha";

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria a tabela de usuários
        String CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_SENHA + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Apaga a tabela se a versão do banco for alterada
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Método para verificar se o usuário existe no banco de dados
    public boolean checkUser(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_SENHA + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email, senha});

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return userExists;
    }

    // (Opcional) Método para adicionar usuários ao banco de dados
    public void addUser(String email, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_USERS + " (" + COLUMN_EMAIL + ", " + COLUMN_SENHA + ") VALUES (?, ?)";
        db.execSQL(query, new String[]{email, senha});
        db.close();
    }
}

