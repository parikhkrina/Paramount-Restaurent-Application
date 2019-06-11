package com.application.paramountfinefoods;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.application.paramountfinefoods.utils.SharedPreferenceHelper;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentController fragmentController;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        if(intent!=null)
        {
            if(intent.hasExtra("confirmorders"))
            {
                if(intent.getBooleanExtra("confirmorders",false))
                {
                    onNavigationItemSelected(navigationView.getMenu().getItem(1));
                }else {
                    onNavigationItemSelected(navigationView.getMenu().getItem(0));
                }
            }else {
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
            }
        }else {
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        Fragment fragment = null;
        fragmentController = getFragmentController();
        Boolean addOnBackStack = true;

        if (id == R.id.nav_home) {
            fragment = HomeFragment.newInstance("", "");
            addOnBackStack = false;
        }
        else if (id == R.id.nav_confirm) {
            fragment = ConfirmOrderFragment.newInstance("", "");
        } else if (id == R.id.nav_history) {
            fragment = OrderHistoryFragment.newInstance("", "");
        } else if (id == R.id.nav_offline) {
            fragment = new OnlineOffline();
        } else if (id == R.id.nav_go_online) {
        } else if (id == R.id.nav_logout) {
            logout();

        }else
        {
            return true;
        }

        fragmentController.clear();
        if (fragment != null)
            fragmentController.pushStateless(fragment, addOnBackStack);
        ;
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();*/

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        Utility.setTitle(HomeActivity.this,item.getTitle()+"");
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    private void logout() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        FirebaseAuth.getInstance().signOut();
                        SharedPreferenceHelper.setSharedPreferenceString(getApplicationContext(), "user_id", "");
                        SharedPreferences settings = getApplicationContext().getSharedPreferences("user_id", Context.MODE_PRIVATE);
                        settings.edit().remove("user_id").commit();
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    // region Fragment Controller Helpers.
    public FragmentController getFragmentController() {
        if (fragmentController == null) {
            fragmentController = FragmentController.getInstance();
            fragmentController.setManager(getSupportFragmentManager());
            return fragmentController;
        }
        return fragmentController;
    }
}
