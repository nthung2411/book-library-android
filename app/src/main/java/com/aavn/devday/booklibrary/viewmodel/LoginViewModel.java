package com.aavn.devday.booklibrary.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aavn.devday.booklibrary.data.manager.UserManager;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.data.model.User;
import com.aavn.devday.booklibrary.data.repository.UserRepository;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<ResponseData<User>> loginLiveData;

    private UserRepository userRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LoginViewModel() {
        userRepository = new UserRepository();
    }

    //For test
    public LoginViewModel(UserRepository userRepo) {
        userRepository = userRepo;
    }

    public LiveData<ResponseData<User>> getUserLiveData() {
        if (loginLiveData == null) {
            loginLiveData = new MutableLiveData<>();
        }
        return loginLiveData;
    }

    public void login(String username, String password) {
        if (username == null || username.isEmpty()) {
            loginLiveData.setValue(new ResponseData<User>(ResponseData.State.ERROR, "email is empty"));
            return;
        }

        if (password == null || password.isEmpty()) {
            loginLiveData.setValue(new ResponseData<User>(ResponseData.State.ERROR, "password is empty"));
            return;
        }

        loginLiveData.setValue(new ResponseData<User>(ResponseData.State.LOADING));

        userRepository.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(User response) {
                        loginLiveData.setValue(new ResponseData<User>(ResponseData.State.SUCCESS, response));
                        UserManager.getInstance().setUserInfo(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginLiveData.setValue(new ResponseData<User>(ResponseData.State.ERROR, e.getMessage()));
                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
