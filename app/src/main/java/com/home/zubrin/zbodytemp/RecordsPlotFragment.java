package com.home.zubrin.zbodytemp;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.Plot;
import com.androidplot.PlotListener;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.home.zubrin.zbodytemp.Interfaces.OnCardChangedListener;
import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Model.Record;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordsPlotFragment
        extends Fragment
        implements OnCardChangedListener, PlotListener
{
    private static final String ARG_SECTION_NUMBER = "home.zubrin.zbodytemp.RecordsPlotFragment.section_number";
    private static final String ARG_PERSON_ID = "home.zubrin.zbodytemp.RecordsPlotFragment.person_id";

    // UI
    private XYPlot mXYPlot;
    private Boolean isGradientAdded = false;
    // model
    private UUID mPersonId;
    private ArrayList<Float> mTemperatures = new ArrayList<>();

    public static RecordsPlotFragment newInstance(int sectionNumber, UUID personId) {
        RecordsPlotFragment fragment = new RecordsPlotFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ARG_PERSON_ID, personId);
        fragment.setArguments(args);
        return fragment;
    }

    public RecordsPlotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fetch data
        mPersonId = (UUID)getArguments().getSerializable(ARG_PERSON_ID);
        fetchTemperatures();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_records_plot, container, false);

        // Prepare plot
        mXYPlot = (XYPlot)v.findViewById(R.id.mySimpleXYPlot);
        configurePlot();
        feedPlotWithData();
        mXYPlot.addListener(this);

        return v;
    }

    @Override
    public void onCardChanged() {
        fetchTemperatures();
        mXYPlot.clear();
        feedPlotWithData();
        mXYPlot.redraw();
    }

    // Data management

    private void fetchTemperatures() {
        Person p = Persons.sharedInstance.findPersonById(mPersonId);
        if (p != null) {
            ArrayList<Record> records = p.getCard().getRecords();
            mTemperatures.clear();
            for (Record r: records) {
                mTemperatures.add(r.getValue());
            }
        }

        Log.d("RecordsPlotFragment", "Temperatures.count = " + mTemperatures.size());
    }

    // Plot management

    private void configurePlot() {
        // default plot settings
        mXYPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        mXYPlot.setPlotMargins(0, 0, 0, 0);
        mXYPlot.setPlotPadding(0, 0, 0, 0);
        mXYPlot.setRangeStepMode(XYStepMode.INCREMENT_BY_VAL);
        mXYPlot.setRangeStepValue(1);
        mXYPlot.setRangeBoundaries(Record.MinTemp, Record.MaxTemp, BoundaryMode.FIXED);
        mXYPlot.setRangeValueFormat(new DecimalFormat("##.# "));
        mXYPlot.setTicksPerRangeLabel(1);
    }

    private  void configurePlotGradient() {
        // Gradient background
        RectF graphRect = mXYPlot.getGraphWidget().getGridRect();
        if (graphRect != null) {
            int segmentsCount = (int)(Record.MaxTemp - Record.MinTemp);
            float segmentSize = 1.0f / segmentsCount;
            LinearGradient lg = new LinearGradient(
                    0,
                    graphRect.top,
                    0,
                    graphRect.bottom,
                    new int[]{
                            Color.RED,
                            Color.WHITE,
                            Color.WHITE,
                            Color.BLUE},
                    new float[]{
                            0,
                            segmentSize * (Record.MaxTemp - 37.0f),
                            segmentSize * (Record.MaxTemp - 36.0f),
                            segmentSize * segmentsCount
                    },
                    Shader.TileMode.CLAMP
            );
            mXYPlot.getGraphWidget().getGridBackgroundPaint().setShader(lg);
        }
    }

    private void feedPlotWithData() {
        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = mTemperatures.toArray(new Number[mTemperatures.size()]);

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getActivity().getApplicationContext(),
                R.xml.line_point_formatter_with_plf1);

        // add a new series' to the xyplot:
        mXYPlot.addSeries(series1, series1Format);
    }

    // Plot lifecycle callbacks

    @Override
    public void onBeforeDraw(Plot source, Canvas canvas) {
    }

    @Override
    public void onAfterDraw(Plot source, Canvas canvas) {
        if (isGradientAdded == false) {
            configurePlotGradient();
            source.redraw();
            isGradientAdded = true;
        }
    }
}
