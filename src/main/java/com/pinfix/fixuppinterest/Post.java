package com.pinfix.fixuppinterest;

/** Represents the important elements of a Pinterest post */
public class Post {

    public final String imageUrl; /** The URL of the image in the post */
	public final String title; /** Title of this post */
	public final String srcUrl; /** The URL of this post on Pinterest */
    public final String description; /** Post description */

	public Post(String imageUrl, String title, String srcUrl, String description){
		this.imageUrl = imageUrl;
		this.title = title;
		this.srcUrl = srcUrl;
		this.description = description;
	}
}