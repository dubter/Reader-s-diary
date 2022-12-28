package com.example.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.List;
import java.util.UUID;

public class BookPagerActivity extends AppCompatActivity
        implements  BookFragment.Callbacks{
    private ViewPager mViewPager;
    private List<Book> mBooks;
    private static final String EXTRA_CRIME_ID = "crimeID";
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_book_pager);
        mViewPager = findViewById(R.id.crime_view_pager);
        mBooks = BookLab.get(this).getBooks();
        UUID ID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                       Book crime = mBooks.get(i);
                return BookFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mBooks.size();
            }
        });
        for(int i = 0; i < mBooks.size(); i++){
            if(mBooks.get(i).getId().equals(ID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, BookPagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }

    @Override
    public void onCrimeUpdated(Book crime) {

    }
}