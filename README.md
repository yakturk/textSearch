# textSearch
searches text inside of files which are under a given folder
This is Text Engine Application which search a sentence inside of files which are under a directory.

How it runs?
you can run textEngine.jar file from command prompt via this command: In the below example I wrote my folder name which contains files, you can write yours.
java -jar textEngine.jar C:\Users\yasemin\Desktop\tests

How it works?
when it runs: it reads files under the folder and wrote as a message:
5 files read in the directory C:\Users\yasemin\Desktop\tests

write he text that ou would like to search. It returns rank of files contains the word.Such as:
text4.txt=[fileName=text4.txt, rankScore=100.0]
freddie.txt=[fileName=freddie.txt, rankScore=100.0]
text3.txt=[fileName=text3.txt, rankScore=66.0]
michael.txt=[fileName=michael.txt, rankScore=33.0]

-------------------------------------------------------------------------------------
In the Backend how it works?

There are 3 classes : App, Rank, Trie

App is main class which gets inputs from user and returns outputs.
Trie is a class that is used to read file an create a Trie memory representation from the files
Rank is a class which is used to keep rank data of each file

->When the program runs, it reads all files under the directory and then create a Trie. It reads each file from stream and then divide each sentence in the file by "." (dot).
Then it divides each sentence by whitespace. Then it creates a Trie from start of each sentence to end of the each sentence.

	For Example; a file consists 3 sentences: Welcome to world. This is world. Welcome to school.
	Trie will be like in below:

             Welcome                     This
		             |                         |
                To                        is 
              |------|                    |      
           World   School                World

	While it is witten, file information is kept with each word. For Example: 
	Welcome ->files.add(fileName)
	to -> files.add (fileName)
	school ->files.add(fileName)

	So, when a text is searched on the tree, we will know that which word is in which file. The important thing is here; the Trie system is created for sentences. Not for 	words. This is not like a dictionary. It focuses for the texts. If you search "to", it can not find. Bu if you search "welcome to", it finds the all details. Because 	Trie starts with start of each sentences.

-> Then user writes a sentence to search that in which document the sentence is written.

For Example: Welcome to World

It finds "Welcome" on the Trie as a start point and then continue to read the Trie. While it reads the trie, It will calculate rank each time for each word and put inside of a map via fileName.

	How rank is calculated: 
	Welcome to world => if this is a sentences that I search, and if a file contains full of it: its rank is 100.
	if the file contains "welcome to" : its rank is: foundWordSize * 100 /sentence.size() 
	found words: "welcome to " >2 
	sentence size : "welcome to world" > 3
	Rank: 2*100/3 =66.6
	
	Map<FileName, Rank> : <file1.txt, Rank(file1.txt, 66.6)>

So, at the end of the search, all ranks are calculated and put inside of hashmapfor each file.


Note: you can imagine the Trie like below :


             	         Welcome   (files: text1.txt, text2.txt)                  This (files: text2.txt)
		              	      |                                                        |
                      	To  (files: text1.txt, text2.txt)                          is (files: text2.txt)
             	        |------|                                                     |      
 (files:text1.txt)  World   School  (files:text2.txt)                         World (files: text2.txt)



