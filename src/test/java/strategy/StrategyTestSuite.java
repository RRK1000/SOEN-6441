package strategy;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({BenevolentStrategyTest.class, AggressiveStrategyTest.class})
public class StrategyTestSuite {
}