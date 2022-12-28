package com.example.criminalintent;
import java.util.Date;
import java.util.UUID;

public class Book {
    private UUID mId;
    private String mTitle,mTitleOfAuthor, mSummary, mNote, mGenre;
    private Date mDateStarting, mDateFinishing;
    private int mPages, mRating;
    private boolean mStatus1, mStatus2 , mStatus3;
        public Book(){
            this(UUID.randomUUID());
      }
    public Book(UUID id){
        mId = id;
        mDateStarting = new Date();
        mDateFinishing = new Date();
    }
    public String getTitleOfAuthor() {
        return mTitleOfAuthor;
    }
    public void setTitleOfAuthor(String titleOfAuthor) {
        mTitleOfAuthor = titleOfAuthor;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDateStarting() {
        return mDateStarting;
    }

    public void setDateStarting(Date date) {
        mDateStarting = date;
    }

    public Date getDateFinishing() {
        return mDateFinishing;
    }

    public void setDateFinishing(Date date) {
        mDateFinishing = date;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public int getPages() {
        return mPages;
    }

    public void setPages(int pages) {
        mPages = pages;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public boolean isStatus1() {
        return mStatus1;
    }

    public void setStatus1(boolean status) {
        mStatus1 = status;
    }

    public boolean isStatus2() {
        return mStatus2;
    }

    public void setStatus2(boolean status2) {
        mStatus2 = status2;
    }

    public boolean isStatus3() {
        return mStatus3;
    }

    public void setStatus3(boolean status3) {
        mStatus3 = status3;
    }
}
