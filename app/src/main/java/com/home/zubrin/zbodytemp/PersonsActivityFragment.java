package com.home.zubrin.zbodytemp;

import android.app.ListFragment;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public
class PersonsActivityFragment extends android.support.v4.app.ListFragment {

    public static final String EXTRA_SELECTED_PERSON_ID = "home.zubrin.zbodytemp.PersonsActivityFragment.PersonId";

    private ArrayList<Person> mPersons;

    public
    PersonsActivityFragment() {

    }

    @Override
    public
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.persons_title);

        mPersons = Persons.getSharedInstance(getActivity()).getPersons();

        PersonAdapter adapter = new PersonAdapter(mPersons);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((PersonAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Person p = ((PersonAdapter)getListAdapter()).getItem(position);

        // Show card of the selected person
        Intent i = new Intent(getActivity(), CardActivity.class);
        i.putExtra(EXTRA_SELECTED_PERSON_ID, p.getId());
        startActivity(i);
    }

    // Custom list adapter

    private
    class PersonAdapter extends ArrayAdapter<Person> {

        public
        PersonAdapter(ArrayList<Person> persons) {
            super(getActivity(), 0, persons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.activity_persons_row, null);
            }

            Person p = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.crime_list_item_header);
            titleTextView.setText(p.getName());

            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.crime_list_item_subheader);
            dateTextView.setText(p.getAge().toString() + " years old");

            return convertView;
        }
    }
}
