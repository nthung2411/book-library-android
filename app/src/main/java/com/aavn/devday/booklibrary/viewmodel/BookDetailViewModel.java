package com.aavn.devday.booklibrary.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.data.repository.BookRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class BookDetailViewModel extends ViewModel {
    private MutableLiveData<Book> bookLiveData = new MutableLiveData<>();

    BookRepository bookRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BookDetailViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void writeComment() {

    }

    public void rateBook() {

    }

    public void getComments() {

    }

    public void getRate() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
