package com.aavn.devday.booklibrary.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.BookComment;
import com.aavn.devday.booklibrary.data.model.CommentRequest;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.data.repository.BookRepository;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;

public class BookDetailViewModel extends ViewModel {
    public MutableLiveData<ResponseData<Book>> bookLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseData<List<BookComment>>> bookComments = new MutableLiveData<>();

    BookRepository bookRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BookDetailViewModel() {
        bookRepository = new BookRepository();
    }

    public BookDetailViewModel(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public LiveData<ResponseData<Book>> getBookLiveData() {
        return bookLiveData;
    }

    public LiveData<ResponseData<List<BookComment>>> getCommentsLiveData() {
        return bookComments;
    }

    public void writeComment(Long bookDetailId, String content) {
        CommentRequest request = new CommentRequest();
        request.setComment(content);
        this.bookRepository.commentBook(bookDetailId, request);
    }

    public void getBookDetail(Long id) {
        bookLiveData.setValue(ResponseData.loading());
        bookRepository.getBookDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Book>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Book book) {
                        bookLiveData.setValue(ResponseData.success(book));
                    }

                    @Override
                    public void onError(Throwable e) {
                        bookLiveData.setValue(ResponseData.error(e.getMessage()));
                    }
                });
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
