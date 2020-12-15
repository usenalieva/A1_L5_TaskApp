package kg.taskapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.taskapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import kg.taskapp.utils.Prefs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

/*
1. На долгое нажатие открыть AlertDialog, c текстом об удалении, если нажать удалить из списка
2. При нажатии на элемент из списка, открыть заполненный FormFragment для редактирования
3. Создать меню HomeFragment, в котором будет "Очистить", при нажатии очистить SharedPreferences и выйти из приложения
Bonus. Дизайн профиля */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavController();

        // BoardFragment launching setUp
        boolean isShown = new Prefs(this).isShown();
        if (!isShown) navController.navigate(R.id.boardFragment);

    }

    // Menu initialization
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_clear_prefs)
            new Prefs(this).clearPrefs();
        return super.onOptionsItemSelected(item);
    }

    private void initNavController() {
        BottomNavigationView navView = findViewById(R.id.nav_view);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {

            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.navigation_home ||
                        destination.getId() == R.id.navigation_dashboard ||
                        destination.getId() == R.id.navigation_profile ||
                        destination.getId() == R.id.navigation_notifications) {
                    navView.setVisibility(View.VISIBLE);
                } else {
                    navView.setVisibility(View.GONE);
                }
                if (getSupportActionBar() != null) {
                    if (destination.getId() == R.id.boardFragment)
                        getSupportActionBar().hide();
                    else
                        getSupportActionBar().show();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || onSupportNavigateUp();
    }

    // method to close a fragment
    public void closeFragment() {
        navController.navigateUp();
    }


}