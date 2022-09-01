package com.nsbtas.nsbtas.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nsbtas.nsbtas.fragments.HomepageFragment;
import com.nsbtas.nsbtas.R;
import com.nsbtas.nsbtas.fragments.ProfileFragment;
import com.nsbtas.nsbtas.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        Utils.setTheme(this);

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

        bottomNavigationView.setOnItemReselectedListener(item -> {
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
        });
    }

    private void changeFragment(Fragment fragment, String Tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
        );
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, Tag);
        fragmentTransaction.addToBackStack(Tag);
        fragmentTransaction.commit();
    }
}