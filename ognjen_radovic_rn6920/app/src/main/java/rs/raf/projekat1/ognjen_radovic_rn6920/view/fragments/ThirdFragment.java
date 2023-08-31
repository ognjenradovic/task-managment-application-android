package rs.raf.projekat1.ognjen_radovic_rn6920.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.User;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.activities.ChangePasswordActivity;


public class ThirdFragment extends Fragment {

    Button logoutBtn;
    Button changePassword;
    TextView username;
    TextView email;
    public ThirdFragment() {
        super(R.layout.fragment_third);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_third, container, false);
        this.logoutBtn = rootView.findViewById(R.id.logout_button);
        this.changePassword = rootView.findViewById(R.id.change_password_button);
        this.username = rootView.findViewById(R.id.username_textview);
        this.email = rootView.findViewById(R.id.email_textview);

        ImageView imageView = rootView.findViewById(R.id.profile_picture);
        Picasso.get().load("https://i.imgur.com/tGbaZCY.jpg").into(imageView);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Gson gson=new Gson();
        // Check if a user already exists in SharedPreferences
        String existingUserJson = prefs.getString("user", null);
        User existingUser = gson.fromJson(existingUserJson, User.class);
        this.username.setText(existingUser.getUsername());
        this.email.setText(existingUser.getEmail());

        logoutBtn.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("preff", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isUserLoggedIn", false);
            editor.apply();

            Toast.makeText(requireContext(), "LOGOUT", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        });

        changePassword.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "CHANGE PASSWORD", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            //intent.putExtra("user", existingUserJson);
            startActivity(intent);
        });
        return rootView;

    }
}
