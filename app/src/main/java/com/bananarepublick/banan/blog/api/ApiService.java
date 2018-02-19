package com.bananarepublick.banan.blog.api;

import com.bananarepublick.banan.blog.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Banan on 16.02.2018.
 */

  public   interface ApiService {

    @GET("posts")
    Call<List<Post>> getMyJSON();


    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @DELETE("posts/{id}")
    Call<Post> delete(@Path("id")Integer id);

    @PUT("posts/{id}")
    Call<Post> change (@Path("id")Integer id,@Body Post post);


}
