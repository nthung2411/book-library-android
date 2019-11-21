package com.aavn.devday.booklibrary.view.detail;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aavn.devday.booklibrary.R;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tvBookTitle;
    private TextView tvBookAuthor;
    private EditText etComment;
    private RatingBar rbBookRating;
    private ImageView ivBookCover;
    private TextView tvBookDescription;
    private RecyclerView rvBookComments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        bindView();
    }

    private void bindView() {
        tvBookTitle = findViewById(R.id.tv_item_book_title);
        tvBookAuthor = findViewById(R.id.tv_item_book_author);
        etComment = findViewById(R.id.et_item_book_comment);
        rbBookRating = findViewById(R.id.rb_item_book_rating);
        ivBookCover = findViewById(R.id.iv_book_cover);
        tvBookDescription = findViewById(R.id.tv_item_book_description);
        rvBookComments = findViewById(R.id.rv_comments);
    }
}
