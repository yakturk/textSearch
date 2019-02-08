package textSearch.engine;



import org.springframework.boot.test.context.SpringBootTest;

import org.junit.*;
import static org.junit.Assert.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest 
{
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	String currentWorkingDir = Paths.get("").toAbsolutePath().toString();
	String directory = currentWorkingDir.replace("\\", "/") +"/src/test/java/textSearch/engine/files";

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
	
   @Test
    public void testReadFiles()
    {
	   	App app = new App();
	   	Trie trie =  new Trie();
	   	String currentWorkingDir = Paths.get("").toAbsolutePath().toString();
	   	
	   	String directory = currentWorkingDir.replace("\\", "/") +"/src/test/java/textSearch/engine/files";
	   	app.readFiles(directory, trie);
	   	
	   	String expected = "5 files read in the directory " +directory;
	   	Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, outContent.toString()));
    }
   
	
   @Test
   public void checkRank1()
   {
	   Rank rank = new Rank("fileName", 3, 0);
	   assertEquals(0.0, rank.rankScore,0);
   }
   
   @Test
   public void checkRank2()
   {
	   Rank rank = new Rank("fileName", 3, 1);
	   assertEquals(33.3, rank.rankScore,1);
   }
   
   @Test
   public void sortRankedFiles()
   {
	   App app = new App();
	   Trie trie = new Trie();
	   app.readFiles(directory, trie);
	   
	   Rank rank1= new Rank("text4.txt",3,3);
	   Rank rank2= new Rank("freddie.txt",3,3);
	   Rank rank3= new Rank("text3.txt",3,2);
	   Rank rank4= new Rank("michael.txt",3,1);
	   
	   HashMap<String, Rank> maps = new HashMap<String, Rank>();
	   maps.put(rank1.fileName,rank1);
	   maps.put(rank2.fileName,rank2);
	   maps.put(rank3.fileName,rank3);
	   maps.put(rank4.fileName,rank4);
	   
		List<Entry<String, Rank>> list = new LinkedList<Map.Entry<String, Rank>>(maps.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Rank>>() {
			public int compare(Map.Entry<String, Rank> o1, Map.Entry<String, Rank> o2) {
				return Float.compare(o2.getValue().rankScore, o1.getValue().rankScore);
			}
		});
		
		Assert.assertTrue(EqualsBuilder.reflectionEquals(list, app.sortRankedFiles(trie, "welcome to world")));
	  
   }
   
   

}
