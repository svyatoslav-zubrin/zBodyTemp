package com.home.zubrin.zbodytemp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.home.zubrin.zbodytemp.Interfaces.OnCardChangedListener;
import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Model.Record;
import com.home.zubrin.zbodytemp.Utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public
class RecordsListFragment
        extends ListFragment
        implements OnCardChangedListener
{
    private static final String ARG_SECTION_NUMBER = "home.zubrin.zbodytemp.RecordsListFragment.section_number";
    private static final String ARG_PERSON_ID = "home.zubrin.zbodytemp.RecordsListFragment.person_id";

    private int HEADER_VIEW_TYPE = 0;
    private int CELL_VIEW_TYPE   = 1;

    private ArrayList<Record> mRecords = new ArrayList<Record>();
    private ArrayList<Object> mListData = new ArrayList<>();
    private ArrayList<Integer> mHeaderIndexes = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    private SimpleDateFormat mHeaderTitleFormatter = new SimpleDateFormat("EEEE (d LLLL)");

    public static RecordsListFragment newInstance(int sectionNumber, UUID personId) {
        RecordsListFragment fragment = new RecordsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ARG_PERSON_ID, personId);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecordsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fetchData();
        RecordsAndHeadersAdapter adapter = new RecordsAndHeadersAdapter(mListData);
        setListAdapter(adapter);
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // List management

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    // Callbacks

    @Override
    public
    void onCardChanged() {
        fetchData();
        ((RecordsAndHeadersAdapter)getListAdapter()).notifyDataSetChanged();
    }


    // Custom list adapter

    private
    class RecordsAndHeadersAdapter extends ArrayAdapter<Object> {

        private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - d LLL (EEE)");

        public RecordsAndHeadersAdapter(ArrayList<Object> recordsAndHeaders) {
            super(getActivity(), 0, recordsAndHeaders);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (!isHeaderPosition(position)) {
                if (convertView == null) {
                    convertView = getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.activity_card_records_row, null);
                }

                Record r = (Record)getItem(position);

                TextView titleTextView =
                        (TextView) convertView.findViewById(R.id.activity_card_records_row_tempTextView);
                titleTextView.setText(r.getValue().toString() + " \u00B0C");

                TextView dateTextView =
                        (TextView) convertView.findViewById(R.id.activity_card_records_row_timeTextView);
                dateTextView.setText(sdf.format(r.getDate()));
            }
            else {
                if (convertView == null) {
                    convertView = getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.activity_card_section_header, null);
                }

                String title = (String)getItem(position);

                TextView titleTextView =
                        (TextView) convertView.findViewById(R.id.activity_card_section_header_textView);
                titleTextView.setText(title);
            }

            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return isHeaderPosition(position) ? HEADER_VIEW_TYPE : CELL_VIEW_TYPE ;
        }
    }

    private
    void fetchData() {
        UUID personId = (UUID)getArguments().getSerializable(ARG_PERSON_ID);
        Person p = Persons.getSharedInstance(getActivity()).findPersonById(personId);
        if (p != null) {
            mRecords = p.getCard().getRecords();
        }

        mHeaderIndexes.clear();
        mListData.clear();

        if (mRecords.size() == 0) {
            return;
        }

        Date previousSectionDate = DateUtils.startOfDay(mRecords.get(0).getDate());
        mHeaderIndexes.add(0);
        mListData.add(mHeaderTitleFormatter.format(previousSectionDate));

        for (int i = 0; i < mRecords.size(); i++) {
            Record currentRecord = mRecords.get(i);
            Date currentSectionDate = DateUtils.startOfDay(currentRecord.getDate());
            if (!previousSectionDate.equals(currentSectionDate)) {
                // add new section header
                previousSectionDate = currentRecord.getDate();
                mHeaderIndexes.add(mHeaderIndexes.size() + i);
                mListData.add(mHeaderTitleFormatter.format(previousSectionDate));
            }
            // add the new record themselves
            mListData.add(currentRecord);
        }
    }

    private
    Boolean isHeaderPosition(int position) {
        return mHeaderIndexes.contains(position);
    }
}
