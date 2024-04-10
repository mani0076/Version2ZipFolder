package com.movieinformation.versionnumber2.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.movieinformation.versionnumber2.R;
import com.movieinformation.versionnumber2.databinding.ActivityMovieInfoBinding;
import com.movieinformation.versionnumber2.databinding.CustomDialogBinding;

import java.util.Locale;

public class MovieInfoActivity extends AppCompatActivity {

    NavController navController;
    ActivityMovieInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.movie_fragment);
        navController = navHostFragment.getNavController();
        setSupportActionBar(binding.toolbar);


    }
    public NavController getNavController() {
        return navController;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back to previous activity
            return true;
        }
        if(id == R.id.nav_language){
            /* Show a dialog to allow the user to choose the language.
             * When a language option is selected, change the application language accordingly.
             */
            final Dialog customDialog = new Dialog(this);
            CustomDialogBinding dialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(this));
            customDialog.setContentView(dialogBinding.getRoot());

            RadioGroup radioGroup = customDialog.findViewById(R.id.radioGroup);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = customDialog.findViewById(checkedId);
                    String selectedLanguage = radioButton.getText().toString();
                    changeAppLanguage(selectedLanguage);
                }
            });
            customDialog.show();
        }
        if (id==R.id.nav_instructions){
            /* Create an AlertDialog to display instructions to the user.
             * Set the title and message of the dialog with appropriate strings.
             * Add a positive button "Cancel" to dismiss the dialog when clicked.
             */
            androidx.appcompat.app.AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getResources().getString(R.string.instructions));
            dialog.setMessage(getResources().getString(R.string.movie_instruction));
            dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }

        return true;
    }
    private void changeAppLanguage(String language) {
        /* Change the language of the application based on the selected language.
         * Supported languages: British English, American English.
         * @param language The selected language as a string.
         */
        switch (language) {
            case "British English":
                changeLanguage("en-GB");
                break;
            case "American Language":
                changeLanguage("en");
                break;
            default:
                break;
        }
    }
    private void changeLanguage(String languageCode) {
        /* Change the language of the application to the specified language code.
         * @param languageCode The language code to set (e.g., "en", "en-GB").
         */
        Locale newLocale = new Locale(languageCode);
        Locale.setDefault(newLocale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(newLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // You may need to recreate the activity to apply the language change
        recreate();
    }



}