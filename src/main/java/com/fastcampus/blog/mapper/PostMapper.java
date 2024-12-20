package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.request.post.CreatePostRequest;
import com.fastcampus.blog.response.post.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post mapToCreatePostResponse(CreatePostRequest postRequest);

    CreatePostResponse mapToCreatePostResponse(Post post);

    GetPostResponse mapToGetPostResponse(Post post);

    UpdatePostBySlugResponse mapToUpdatePostBySlugResponse(Post post);

    DeletePostByIdResponse mapToDeletePostByIdResponse(Post post);

    PublishPostResponse mapToPublishPostResponse(Post post);
}
