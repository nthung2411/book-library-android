package com.aavn.devday.booklibrary.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aavn.devday.booklibrary.R;
import com.aavn.devday.booklibrary.data.manager.UserManager;
import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.BookViewModel;
import com.aavn.devday.booklibrary.data.model.ResponseData;
import com.aavn.devday.booklibrary.view.detail.BookDetailActivity;
import com.aavn.devday.booklibrary.viewmodel.BookListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BookListAdapter.OnItemClickListener {

    private BookListViewModel bookListViewModel;
    private BookListAdapter bookListAdapter;
    private EditText edtBookKeyword;
    private View loadingView;
    private TextView tvErrorMsg;
    private RecyclerView rvBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();

        bookListViewModel = ViewModelProviders.of(this).get(BookListViewModel.class);
        observeBookListData();

        bookListAdapter = new BookListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvBookList.setLayoutManager(layoutManager);
        rvBookList.setAdapter(bookListAdapter);

        edtBookKeyword.clearFocus();

        edtBookKeyword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    bookListViewModel.searchBook(textView.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtBookKeyword.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        loadDefaultBookList();

        Toast.makeText(this, "Welcome " + UserManager.getInstance().getUserInfo().getUsername(), Toast.LENGTH_LONG).show();
    }

    private void bindView() {
        loadingView = findViewById(R.id.view_loading);
        tvErrorMsg = findViewById(R.id.tv_error_message);
        rvBookList = findViewById(R.id.rv_book_list);
        edtBookKeyword = findViewById(R.id.edt_search_book);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            Toast.makeText(this, "Clicked Favorite", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDefaultBookList() {
        if (bookListViewModel.getBookListLiveData().getValue() != null
                && bookListViewModel.getBookListLiveData().getValue().getData() != null) {
            bookListAdapter.setItems(bookListViewModel.getBookListLiveData().getValue().getData());
        } else {
            bookListViewModel.fetchDefaultBookList();
        }
    }

    private void observeBookListData() {
        bookListViewModel.getBookListLiveData().observe(this, new Observer<ResponseData<List<BookViewModel>>>() {
            @Override
            public void onChanged(ResponseData<List<BookViewModel>> response) {
                switch (response.getState()) {
                    case LOADING:
                        tvErrorMsg.setVisibility(View.GONE);
                        loadingView.setVisibility(View.VISIBLE);
                        bookListAdapter.clearData();
                        break;
                    case SUCCESS:
                        loadingView.setVisibility(View.GONE);
                        if (response.getData().isEmpty()) {
                            tvErrorMsg.setVisibility(View.VISIBLE);
                            tvErrorMsg.setText(getString(R.string.result_empty_msg));
                        } else {
                            bookListAdapter.setItems(response.getData());
                        }
                        break;
                    default:
                        loadingView.setVisibility(View.GONE);
                        tvErrorMsg.setVisibility(View.VISIBLE);
                        tvErrorMsg.setText(getString(R.string.general_error_msg));
                        break;
                }
            }
        });
    }

    @Override
    public void onItemClick(Book item) {

        Intent intent = new Intent(this, BookDetailActivity.class);
        startActivity(intent);
    }
}
