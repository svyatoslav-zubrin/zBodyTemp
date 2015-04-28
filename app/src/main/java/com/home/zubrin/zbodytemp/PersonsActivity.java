package com.home.zubrin.zbodytemp;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public
class PersonsActivity extends ActionBarActivity {

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        PersonsActivityFragment fragment = (PersonsActivityFragment)fm.findFragmentById(R.id.persons_fragment);

        if (fragment == null) {
            fragment = new PersonsActivityFragment();
            fm.beginTransaction()
                    .add(R.id.persons_fragment, fragment)
                    .commit();
        }
    }

    // Menu management

    @Override
    public
    boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_persons, menu);
        return true;
    }

    @Override
    public
    boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatementq
        if (id == R.id.activity_persons_menu_add) {
            Intent i = new Intent(this, PersonInfoActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
