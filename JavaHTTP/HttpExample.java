package http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.quickconnect.json.JSONInputStream;

public class HttpExample {
    
    String symbols = "";
    HashMap<String, Object> data;
    ArrayList<SymbolBean> stringBeans = new ArrayList<SymbolBean>();
    
    public HttpExample(String symbols) {
	setSymbols(symbols);
	runHttp();
    }
    
    public HttpExample(String[] symbols) {
	int count = symbols.length - 1;
	for (int i=0;i<symbols.length;i++) {
	    if (i != count) {
		this.symbols += symbols[i]+"+";
	    } else {
		this.symbols += symbols[i];
	    }
	}
	runHttp();
    }

    @SuppressWarnings("unchecked")
    public void runHttp() {
	// Somewhere in your code this is called in a
	// thread which is not the user interface thread

	try {
	    // Set the URL object to point to the JSON file
	    URL url = new URL("http://cwcraigo.com/stockfeed.php?symbols="+this.symbols);

	    // Connect to the site using HttpURLConnecton
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();

	    // Setup input stream using JSONInputStream to easily retrieve data
	    JSONInputStream input = new JSONInputStream(con.getInputStream());

	    // Get the JSON object as a HashMap
	    setData((HashMap<String, Object>) input.readObject());
	    
	    HashMap<String, String> map = new HashMap<String,String>();
	    for (String key : this.data.keySet()) {
		SymbolBean bean = new SymbolBean();
		bean.setSymbol(key);
		map = (HashMap<String, String>) this.data.get(key);
		bean.setName(map.get("name"));
		bean.setSymbol(map.get("symbol"));
		bean.setPrice(map.get("price"));
		bean.setChange(map.get("change"));
		bean.setChangePercent(map.get("changePercent"));
		bean.setOpen(map.get("open"));
		bean.setLow(map.get("low"));
		bean.setHigh(map.get("high"));
		bean.setMarket(map.get("market"));
		this.stringBeans.add(bean);
	    }

//	    System.out.println(this.stringBeans.toString());

	} catch (Exception e) {
	    e.printStackTrace();
	}

    } // end constructor

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public ArrayList<SymbolBean> getStringBeans() {
        return stringBeans;
    }

    public void setStringBeans(ArrayList<SymbolBean> stringBeans) {
        this.stringBeans = stringBeans;
    }
    
    
}
