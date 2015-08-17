package daruk.pl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ThirdTest {

	/*
	 * Checking 3rd example with 7x7, 2 Kings, 2 Queens, 2 Bishops, 1 Knight
	 */
	@Test
	public void test() {
		long start = System.currentTimeMillis();
		final ChessChallenge chess = new ChessChallenge((byte)7,(byte)7,(byte)2,(byte)2,(byte)2,(byte)0,(byte)1);
		final long stop = System.currentTimeMillis();
		
		assertEquals("The number of combination is wrong. ",3063828,ChessChallenge.getCombinationsCount());
		System.out.println("Total time: "+ (stop-start)+ " ms solutions count:"+ChessChallenge.getCombinationsCount());
	}

}
