package com.example.assignment1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.assignment1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the toolbar from the binding
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_app_shortcut_24);  // Set your navigation icon

        // Set up the NavController from the NavHostFragment
        try {
            // Ensure to use FragmentContainerView to contain your NavHostFragment
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        } catch (Exception e) {
            Log.e("MainActivity", "NavController initialization failed", e);
        }

        // Setup the NavController for AppBar and BottomNavigation
        if (navController != null) {
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_about).build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        } else {
            Log.e("MainActivity", "NavController is null");
        }

        // Set title and icon for the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Energy Bill Calculator");
            getSupportActionBar().setIcon(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle navigation when the Up button is pressed
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
