package com.home.zubrin.zbodytemp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePopupFragment extends android.support.v4.app.DialogFragment {

    public static final String EXTRA_DATE = "home.zubrin.zbodytemp.DatePopupFragment.date";

    // UI
    private DatePicker mDatePicker;
    private Button mOkButton;
    private Button mCancelButton;
    // logic
    private Date mDate;

    public static DatePopupFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePopupFragment fragment = new DatePopupFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public DatePopupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get arguments (date)
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);

        // Create a Calendar to get the year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_date_popup, container, false);

        getDialog().setTitle(R.string.date_popup_title);

        mDatePicker = (DatePicker) v.findViewById(R.id.date_popup_datePicker);
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(mDate);
                int oldHours = cal.get(Calendar.HOUR_OF_DAY);
                int oldMinutes = cal.get(Calendar.MINUTE);
                mDate = new GregorianCalendar(year,
                        monthOfYear,
                        dayOfMonth,
                        oldHours,
                        oldMinutes).getTime();

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        mOkButton = (Button) v.findViewById(R.id.date_popup_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkButtonPressed();
            }
        });

        mCancelButton = (Button) v.findViewById(R.id.date_popup_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonPressed();
            }
        });

        return v;
    }

    // User actions handlers

    private void onOkButtonPressed() {
        sendResult(Activity.RESULT_OK);
        getDialog().dismiss();
    }

    private void onCancelButtonPressed() {
        getDialog().dismiss();
    }

    // Private

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
