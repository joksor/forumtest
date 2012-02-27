package forumtest.client;



import java.util.ArrayList;
import java.util.Date;

import sun.awt.windows.ThemeReader;

import forumtest.server.User;
import forumtest.shared.FieldVerifier;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
//yo
//yo
//Halloj
public class Forumtest implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private PostServiceAsync postSvc = GWT.create(PostService.class);
	private LoginServiceAsync loginSvc = GWT.create(LoginService.class);
	
	protected int sessionId = 0;
	Date expires = new Date();
	long DURATION;
	String currentUser = null;
	int currentTopic = 0;
	int selectedListBoxRow = 0;
	
	private boolean ADDING_TO_DB = false;
	
	
	ArrayList<Topic> topics = new ArrayList<Topic>();
	ListBox listBox = new ListBox();
	HorizontalPanel topPanel = new HorizontalPanel();
	final PasswordTextBox passwordField = new PasswordTextBox();
	final TextBox nameField = new TextBox();
	final Button loginButton = new Button("Log in");
	final Grid grid = new Grid();
	HorizontalPanel topicPanel = new HorizontalPanel();
	final Label currentTopicLabel = new Label("Topic");

	protected int is_admin;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		//final MyDialogBox myBox = new MyDialogBox(this);
		final Forumtest forumtest = this;
		RootPanel rootPanel = RootPanel.get();
	    
	    passwordField.setText("Password");
	    //rootPanel.add(passwordField, 250, 44);
	    passwordField.setSize("157px", "20px");
	    
	    
	    //rootPanel.add(nameField, 20, 44);
	    nameField.setSize("157px", "20px");
	    nameField.setText("Username");
	    
	   
	    loginButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		logIn(nameField.getText(), passwordField.getText());
	    	}
	    });
	    
	    
	    
	    topPanel.add(nameField);
	    topPanel.setSpacing(10);
	    topPanel.add(passwordField);
	    topPanel.add(loginButton);
	    rootPanel.add(topPanel);
	    
	    Button btnNewTopic = new Button("New Topic");
	    btnNewTopic.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		if(sessionId == 0){
	    			Window.alert("Please log in to create a new topic");
	    		}
	    		else{
	    		MyDialogBox.showDialog(forumtest);
	    		//myBox.showDialog();
	    		}
	    	}
	    });
	    rootPanel.add(btnNewTopic);
	    
	    VerticalPanel verticalPanel = new VerticalPanel();
	    rootPanel.add(verticalPanel);
	    verticalPanel.setSize("297px", "74px");
	    
	    Label lblTopics = new Label("Topics");
	    verticalPanel.add(lblTopics);
	    
	    
	    listBox.addDoubleClickHandler(new DoubleClickHandler() {
	    	public void onDoubleClick(DoubleClickEvent event) {
	    		selectedListBoxRow = listBox.getSelectedIndex();
	    		currentTopic = ((Topic)(topics.get(listBox.getSelectedIndex()))).getId();
	    		currentTopicLabel.setText(((Topic)(topics.get(listBox.getSelectedIndex()))).getText());
	    		System.out.println(currentTopic);
	    		System.out.println(currentUser);
	    		System.out.println(topics.size());
	    		getPosts(currentTopic);
	    	}
	    });
	    
	    	    
	    verticalPanel.add(listBox);
	    listBox.setSize("400px", "200px");
	    listBox.setVisibleItemCount(5);
	    
	    
	    
	    
	    
	    Button replyButton = new Button("Reply");
	    replyButton.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		if(sessionId == 0){
	    			Window.alert("Please log in to reply");
	    		}
	    		else{
	    		MyDialogBox2.showDialog(forumtest);
	    		//myBox.showDialog();
	    		}
	    	}
	    });
	    
	    topicPanel.add(currentTopicLabel);
	    verticalPanel.add(topicPanel);
	    verticalPanel.add(grid);
	    verticalPanel.add(replyButton);
	    
	    getTopics();
	    
	    
		
		// Create a handler for the sendButton and nameField
