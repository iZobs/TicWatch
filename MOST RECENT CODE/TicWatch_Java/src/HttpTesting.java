import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class HttpTesting {

    public static class TestJunit extends TestCase {

	@Test
	public void testHttp1() {
	    HttpExample http = new HttpExample("AAPL+ORCL+GOOG");
	    assertNotNull(http.getStringBeans());
	    assertEquals("AAPL+ORCL+GOOG", http.getSymbols());
	} // end testHttp1
	
	@Test
	public void testHttp2() {
	    String[] s = {"AAPL","ORCL","GOOG"};
	    HttpExample http = new HttpExample(s);
	    assertNotNull(http.getStringBeans());
	    assertEquals("AAPL+ORCL+GOOG", http.getSymbols());
	} // end testHttp2
	
	@Test
	public void testHttp3() {
	    HttpExample http = new HttpExample("AAPL");
	    assertNotNull(http.getStringBeans());
	    assertEquals("AAPL", http.getSymbols());
	} // end testHttp3
	
	@Test
	public void testHttp4() {
	    HttpExample http = new HttpExample("AAPL");
	    assertNotNull(http.getStringBeans());
	    assertEquals("AAPL", http.getSymbols());
	} // end testHttp4
	
    } // end TestJunit class
    
    public static void main(String[] args) {
	Result result = JUnitCore.runClasses(TestJunit.class);
	for (Failure failure : result.getFailures()) {
	    System.out.println(failure.toString());
	}
	System.out.println(result.wasSuccessful());
    } // end main

} // end HttpTesting class