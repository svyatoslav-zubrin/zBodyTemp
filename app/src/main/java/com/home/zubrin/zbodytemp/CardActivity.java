package com.home.zubrin.zbodytemp;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.home.zubrin.zbodytemp.Interfaces.OnCardChangedListener;
import com.home.zubrin.zbodytemp.Model.Card;
import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Model.Record;
import com.home.zubrin.zbodytemp.Model.xml.ZBodyTempXMLSerializer;

public
class CardActivity
    extends
        ActionBarActivity
    implements
        ActionBar.TabListener
        , RecordTypePopupFragment.OnRecordTypeSelectedListener
        , TempPopupFragment.OnTemperatureSelectionListener
{
    public static final String DIALOG_RECORD_TYPE_TAG = "zBodyTemp.tag.dialog_record_type_tag";
    public static final String DIALOG_TEMP_TAG = "zBodyTemp.tag.dialog_temperature_tag";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private Person mPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        UUID personId = (UUID)intent.getSerializableExtra(PersonsActivityFragment.EXTRA_SELECTED_PERSON_ID);
        mPerson = Persons.sharedInstance.findPersonById(personId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_card);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.card_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    // Menu management

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.activity_card_menu_add) {
            showRecordTypeSelectionDialog();
            return true;
        }
        else if (id == R.id.activity_card_menu_save) {
            ZBodyTempXMLSerializer.serializePerson(mPerson); // TODO: should serialize all persons probably
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Tabs management

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    // Dialog callbacks

    @Override
    public void onRecordTypeSelected(Record.Type type) {
        switch (type) {
            case TEMPERATURE:
                showTemperatureSelectionDialog();
                return;
            case MEDICINE:
            case NOTE:
            default:
                Toast.makeText(this, "This record type is still in development", Toast.LENGTH_LONG).show();
                return;
        }
    }

    @Override
    public void onTemperatureSelected(Float temperature, Date date) {
        Record r = new Record(temperature, date);
        Card c = mPerson.getCard();
        c.addRecord(r);

        informFragmentsAboutCardChanges();
    }

    @Override
    public void onTemperatureCanceled() {
        // do nothing here
    }

    // Dialogs management

    private
    void showRecordTypeSelectionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        RecordTypePopupFragment f = new RecordTypePopupFragment();
        f.show(fm, DIALOG_RECORD_TYPE_TAG);
    }

    private
    void showTemperatureSelectionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        TempPopupFragment f = new TempPopupFragment();
        f.show(fm, DIALOG_TEMP_TAG);
    }

    private
    void informFragmentsAboutCardChanges() {
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment f: fm.getFragments()) {
            if (f instanceof OnCardChangedListener) {
                ((OnCardChangedListener) f).onCardChanged();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return RecordsListFragment.newInstance(position + 1, mPerson.getId());
            }
            else if (position == 1) {
                return RecordsPlotFragment.newInstance(position + 1, mPerson.getId());
            }
            else {
                return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.activity_card_list_title).toUpperCase(l);
                case 1:
                    return getString(R.string.activity_card_graph_title).toUpperCase(l);
                case 2:
                    return getString(R.string.activity_card_note_title).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static
    class PlaceholderFragment
            extends Fragment
            implements OnCardChangedListener
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_card, container, false);
            return rootView;
        }

        @Override
        public void onCardChanged() {
            // do nothing in that case (stub fragment implementation)
        }
    }
}
