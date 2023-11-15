package gamelog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the LogEntryBuffer class
 * 
 * @author Yusuke Ishii
 */
public class LogEntryBufferTest {

	private LogEntryBuffer d_logBuffer;

	private MockObserver d_mockObserver;

	/**
	 * Setup method to initialize the test environment
	 */
	@BeforeEach
	public void setUp() {
		d_logBuffer = new LogEntryBuffer();
		d_mockObserver = new MockObserver();
	}

	/**
	 * Test to check if an observer can be added
	 */
	@Test
	public void testAddObserver() {
		d_logBuffer.addObserver(d_mockObserver);
		d_logBuffer.setActionInfo("Test Action");
		assertTrue(d_mockObserver.isUpdated());
	}

	/**
	 * Test to check if an observer can be removed
	 */
	@Test
	public void testRemoveObserver() {
		d_logBuffer.addObserver(d_mockObserver);
		d_logBuffer.removeObserver(d_mockObserver);
		d_mockObserver.reset();
		d_logBuffer.setActionInfo("Test Action");
		assertFalse(d_mockObserver.isUpdated());
	}

	/**
	 * Test to check if action info can be set and retrieved
	 */
	@Test
	public void testSetAndGetActionInfo() {
		String l_actionInfo = "Sample Action";
		d_logBuffer.setActionInfo(l_actionInfo);
		assertEquals(l_actionInfo, d_logBuffer.getActionInfo());
	}

	/**
	 * Mock observer class for testing
	 */
	private class MockObserver implements Observer {
		private boolean d_updated = false;

		/**
		 * Update method for the observer
		 * 
		 * @param p_o   Observable object
		 * @param p_arg Argument passed to the observer
		 */
		@Override
		public void update(Observable p_o) {
			d_updated = true;
		}

		/**
		 * Check if the observer was updated
		 * 
		 * @return true if updated, false otherwise
		 */
		public boolean isUpdated() {
			return d_updated;
		}

		/**
		 * Reset the updated flag
		 */
		public void reset() {
			d_updated = false;
		}
	}
}
