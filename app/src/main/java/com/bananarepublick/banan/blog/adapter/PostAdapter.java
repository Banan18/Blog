package com.bananarepublick.banan.blog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bananarepublick.banan.blog.R;
import com.bananarepublick.banan.blog.api.ApiService;
import com.bananarepublick.banan.blog.model.Post;
import com.bananarepublick.banan.blog.utils.InternetConnection;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Banan on 16.02.2018.
 */

public class PostAdapter extends ArrayAdapter<Post> {
    List<Post> posts;
    Context context;
    ApiService apiService;
    private LayoutInflater mInflater;
    PostAdapter postAdapter = this;

    public PostAdapter( Context context,List<Post> objects,ApiService apiService) {
        super(context,0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.apiService = apiService;
        posts = objects;
    }


    public void addPost(List<Post> objects) {
       posts = objects;
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            View view = mInflater.inflate(R.layout.layout_row,parent,false);
            viewHolder = ViewHolder.create((LinearLayout)view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Post item = getItem(position);
        viewHolder.textViewBody.setText(item.getBody());
        viewHolder.textViewTitle.setText(item.getTitle());
        if (item.getUserId() == 3){
            viewHolder.delete.setVisibility(View.VISIBLE);
            viewHolder.change.setVisibility(View.VISIBLE);
        }
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnection.checkConnection(context)) {
                    Call<Post> delete = apiService.delete(position);
                    delete.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            posts.remove(position);

                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }else Toast.makeText(context,"Подключите интернет",Toast.LENGTH_SHORT).show();

            }
        });

        viewHolder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Post> change = apiService.change(position,new Post(3,"Изменение","Изменение"));
                change.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        Log.d("res", String.valueOf(response.body().getId()));
                        posts.remove(position);
                        posts.add(position,response.body());

                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });

            }
        });


return viewHolder.linearLayout;
    }

    private static class ViewHolder{
        public final LinearLayout linearLayout;
        public final TextView textViewTitle;
        public final TextView textViewBody;
        public final Button delete;
        public final Button change;


        private ViewHolder(LinearLayout linearLayout, TextView textViewTitle, TextView textViewBody, Button delete,Button change) {
            this.linearLayout = linearLayout;
            this.textViewTitle = textViewTitle;
            this.textViewBody = textViewBody;
            this.delete = delete;
            this.change = change;
        }

        public static ViewHolder create(LinearLayout constraintLayout){
            TextView textViewTitle = (TextView) constraintLayout.findViewById(R.id.textViewTitle);
            TextView textViewBody = (TextView) constraintLayout.findViewById(R.id.textViewBody);
            Button delete = (Button) constraintLayout.findViewById(R.id.button);
            Button change = (Button) constraintLayout.findViewById(R.id.buttonChange);
            return new ViewHolder(constraintLayout,textViewTitle,textViewBody,delete,change);
        }
    }
}
