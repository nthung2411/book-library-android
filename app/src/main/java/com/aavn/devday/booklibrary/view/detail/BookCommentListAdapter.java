package com.aavn.devday.booklibrary.view.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aavn.devday.booklibrary.R;
import com.aavn.devday.booklibrary.data.model.BookComment;

import java.util.ArrayList;
import java.util.List;

public class BookCommentListAdapter extends RecyclerView.Adapter<BookCommentListAdapter.BookCommentViewHolder> {

    List<BookComment> comments = new ArrayList<>();

    @NonNull
    @Override
    public BookCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new BookCommentListAdapter.BookCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCommentViewHolder holder, int position) {
        BookComment data = comments.get(position);
        holder.bindData(data);
    }

    public void setItems(List<BookComment> comments) {
        this.comments = comments;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    void clearData(){
        comments.clear();
        notifyDataSetChanged();
    }

    static class BookCommentViewHolder extends RecyclerView.ViewHolder {

        TextView tvUser;
        TextView tvComment;

        BookCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tv_item_book_title);
            tvComment = itemView.findViewById(R.id.tv_item_book_brief_description);
        }

        void bindData(BookComment comment) {
            if (comment != null) {
                if (comment.getUser() != null) {
                    tvUser.setText(comment.getUser().getFullName());
                }
                tvComment.setText(comment.getContent());
            }
        }
    }
}
