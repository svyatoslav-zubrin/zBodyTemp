package com.home.zubrin.zbodytemp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.home.zubrin.zbodytemp.Model.Person;
import com.home.zubrin.zbodytemp.Model.Persons;
import com.home.zubrin.zbodytemp.Storage.XMLStorage;

import java.sql.Date;

/**
 * Created by SlavaZu on 4/28/15.
 */
public
class PersonInfoActivity extends ActionBarActivity {

    public static final String DIALOG_DATE_TAG = "zBodyTemp.tag.dialog_date_tag";

    // Outlets
    private EditText mNameEditText;
    private EditText mSurnameEditText;
    private Button mBirthdayButton;
    private ImageButton mIconButton;
    // Variables
    private String mName;
    private String mSurname;
    private Date mBirthday;


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
            if (mSurnameEditText == null) {
                mSurnameEditText = (EditText) v.findViewById(R.id.person_info_surnameEditText);
            }
            if (mBirthdayButton == null) {
                mBirthdayButton = (Button) v.findViewById(R.id.person_info_birthdayButton);
                mBirthdayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBirthdayButtonPressed();
                    }
                });
            }
            if (mIconButton == null) {
                mIconButton = (ImageButton) v.findViewById(R.id.person_info_iconButton);
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

    // Dialogs management

    private
    void onBirthdayButtonPressed() {
        FragmentManager fm = getSupportFragmentManager();
        DatePopupFragment f = DatePopupFragment.newInstance(mBirthday);
        f.show(fm, DIALOG_DATE_TAG);
    }

    // Private

    private
    Boolean validateForm() {
        // Name
        String name = getName();
        Boolean isNameValid = name != null && name.length() > 0;

        // Name
        String surname = getSurname();
        Boolean isSurnameValid = surname != null && surname.length() > 0;

        // Birthday
        Boolean isBirthdayValid = mBirthday != null;

        return isNameValid && isSurnameValid && isBirthdayValid;
    }

    private
    String getName() {
        return mNameEditText.getText().toString();
    }

    private
    String getSurname() {
        return mSurnameEditText.getText().toString();
    }

    private
    void createNewPerson() {
        String name = getName();
        String surname = getSurname();

        Person p = new Person(name, surname, mBirthday);
        Persons.getSharedInstance(this).addPerson(p);

        XMLStorage.save(this);
    }
}
