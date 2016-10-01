package test.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Test;

import main.java.CustomHashMap;

public class HMapTest {

	private static int TABLE_SIZE = 100;
	private static final String DATA_FILE = "src/test/resources/data.txt";

	@Test
	public void testCustomHashTable(){
		/* Creating two identical sized hashmaps */
		CustomHashMap chm = new CustomHashMap(TABLE_SIZE);
		CustomHashMap chm2 = new CustomHashMap(TABLE_SIZE);
		assert(chm.equals(chm2));
		
		/* Creating two different sized hashmaps */
		chm = new CustomHashMap(TABLE_SIZE - 1);
		chm2 = new CustomHashMap(TABLE_SIZE);
		assert(!chm.equals(chm2));
		
		/* Creating two empty hashmaps */
		chm = new CustomHashMap(0);
		chm2 = new CustomHashMap(0);
		assert(chm.equals(chm2));
	}

	@Test
	public void testSet(){
		/* Creating two identical sized hashmaps */
		CustomHashMap chm = new CustomHashMap(TABLE_SIZE);
		CustomHashMap chm2 = new CustomHashMap(TABLE_SIZE);
		
		/* Setting key and value that are null */
		assert(chm.set(null, null));
		assert(chm2.set(null, null));
		assert(chm.equals(chm2));
		assert(chm.load() == 1/TABLE_SIZE);
		assert(chm2.load() == 1/TABLE_SIZE);
		
		/* Setting null key with non-null value */
		Object key0 = null;
		String value0 = "In essence, a Turing machine is imagined to be a simple computer that reads and writes symbols one at a time on an endless tape by strictly following a set of rules.";
		chm.set(key0, value0);
		chm2.set(key0, value0);
		assert(chm.equals(chm2));
		assert(chm.load() == 2/TABLE_SIZE);
		assert(chm2.load() == 2/TABLE_SIZE);
		
		/* Setting non-null key with null value */
		String key1 = "A Turing machine is an abstract machine that manipulates symbols on a strip of tape according to a table of rules";
		Object value1 = null;
		
		/* Adding identical key & value pair to maps */
		String key2 = "A";
		int value2 = 2;
		assert(chm.set(key2, value2));
		assert(chm2.set(key2, value2));
		assert(chm.equals(chm2));
		assert(chm.load() == 3/TABLE_SIZE);
		assert(chm2.load() == 3/TABLE_SIZE);
		
		/* Adding different identical key & value pair */
		String key3 = "BCD";
		double value3 = 17.3;
		assert(chm.set(key3, value3));
		assert(chm2.set(key3, value3));
		assert(chm.equals(chm2));
		assert(chm.load() == 4/TABLE_SIZE);
		assert(chm2.load() == 4/TABLE_SIZE);
		
		/* Setting key2, value2 pair again to first map (no change in load) */
		chm.set(key2, value2);
		assert(chm.equals(chm2));
		assert(chm.load() == 4/TABLE_SIZE);
		assert(chm2.load() == 4/TABLE_SIZE);
		/* Setting same key2, value2 pair as above to second map */
		assert(chm2.set(key2, value2));
		assert(chm.equals(chm2));
		assert(chm.load() == 4/TABLE_SIZE);
		assert(chm2.load() == 4/TABLE_SIZE);
		
		/* Setting key4, value4 pair to only first map */
		String key4 = "Aurora in the sky";
		int value4 = 3;
		assert(chm.set(key4, value4));
		assert(!chm.equals(chm2));
		assert(chm.load() == 5/TABLE_SIZE);
		assert(chm2.load() == 4/TABLE_SIZE);
		
		/* Setting key4, value4 pair to second map */
		assert(chm2.set(key4, value4));
		assert(chm.equals(chm2));
		assert(chm.load() == 5/TABLE_SIZE);
		assert(chm2.load() == 5/TABLE_SIZE);
		
		/* Setting key5, value5 pair to only first map */
		String key5 = "The quick brown fox jumps over the lazy dog";
		String value5 = "Pangrams are fun";
		assert(chm.set(key5, value5));
		assert(!chm.equals(chm2));
		assert(chm.load() == 6/TABLE_SIZE);
		assert(chm2.load() == 5/TABLE_SIZE);
		
		/* Setting key5, value5 pair to second map */
		assert(chm2.set(key5, value5));
		assert(chm.equals(chm2));
		assert(chm.load() == 6/TABLE_SIZE);
		assert(chm2.load() == 6/TABLE_SIZE);
		
		/* Repeatedly setting key, value pairs to both maps */
		for(int x=0; x<10; x++){
			assert(chm.set(key2, value4));
			assert(chm2.set(key2, value4));
			assert(chm.load() == 6/TABLE_SIZE);
			assert(chm2.load() == 6/TABLE_SIZE);
		}
		assert(chm.equals(chm2));
		
		/* Extra tests */
		String[] data = createData();
		for(int x=0; x<50; x++){
			String randomKey = getRandomData(data);
			String randomValue = getRandomData(data);
			assert(chm.set(randomKey, randomValue));
		}
	}

