package com.razor.ourjournal.screens;


import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razor.ourjournal.R;
import com.razor.ourjournal.screens.settings.SettingsActivity;
import com.razor.ourjournal.screens.timeline.view.TimelineActivity;

public class BaseDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected void setContent(@LayoutRes int layout) {
        setContentView(layout);

        setupDrawer();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = drawer.findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        TextView nameTextView = view.findViewById(R.id.drawer_name);
        TextView emailTextView = view.findViewById(R.id.drawer_email);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            nameTextView.setText(firebaseUser.getDisplayName());
            emailTextView.setText(firebaseUser.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timeline) {
            navigateToTimelineActivity();
        } else if (id == R.id.nav_settings) {
            navigateToSettingsActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToTimelineActivity() {
        Intent destination = new Intent(getApplicationContext(), TimelineActivity.class);
        destination.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(destination);
    }

    private void navigateToSettingsActivity() {
        Intent destination = new Intent(getApplicationContext(), SettingsActivity.class);
        destination.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(destination);
    }
}
