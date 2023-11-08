package orders;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({AdvanceOrderTest.class, AirliftOrderTest.class, BlockadeOrderTest.class, BombOrder.class, DeployOrderTest.class,
        NegotiateOrderTest.class})
/**
 * Test suite for the orders package
 */
public class OrdersTestSuite {

}