package com.home.zubrin.zbodytemp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePopupFragment extends android.support.v4.app.DialogFragment {

    private Button mOkButton;
    private Button mCancelButton;

    public TimePopupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_time_popup, container, false);

        getDialog().setTitle(R.string.time_popup_title);

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
//        if (mListener != null) {
//            if (validateForm()) {
//                Float value = Float.parseFloat(mTempEditText.getText().toString());
//                getDialog().hide();
//                mListener.onTemperatureSelected(value);
//            } else {
//                Toast.makeText(getActivity(),
//                        R.string.validation_failed_message,
//                        Toast.LENGTH_LONG).show();
//            }
//        }
    }

    private
    void onCancelButtonPressed() {
        getDialog().hide();
    }
}
