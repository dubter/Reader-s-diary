package com.example.criminalintent.database;

public class BookDbSchema {
    public static final class CrimeTable{
        public static final String NAME = "crimes";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATA_STARTING = "date_starting";
            public static final String DATA_FINISHING = "date_finishing";
            public static final String AUTHOR = "author";
            public static final String SUMMARY = "summary";
            public static final String NOTE = "note";
            public static final String STATUS1 = "status1";
            public static final String STATUS2 = "status2";
            public static final String STATUS3 = "status3";
            public static final String PAGES = "pages";
            public static final String GENRE = "genre";
            public static final String RATING = "rating";
        }
    }
}
