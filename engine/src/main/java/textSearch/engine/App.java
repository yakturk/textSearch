package textSearch.engine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class App {
	private final static int topNumberofRank = 10; // keeps number of ranked files to be printed

	private static boolean success = false;
	/*
	 * reads file from directory and insert memory as a Trie
	 */
	public void readFiles(String directory, Trie trie) {
		try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
			List<String> listOfFiles = paths.filter(Files::isRegularFile).map(path -> path.getFileName().toString())
					.collect(Collectors.toList());

			for (int i = 0; i < listOfFiles.size(); i++) {
				String fileName = listOfFiles.get(i);
				Stream<String> stream = Files.lines(Paths.get(directory + "/" + fileName),
						StandardCharsets.ISO_8859_1);
				stream.forEach(s -> trie.insert(s, fileName));
			}
			
			if(listOfFiles.size()>0)
				success = true;

			System.out.println(listOfFiles.size() + " files read in the directory " + directory);

		}
		catch (IOException e) {
			System.out.println("Exception occured during read file. Check directory and also files.");
		}
	}

	/*
	 * sorts ranked files
	 */
	public List  sortRankedFiles(Trie trie, String searchedText) {
		trie.createRanksForSentence(searchedText); // search text in Trie and create ranks for files

		List<Entry<String, Rank>> list = new LinkedList<Map.Entry<String, Rank>>(trie.rankMap.entrySet()); // get ranked
																											// Files

		Collections.sort(list, new Comparator<Map.Entry<String, Rank>>() { // sort ranked files in descending order
			public int compare(Map.Entry<String, Rank> o1, Map.Entry<String, Rank> o2) {
				return Float.compare(o2.getValue().rankScore, o1.getValue().rankScore);
			}
		});

		return list;
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		App app = new App();
		app.readFiles(args[0], trie);

		if(success)
		{
			Scanner keyboard = new Scanner(System.in);
			System.out.println("\n Welcome to Text Search Engine. You can search a text inside of files under a folder.");
			System.out.println("Write a text to search and then click Enter.");
			System.out.println("To quit from system, write qqqq \n");

			while (true) {
				System.out.println("search> ");
				final String line = keyboard.nextLine();
				if (line.equals("qqqq"))
					break;

				List list = app.sortRankedFiles(trie, line);
				list.stream().limit(topNumberofRank).forEach(e -> System.out.println(e.toString())); // print just top 10 ranked files
				trie.clear();
			}
		}
		else
			System.out.println("There is no file to read");

	}
}
