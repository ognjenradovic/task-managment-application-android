package rs.raf.projekat1.ognjen_radovic_rn6920.view.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.User;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextView usernameTextView;
    private TextView passwordTextView;
    private TextView emailTextView;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        Button changePasswordButton = findViewById(R.id.change_password_submit);
        EditText editPassword = findViewById(R.id.newPasswordField);

        changePasswordButton.setOnClickListener(v -> {
            String newPassword = editPassword.getText().toString();
            try {
                File file = new File(getApplicationContext().getFilesDir(), "password.txt");
                FileWriter writer = new FileWriter(file, false); // set append parameter to false
                writer.write(newPassword); // write the new password
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        this.finish();
        });

    }

}