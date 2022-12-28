package com.example.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import java.util.Date;
import java.util.UUID;

public class BookFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private Book mBook;
    private EditText mAuthorField, mTitleField, mSummaryField, mNoteField, mPagesField;
    private Button mStartedDateButton, mFinishedDateButton, mDeleteButton, mReportButton;
    private RadioButton mRadioButton1, mRadioButton2, mRadioButton3;
    private RatingBar mRatingST;
    private Spinner mGenreList;
    private Callbacks mCallbacks;
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_DATE2 = "DialogDate2";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_DATE2 = 3;

public interface  Callbacks {
    void onCrimeUpdated(Book book);
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID bookId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mBook = BookLab.get(getActivity()).getBook(bookId);
    }
    @Override
    public void onPause() {
        super.onPause();
        BookLab.get(getActivity()).updateBook(mBook);
    }
 @Override
 public void onAttach (Context context) {
     super.onAttach(context);
     mCallbacks = (Callbacks) context;
 }
 @Override
 public void onDetach() {
    super.onDetach();
     mCallbacks = null;
 }
 private void updateCrime (){
    BookLab.get(getActivity()).updateBook(mBook);
    mCallbacks.onCrimeUpdated(mBook);
 }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book, container, false);
        mTitleField = v.findViewById(R.id.book_title);
        mTitleField.setText(mBook.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBook.setTitle(s.toString());
                updateCrime();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mAuthorField = v.findViewById(R.id.book_author);
        mAuthorField.setText(mBook.getTitleOfAuthor());
        mAuthorField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBook.setTitleOfAuthor(s.toString());
                updateCrime();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mStartedDateButton = v.findViewById(R.id.starting_date);
        mStartedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mBook.getDateStarting());
                dialog.setTargetFragment(BookFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });
        mFinishedDateButton = v.findViewById(R.id.finishing_date);
        mFinishedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mBook.getDateFinishing());
                dialog.setTargetFragment(BookFragment.this, REQUEST_DATE2);
                dialog.show(fragmentManager, DIALOG_DATE2);
            }
        });
        mPagesField =  v.findViewById(R.id.pages);
        if (0 != mBook.getPages()){
        mPagesField.setText(Integer.toString(mBook.getPages()));}
        mPagesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    mBook.setPages(Integer.parseInt(s.toString()));
                    updateCrime(); } 
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mGenreList = v.findViewById(R.id.genre);
        ArrayAdapter adapter = (ArrayAdapter) mGenreList.getAdapter();
        int position = adapter.getPosition(mBook.getGenre());
        mGenreList.setSelection(position);
        mGenreList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.book_genre);
                mBook.setGenre(choose[selectedItemPosition]);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSummaryField = v.findViewById(R.id.summary);
        mSummaryField.setText(mBook.getSummary());
        mSummaryField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBook.setSummary(s.toString());
                updateCrime();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mNoteField = v.findViewById(R.id.note);
        mNoteField.setText(mBook.getNote());
        mNoteField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBook.setNote(s.toString());
                updateCrime();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mRatingST = v.findViewById(R.id.rating);
        mRatingST.setRating(mBook.getRating());
        mRatingST.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                mBook.setRating(Integer.valueOf((int) rating));
            }
        });
        mRadioButton1 = v.findViewById(R.id.radioButton1);
        mRadioButton2 = v.findViewById(R.id.radioButton2);
        mRadioButton3 = v.findViewById(R.id.radioButton3);
        mRadioButton1.setOnClickListener(radioButtonClickListener);
        mRadioButton2.setOnClickListener(radioButtonClickListener);
        mRadioButton3.setOnClickListener(radioButtonClickListener);
        if (mBook.isStatus1()) {
            mRadioButton1.setChecked(true);
        } else if (mBook.isStatus2()){
            mRadioButton2.setChecked(true);
        }   else if (mBook.isStatus3()){
            mRadioButton3.setChecked(true);
        }
        else {
            mRadioButton3.setChecked(true);
            mBook.setStatus1(false);
            mBook.setStatus2(false);
            mBook.setStatus3(true);
            updateCrime();
        }

        mReportButton = v.findViewById(R.id.book_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });
        mDeleteButton = v.findViewById(R.id.book_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookLab.get(getContext()).deleteBook(mBook);
                getActivity().finish();
            }
        });
              updateDate();
                return v;
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.radioButton1:
                    mBook.setStatus1(true);
                    mBook.setStatus2(false);
                    mBook.setStatus3(false);
                    updateCrime();
                    break;
                case R.id.radioButton2:
                    mBook.setStatus1(false);
                    mBook.setStatus2(true);
                    mBook.setStatus3(false);
                    updateCrime();
                    break;
                    case R.id.radioButton3:
                        mBook.setStatus3(true);
                        mBook.setStatus2(false);
                        mBook.setStatus1(false);
                        break;
            }
        }
    };
    public static BookFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, id);
        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String dateFormat = "MMM dd, yyyy";
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBook.setDateStarting(date);
            updateCrime();
            String dateFormS = DateFormat.format(dateFormat, mBook.getDateStarting()).toString();
            mStartedDateButton.setText(dateFormS);
        } else if (requestCode == REQUEST_DATE2) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBook.setDateFinishing(date);
            updateCrime();
            String dateFormF = DateFormat.format(dateFormat, mBook.getDateFinishing()).toString();
            mFinishedDateButton.setText(dateFormF);
        }
        else{
            return;
        }
    }


    private void updateDate() {
        String dateFormat = "MMM dd, yyyy";
        String dateFormS = DateFormat.format(dateFormat, mBook.getDateStarting()).toString();
        String dateFormF = DateFormat.format(dateFormat, mBook.getDateFinishing()).toString();
        mStartedDateButton.setText(dateFormS);
        mFinishedDateButton.setText(dateFormF);
    }

    private String getCrimeReport() {
        String status_string;
        if (mBook.isStatus1() == true){
            status_string = getString(R.string.book_finish);
        }
        else if (mBook.isStatus2() == true) {
            status_string = getString(R.string.book_now);
        }
        else  status_string = getString(R.string.book_will);

        String report;
        if (mBook.getTitle() == null) {
            if (mBook.getTitleOfAuthor() == null){
                report = getString(R.string.reportWithoutName,status_string, getString(R.string.no_author),
                        Integer.toString(mBook.getPages()),mBook.getNote(),Integer.toString(mBook.getRating()));
            }else{
                report = getString(R.string.reportWithoutName,status_string,mBook.getTitleOfAuthor(),
                        Integer.toString(mBook.getPages()), mBook.getNote(),Integer.toString(mBook.getRating()));
        }}else{
                report = getString(R.string.report,status_string,mBook.getTitle(),mBook.getTitleOfAuthor(),
                        Integer.toString(mBook.getPages()), mBook.getNote(),Integer.toString(mBook.getRating()));
        }
        return report;}
}