	@Test
	public void testGet(){
		CustomHashMap chm = new CustomHashMap(TABLE_SIZE);
		
		/* Setting then getting key and value that are null */
		Object key0 = null;
		Object value0 = null;
		chm.set(key0, value0);
		assert(chm.get(key0) == value0);
		
		/* Setting then getting non-null key and null value */
		Object key1 = "A team of explorers say they've discovered that a cave in the eastern Czech Republic is the world's deepest flooded fissure, going at least 404 meters (1,325 feet) deep";
		Object value1 = null;
		chm.set(key1, value1);
		assert(chm.get(key1) == value1);
		
		/* Setting then getting null key and non-null value */
		chm.set(key0, key1);
		assert(chm.get(key0).equals(key1));
		
		/* Setting then getting key, value pair */
		String key2 = "Elon Musk says 'There’s a one in billions chance we’re base reality'";
		String value2 = "Simulated reality is the hypothesis that reality could be simulated—for example by computer simulation—to a degree indistinguishable from 'true' reality. It could contain conscious minds which may or may not be fully aware that they are living inside a simulation.";
		chm.set(key2, value2);
		assert(chm.get(key2).equals(value2));
		
		/* Setting then getting different key, value pair */
		String key3 = "A Turing machine is an abstract machine that manipulates symbols on a strip of tape according to a table of rules";
		String value3 = "In essence, a Turing machine is imagined to be a simple computer that reads and writes symbols one at a time on an endless tape by strictly following a set of rules.";
		chm.set(key3, value3);
		assert(chm.get(key3).equals(value3));
		
		/* Setting then getting same key, value pair */
		chm.set(key3, value3);
		assert(chm.get(key3).equals(value3));
		
		/* Setting then getting different key, value pair */
		String key4 = "In a deterministic Turing machine, the set of rules prescribes at most one action to be performed for any given situation.";
		String value4 = "By contrast, a non-deterministic Turing machine (NTM) may have a set of rules that prescribes more than one action for a given situation.";
		chm.set(key4, value4);
		assert(chm.get(key4).equals(value4));
		
		/* Setting then getting different key, value pair */
		String key5 = "Hawking radiation is black-body radiation that is predicted to be released by black holes, due to quantum effects near the event horizon.";
		String value5 = "Some scientists predict that Hawking radiation could be studied by analogy using sonic black holes, in which sound perturbations are analogous to light in a gravitational black hole and the flow of an approximately perfect fluid is analogous to gravity";
		chm.set(key5, value5);
		assert(chm.get(key5).equals(value5));
		
		/* Setting then getting different key, value pair */
		chm.set(key1, value4);
		assert(chm.get(key1).equals(value4));
		
		/* Extra tests: setting then getting */
		String[] data = createData();
		for(int x=0; x<50; x++){
			String randomKey = getRandomData(data);
			String randomValue = getRandomData(data);
			assert(chm.set(randomKey, randomValue));
			assert(chm.get(randomKey).equals(randomValue));
		}
		
		/* Extra tests: setting then getting then deleting then getting */
		for(int x=0; x<50; x++){
			String randomKey = getRandomData(data);
			String randomValue = getRandomData(data);
			assert(chm.set(randomKey, randomValue));
			assert(chm.get(randomKey).equals(randomValue));
			chm.delete(randomKey);
			assert(chm.get(randomKey) == null);
		}
	}

