package Server;

import java.net.Socket;
import java.util.HashMap;

import org.quickconnect.json.JSONInputStream;
import org.quickconnect.json.JSONOutputStream;
import org.quickconnect.json.JSONUtilities;

public class Client {

    public FavBean bean;

    Socket toServer;

    public Client(FavBean bean) {
	this.bean = bean;
    }

    public void connectToServer() {

	try {

	    toServer = new Socket("127.0.0.1", 9292);

	    System.out.println("CLIENT: socket connected.");

	    // create an input and output stream (channel/thread?) from client
	    JSONOutputStream outToServer = new JSONOutputStream(toServer.getOutputStream());
	    JSONInputStream inFromServer = new JSONInputStream(toServer.getInputStream());
	    
	    // send the bean
	    outToServer.writeObject(this.bean);

	    boolean check = true;
	    int count = 0;
	    while (check) {
		System.out.println("CLIENT: waiting for responsse.");
		HashMap result = (HashMap) inFromServer.readObject();

		System.out.println("CLIENT: Result is [" + result + "]");
		String done = (String) result.get("command");

		if (done.equals("done") || count == 3) {
		    // outToServer.writeObject("close");
		    toServer.close();
		    System.out.println("CLIENT: connection closed. COUNT["+ count + "]");
		    check = false;
		} else {
		    System.out.println("CLIENT: not done.");
		    outToServer.writeObject(this.bean);
		    count++;
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}