package com.spilna.sprava.dao;

import java.util.List;

import com.spilna.sprava.model.Post;
import com.spilna.sprava.businesslogic.object.PostRO;

/**
 * Interface MessageDAO
 * @author Ivanov Eduard
 * @version 1.0
 */

public interface PostDAO {
	
	/** Save and publish messages to facebook*/
	public void saveMessage (String token, Post message, String userId );
	/** Saving the post in the database*/
	public void saveMessage (String idP, String idU,String message); 
	/**Getting the user record from the database*/ 
	public List<PostRO> getMessage(String idUser);
	/**Obtaining records the number of records the user*/
	public Integer getCountPost(String idUser);
    public void updatePost(Post post);
    public Post getPostByID(long id);
    public List<Post> getAllPostInf();
	public void saveOrUpdatePost (List<com.restfb.types.Post> post, String idUser);
    
}
