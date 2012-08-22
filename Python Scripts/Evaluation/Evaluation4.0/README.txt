August 2, 2012
Imanol Arrieta Ibarra

==============================

Contents:
1. Getting Started
  a. Folder contents
  b. Inputs
  c. Running the Evaluation Script
  d. Output

2. Examples


=============================

1. Getting Started:

a. Folder contents
In order to perform the evaluation over a set of languages, the following files must be contained in the same folder:
	i)      diccionario.py
	ii)     Evaluation4.py
	iii)    Evaluation Script.py
	iv)     rewrite_en_file.py
	v)      Key.csv
	vi)     All the language files that come as an output from lucene in csv format


b. Input
The following format is required for the script to run:
	i)      Each of the language files must contain: Query ID; Keyword ID.
	ii)     If it is the first time the script is going to be run, the English file must be modified. 

		To do this,the English file must be called English2.csv  and the script rewrite_en_file.py has to be run once.
c. Running the Evaluation Script
For the evaluation process to begin, the Evaluation Script.py file has to be run. The program is going to ask the user:
	i)      The number of languages that are going to be evaluated
	ii)     The name of the language files. In this part, the modified English file called English.csv must go first
	iii)    The name of the output files. 


d. Output
After the process is complited, two files are obtained: the Keyword and Video evaluation files which consist in of csv matrix. The columns of the matrix represent the following statistics for each language: # of results, # of results in the union, # of results in the intersection
# of results in the difference of the sets, the F-measure and Recall. The rows of the matrix represent the queries which returned something for the english query.


=============================

2. Examples
In our example, two languages will be compared: Spanish and Italian. From the Lucene script we obtain 3 files: An English file, a Spanish file and an Italian file. First we have to rename the English file to English2.csv and run the 
rewrite_en_file.py script. After this process we are left with four files, let us call them: English.csv, English2.csv, Spanish.csv, Italian.csv. After this, we run the Evaluation Script.py script. The program is going to ask us how
many languages are we comparing. In our case the answer is 3. Then we will input the names of the language files ignoring the English2.csv file ( in fact this file can be deleted). Then we are asked the names of
the output files which are going to be called: Keyword.csv and Video.csv. After all processes have ended, we will be left with the two evaluation files.