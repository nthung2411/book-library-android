package com.aavn.devday.booklibrary.view.detail;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aavn.devday.booklibrary.R;
import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.BookComment;
import com.aavn.devday.booklibrary.data.model.BookDetail;
import com.aavn.devday.booklibrary.data.model.BookViewModel;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.viewmodel.BookDetailViewModel;
import com.aavn.devday.booklibrary.viewmodel.BookListViewModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tvBookTitle;
    private TextView tvBookAuthor;
    private EditText etComment;
    private RatingBar rbBookRating;
    private ImageView ivBookCover;
    private TextView tvBookDescription;
    private RecyclerView rvBookComments;
    private Button btnSubmitComment;
    private View loadingView;

    private BookDetailViewModel bookDetailViewModel;
    private BookCommentListAdapter commentListAdapter;

    private Long bookId;
    private Long bookDetailId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        bindView();

        bookDetailViewModel = ViewModelProviders.of(this).get(BookDetailViewModel.class);
        commentListAdapter = new BookCommentListAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvBookComments.setLayoutManager(layoutManager);
        rvBookComments.setAdapter(commentListAdapter);

        etComment.clearFocus();
        btnSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookDetailId > 0 && etComment != null && etComment.getText() != null) {
                    bookDetailViewModel.writeComment(bookDetailId, etComment.getText().toString());
                    etComment.setText("");
                }
            }
        });
        bookId = getIntent().getLongExtra("bookId", 0L);
        bookDetailId = getIntent().getLongExtra("bookDetailId", 0L);

        if (bookId > 0 || bookDetailId > 0) {
            bookDetailViewModel.getBookDetail(bookId, bookDetailId);
            bookDetailViewModel.getBookComments(bookId);
        } else {
            BookViewModel bookViewModel = new Gson().fromJson(getIntent().getStringExtra("book"), BookViewModel.class);
            if (bookViewModel != null) {
                tvBookTitle.setText(bookViewModel.getTitle());
                tvBookAuthor.setText(bookViewModel.getAuthor());
                bindDescription(bookViewModel.getDescription());
                if (bookViewModel.getBookComments() != null && !bookViewModel.getBookComments().isEmpty())
                commentListAdapter.setItems(bookViewModel.getBookComments());

                if (bookViewModel.getCoverUrl() != null && !bookViewModel.getCoverUrl().isEmpty()) {
                    Glide.with(ivBookCover)
                            .load(bookViewModel.getCoverUrl())
                            .placeholder(R.drawable.book_cover_placeholder)
                            .thumbnail(0.1f)
                            .into(ivBookCover);
                }

            }
        }
        loadData();
    }

    private void bindView() {
        tvBookTitle = findViewById(R.id.tv_item_book_title);
        tvBookAuthor = findViewById(R.id.tv_item_book_author);
        etComment = findViewById(R.id.et_item_book_comment);
        rbBookRating = findViewById(R.id.rb_item_book_rating);
        ivBookCover = findViewById(R.id.iv_book_cover);
        tvBookDescription = findViewById(R.id.tv_item_book_description);
        rvBookComments = findViewById(R.id.rv_comments);
        loadingView = findViewById(R.id.view_loading);
        btnSubmitComment = findViewById(R.id.btn_submit_comment);
    }

    private void loadData() {
        bookDetailViewModel.getCommentsLiveData().observe(this, new Observer<ResponseData<List<BookComment>>>() {
            @Override
            public void onChanged(ResponseData<List<BookComment>> response) {
                switch (response.getState()) {
                    case LOADING:
                        loadingView.setVisibility(View.VISIBLE);
                        commentListAdapter.clearData();
                        break;
                    case SUCCESS:
                        loadingView.setVisibility(View.GONE);
                        List<BookComment> data = response.getData();
                        if (data != null && !data.isEmpty()) {
                            commentListAdapter.setItems(data);
                        }
                        break;
                    default:
                        loadingView.setVisibility(View.GONE);
                        commentListAdapter.clearData();
                        break;
                }
            }
        });
        bookDetailViewModel.getBookLiveData().observe(this, new Observer<ResponseData<BookViewModel>>() {
            @Override
            public void onChanged(ResponseData<BookViewModel> response) {
                switch (response.getState()) {
                    case LOADING:
                        loadingView.setVisibility(View.VISIBLE);
                        commentListAdapter.clearData();
                        break;
                    case SUCCESS:
                        loadingView.setVisibility(View.GONE);
                        BookViewModel data = response.getData();
                        if (data != null ) {
                            tvBookAuthor.setText(data.getAuthor());
                            bindDescription(data.getDescription());
                            tvBookTitle.setText(data.getTitle());
                            rbBookRating.setRating(data.getAverageRating());
                            if (data.getCoverUrl() != null && !data.getCoverUrl().isEmpty()) {
                                Glide.with(ivBookCover)
                                        .load(data.getCoverUrl())
                                        .placeholder(R.drawable.book_cover_placeholder)
                                        .thumbnail(0.1f)
                                        .into(ivBookCover);
                            }
                        }
                        break;
                    default:
                        loadingView.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }


    private void bindDescription(String rawData){
        String description = "";

        if(rawData != null && rawData.length() > 0){
            description = rawData;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvBookDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvBookDescription.setText(Html.fromHtml(description));
        }
    }


}
