package com.aavn.devday.booklibrary;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.aavn.devday.booklibrary.common.JsonHelper;
import com.aavn.devday.booklibrary.common.RxImmediateSchedulerRule;
import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.data.repository.BookRepository;
import com.aavn.devday.booklibrary.viewmodel.BookListViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(application = Application.class)
public class BookListViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Rule
    public final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    private BookListViewModel mockBookListVM;

    @Mock
    private Observer<ResponseData<List<Book>>> mockObserver;

    @Mock
    private BookRepository mockBookRepo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockBookListVM = new BookListViewModel(mockBookRepo);
        mockBookListVM.getBookListLiveData().observeForever(mockObserver);
    }

    @Test
    public void test_liveData_notNull() {
        assertNotNull(mockBookListVM.getBookListLiveData());
        assertTrue(mockBookListVM.getBookListLiveData().hasObservers());
    }

    @Test
    public void loadDefaultBooks_fail() {

        when(mockBookRepo.getDefaultBook()).thenReturn(Single.error(new IllegalArgumentException("Mock error")));

        mockBookListVM.fetchDefaultBookList();

        ArgumentCaptor<ResponseData<List<Book>>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(2)).onChanged(captor.capture());

        assertEquals(ResponseData.State.LOADING, captor.getAllValues().get(0).getState());
        assertEquals(ResponseData.State.ERROR, captor.getAllValues().get(1).getState());
    }

    @Test
    public void loadDefaultBooks_success() {
        String jsonText = JsonHelper.getInstance().readJSONFromAsset("default_book_data.json");
        Type listType = new TypeToken<List<Book>>() {
        }.getType();
        List<Book> responseData = new Gson().fromJson(jsonText, listType);

        when(mockBookRepo.getDefaultBook()).thenReturn(Single.just(responseData));

        mockBookListVM.fetchDefaultBookList();

        ArgumentCaptor<ResponseData<List<Book>>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(2)).onChanged(captor.capture());

        assertEquals(ResponseData.State.LOADING, captor.getAllValues().get(0).getState());
        assertEquals(ResponseData.State.SUCCESS, captor.getAllValues().get(1).getState());
        assertEquals(54, captor.getAllValues().get(1).getData().size());
        assertEquals("Charlotte's Web", captor.getAllValues().get(1).getData().get(0).getTitle());
    }

    @Test
    public void searchBooks_fail() {
        when(mockBookRepo.searchBook("java")).thenReturn(Single.error(new IllegalArgumentException("Mock error")));

        mockBookListVM.searchBook("java");

        ArgumentCaptor<ResponseData<List<Book>>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(2)).onChanged(captor.capture());

        assertEquals(ResponseData.State.LOADING, captor.getAllValues().get(0).getState());
        assertEquals(ResponseData.State.ERROR, captor.getAllValues().get(1).getState());
    }

    @Test
    public void searchBooks_success() {
        String jsonText = JsonHelper.getInstance().readJSONFromAsset("search_book_result_keyword_java.json");
        Type listType = new TypeToken<List<Book>>() {
        }.getType();
        List<Book> responseData = new Gson().fromJson(jsonText, listType);

        when(mockBookRepo.searchBook("java")).thenReturn(Single.just(responseData));

        mockBookListVM.searchBook("java");

        ArgumentCaptor<ResponseData<List<Book>>> captor = ArgumentCaptor.forClass(ResponseData.class);

        verify(mockObserver, times(2)).onChanged(captor.capture());

        assertEquals(ResponseData.State.LOADING, captor.getAllValues().get(0).getState());
        assertEquals(ResponseData.State.SUCCESS, captor.getAllValues().get(1).getState());
        assertEquals(160, captor.getAllValues().get(1).getData().size());
        assertEquals("Java", captor.getAllValues().get(1).getData().get(0).getTitle());
    }

}
