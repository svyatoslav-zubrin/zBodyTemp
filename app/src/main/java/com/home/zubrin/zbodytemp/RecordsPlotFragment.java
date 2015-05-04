package com.home.zubrin.zbodytemp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.home.zubrin.zbodytemp.Interfaces.OnCardChangedListener;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsPlotFragment
        extends Fragment
        implements OnCardChangedListener
{
    private XYPlot mXYPlot;

    public RecordsPlotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_records_plot, container, false);

        // initialize our XYPlot reference:
        mXYPlot = (XYPlot)v.findViewById(R.id.mySimpleXYPlot);

        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series2Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getActivity().getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getActivity().getApplicationContext(),
                R.xml.line_point_formatter_with_plf2);


        // add a new series' to the xyplot:
        mXYPlot.addSeries(series1, series1Format);
        mXYPlot.addSeries(series2, series2Format);

        // reduce the number of range labels
        mXYPlot.setTicksPerRangeLabel(3);
        mXYPlot.getGraphWidget().setDomainLabelOrientation(-45);

        return v;
    }

    @Override
    public void onCardChanged() {
        Log.d("RecordsPlotFragment", "onCardChanged called");
    }
}
