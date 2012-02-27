package forumtest.client;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextArea;

public class MyDialogBox2 extends DialogBox {
	
	

	public MyDialogBox2(final Forumtest parent) {
		setHTML("My Dialog");

		final FlexTable flexTable = new FlexTable();
		setWidget(flexTable);
		flexTable.setSize("378px", "261px");
		

		Label lblText = new Label("Topic: "+((parent.topics).get(parent.selectedListBoxRow)).getText());
		flexTable.setWidget(1, 1, lblText);
		
		Label lblSvara = new Label("Reply");
		flexTable.setWidget(2, 0, lblSvara);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		flexTable.setWidget(2, 1, verticalPanel);
		
		final TextArea textArea = new TextArea();
		verticalPanel.add(textArea);
		textArea.setSize("270px", "58px");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		flexTable.setWidget(3, 1, horizontalPanel);
		horizontalPanel.setWidth("100%");

		final Button btnReply = new Button();
		horizontalPanel.add(btnReply);
		horizontalPanel.setCellWidth(btnReply, "90px");
		btnReply.setWidth("80px");
		btnReply.setText("Save");
		btnReply.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Post newPost = new Post(textArea.getText(), parent.currentUser, parent.currentTopic, false);
				parent.addPost(newPost);
				textArea.setText(null);
				MyDialogBox2.this.hide();
				
			}
		});
		
		Button btnRemove = new Button("Remove");
		horizontalPanel.add(btnRemove);

		final Button btnCancel = new Button();
		horizontalPanel.add(btnCancel);
		btnCancel.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				
				MyDialogBox2.this.hide();
			}
		});
		horizontalPanel.setCellWidth(btnCancel, "90px");
		btnCancel.setWidth("80px");
		btnCancel.setText("Cancel");
	}
	
	private static MyDialogBox2 dialog;

	public static void showDialog(Forumtest parent){
		if (dialog == null) {
			dialog = new MyDialogBox2(parent);
			
			dialog.setSize("500px", "300px");
		}
		
		dialog.center();
	}
}