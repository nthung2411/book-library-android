package com.aavn.devday.booklibrary.data.remote.service;

import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.BookComment;
import com.aavn.devday.booklibrary.data.model.CommentRequest;
import com.aavn.devday.booklibrary.data.model.SearchBookRequest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookService {
    @POST("library-core/api/books/search")
    public Single<List<Book>> searchBook(@Body SearchBookRequest body);

    @GET("library-core/api/books/{id}")
    public  Single<Book> getBookById(@Path("id") long id);

    @GET("library-core/api/books")
    public Single<List<Book>> getDefaultBook();

    @GET("library-core/api/books/{book-id}/comments")
    public Single<List<BookComment>> getListCommentByBookId(@Path("book-id") long id);

    @POST("library-core/api/book-details/{book-detail-id}/comment")
    public Single<BookComment> commentBook(@Path("book-detail-id") long id, @Body CommentRequest request);

    @POST("library-core/api/book-details/{book-detail-id}/rating")
    public void rateBook(@Path("book-detail-id") long id);
}
