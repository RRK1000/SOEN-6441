package strategy;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
/**
 * JUnit test suite for strategy implementations.
 */
@Suite
@SelectClasses({BenevolentStrategyTest.class, AggressiveStrategyTest.class, RandomStrategyTest.class, CheaterStrategyTest.class})
public class StrategyTestSuite {
}