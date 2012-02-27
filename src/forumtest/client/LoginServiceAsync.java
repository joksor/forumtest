package forumtest.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import forumtest.server.User;

public interface LoginServiceAsync {

	void logIn(String user, String password, AsyncCallback<int[]> callback);
}
