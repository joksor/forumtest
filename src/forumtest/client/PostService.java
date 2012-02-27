package forumtest.client;

import com.google.gwt.user.client.rpc.RemoteService;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;



@RemoteServiceRelativePath("posts")
public interface PostService extends RemoteService {

	Post[] getPosts(int topic_id) throws Exception;
	Topic[] getTopics() throws Exception;
	Post addPost(Post post) throws Exception;
	Post[] addTopic(Post[] newTopic) throws Exception;
}
