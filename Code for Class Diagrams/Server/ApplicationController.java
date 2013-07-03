package Server;

import java.net.Socket;
import java.util.HashMap;

public class ApplicationController {

    private HashMap<String, AppHandler> commMap;

    public ApplicationController() {
	commMap = new HashMap<String, AppHandler>();
	commMap.put("Update", new UpdateFavorites());
	commMap.put("Retrieve", new RetrieveFavorites());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void handleRequest(HashMap input, Socket clientSocket) {
	FavBean bean = new FavBean(input);
	String cmd = bean.getCommand();
	AppHandler handler = commMap.get(cmd);
	handler.handleIt(bean,clientSocket);
    }
}