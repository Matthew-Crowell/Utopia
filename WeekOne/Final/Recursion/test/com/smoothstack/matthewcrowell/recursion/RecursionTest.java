package test.com.smoothstack.matthewcrowell.recursion;

import com.smoothstack.matthewcrowell.recursion.Recursion;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class to test Recursion.
 */
public class RecursionTest {

	List<Integer> listTwo;
	Integer listTwoTarget;
	List<Integer> listThree;
	Integer listThreeTarget;
	private Integer begin;
	private List<Integer> listOne;
	private Integer listOneTarget;
	private Recursion app;

	/**
	 * Prepares the fixtures for the Recursion tests.
	 */
	@Before
	public void setUp() {
		try {
			app = new Recursion();

			begin = 0;

			listOne = new ArrayList<>();
			Collections.addAll(listOne, 2, 4, 8);
			listOneTarget = 10;

			listTwo = new ArrayList<>();
			Collections.addAll(listTwo, 1, 2, 4, 8, 1);
			listTwoTarget = 14;

			listThree = new ArrayList<>();
			Collections.addAll(listThree, 2, 4, 4, 8);
			listThreeTarget = 14;
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	/**
	 * Test the Recursion class's groupSumClump method.
	 */
	@Test
	public void groupSumClump() {
		assertEquals(true, app.groupSumClump(begin, listOne, listOneTarget));
		assertEquals(true, app.groupSumClump(begin, listTwo, listTwoTarget));
		assertEquals(false, app.groupSumClump(begin, listThree, listThreeTarget));
	}
}