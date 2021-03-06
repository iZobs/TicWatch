package Server;

import java.net.Socket;
import java.util.HashMap;

import org.quickconnect.json.JSONInputStream;
import org.quickconnect.json.JSONOutputStream;

public class Client implements Runnable {

	public FavBean bean;

	Socket toServer;

	public Client(FavBean bean) {
		this.bean = bean;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {

		try {

			toServer = new Socket("127.0.0.1", 9292);

			System.out.println("CLIENT: socket connected.");

			// create an input and output stream (channel) from client
			JSONOutputStream outToServer = new JSONOutputStream(
					toServer.getOutputStream());
			JSONInputStream inFromServer = new JSONInputStream(
					toServer.getInputStream());
			// send the bean
			outToServer.writeObject(this.bean);

			boolean check = true;
			int count = 0;
			while (check) {
				System.out.println("CLIENT: waiting for responsse.");
				HashMap result = (HashMap) inFromServer.readObject();

				System.out.println("CLIENT: Result is [" + result + "]");
				this.bean = new FavBean(result);
				String done = this.bean.getCommand();

				if (done.equals("done") || count == 3) {
					toServer.close();
					System.out.println("CLIENT: connection closed. COUNT["
							+ count + "]");
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
