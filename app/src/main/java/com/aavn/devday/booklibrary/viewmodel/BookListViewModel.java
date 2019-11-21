package com.aavn.devday.booklibrary.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.data.repository.BookRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookListViewModel extends ViewModel {
    private MutableLiveData<ResponseData<List<Book>>> bookListLiveData = new MutableLiveData<>();

    private BookRepository bookRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BookListViewModel() {
        bookRepository = new BookRepository();
    }

    //For test
    public BookListViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public LiveData<ResponseData<List<Book>>> getBookListLiveData() {
        return bookListLiveData;
    }

    public void searchBook(String keyword) {
        bookListLiveData.setValue(ResponseData.loading());

        bookRepository.searchBook(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Book>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Book> books) {
                        bookListLiveData.setValue(ResponseData.success(books));
                    }

                    @Override
                    public void onError(Throwable e) {
                        bookListLiveData.setValue(ResponseData.error(e.getMessage()));
                    }
                });

    }

    public void fetchDefaultBookList() {
        bookListLiveData.setValue(ResponseData.loading());

        bookRepository.getDefaultBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Book>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Book> books) {
                        bookListLiveData.setValue(ResponseData.success(books));
                    }

                    @Override
                    public void onError(Throwable e) {
                        bookListLiveData.setValue(ResponseData.error(e.getMessage()));
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
