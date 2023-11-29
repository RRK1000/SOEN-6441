package orders;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit test suite for orders classes.
 */
@Suite
@SelectClasses({AdvanceOrderTest.class, AirliftOrderTest.class, BlockadeOrderTest.class, BombOrderTest.class, DeployOrderTest.class,
        NegotiateOrderTest.class})
public class OrdersTestSuite {

}