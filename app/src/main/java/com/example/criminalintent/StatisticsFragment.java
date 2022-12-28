package com.example.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class StatisticsFragment extends DialogFragment {
    public static final String EXTRA_STAT = "com.example";
    private TextView mTextView1, mTextView2,mTextView3,mTextView4,mTextView5,
    mTextView6,mTextView7,mTextView8,mTextView9,mTextView10;
    int k1, k2, k3, max,index, pages;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_statistics,null);
        mTextView1 = v.findViewById(R.id.textView11);
        mTextView2 = v.findViewById(R.id.textView12);
        mTextView3 = v.findViewById(R.id.textView13);
        mTextView4 = v.findViewById(R.id.textView14);
        mTextView5 = v.findViewById(R.id.textView15);
        mTextView6 = v.findViewById(R.id.textView16);
        mTextView7 = v.findViewById(R.id.textView17);
        mTextView8 = v.findViewById(R.id.textView18);
        mTextView9 = v.findViewById(R.id.textView19);
        mTextView10 = v.findViewById(R.id.textView20);
        Log.d(Integer.toString(BookLab.get(getContext()).getBooks().size()),EXTRA_STAT);
        mTextView1.setText(getString(R.string.stat1)+" "+BookLab.get(getContext()).getBooks().size());
        for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++){
            if(BookLab.get(getContext()).getBooks().get(i).isStatus1()){
                k1 = k1 + 1;
            } else if (BookLab.get(getContext()).getBooks().get(i).isStatus2()){
                k2 = k2 + 1;
            } else if (BookLab.get(getContext()).getBooks().get(i).isStatus3()){
                k3 = k3 + 1;
            }
        }
        mTextView2.setText(getString(R.string.stat2)+" "+k1);
        mTextView3.setText(getString(R.string.stat3)+" "+k2);
        mTextView4.setText(getString(R.string.stat4)+" "+k3);
        max = 0;
        index = 0;
        for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++) {
            if (BookLab.get(getContext()).getBooks().get(i).getTitleOfAuthor() != null)
            if (!BookLab.get(getContext()).getBooks().get(i).getTitleOfAuthor().equals("")){
                Log.d(SortFragment.EXTRA_SORT1,Integer.toString(i));
                k1 = 0;
            for (int j = 0; j < BookLab.get(getContext()).getBooks().size(); j++) {
                    if (BookLab.get(getContext()).getBooks().get(i).getTitleOfAuthor().
                            equals(BookLab.get(getContext()).getBooks().get(j).getTitleOfAuthor())){
                        k1 = k1 + 1;
                    }
                    if (max < k1){
                        max = k1;
                        index = i;
                    }
            }}
            }
        mTextView5.setText(getString(R.string.stat5)+" "+BookLab.get(getContext()).getBooks().get(index).getTitleOfAuthor());
        for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++) {
                pages = pages + BookLab.get(getContext()).getBooks().get(i).getPages();
        }
        mTextView6.setText(getString(R.string.stat6)+" "+pages);
        max = 0;
        for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++) {
            if (BookLab.get(getContext()).getBooks().get(i).getPages() > max){
                max = BookLab.get(getContext()).getBooks().get(i).getPages();
            }
        }
        mTextView7.setText(getString(R.string.stat7)+" "+max);
        k1 = 0;
        max = 0;
        for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++) {
            k1 = -1;
            for (int j = 0; j < BookLab.get(getContext()).getBooks().size(); j++) {
                if (BookLab.get(getContext()).getBooks().get(i).getGenre().
                        equals(BookLab.get(getContext()).getBooks().get(j).getGenre())){
                    k1 = k1 + 1;
                }
                if (max < k1){
                    max = k1;
                    index = i;
                }
            }
        }
        String text = BookLab.get(getContext()).getBooks().get(index).getGenre();
        text.toUpperCase();
        mTextView8.setText(getString(R.string.stat8)+" "+text);
        k2 = 0;
        Float result;
        for (int i = 0; i < BookLab.get(getContext()).getBooks().size(); i++) {
           k2 = k2 + BookLab.get(getContext()).getBooks().get(i).getRating();
        }
        Float k = (float) k2;
        result =  (k / BookLab.get(getContext()).getBooks().size());
        mTextView9.setText(getString(R.string.stat9)+" "+result);
            return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.statistics)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                            }
                        })
                .create();
    }
}