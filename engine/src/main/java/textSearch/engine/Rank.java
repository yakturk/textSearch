package textSearch.engine;

public class Rank {

	public String fileName;
	public float rankScore;
	
	public Rank(String fileName, int sentenceCount, int foundWords)
	{
		this.rankScore = (100*foundWords)/sentenceCount;
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "[fileName=" + fileName + ", rankScore=" + rankScore + "]" + "\n";
	}
	
}
