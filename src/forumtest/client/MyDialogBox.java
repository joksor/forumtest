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

public class MyDialogBox extends DialogBox {

	public MyDialogBox(final Forumtest parent) {
		setHTML("My Dialog");
		
		final Post[] newTopic = new Post[2];

		final FlexTable flexTable = new FlexTable();
		setWidget(flexTable);
		flexTable.setSize("378px", "261px");
		
		Label lblNewLabel = new Label("Topic");
		flexTable.setWidget(1, 0, lblNewLabel);
		
		final TextBox textBox_1 = new TextBox();
		textBox_1.setMaxLength(60);
		flexTable.setWidget(1, 1, textBox_1);
		textBox_1.setSize("328px", "31px");
		
		
		Label lblTopic = new Label("Message");
		flexTable.setWidget(2, 0, lblTopic);
		
		VerticalPanel verticalPanel = new VerticalPanel();
		flexTable.setWidget(2, 1, verticalPanel);
		
		final TextArea textArea = new TextArea();
		verticalPanel.add(textArea);
		textArea.setSize("331px", "140px");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		flexTable.setWidget(3, 1, horizontalPanel);
		horizontalPanel.setWidth("100%");

		final Button btnLogin = new Button();
		horizontalPanel.add(btnLogin);
		horizontalPanel.setCellWidth(btnLogin, "90px");
		btnLogin.setWidth("80px");
		btnLogin.setText("Save");
		btnLogin.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				newTopic[0] = new Topic(textBox_1.getText(),parent.currentUser, true);
				newTopic[1] = new Post(textArea.getText(), parent.currentUser, newTopic[0].getId(), false);
				parent.addTopic(newTopic);
				MyDialogBox.this.hide();
				
			}
		});
		

		final Button btnCancel = new Button();
		horizontalPanel.add(btnCancel);
		
		
		btnCancel.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				
				MyDialogBox.this.hide();
			}
		});
		horizontalPanel.setCellWidth(btnCancel, "90px");
		btnCancel.setWidth("80px");
		btnCancel.setText("Cancel");
	}
	
	private static MyDialogBox dialog;

	public static void showDialog(Forumtest parent){
		if (dialog == null) {
			dialog = new MyDialogBox(parent);
			
			dialog.setSize("500px", "300px");
		}
		
		dialog.center();
	}
}