package com.example.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class BookListActivity extends SingleFragmentActivity implements  BookListFragment.Callbacks, BookFragment.Callbacks{
    @Override
    protected Fragment createFragment() {
        return new BookListFragment();
    }
    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onBookSelected(Book book) {
        if(findViewById(R.id.detail_fragment_container) == null){
            Intent intent = BookPagerActivity.newIntent(this,book.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = BookFragment.newInstance(book.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container,newDetail)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Book book) {
        BookListFragment listFragment = (BookListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
