package com.home.zubrin.zbodytemp;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public
class PersonsActivityFragment extends android.support.v4.app.ListFragment {

    private ArrayList<Person> mPersons;

    public
    PersonsActivityFragment() {

    }

    @Override
    public
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.persons_title);

        mPersons = Persons.sharedInstance.getPersons();

        ArrayAdapter<Person> adapter =
                new ArrayAdapter<Person>(getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        mPersons);
        setListAdapter(adapter);
    }
}
