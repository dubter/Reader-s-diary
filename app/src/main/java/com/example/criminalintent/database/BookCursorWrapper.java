package com.example.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.criminalintent.database.BookDbSchema.CrimeTable;
import com.example.criminalintent.Book;
import java.util.Date;
import java.util.UUID;

public class BookCursorWrapper extends CursorWrapper {
    public BookCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Book getBook(){
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        String author = getString(getColumnIndex(CrimeTable.Cols.AUTHOR));
        String summary = getString(getColumnIndex(CrimeTable.Cols.SUMMARY));
        String genre = getString(getColumnIndex(CrimeTable.Cols.GENRE));
        String note = getString(getColumnIndex(CrimeTable.Cols.NOTE));
        int Status2 = getInt(getColumnIndex(CrimeTable.Cols.STATUS2));
        int Status3 = getInt(getColumnIndex(CrimeTable.Cols.STATUS3));
        int Status1 = getInt(getColumnIndex(CrimeTable.Cols.STATUS1));
        int rating = getInt(getColumnIndex(CrimeTable.Cols.RATING));
        int pages = getInt(getColumnIndex(CrimeTable.Cols.PAGES));
        Long data_st = getLong(getColumnIndex(CrimeTable.Cols.DATA_STARTING));
        Long data_fi = getLong(getColumnIndex(CrimeTable.Cols.DATA_FINISHING));
        Book book = new Book(UUID.fromString(uuidString));
        book.setTitle(title);
        book.setTitleOfAuthor(author);
        book.setSummary(summary);
        book.setGenre(genre);
        book.setNote(note);
        book.setRating(rating);
        book.setPages(pages);
       book.setDateFinishing(new Date(data_fi));
        book.setDateStarting(new Date(data_st));
        book.setStatus1(Status1 == 1);
        book.setStatus2(Status2 == 1);
        book.setStatus3(Status3 == 1);
        return book;
    }
}
