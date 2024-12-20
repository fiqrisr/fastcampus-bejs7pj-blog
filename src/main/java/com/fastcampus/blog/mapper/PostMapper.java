package com.fastcampus.blog.mapper;

import com.fastcampus.blog.entity.Post;
import com.fastcampus.blog.request.CreatePostRequest;
import com.fastcampus.blog.resposne.CreatePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post map(CreatePostRequest postRequest);

    CreatePostResponse map(Post post);
}
