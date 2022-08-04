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
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        SharedPreferences sharedPreferences = getSharedPreferences("NSBTAS_APP_SETTINGS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isLightThemeActive = sharedPreferences.getBoolean("isLightThemeActive", true);
        if (isLightThemeActive) {
            topAppBar.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_dark_mode));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                topAppBar.getMenu().getItem(0).setIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.icon_tint_color));
            }
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            topAppBar.getMenu().getItem(0).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icon_light_mode));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        System.out.println(AppCompatDelegate.getDefaultNightMode());

        topAppBar.setNavigationOnClickListener(view -> {
            getSupportFragmentManager().popBackStack();
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                topAppBar.setNavigationIcon(null);
            }
        });

        topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case (R.id.switchTheme):
                    System.out.println(AppCompatDelegate.getDefaultNightMode());
                    if (isLightThemeActive) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putBoolean("isLightThemeActive", false);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putBoolean("isLightThemeActive", true);
                    }
                    editor.apply();
                    break;
                case (R.id.favorite):
                case (R.id.more):
                    break;
            }
            return false;
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case (R.id.page_1):
                    topAppBar.setNavigationIcon(R.drawable.icon_back);
                    topAppBar.setTitle("Anasayfa");
                    changeFragment(new HomepageFragment(), "HOMEPAGE_FRAGMENT");
                    break;
                case (R.id.page_2):
                    topAppBar.setNavigationIcon(R.drawable.icon_back);
                    topAppBar.setTitle("Ödeme");
                    Intent intent = new Intent(this, PaymentActivity.class);
                    startActivity(intent);
                    break;
                case (R.id.page_3):
                    topAppBar.setNavigationIcon(R.drawable.icon_back);
                    topAppBar.setTitle("Profil");
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