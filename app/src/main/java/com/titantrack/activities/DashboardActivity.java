package com.titantrack.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.titantrack.R;
import com.titantrack.fragments.HomeFragment;
import com.titantrack.fragments.ProfileFragment;
import com.titantrack.fragments.ProgressFragment;
import com.titantrack.fragments.RoutinesFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        // Se ejecuta cada vez que el usuario toca una pestaña
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment;

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_routines) {
                fragment = new RoutinesFragment();
            } else if (id == R.id.nav_progress) {
                fragment = new ProgressFragment();
            } else if (id == R.id.nav_profile) {
                fragment = new ProfileFragment();
            } else {
                return false;
            }

            showFragment(fragment);
            return true; // true = evento gestionado, la pestaña queda marcada
        });

        // Solo en el primer arranque: si se gira el móvil, Android restaura el fragment solo
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}