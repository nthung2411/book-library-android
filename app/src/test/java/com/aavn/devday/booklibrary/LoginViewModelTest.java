package com.aavn.devday.booklibrary;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.aavn.devday.booklibrary.common.RxImmediateSchedulerRule;
import com.aavn.devday.booklibrary.data.manager.UserManager;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.data.model.User;
import com.aavn.devday.booklibrary.data.repository.UserRepository;
import com.aavn.devday.booklibrary.viewmodel.LoginViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoginViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    private LoginViewModel mockLoginVM;

    @Mock
    private Observer<ResponseData<User>> mockObserver;

    @Mock
    private UserRepository mockUserRepo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockLoginVM = new LoginViewModel(mockUserRepo);
        mockLoginVM.getUserLiveData().observeForever(mockObserver);
    }

    @Test
    public void test_liveData_notNull() {
        assertNotNull(mockLoginVM.getUserLiveData());
        assertTrue(mockLoginVM.getUserLiveData().hasObservers());
    }

    @Test
    public void login_success() {
        User expectUser = new User("simple");
        when(mockUserRepo.login("simple", "simple")).thenReturn(Single.just(expectUser));
        mockLoginVM.login("simple", "simple");
        ArgumentCaptor<ResponseData<User>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(2)).onChanged(captor.capture());

        assertEquals(ResponseData.State.LOADING, captor.getAllValues().get(0).getState());
        assertEquals(ResponseData.State.SUCCESS, captor.getAllValues().get(1).getState());
        assertEquals("simple", captor.getAllValues().get(1).getData().getUsername());
        assertEquals("simple", UserManager.getInstance().getUserInfo().getUsername());
    }

    @Test
    public void login_fail() {
        when(mockUserRepo.login("simple", "simple")).thenReturn(Single.error(new IllegalArgumentException("Mock error")));
        mockLoginVM.login("simple", "simple");
        ArgumentCaptor<ResponseData<User>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(2)).onChanged(captor.capture());

        assertEquals(ResponseData.State.LOADING, captor.getAllValues().get(0).getState());
        assertEquals(ResponseData.State.ERROR, captor.getAllValues().get(1).getState());
    }

    @Test
    public void login_username_blank() {

        mockLoginVM.login("", "simple");

        ArgumentCaptor<ResponseData<User>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(1)).onChanged(captor.capture());

        assertEquals(ResponseData.State.ERROR, captor.getValue().getState());
        assertEquals("email is empty", captor.getValue().getMessage());
    }

    @Test
    public void login_password_blank() {
        mockLoginVM.login("simple", "");

        ArgumentCaptor<ResponseData<User>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(1)).onChanged(captor.capture());

        assertEquals(ResponseData.State.ERROR, captor.getValue().getState());
        assertEquals("password is empty", captor.getValue().getMessage());
    }
}
