package forumtest.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import forumtest.server.User;


@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	int[] logIn(String user, String password) throws Exception;
}
