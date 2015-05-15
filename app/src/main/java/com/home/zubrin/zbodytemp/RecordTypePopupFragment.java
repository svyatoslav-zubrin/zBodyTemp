package com.home.zubrin.zbodytemp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.home.zubrin.zbodytemp.Model.Record;

public class RecordTypePopupFragment extends android.support.v4.app.DialogFragment {

    // UI
    private Button mTemperatureButton;
    private Button mMedicineButton;
    private Button mNoteButton;
    // logic
    private Record.Type mSelectedType;

    public interface OnRecordTypeSelectedListener {
        public void onRecordTypeSelected(Record.Type type);
    }
    OnRecordTypeSelectedListener mListener;

    public RecordTypePopupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRecordTypeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnRecordTypeSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_record_type_popup, container, false);

        getDialog().setTitle(R.string.record_type_popup_title);

        mTemperatureButton = (Button)v.findViewById(R.id.record_type_popup_temperatureButton);
        mTemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedType = Record.Type.TEMPERATURE;
                finishSelection();
            }
        });
        mMedicineButton = (Button)v.findViewById(R.id.record_type_popup_medicineButton);
        mMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedType = Record.Type.MEDICINE;
                finishSelection();
            }
        });
        mNoteButton = (Button)v.findViewById(R.id.record_type_popup_noteButton);
        mNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedType = Record.Type.NOTE;
                finishSelection();
            }
        });

        return v;
    }

    // Private

    private
    void finishSelection() {
        getDialog().dismiss();
        mListener.onRecordTypeSelected(mSelectedType);
    }
}
