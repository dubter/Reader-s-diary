package com.example.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookListFragment extends Fragment {
    private RecyclerView mBookRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible = false;
    private Callbacks mCallbacks;
    private static final String DIALOG_SORT = "DialogSort";
    private static final int REQUEST_SORT = 5;
    private static final int CONST = 10;
    private static final String DIALOG_STATISTICS = "DialogStatistics";
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    public interface Callbacks {
        void onBookSelected(Book crime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_book_list, container, false);
        mBookRecyclerView = v.findViewById(R.id.book_recycler_view);
        mBookRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateInstance(savedInstanceState);
        updateUI();
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mBookRecyclerView);
        return v;
    }

    private void updateInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_book_list, menu);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    private void updateSubtitle() {
        String subtitle;
        BookLab bookLab = BookLab.get(getContext());
        int bookCount = bookLab.getBooks().size();
        if (bookCount == 1) {
            subtitle = getString(R.string.subtitle_format1, bookCount);
        } else {
            subtitle = getString(R.string.subtitle_format2, bookCount);
        }
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_sort:
                goToSortST();
                return true;
            case R.id.ic_statistics:
                goToStatistics();
                return true;
            case R.id.new_book:
                Book book = new Book();
                BookLab.get(getActivity()).addBook(book);
                updateUI();
                mCallbacks.onBookSelected(book);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                if (mSubtitleVisible) {
                    item.setTitle(R.string.hide_subtitle);
                } else {
                    item.setTitle(R.string.show_subtitle);
                }
                updateSubtitle();
                return true;
            case R.id.show_info:
                alert();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToSortST() {
        FragmentManager fragmentManager = getFragmentManager();
        SortFragment dialog = new SortFragment();
        dialog.setTargetFragment(BookListFragment.this, REQUEST_SORT);
        dialog.show(fragmentManager, DIALOG_SORT);
    }
    private  void goToStatistics(){
        FragmentManager fragmentManager = getFragmentManager();
        StatisticsFragment dialog = new StatisticsFragment();
        dialog.show(fragmentManager, DIALOG_STATISTICS);
    }

    void updateUI() {
        BookLab bookLab = BookLab.get(getActivity());
        List<Book> books = bookLab.getBooks();
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(books);
            mBookRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(books);
            mAdapter.notifyDataSetChanged();
            updateSubtitle();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mAuthorTextView;
        private Book mBook;
        private TextView mStatusTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_book, parent, false));
            mTitleTextView = itemView.findViewById(R.id.book_title);
            mAuthorTextView = itemView.findViewById(R.id.book_author);
            mStatusTextView = itemView.findViewById(R.id.book_status);
            itemView.setOnClickListener(this);
        }

        public void bind(Book book) {
            mBook = book;
            if ((mBook.getTitle() == null) || (mBook.getTitle().equals(""))) {
                mTitleTextView.setText(R.string.no_name);
            } else mTitleTextView.setText(mBook.getTitle());
            if ((mBook.getTitleOfAuthor() == null) || (mBook.getTitleOfAuthor().equals(""))) {
                mAuthorTextView.setText(R.string.no_authorName);
            } else
                mAuthorTextView.setText(mBook.getTitleOfAuthor());
            if (mBook.isStatus1()) {
                mStatusTextView.setText(R.string.book_finish);
                mStatusTextView.setTextColor(getResources().getColor(R.color.colorRed));
            } else if (mBook.isStatus2()) {
                mStatusTextView.setText(R.string.book_now);
                mStatusTextView.setTextColor(getResources().getColor(R.color.colorLime));
            } else if (mBook.isStatus3()) {
                mStatusTextView.setText(R.string.book_will);
                mStatusTextView.setTextColor(getResources().getColor(R.color.colorBlue));}
                if (mBook.getRating() == 5) {
                    itemView.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                } else itemView.setBackgroundColor(getResources().getColor(R.color.colorWhite));

        }
        @Override
        public void onClick(View v) {
            mCallbacks.onBookSelected(mBook);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> implements ItemTouchHelperAdapter {
        private List<Book> mBooks;

        public CrimeAdapter(List<Book> books) {
            mBooks = books;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }

        public void setCrimes(List<Book> books) {
            mBooks = books;
        }

        @Override
        public void onItemDismiss(int position) {
            BookLab.get(getContext()).deleteBook(mBooks.get(position));
            mBooks.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mBooks, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mBooks, i, i - 1);
                }
            }
            BookLab.get(getContext()).setBooks(mBooks);
            notifyItemMoved(fromPosition, toPosition);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int k = 0;
        List<Book> ListBooks = new ArrayList();
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_SORT) {
            int f = intent.getIntExtra(SortFragment.EXTRA_SORT2, CONST);
            if (f == 1) {
                ArrayList<String> list = intent.getStringArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0)
                    if (list.get(i).equals(list.get(i-1))){
                        k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        if (list.get(i).equals(BookLab.get(getContext()).getBooks().get(k).getTitle())) {
                            ListBooks.add( BookLab.get(getContext()).getBooks().get(k));
                            break;
                        }
                        k = k + 1;
                        }
                    }
            } else if (f == 2) {
                ArrayList<String> list = intent.getStringArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0)
                    if (list.get(i).equals(list.get(i-1))){
                        k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        if (list.get(i).equals(BookLab.get(getContext()).getBooks().get(k).getTitleOfAuthor())) {
                            ListBooks.add(BookLab.get(getContext()).getBooks().get(k));
                            break;
                        }
                        k = k + 1;
                    }
                }
            } else if (f == 3) {
                ArrayList<String> list = intent.getStringArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0)
                    if (list.get(i).equals(list.get(i-1))){
                        k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        if (list.get(i).equals("1")) {
                            if (BookLab.get(getContext()).getBooks().get(k).isStatus1()) {
                                ListBooks.add( BookLab.get(getContext()).getBooks().get(k));
                                break;}
                            } else if (list.get(i).equals("2")) {
                                if (BookLab.get(getContext()).getBooks().get(k).isStatus2()) {
                                    ListBooks.add( BookLab.get(getContext()).getBooks().get(k));
                                    break;}
                                } else if (list.get(i).equals("3")) {
                                    if (BookLab.get(getContext()).getBooks().get(k).isStatus3()) {
                                        ListBooks.add( BookLab.get(getContext()).getBooks().get(k));
                                        break;}
                                    }
                        k = k + 1;
                    }
                }
            } else if (f == 4) {
                String dateFormS;
                String dateFormat = "yyyyMMdd";
                ArrayList<String> list = intent.getStringArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0)
                    if (Integer.parseInt(list.get(i)) == Integer.parseInt(list.get(i-1))){
                        k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        dateFormS = DateFormat.format(dateFormat, BookLab.get(getContext()).getBooks().get(k).getDateStarting()).toString();
                        if (list.get(i).equals(dateFormS)) {
                            ListBooks.add(i,BookLab.get(getContext()).getBooks().get(k));
                            Log.d(SortFragment.EXTRA_SORT1,ListBooks.toString());
                            break;
                        }
                        k = k + 1;
                    }
                }
            } else if (f == 5) {
                ArrayList<String> list = intent.getStringArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0)
                    if (list.get(i).equals(list.get(i-1))){
                        k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        if (Integer.parseInt(list.get(i)) == BookLab.get(getContext()).getBooks().get(k).getRating()) {
                            ListBooks.add(BookLab.get(getContext()).getBooks().get(k));
                            break;
                        }
                        k = k + 1;
                    }
                }
            } else if (f == 6) {
                ArrayList<String> list = intent.getStringArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0)
                    if (list.get(i).equals(list.get(i-1))){
                        k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        if (list.get(i).equals(BookLab.get(getContext()).getBooks().get(k).getGenre())) {
                            ListBooks.add(BookLab.get(getContext()).getBooks().get(k));
                            break;
                        }
                        k = k + 1;
                    }
                }
            } else if (f == 7) {
                ArrayList <Integer> listIn = intent.getIntegerArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < listIn.size(); i++) {
                    if (i != 0)
                    if (listIn.get(i).equals(listIn.get(i-1))){
                        k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        if (listIn.get(i) == (BookLab.get(getContext()).getBooks().get(k).getPages())) {
                            ListBooks.add(BookLab.get(getContext()).getBooks().get(k));
                            break;
                        }
                        k = k + 1;
                    }
                }
            }
            else if (f == 8) {
                String dateFormS;
                String dateFormat = "yyyyMMdd";
                ArrayList<String> list = intent.getStringArrayListExtra(SortFragment.EXTRA_SORT1);
                for (int i = 0; i < list.size(); i++) {
                    if (i != 0)
                        if (list.get(i).equals(list.get(i-1))){
                            k = k + 1;} else k = 0;
                    while (k < BookLab.get(getContext()).getBooks().size()){
                        dateFormS = DateFormat.format(dateFormat, BookLab.get(getContext()).getBooks().get(k).getDateFinishing()).toString();
                        if (list.get(i).equals(dateFormS)) {
                            ListBooks.add(BookLab.get(getContext()).getBooks().get(k));
                            break;
                        }
                        k = k + 1;
                    }
                }
            }
            BookLab.get(getContext()).setBooks(ListBooks);
            updateUI();
        }
    }
    private void alert() {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(getActivity());
        a_builder.setMessage(R.string.information).setNeutralButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = a_builder.create();
        alert.setTitle(R.string.message);
        alert.show();
    }
}
