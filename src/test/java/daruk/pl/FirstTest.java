package daruk.pl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class FirstTest {

	/*
	 * Checking first example with 3x3, 2 Kings one Rook
	 */
	@Test
	public void test() {
		//long start = System.currentTimeMillis();
		final ChessChallenge chess = new ChessChallenge((byte)3,(byte)3,(byte)2,(byte)0,(byte)0,(byte)1,(byte)0);
		//final long stop = System.currentTimeMillis();
		
		assertEquals("The number of combination is wrong. ",4,ChessChallenge.getCombinationsCount());
		
		final String acceptedResult = 	"K00K02R21\nK00R12K20\nK02R10K22\nR01K20K22\n";
		String[] acceptedArray = acceptedResult.split("\n");
		String allCombinations = ChessChallenge.getAllCombinations();
		String[] allArray = allCombinations.split("\n");
		
		HashMap<String,String> map = new HashMap<String,String>(8);
		for(String accepted:acceptedArray) {
			map.put(accepted,"missing");
		}
		
		for(String check:allArray) {
			String one = (String) map.get(check);
			assertTrue("Unknown good combination: "+check,(one != null && "missing".equals(one)));
			
			if(one != null && "missing".equals(one)) {
				map.put(check, check);
			} else {
				map.put(check, "unknown");
			}
		}
		
		for(String key:map.keySet()) {
			assertEquals("Missing good combination: "+key,key, map.get(key));
		}
		
	}
}