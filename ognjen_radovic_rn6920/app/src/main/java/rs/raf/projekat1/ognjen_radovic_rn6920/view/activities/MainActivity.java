package rs.raf.projekat1.ognjen_radovic_rn6920.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.User;
import rs.raf.projekat1.ognjen_radovic_rn6920.viewmodels.SplashViewModel;

public class MainActivity extends AppCompatActivity {

    // View components
    private Button loginBtn;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;

    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("preff", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isUserLoggedIn", false)) {
            Intent intent = new Intent(this, BottomNavigationActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_main);
            init();
        }
    }

    private void init() {
        initView();
        initListeners();
    }


    private void initView() {
        loginBtn = findViewById(R.id.loginButton);
        emailInput = findViewById(R.id.inputEmail);
        passwordInput = findViewById(R.id.inputPassword);
        usernameInput = findViewById(R.id.inputUsername);
    }

    private void initListeners() {
        Gson gson = new Gson();
        loginBtn.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            String email = emailInput.getText().toString();

            String password1 = "";

            try {
                File file = new File(getApplicationContext().getFilesDir(), "password.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    password1 += line;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Validate input fields
            boolean isUsernameValid = !TextUtils.isEmpty(username);
            boolean isPasswordValid = isValidPassword(password);
            boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();

            // Set error message and outline color for invalid input fields
            if (!isUsernameValid) {
                usernameInput.setError("Please enter username");
            } else {
                usernameInput.setError(null);
            }
            if (!isPasswordValid) {
                passwordInput.setError("Please enter a valid password");
            } else {
                passwordInput.setError(null);
            }
            if (!isEmailValid) {
                emailInput.setError("Please enter a valid email address");
            } else {
                emailInput.setError(null);
            }

            if (isUsernameValid && isPasswordValid && isEmailValid && password.equals(password1)) {
                User newUser = new User(username, email, password);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

                String existingUserJson = prefs.getString("user", null);
                if (existingUserJson != null) {
                    User existingUser = gson.fromJson(existingUserJson, User.class);
                    if (existingUser != null) {
                        if (existingUser.getUsername().equals(newUser.getUsername()) &&
                                existingUser.getEmail().equals(newUser.getEmail())) {
                            SharedPreferences sharedPreferences = getSharedPreferences("preff", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isUserLoggedIn", true);
                            editor.apply();


                            Intent intent = new Intent(this, BottomNavigationActivity.class);
                            startActivity(intent);
                            return;
                        } else {
                            Toast.makeText(this, "Wrong username or email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                SharedPreferences.Editor editor = prefs.edit();

                String newUserJson = gson.toJson(newUser);
                editor.putString("user", newUserJson);
                editor.apply();

                Intent intent = new Intent(this, BottomNavigationActivity.class);
                startActivity(intent);
            }


        });
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?!.*[~#^|$%&*!]).{5,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return !TextUtils.isEmpty(password) && matcher.matches();
    }


}
