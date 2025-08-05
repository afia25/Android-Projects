package com.example.omkar.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.omkar.android.fragments.AddAttendanceFragment;
import com.example.omkar.android.fragments.AddNotificationFragment;
import com.example.omkar.android.fragments.SearchByStudentIdFragment;
import com.example.omkar.android.fragments.StudentDetailsFragment;
import com.example.omkar.android.fragments.AddMarksFragment;
import com.example.omkar.android.fragments.ViewAttendanceFragment;
import com.example.omkar.android.interfaces.CourseViewInterface;

public class CourseActivity extends AppCompatActivity implements CourseViewInterface {

    private String mCourseCode;
    private String mCrEmail;
    private String mTaEmail;
    private String mCourseName;

    private static final String EMAIL_BODY = "\n\nSent from: Course Assistant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Intent bundle for getting extra info
        Bundle courseInfo = getIntent().getExtras();
        mCourseCode = courseInfo.getString("courseCode");
        mCrEmail = courseInfo.getString("emailCr");
        mTaEmail = courseInfo.getString("emailTa");
        mCourseName = courseInfo.getString("courseName");

        // init methods
        initToolbar(mCourseCode);
        initButtons();
    }


    /**
     * Set toolbar details
     * @param title title for toolbar
     */
    @Override
    public void initToolbar(String title) {
        // Add toolbar support
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        // add home button in toolbar
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        // set title
        actionbar.setTitle(title);
    }


    /**
     * Hide activity view
     * @param enabled true: hide, false: show
     */
    @Override
    public void setViewHidden(boolean enabled, int color) {
        LinearLayout courseView = findViewById(R.id.course_view);
        LinearLayout l = findViewById(R.id.layout_course);
        l.setBackgroundColor(getResources().getColor(color));
        if (enabled) {
            courseView.setVisibility(View.GONE);
        }
        else {
            courseView.setVisibility(View.VISIBLE);
            initToolbar(mCourseCode);
        }
    }


    /**
     * initialize buttons in the cards of view
     */
    private void initButtons() {
        // TODO: Remove redundant FragmentManager objects in each listener
        // add attendance button
        Button addAttendance = findViewById(R.id.addAttendance);
        addAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new fragment
                AddAttendanceFragment addAttendanceFragment = new AddAttendanceFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragContainer, addAttendanceFragment, "Add Attendance Fragment");
                // add this fragment to stack
                transaction.addToBackStack("Add Attendance Fragment");
                // commit this transaction
                transaction.commit();
            }
        });

        // view attendance button
        Button viewAttendance = findViewById(R.id.viewAttendance);
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new fragment
                ViewAttendanceFragment viewAttendanceFragment = new ViewAttendanceFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragContainer, viewAttendanceFragment, "View Attendance Fragment");
                // add this fragment to stack
                transaction.addToBackStack("View Attendance Fragment");
                // commit this transaction
                transaction.commit();
            }
        });


        // view student details button
        Button viewStudentDetails = findViewById(R.id.viewStudentDetails);
        viewStudentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new fragment
                StudentDetailsFragment studentDetailsFragment = new StudentDetailsFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragContainer, studentDetailsFragment, "Student Details Fragment");
                // add this fragment to stack
                transaction.addToBackStack("Student Details Fragment");
                transaction.commit();
            }
        });

        // search for a specific student's details
        Button searchByStudentId = findViewById(R.id.searchByStudentId);
        searchByStudentId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new fragment
                SearchByStudentIdFragment searchByStudentIdFragment = new SearchByStudentIdFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fragContainer, searchByStudentIdFragment, "Search By Student ID Fragment");
                // add this fragment to stack
                transaction.addToBackStack("Search By Student ID Fragment");
                transaction.commit();
            }
        });

        // add marks
        Button addMarks = findViewById(R.id.addMarks);
        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new fragment
                AddMarksFragment addMarksFragment = new AddMarksFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.fragContainer, addMarksFragment, "Add Marks Fragment");
                // add this fragment to stack
                transaction.addToBackStack("Add Marks Fragment");
                // commit this transaction
                transaction.commit();
            }
        });

        // add notification
        Button addNotification = findViewById(R.id.addNotification);
        addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new fragment
                AddNotificationFragment addNotificationFragment = new AddNotificationFragment();
                // get transaction manager
                FragmentManager manager = getFragmentManager();
                // start transaction
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.fragContainer, addNotificationFragment, "Add Notification Fragment");
                // add this fragment to stack
                transaction.addToBackStack("Add Notification Fragment");
                // commit this transaction
                transaction.commit();
            }
        });

        // view notification
        Button viewNotification = findViewById(R.id.viewNotification);
        viewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startMillis = System.currentTimeMillis();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
                startActivity(intent);
            }
        });
    }


    // methods to contact CR and TA
    // TODO: Limit send options to only email apps
    public void contactMailCr(View v) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);

        email.setType("text/plain");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mCrEmail});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, mCourseCode + ": " + mCourseName);
        email.putExtra(android.content.Intent.EXTRA_TEXT, EMAIL_BODY);

        startActivity(Intent.createChooser(email, "Send mail"));
    }
    public void contactMailTa(View v) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
        email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mTaEmail});
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, mCourseCode + ": " + mCourseName);
        email.putExtra(android.content.Intent.EXTRA_TEXT, EMAIL_BODY);

        startActivity(Intent.createChooser(email, "Send mail"));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
            finish();
        }
    }
}
