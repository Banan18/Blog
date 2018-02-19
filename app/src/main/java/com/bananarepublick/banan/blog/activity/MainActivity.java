package com.bananarepublick.banan.blog.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bananarepublick.banan.blog.R;
import com.bananarepublick.banan.blog.adapter.PostAdapter;
import com.bananarepublick.banan.blog.api.ApiService;
import com.bananarepublick.banan.blog.api.RetroClient;
import com.bananarepublick.banan.blog.model.Post;
import com.bananarepublick.banan.blog.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private View view;
    private List<Post> posts;
    private PostAdapter postAdapter;
    ProgressBar progressBar;
    private ApiService apiService;
    final int userID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        posts = new ArrayList<>();

        if (InternetConnection.checkConnection(getApplicationContext())) {
            showPosts();
        }else Toast.makeText(getApplicationContext(),"Подключите интерент",Toast.LENGTH_SHORT).show();
        view = findViewById(R.id.parentLayout);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.fab);
        assert fb != null;
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    Call<Post> createPost = apiService.createPost(new Post(userID, "Ваш заголовок", "Ваш текст"));
                    createPost.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {

                            posts.add(response.body());
                            Log.d("res",response.body().toString() +" "+response.body().getId()+"\n "+posts.toString());
                            postAdapter.notifyDataSetChanged();



                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Подключите интерент",Toast.LENGTH_SHORT).show();

                        }
                    });


                }else Toast.makeText(getApplicationContext(),"Подключите интернет",Toast.LENGTH_SHORT).show();
            }
        });


    }





    public void showPosts() {



             apiService = RetroClient.getApiService();

            Call<List<Post>> call = apiService.getMyJSON();

            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                    if (response.isSuccessful()) {

                        posts = response.body();

                        postAdapter = new PostAdapter(MainActivity.this, posts,apiService);
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        listView.setAdapter(postAdapter);
                        Log.d("res", String.valueOf(posts));

                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {

                }
            });




    }
}
