package textSearch.engine;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * keeps words of sentences in text files as Trie
 */
public class Trie {

	public Node root;
	public HashMap<String, Rank> rankMap;

	public Trie() {
		root = new Node();
		rankMap = new HashMap<String, Rank>();
	}

	class Node {
		private HashMap<String, Node> children;
		private String content;
		private ArrayList<String> files;

		public Node() {
			children = new HashMap<String, Trie.Node>();
		}
	}

	/*
	 * gets line from read file as an input parameter split all line by . (dot) to
	 * find each sentence split each sentence by whitespace to find each word add
	 * each word as an child of previous word
	 * 
	 * when a word is added, current file name is added into the list of the word
	 */
	public void insert(String sentence, String fileName) {
		String[] sentences = sentence.split("\\."); // divide into sentences

		for (int j = 0; j < sentences.length; j++) {
			Node current = root;
			String[] words = sentences[j].trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"); 
			if (words != null) {
				for (int i = 0; i < words.length; i++) {
					current = current.children.computeIfAbsent(words[i], c -> new Node());
					current.content = words[i];

					if (current.files == null)
						current.files = new ArrayList<String>();
					current.files.add(fileName);
				}
			}
		}

	}

	/*
	 * gets searched text as an input parameter
	 * search it in Trie
	 * if it is founded, its files list gotten and rank is calculated for each file and put inside of map
	 * at the end of the loop, if a file has total of the world, it gets max ranked number.
	 * 
	 */
	public void createRanksForSentence(String sentence) {
		Node current = root;
		String[] words = sentence.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		if (words != null) {
			for (int i = 0; i < words.length; i++) {
				Node node = current.children.get(words[i]);

				if (node == null)
				{
					System.out.println("no matches found"); break;
				}

				if (node.files != null) {
					for (int j = 0; j < node.files.size(); j++) {
						Rank rank = new Rank(node.files.get(j), words.length, i + 1);
						rankMap.put(node.files.get(j), rank);
					}
				}

				current = node;
			}
		}
	}
	
	public void clear()
	{
		rankMap.clear();
	}
}
