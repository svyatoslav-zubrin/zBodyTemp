package com.home.zubrin.zbodytemp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Utils.NumericUtils;

/**
 * Created by SlavaZu on 4/28/15.
 */
public
class PersonInfoActivity extends ActionBarActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_person_info);
    }

    @Override
    public View onCreateView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View v = super.onCreateView(name, context, attrs);

        if (v != null) {
            if (mNameEditText == null) {
                mNameEditText = (EditText) v.findViewById(R.id.person_info_nameEditText);
            }
            if (mAgeEditText == null) {
                mAgeEditText = (EditText) v.findViewById(R.id.person_info_ageEditText);
            }
        }

        return v;
    }

    // Menu management

    @Override
    public
    boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_info, menu);
        return true;
    }

    @Override
    public
    boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.activity_person_info_menu_save:
                if (validateForm() == true) {
                    createNewPerson();
                    finish();
                } else {
                    Toast.makeText(
                            this,
                            "Check if data is correct and try again, please.",
                            Toast.LENGTH_LONG).show();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Private

    private
    Boolean validateForm() {
        // Name
        String name = getName();
        Boolean isNameValid = name != null && name.length() > 0;
        // Age
        Boolean isAgeValid = false;
        String ageString = mAgeEditText.getText().toString();
        if (NumericUtils.isInteger(ageString)) {
            Integer age = getAge();
            isAgeValid = age != null && age > 0 && age < 150;
        }

        return isNameValid && isAgeValid;
    }

    private
    String getName() {
        return mNameEditText.getText().toString();
    }

    private
    Integer getAge() {
        return Integer.parseInt(mAgeEditText.getText().toString());
    }

    private
    void createNewPerson() {
        String name = getName();
        Integer age = getAge();

        Person p = new Person(name, age);
        Persons.sharedInstance.addPerson(p);
    }
}
