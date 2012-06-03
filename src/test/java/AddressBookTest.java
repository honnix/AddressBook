import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import com.xiao.project.AddressBook;

public class AddressBookTest extends Thread {

	private static Object lock = new Object();
	
	public void testMain() throws InterruptedException {
		this.start();
		AddressBook.main(new String[] {});
	}

	public void run() {
		ByteArrayInputStream input = new ByteArrayInputStream(
				"add\r\n".getBytes());
		System.setIn(input);
		try {
			Thread.sleep(100);
			input.read("aaa\r\n".getBytes());
			Thread.sleep(100);
			input.read("a\r\n".getBytes());
			Thread.sleep(100);
			input.read("13411111111\r\n".getBytes());
			Thread.sleep(100);
			input.read("a\r\n".getBytes());
			Thread.sleep(100);
			input.reset();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