/*		class MyHandler implements ClickHandler, KeyUpHandler {

			public void onClick(ClickEvent event) {
				
				
				try {
					System.out.println("addPost attempt:");
					//Post test_post = new Post("Yo. Zup?", jocke, test_topic, false);
					try{
					sessionId = Integer.valueOf(Cookies.getCookie("sid"));
					System.out.println("leta cookie");
					}
					catch(NumberFormatException e){
						System.out.println("fel");
					}
					System.out.println(sessionId);
					logIn("test_user","asdf");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			/*	public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}/*

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
		/*	private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
			
			
		}
	*/

		// Add a handler to send the name to the server
	/*	MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);*/
	}
	
	private void getPosts(int topic_id){
		// Initialize the service proxy.
		Post[] posts = null;
	    if (postSvc == null) {
	    	postSvc = GWT.create(PostService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<Post[]> callback = new AsyncCallback<Post[]>() {
	      public void onFailure(Throwable caught) {
	    	  
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(Post[] result) {
	    	  grid.clear();
	    	  grid.resize(result.length, 2);
	        System.out.println("Posts belonging to topic: "+currentTopic+":");
	        for(int i=0;i<result.length;i++){
	        	HTML html = new HTML(new SafeHtmlBuilder().appendEscapedLines(result[i].getText()).toSafeHtml());

	        	Label label = new Label(result[i].getUser());
	        	Label label2 = new Label(result[i].getText());
	        	label2.setWordWrap(true);
	        	grid.setWidget(i, 0, label);
	        	grid.setWidget(i, 1, html);
	        	System.out.println("Message: "+result[i].getText()+" by user: "+result[i].getUser());
	        	
	        }
	        
	      }
	    };

	    // Make the call to the stock price service.
	    postSvc.getPosts(topic_id,callback);
	    System.out.println("herro");
	    
	}
	
	protected void showPosts(Post[] posts) {
		
		
	}

	public void addPost(Post post){
		
		// Initialize the service proxy.
	    if (postSvc == null) {
	    	postSvc = GWT.create(PostService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<Post> callback = new AsyncCallback<Post>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(Post result) {
	    	  try{
	    	  getPosts(result.getTopic());
	    	  }catch(Exception e){e.printStackTrace();}
	        System.out.println("rpc ok!");
	        
	      }
	    };

	    // Make the call to the stock price service.
	    postSvc.addPost(post,callback);
	    System.out.println("herro");
	}
	
	public void addTopic(Post[] newPost){
		
		// Initialize the service proxy.
	    if (postSvc == null) {
	    	postSvc = GWT.create(PostService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<Post[]> callback = new AsyncCallback<Post[]>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(Post[] result) {
	    	  try{
	    	  getTopics();
	    	  }catch(Exception e){e.printStackTrace();}
	        System.out.println("rpc ok!");
	        
	      }
	    };

	    // Make the call to the stock price service.
	    postSvc.addTopic(newPost,callback);
	    
	}
	
	private void logIn(final String user, String password){
		
		// Initialize the service proxy.
	    if (loginSvc == null) {
	    	loginSvc = GWT.create(PostService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<int[]> callback = new AsyncCallback<int[]>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(int[] result) {
	    	  try{
	    		  sessionId = result[0];
	    		  is_admin = result[1];
	    		  if(sessionId == 0){
	    			  Window.alert("Wrong username or password, try again");
	    		  }
	    		  else{
	    		  //DURATION = 1000 * 60 * 60 * 24 * 1; //duration one day
	    		  //expires = new Date(System.currentTimeMillis() + DURATION);
	    		  //Cookies.setCookie("sid", Integer.toString(sessionId) , expires, null, "/", false);
	    			  currentUser = user;
	    			  topPanel.clear();
	    			  topPanel.add(new Label("You are logged in as: "+user));
	    			  Button logoutButton = new Button("Log out");
	    			  logoutButton.addClickHandler(new ClickHandler() {
	    			    	public void onClick(ClickEvent event) {
	    			    		sessionId = 0;
	    			    		currentUser = null;
	    			    		topPanel.clear();
	    			    		topPanel.add(nameField);
	    			    	    topPanel.add(passwordField);
	    			    	    topPanel.add(loginButton);
	    			    	    nameField.setText("username");
	    			    	    passwordField.setText("password");
	    			    	}
	    			    });
	    			  topPanel.add(logoutButton);
	    			  if(is_admin == 1){
	    				  Button handleUsersButton = new Button("Handle Users");
	    				  handleUsersButton.addClickHandler(new ClickHandler() {
		    			    	public void onClick(ClickEvent event) {
		    			    		System.out.println("crazy.");
		    			    	}
		    			    });
	    				  topPanel.add(handleUsersButton);
	    				  
	    				  Button removeTopicButton = new Button("Remove Topic");
	    				  removeTopicButton.addClickHandler(new ClickHandler() {
		    			    	public void onClick(ClickEvent event) {
		    			    		//deleteTopic(currentTopic);
		    			    	}
		    			    });
	    				  topicPanel.add(removeTopicButton);
	    				  
	    			  }
	    		  }
	    		  
	    	  }
	    	  
	    	  catch(Exception e){
	    		  
	    		  e.printStackTrace();
	    	  }
	        System.out.println("rpc ok!");
	        
	      }
	    };

	    // Make the call to the stock price service.
	    loginSvc.logIn(user,password,callback);
	    
	}
	
	private void getTopics(){
		// Initialize the service proxy.
		
	    if (postSvc == null) {
	    	postSvc = GWT.create(PostService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<Topic[]> callback = new AsyncCallback<Topic[]>() {
	      public void onFailure(Throwable caught) {
	    	  
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(Topic[] result) {
	    	  listBox.clear();
	    	  topics.clear();
	    	for(int i=0; i<result.length; i++){
	    		System.out.println("id: "+result[i].getId()+" topic id: "+result[i].getTopic());
	    		topics.add(result[i]);
	    	}
	    	for(int i=0;i<topics.size();i++){
		    	
		    listBox.addItem(topics.get(i).getText());
		    }
	        System.out.println("rpc ok!");
	        
	      }
	     
	    };

	    // Make the call to the stock price service.
	    postSvc.getTopics(callback);
	    System.out.println("herro");
	    
	}
	
}
