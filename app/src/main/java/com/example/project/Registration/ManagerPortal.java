package com.example.project.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.project.Books.AddBook;
import com.example.project.Books.DeleteBook;
import com.example.project.Books.ReadAllBooks;
import com.example.project.Books.UpdateBook;
import com.example.project.R;
import com.example.project.Ratings.AdminRates;
import com.example.project.User.DeleteUser;
import com.example.project.User.ReadAllUsers;
import com.example.project.User.UpdateUser;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ManagerPortal extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;
    //Button button1,button2,button3,button5,button6,button7,button8,button9;
    Fragment fragment;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_portal);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        Fragment frag=null;
        Class newFragment = ReadAllBooks.class;
        try {
            frag= (Fragment) newFragment.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, frag).commit();

    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
            switch (menuItem.getItemId()) {
                case R.id.nav_first_fragment:
                    fragmentClass = ReadAllUsers.class;
                    break;
                case R.id.nav_second_fragment:
                    fragmentClass = UpdateUser.class;
                    break;
                case R.id.nav_third_fragment:
                    fragmentClass = DeleteUser.class;
                    break;
                case R.id.nav_fourth_fragment:
                    fragmentClass = ReadAllBooks.class;
                    break;
                case R.id.nav_fifth_fragment:
                    fragmentClass = UpdateBook.class;
                    break;
                case R.id.nav_sixth_fragment:
                    fragmentClass = DeleteBook.class;
                    break;
                case R.id.nav_seventh_fragment:
                    fragmentClass = AddBook.class;
                    break;
                case R.id.nav_eigthtth:
                    fragmentClass = AdminRates.class;
                    break;
                case R.id.nav_last_fragment:
                    auth.signOut();
                    fragmentClass = null;
                    break;
                default:
                    fragmentClass = ReadAllUsers.class;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            if (fragmentClass != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                // Highlight the selected item has been done by NavigationView
                menuItem.setChecked(true);
                // Set action bar title
                setTitle(menuItem.getTitle());
                // Close the navigation drawer
                mDrawer.closeDrawers();
            } else {
                Intent intent = new Intent(ManagerPortal.this, MainActivity.class);
                startActivity(intent);
            }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}
