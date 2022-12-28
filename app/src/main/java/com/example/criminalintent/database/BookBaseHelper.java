package com.example.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.criminalintent.database.BookDbSchema.CrimeTable;

public class BookBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 23;
    private static final String DATABASE_NAME = "bookBase.db";
    public BookBaseHelper(Context context) {
        super(context,DATABASE_NAME , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ CrimeTable.NAME + "(" +
                        " _id INTEGER primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", "+
                CrimeTable.Cols.TITLE + ", "+
                CrimeTable.Cols.DATA_STARTING + ", "+
                CrimeTable.Cols.DATA_FINISHING + ", "
               + CrimeTable.Cols.AUTHOR + ", "+
               CrimeTable.Cols.NOTE + ", "+
               CrimeTable.Cols.STATUS1 + ", "+
                  CrimeTable.Cols.STATUS2 + ", "+
               CrimeTable.Cols.STATUS3 + ", "+
               CrimeTable.Cols.PAGES + ", "+
                   CrimeTable.Cols.GENRE + ", "+
          CrimeTable.Cols.RATING + ", "+
                CrimeTable.Cols.SUMMARY  +  " )"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ CrimeTable.NAME);
        onCreate(db);
    }
}
