package com.home.zubrin.zbodytemp;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.zubrin.zbodytemp.Model.Record;
import com.home.zubrin.zbodytemp.Utils.NumericUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.home.zubrin.zbodytemp.TempPopupFragment.OnTemperatureSelectionListener} interface
 * to handle interaction events.
 */
public class TempPopupFragment extends android.support.v4.app.DialogFragment {

    public static final String DIALOG_TIME_TAG = "zBodyTemp.tag.dialog_time_tag";
    public static final String DIALOG_DATE_TAG = "zBodyTemp.tag.dialog_date_tag";

    public static final int DIALOG_TIME_REQUEST_CODE = 0;
    public static final int DIALOG_DATE_REQUEST_CODE = 1;

    private EditText mTempEditText;
    private Button mTimeButton;
    private Button mDateButton;
    private Button mOkButton;
    private Button mCancelButton;

    private OnTemperatureSelectionListener mListener;

    public TempPopupFragment() {
        // Required empty public constructor
    }

    @Override
    public
    View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_temp_popup, container, false);

        getDialog().setTitle(R.string.temp_popup_title);

        mTempEditText = (EditText)v.findViewById(R.id.temp_popup_tempEditText);
        mTimeButton = (Button)v.findViewById(R.id.temp_popup_time_button);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                onTimeButtonPressed();
            }
        });
        mDateButton = (Button)v.findViewById(R.id.temp_popup_date_button);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                onDateButtonPressed();
            }
        });
        mOkButton = (Button)v.findViewById(R.id.temp_popup_ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                onOkButtonPressed();
            }
        });
        mCancelButton = (Button)v.findViewById(R.id.temp_popup_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                onCancelButtonPressed();
            }
        });

        return v;
    }

    @Override
    public
    void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTemperatureSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTemperatureSelectionListener");
        }
    }

    @Override
    public
    void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // User actions handlers

    private
    void onOkButtonPressed() {
        if (mListener != null) {
            if (validateForm()) {
                Float value = Float.parseFloat(mTempEditText.getText().toString());
                getDialog().hide();
                mListener.onTemperatureSelected(value);
            } else {
                Toast.makeText(getActivity(),
                        R.string.validation_failed_message,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private
    void onCancelButtonPressed() {
        if (mListener != null) {
            getDialog().hide();
            mListener.onTemperatureCanceled();
        }
    }

    private
    void onTimeButtonPressed() {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        TimePopupFragment f = new TimePopupFragment();
        f.setTargetFragment(this, DIALOG_TIME_REQUEST_CODE);
        f.show(fm, DIALOG_TIME_TAG);
    }

    private
    void onDateButtonPressed() {
        android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
        DatePopupFragment f = new DatePopupFragment();
        f.setTargetFragment(this, DIALOG_DATE_REQUEST_CODE);
        f.show(fm, DIALOG_DATE_TAG);
    }

    // Private

    private
    Boolean validateForm() {
        Boolean isValid = false;

        String tempString = mTempEditText.getText().toString();
        if (NumericUtils.isDouble(tempString)) {
            Float val = Float.parseFloat(tempString);
            if (val <= Record.MaxTemp && val >= Record.MinTemp) {
                isValid = true;
            }
        }

        return isValid;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTemperatureSelectionListener {
        public void onTemperatureSelected(Float temperature);
        public void onTemperatureCanceled();
    }
}
