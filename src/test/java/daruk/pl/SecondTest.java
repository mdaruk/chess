package daruk.pl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

public class SecondTest {

	/*
	 * Checking 2nd example with 4x4, 2 Rooks, 4 Knights
	 */
	@Test
	public void test() {
		//long start = System.currentTimeMillis();
		final ChessChallenge chess = new ChessChallenge((byte)4,(byte)4,(byte)0,(byte)0,(byte)0,(byte)2,(byte)4);
		//final long stop = System.currentTimeMillis();
		
		assertEquals("The number of combination is wrong. ",8, ChessChallenge.getCombinationsCount());

		
		final String acceptedResult = 	"R02N11N13R20N31N33\nR00N11N13R22N31N33\nR01N10N12R23N30N32\nR03N10N12R21N30N32\n"+
										"N01N03R10N21N23R32\nN01N03R12N21N23R30\nN00N02R11N20N22R33\nN00N02R13N20N22R31\n";
		String[] acceptedArray = acceptedResult.split("\n");
		String allCombinations = ChessChallenge.getAllCombinations();
		
		System.out.println(allCombinations);
		
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
