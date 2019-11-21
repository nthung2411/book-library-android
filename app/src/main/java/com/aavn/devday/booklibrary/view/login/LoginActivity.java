package com.aavn.devday.booklibrary.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aavn.devday.booklibrary.R;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.data.model.User;
import com.aavn.devday.booklibrary.view.main.MainActivity;
import com.aavn.devday.booklibrary.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Button btnLogin;
    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViewModel();

        btnLogin = findViewById(R.id.btn_login);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);

        edtUsername.setText("simple");
        edtPassword.setText("simple");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPassword.clearFocus();
                edtUsername.clearFocus();
                loginViewModel.login(edtUsername.getText().toString(), edtPassword.getText().toString());
            }
        });
    }

    private void initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        observeData();
    }

    public void observeData() {
        loginViewModel.getUserLiveData().observe(this, new Observer<ResponseData<User>>() {
            @Override
            public void onChanged(ResponseData<User> data) {
                View loadingView = findViewById(R.id.view_loading);
                switch (data.getState()) {
                    case LOADING:
                        loadingView.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        loadingView.setVisibility(View.GONE);
                        goToMainActivity();
                        break;
                    case ERROR:
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Error: " + data.getMessage(), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        loadingView.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
