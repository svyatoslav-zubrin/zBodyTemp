package com.home.zubrin.zbodytemp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePopupFragment extends android.support.v4.app.DialogFragment {

    public static final String EXTRA_DATE = "home.zubrin.zbodytemp.TimePopupFragment.date";

    // UI
    private TimePicker mTimePicker;
    private Button mOkButton;
    private Button mCancelButton;
    // logic
    private Date mDate;

    public static TimePopupFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        TimePopupFragment fragment = new TimePopupFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public TimePopupFragment() {
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
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_time_popup, container, false);

        getDialog().setTitle(R.string.time_popup_title);

        mTimePicker = (TimePicker)v.findViewById(R.id.time_popup_timePicker);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public
            void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // TODO: construct correct mDate object
                Calendar cal = Calendar.getInstance();
                cal.setTime(mDate);
                int oldYear = cal.get(Calendar.YEAR);
                int oldMonth = cal.get(Calendar.MONTH);
                int oldDay = cal.get(Calendar.DAY_OF_MONTH);
                mDate = new GregorianCalendar(oldYear,
                        oldMonth,
                        oldDay,
                        hourOfDay,
                        minute).getTime();

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        mOkButton = (Button)v.findViewById(R.id.time_popup_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                onOkButtonPressed();
            }
        });

        mCancelButton = (Button)v.findViewById(R.id.time_popup_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelButtonPressed();
            }
        });

        return v;
    }

    // User actions handlers

    private
    void onOkButtonPressed() {
        sendResult(Activity.RESULT_OK);
        getDialog().dismiss();
    }

    private
    void onCancelButtonPressed() {
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
