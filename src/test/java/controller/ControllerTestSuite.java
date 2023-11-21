package controller;

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;
@Suite
@SelectClasses({CommandParserTest.class, GameManagerTest.class, TournamentGameManagerTest.class})
public class ControllerTestSuite {

}