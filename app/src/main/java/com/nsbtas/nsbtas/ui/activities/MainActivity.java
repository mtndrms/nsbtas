package com.nsbtas.nsbtas.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nsbtas.nsbtas.ui.fragments.HomepageFragment;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.ui.fragments.ProfileFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS_APP_SETTINGS", Context.MODE_PRIVATE);

        boolean isLightThemeActive = sharedPreferences.getBoolean("isLightThemeActive", true);
        if (isLightThemeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.page_1):
                    changeFragment(new HomepageFragment(), "HOMEPAGE_FRAGMENT");
                    break;
                case (R.id.page_2):
                    Intent intent = new Intent(this, PaymentActivity.class);
                    startActivity(intent);
                    break;
                case (R.id.page_3):
                    changeFragment(new ProfileFragment(), "PROFILE_FRAGMENT");
                    break;
            }

            return true;
        });

        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });
    }

    private void changeFragment(Fragment fragment, String Tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, Tag);
        fragmentTransaction.addToBackStack(Tag);
        fragmentTransaction.commit();
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public Fragment getPreviousFragmentInBackstack() {
        return null;
    }
}