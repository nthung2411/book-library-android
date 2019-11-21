package com.aavn.devday.booklibrary.data.repository;

import android.content.SharedPreferences;

import com.aavn.devday.booklibrary.data.model.User;
import com.aavn.devday.booklibrary.data.remote.RetrofitInstance;
import com.aavn.devday.booklibrary.data.remote.service.UserService;
import com.aavn.devday.booklibrary.utils.Constants;

import java.io.IOException;

import io.reactivex.Single;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    public Single<User> login(String usernameInput, String passwordInput) {
        return RetrofitInstance.getRetrofit().create(UserService.class).login(usernameInput, passwordInput);
    }

}
