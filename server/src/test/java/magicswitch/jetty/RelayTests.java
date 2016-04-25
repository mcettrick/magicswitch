package magicswitch.jetty;

import org.junit.Test;

import magicswitch.jetty.service.Relay;

// integration tests - relay must be connected
public class RelayTests {
	
//	private static final Logger log = LoggerFactory.getLogger(RelayTests.class);
	
	private Relay relay = new Relay();
	
	@Test
	public void status() {
		relay.getPortNames();
	}
	
	@Test
	public void onOff() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			relay.turnOn();
			Thread.sleep(100);
			relay.turnOff();
			Thread.sleep(100);
		}
	}
	
	@Test
	public void toggle() throws InterruptedException {
		relay.turnOff();
		for (int i = 0; i < 10; i++) {
			relay.toggle();
			Thread.sleep(100);
		}
	}
}
