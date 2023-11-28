package controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
/**
 * JUnit test suite for controller classes.
 */
@Suite
@SelectClasses({CommandParserTest.class, GameManagerTest.class, TournamentGameManagerTest.class})
public class ControllerTestSuite {

}