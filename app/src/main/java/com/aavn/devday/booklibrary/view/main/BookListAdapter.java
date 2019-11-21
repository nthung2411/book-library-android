package com.aavn.devday.booklibrary.view.main;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aavn.devday.booklibrary.R;
import com.aavn.devday.booklibrary.data.model.Book;
import com.aavn.devday.booklibrary.data.model.BookDetail;
import com.aavn.devday.booklibrary.data.model.BookViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {


    private List<BookViewModel> items = new ArrayList<>();
    private BookListAdapter.OnItemClickListener clickListener;

    public BookListAdapter(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        BookViewModel data = items.get(position);
        holder.bindData(data, clickListener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<BookViewModel> data) {
        items = new ArrayList<>();
        items.addAll(data);
        notifyDataSetChanged();
    }

    void clearData(){
        items.clear();
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(BookViewModel item);
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView briefDesTv;
        TextView authorTv;
        ImageView coverIv;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_item_book_title);
            briefDesTv = itemView.findViewById(R.id.tv_item_book_brief_description);
            authorTv = itemView.findViewById(R.id.tv_item_book_author);
            coverIv = itemView.findViewById(R.id.iv_book_cover);
        }

        void bindData(BookViewModel data, BookListAdapter.OnItemClickListener listener) {

            titleTv.setText(data.getTitle());

            if(data.getSource() == null || data.getSource().isEmpty()){
                authorTv.setText(data.getAuthor());
            } else {
                authorTv.setText(data.getSource());
            }

            if(data.getDescription() != null && !data.getDescription().isEmpty()){
                bindDescription(data.getDescription());
            }

            if(data.getCoverUrl() != null && !data.getCoverUrl().isEmpty()){
                Glide.with(coverIv)
                        .load(data.getCoverUrl())
                        .placeholder(R.drawable.book_cover_placeholder)
                        .thumbnail(0.1f)
                        .into(coverIv);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(data);
                }
            });
        }

        private void bindDescription(String rawData){
            String description = "";

            if(rawData != null && rawData.length() > 0){
                description = rawData;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                briefDesTv.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
            } else {
                briefDesTv.setText(Html.fromHtml(description));
            }
        }
    }

}
