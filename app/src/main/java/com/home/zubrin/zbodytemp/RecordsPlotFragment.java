package com.home.zubrin.zbodytemp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.home.zubrin.zbodytemp.Interfaces.OnCardChangedListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsPlotFragment
        extends Fragment
        implements OnCardChangedListener
{

    public RecordsPlotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_records_plot, container, false);
    }

    @Override
    public void onCardChanged() {
        Log.d("RecordsPlotFragment", "onCardChanged called");
    }
}