	@Test
	public void testDelete(){
		CustomHashMap chm = new CustomHashMap(TABLE_SIZE);
		
		/* Setting then deleting key and value that are null */
		Object key0 = null;
		Object value0 = null;
		chm.set(key0, value0);
		assert(chm.delete(key0) == value0);
		assert(chm.get(key0) == null);
		assert(chm.load() == 0);
		
		/* Setting then deleting non-null key, null value */
		Object key1 = "A team of explorers say they've discovered that a cave in the eastern Czech Republic is the world's deepest flooded fissure, going at least 404 meters (1,325 feet) deep";
		Object value1 = null;
		chm.set(key1, value1);
		assert(chm.delete(key1) == value1);
		assert(chm.get(key1) == null);
		assert(chm.load() == 0);
		
		/* Setting then deleting null key, non-null value */
		chm.set(key0, key1);
		assert(chm.delete(key0).equals(key1));
		assert(chm.get(key0) == null);
		assert(chm.load() == 0);
		
		/* Setting then deleting non-null key, value */
		String key2 = "ATP synthase has been described as 'a splendid molecular machine,' and 'one of the most beautiful' of 'all enzymes'";
		String value2 = "With its near-TABLE_SIZE% efficiency, far surpassing human technology, ATP synthase manifests clear evidence not merely of engineering but of brilliant engineering.";
		chm.set(key2, value2);
		assert(chm.delete(key2).equals(value2));
		assert(chm.get(key2) == null);
		assert(chm.load() == 0);
		
		/* Setting then deleting another non-null key, value */
		int key3 = 5;
		double value3 = 6.3;
		assert(chm.delete(key3) == null);
		chm.set(key3, value3);
		assert(chm.delete(key3).equals(value3));
		assert(chm.get(key3) == null);
		assert(chm.load() == 0);
		
		/* Setting then deleting non-null keys and values */
		String key4 = "Quantum measurements are often inherently unpredictable, yet the usual way in which quantum theory accounts for unpredictability has long been viewed as somewhat unsatisfactory.";
		String value4 = "In a new study, University of Oxford physicist Chiara Marletto has developed an alternative way to account for the unpredictability observed in quantum measurements by using the recently proposed theory of superinformation—a theory that is inherently non-probabilistic. The new perspective may lead to new possibilities in the search for a successor to quantum theory.";
		chm.set(key4, value4);
		String key5 = "A";
		double value5 = -8443885.3;
		String key6 = "BCD";
		double value6 = 848583;
		chm.set(key4, value4);
		chm.set(key5, value5);
		chm.set(key6, value6);
		assert(chm.delete(key4).equals(value4));
		assert(chm.load() == 2/TABLE_SIZE);
		assert(chm.delete(key5).equals(value5));
		assert(chm.load() == 1/TABLE_SIZE);
		assert(chm.delete(key6).equals(value6));
		assert(chm.load() == 0);
		
		/* Ensuring already deleted keys are gone */
		assert(chm.delete(key4) == null);
		assert(chm.delete(key5) == null);
		assert(chm.delete(key6) == null);
	}

	@Test
	public void testLoad(){
		CustomHashMap chm = new CustomHashMap(TABLE_SIZE);
		String[] data = createData();
		int numItems = 0;
		for(int x=0; x<50; x++){
			String randomKey = getRandomData(data);
			String randomValue = getRandomData(data);
			boolean success = chm.set(randomKey, randomValue);
			if(success){
				numItems++;
			}
		}
		assert(chm.load() == numItems/TABLE_SIZE);
	}
	
	@Test
	public void testEquals(){
		CustomHashMap chm = new CustomHashMap(TABLE_SIZE);
		CustomHashMap chm2 = new CustomHashMap(TABLE_SIZE);
		String[] data = createData();
		for(int x=0; x<50; x++){
			String randomKey = getRandomData(data);
			String randomValue = getRandomData(data);
			chm.set(randomKey, randomValue);
			chm2.set(randomKey, randomValue);
		}
		assert(chm.equals(chm2));
	}

	@Test
	public void testHash(){
		int numEntries = TABLE_SIZE;
		CustomHashMap chm = new CustomHashMap(numEntries);
		assert(chm.hash(null) == 0);
		String[] data = createData();
		for(int x=0; x<50; x++){
			String randomKey = getRandomData(data);
			int expectedHash = Math.abs(randomKey.hashCode() % numEntries);
			assert(chm.hash(randomKey) == expectedHash);
		}
	}
	
	private String getRandomData(String[] data){
		return data[(int)Math.random()*data.length];
	}
	
	private String[] createData(){
		ArrayList<String> data = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(DATA_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(bufferReady(br)){
			String line = readBuffer(br);
			data.add(line);
		}
		String[] dataArr = new String[data.size()];
		for(int x=0; x<data.size(); x++){
			dataArr[x] = data.get(x);
		}
		return dataArr;
	}
	
	private boolean bufferReady(BufferedReader br){
		try{
			return br.ready();
		}catch (Exception e) {
			return false;
		}
	}
	
	private String readBuffer(BufferedReader br){
		try{
			return br.readLine();
		}
		catch(Exception ex){
			return "";
		}
	}
	
}