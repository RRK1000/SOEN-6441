package phases;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains JUnit test cases for Phase class.
 * @author Nimisha Jadav
 */
class PhaseTest {

    /**
     * Tests whether the nextPhase method of StartupPhase returns an instance of IssueOrderPhase
     */
    @Test
    public void testNextPhase1(){
        Phase l_startUpPhase = StartupPhase.getInstance();
        Phase l_nextPhase = l_startUpPhase.nextPhase();
        assertTrue(l_nextPhase instanceof IssueOrderPhase);
    }

    /**
     * Tests whether the nextPhase method of IssueOrderPhase returns an instance of ExecuteOrderPhase
     */
    @Test
    public void testNextPhase2(){
        Phase l_issueOrderPhase = IssueOrderPhase.getInstance();
        Phase l_nextPhase = l_issueOrderPhase.nextPhase();
        assertTrue(l_nextPhase instanceof ExecuteOrderPhase);
    }
}