package com.nolari.benzinverbrauchberechner.navigation;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nolari.benzinverbrauchberechner.R;
import com.nolari.benzinverbrauchberechner.fragment.GraphFragment;
import com.nolari.benzinverbrauchberechner.fragment.HomeFragment;
import com.nolari.benzinverbrauchberechner.fragment.NewEntryFragment;

public class BottomNavigationManager implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = null;
    private Fragment currentFragment = null;

    public BottomNavigationManager(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public boolean loadFragment(Fragment fragment){
        if(fragment == null){
            return false;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment);
        if(currentFragment!=null){
            //not on startup
            ft.addToBackStack(null);
        }
        ft.commit();
        currentFragment = fragment;
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.navigation_home:
                if(!(currentFragment instanceof HomeFragment)){
                    fragment= new HomeFragment();
                }
                break;
            case R.id.navigation_new_entry:
                if(!(currentFragment instanceof NewEntryFragment)){
                    fragment = new NewEntryFragment();
                }
                break;
            case R.id.navigation_history:
                if(!(currentFragment instanceof GraphFragment)){
                    fragment = new GraphFragment();
                }
                break;
        }

        return loadFragment(fragment);
    }
}
