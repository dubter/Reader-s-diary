package com.example.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortFragment extends DialogFragment {
    public static final String EXTRA_SORT1 = "com.example1";
    public static final String EXTRA_SORT2 = "com.example2";
    private int f;
    private RadioButton mSortAuthor, mSortTitle, mSortStatus, mSortDate,
            mSortRating, mSortGenre, mSortPages, mSortDateFinish;
    private List <String> ListST;
    private List <Integer> ListIn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_sort,null);
        mSortTitle = v.findViewById(R.id.radioButton1);
        mSortAuthor = v.findViewById(R.id.radioButton2);
        mSortStatus = v.findViewById(R.id.radioButton3);
        mSortDate = v.findViewById(R.id.radioButton4);
        mSortRating = v.findViewById(R.id.radioButton5);
        mSortGenre = v.findViewById(R.id.radioButton6);
        mSortPages = v.findViewById(R.id.radioButton7);
        mSortDateFinish = v.findViewById(R.id.radioButton8);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.sort)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mSortTitle.isChecked()) {
                                    f = 1;
                                    sort(mSortTitle);
                                } else if (mSortAuthor.isChecked()) {
                                    f = 2;
                                    sort(mSortAuthor);
                                } else if (mSortStatus.isChecked()) {
                                    f = 3;
                                    sort(mSortStatus);
                                } else if (mSortDate.isChecked()) {
                                    f = 4;
                                    sort(mSortDate);
                                } else if (mSortRating.isChecked()) {
                                    f = 5;
                                    sort(mSortRating);
                                } else if (mSortGenre.isChecked()) {
                                    f = 6;
                                    sort(mSortGenre);
                                } else if (mSortPages.isChecked()) {
                                    f = 7;
                                    sort(mSortPages);
                                } else if (mSortDateFinish.isChecked()) {
                                    f = 8;
                                    sort(mSortDateFinish);
                                } else  dialog.cancel();
                            }})
                .create();
    }

        private void sendResult(int resultCode, ArrayList list){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SORT1,list);
        intent.putExtra(EXTRA_SORT2,f);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode, intent);
    }
    private void sort(View view){
        if(view == mSortTitle){
            ListST = new ArrayList<>();
            for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
            if (BookLab.get(getContext()).getBooks().get(i).getTitle() == null){
                ListST.add(getString(R.string.no_name));
            } else
                ListST.add(BookLab.get(getContext()).getBooks().get(i).getTitle());
            }
        } else if (view == mSortAuthor){
            ListST = new ArrayList<>();
            for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
                if (BookLab.get(getContext()).getBooks().get(i).getTitleOfAuthor() == null){
                    ListST.add(getString(R.string.no_authorName));
                } else
                ListST.add(BookLab.get(getContext()).getBooks().get(i).getTitleOfAuthor());
            }} else if (view == mSortStatus){
            ListST = new ArrayList<>();
            String k = null;
                for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
                    if(BookLab.get(getContext()).getBooks().get(i).isStatus1()) k = "1";
                    else if (BookLab.get(getContext()).getBooks().get(i).isStatus2()) k = "2";
                    else if (BookLab.get(getContext()).getBooks().get(i).isStatus3()) k = "3";
                    ListST.add(k);
                }} else if (view == mSortDate){
            ListST = new ArrayList<>();
            String dateFormat = "yyyyMMdd";
            for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
                String dateFormS = DateFormat.format(dateFormat,BookLab.get(getContext()).getBooks().get(i).getDateStarting()).toString();
                    ListST.add(dateFormS);
                }}
        else if (view == mSortDateFinish){
            ListST = new ArrayList<>();
            String dateFormat = "yyyyMMdd";
            for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
                String dateFormS = DateFormat.format(dateFormat,BookLab.get(getContext()).getBooks().get(i).getDateFinishing()).toString();
                ListST.add(dateFormS);
            }}
        else if (view == mSortRating){
            ListST = new ArrayList<>();
            for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
                ListST.add(Integer.toString(BookLab.get(getContext()).getBooks().get(i).getRating()));
            }} else if (view == mSortGenre){
            ListST = new ArrayList<>();
            for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
                ListST.add(BookLab.get(getContext()).getBooks().get(i).getGenre());
            }}
        else if (view == mSortPages){
            ListIn = new ArrayList<>();
            for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
                ListIn.add(BookLab.get(getContext()).getBooks().get(i).getPages());}
            }
                if (view == mSortPages){
                    Collections.sort(ListIn);
                    sendResult(Activity.RESULT_OK, (ArrayList) ListIn); }

                else{
                    Log.d(ListST.toString(),EXTRA_SORT1);
                    Collections.sort(ListST);
            Log.d(ListST.toString(),EXTRA_SORT1);
            sendResult(Activity.RESULT_OK, (ArrayList) ListST);}
        }
    }