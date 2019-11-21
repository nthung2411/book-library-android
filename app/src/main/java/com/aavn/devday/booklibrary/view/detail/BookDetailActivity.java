package com.aavn.devday.booklibrary.view.detail;

import android.content.Context;
import android.os.Bundle;
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
import com.aavn.devday.booklibrary.data.model.BookDetail;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.viewmodel.BookDetailViewModel;
import com.aavn.devday.booklibrary.viewmodel.BookListViewModel;

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
                bookDetailViewModel.writeComment(1L, etComment.getText().toString());
            }
        });
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
    }

    private void loadData() {
        bookDetailViewModel.getBookLiveData().(this, new Observer<ResponseData<Book>>() {
            @Override
            public void onChanged(ResponseData<Book> response) {
                switch (response.getState()) {
                    case LOADING:
                        loadingView.setVisibility(View.VISIBLE);
                        commentListAdapter.clearData();
                        break;
                    case SUCCESS:
                        loadingView.setVisibility(View.GONE);
                        if (response.getData() != null ) {
//                            tvErrorMsg.setVisibility(View.VISIBLE);
//                            tvErrorMsg.setText(getString(R.string.result_empty_msg));
//                            commentListAdapter.setItems(response.getData());
                        } else {
//                            commentListAdapter.setItems(response.getData());
                        }
                        break;
                    default:
                        loadingView.setVisibility(View.GONE);
//                        tvErrorMsg.setVisibility(View.VISIBLE);
//                        tvErrorMsg.setText(getString(R.string.general_error_msg));
                        break;
                }
            }
        });
    }


}
