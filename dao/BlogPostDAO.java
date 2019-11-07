package dao;

import java.util.List;

import project.backend.BlogComment;
import project.backend.BlogPost;

public interface BlogPostDAO {

	void saveBlogPost(BlogPost blogPost);

	List<BlogPost> getBlogPosts(int approved);
	
	BlogPost getBlogPostById(int id);

	void updateBlogPost(BlogPost blogPost);

	void addBlogcomment (BlogComment blogComment);
	
	List<BlogComment> getAllBlogComments(int blogPostId);
}
