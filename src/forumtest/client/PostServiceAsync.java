package forumtest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface PostServiceAsync {

	void getPosts(int topic_id, AsyncCallback<Post[]> callback);
	void getTopics(AsyncCallback<Topic[]> callback);
	void addPost(Post post, AsyncCallback<Post> callback);
	void addTopic(Post[] newTopic, AsyncCallback<Post[]> callback);
}
