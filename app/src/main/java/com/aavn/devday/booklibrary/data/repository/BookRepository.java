package com.aavn.devday.booklibrary.data.repository;

import com.aavn.devday.booklibrary.data.model.BookDetail;
import com.aavn.devday.booklibrary.data.remote.RetrofitInstance;
import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.SearchBookRequest;
import com.aavn.devday.booklibrary.data.remote.service.BookService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public class BookRepository {

    public Single<List<Book>> searchBook(String keyword) {
        return RetrofitInstance.getRetrofit().create(BookService.class).searchBook(new SearchBookRequest(keyword));
    }

    public Single<List<Book>> getDefaultBook() {
        return RetrofitInstance.getRetrofit().create(BookService.class).getDefaultBook();
    }

    public Single<List<Book>> getDummyDefaultBook() {
        List<Book> dummyBookList = new ArrayList<>();
        BookDetail dummyBookDetail = new BookDetail("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s");
        List<BookDetail> dummyBookDetailList = new ArrayList<>();
        dummyBookDetailList.add(dummyBookDetail);
        dummyBookList.add(new Book("Effective Java", "Joshua Bloch", dummyBookDetailList));
        dummyBookList.add(new Book("Head First Java", "Bert Bates and Kathy Sierra", dummyBookDetailList));
        dummyBookList.add(new Book("Java: A Beginner's Guide", "Herbert Schildt", dummyBookDetailList));
        dummyBookList.add(new Book("Thinking in Java", "Bruce Eckel", dummyBookDetailList));
        dummyBookList.add(new Book("Java Concurrency in Practice", "Brian Goetz", dummyBookDetailList));
        dummyBookList.add(new Book("Head First Design Patterns", "Elisabeth Freeman and Kathy Sierra", dummyBookDetailList));
        dummyBookList.add(new Book("Think Java: How to think like a computer scientist", " Allen B. Downey", dummyBookDetailList));
        dummyBookList.add(new Book("Core Java", "Cay S. Horstmann", dummyBookDetailList));

        return Single.just(dummyBookList);
    }

}
