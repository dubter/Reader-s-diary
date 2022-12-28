package com.example.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.criminalintent.database.BookCursorWrapper;
import com.example.criminalintent.database.BookDbSchema.CrimeTable;
import com.example.criminalintent.database.BookBaseHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookLab {
    private static BookLab sBookLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static BookLab get(Context context){
if(sBookLab == null){
    sBookLab = new BookLab(context);
}
return sBookLab;
    }
    private static ContentValues getContentValues(Book book){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.TITLE, book.getTitle());
        values.put(CrimeTable.Cols.UUID, book.getId().toString());
        values.put(CrimeTable.Cols.DATA_STARTING, book.getDateStarting().getTime());
        values.put(CrimeTable.Cols.STATUS1, book.isStatus1() ?1 :0);
        values.put(CrimeTable.Cols.STATUS2, book.isStatus2() ?1 :0);
        values.put(CrimeTable.Cols.STATUS3, book.isStatus3() ?1 :0);
        values.put(CrimeTable.Cols.AUTHOR, book.getTitleOfAuthor());
        values.put(CrimeTable.Cols.NOTE, book.getNote());
        values.put(CrimeTable.Cols.SUMMARY, book.getSummary());
        values.put(CrimeTable.Cols.PAGES, book.getPages());
        values.put(CrimeTable.Cols.DATA_FINISHING, book.getDateFinishing().getTime());
        values.put(CrimeTable.Cols.GENRE, book.getGenre());
        values.put(CrimeTable.Cols.RATING, book.getRating());
        return values;
    }
        public void updateBook(Book book){
        String uuidString = book.getId().toString();
        ContentValues values =  getContentValues(book);
        mDatabase.update(CrimeTable.NAME,values,CrimeTable.Cols.UUID + " = ? ", new String[]{ uuidString});
        }
        private BookCursorWrapper queryBooks(String whereClause, String [] whereArgs){
            Cursor cursor =  mDatabase.query(CrimeTable.NAME,null,whereClause,whereArgs,null,null,null);
        return new BookCursorWrapper(cursor);
        }
    public void addBook(Book book){
        ContentValues values = getContentValues(book);
        mDatabase.insert(CrimeTable.NAME,null,values);
    }
        private BookLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new BookBaseHelper(mContext).getWritableDatabase();
            }

    public List<Book> getBooks() {
        List <Book> books = new ArrayList<>();
        BookCursorWrapper cursor = queryBooks(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                books.add(cursor.getBook());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return books;
    }
    public Book getBook(UUID id) {
        BookCursorWrapper cursor = queryBooks(CrimeTable.Cols.UUID + " = ? " ,new String[]{
                id.toString()
        });
        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBook();

        }finally{
            cursor.close();
        }
    }
    public void deleteBook(Book book){
        mDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID + " = ? ",new  String[]{
            book.getId().toString()
        });
        updateBook(book);
    }
    public void setBooks(List <Book> mBooks){

        List <Book> crimes = mBooks;
        for(int i = 0;i < crimes.size();i++) {
            deleteBook(mBooks.get(i));
        }
            for(int i = 0;i < crimes.size();i++){
            addBook(mBooks.get(i));
        }
    }

}
