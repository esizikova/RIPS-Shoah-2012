
License: GNU Public Licence v3.0

==============================

August 2, 2012
Christie Quaranta

==============================

Contents:
1. Getting Started
  a. IndexItems
  b. Indexer
  c. Searcher
  d. Main Method
  e. What to Edit
2. Examples

=============================

1. Getting Started:

Open the project in JCreator or another IDE.  Add the two jar files as paths to JDK Profile.

a. IndexItems

The first method sets the values for each documents in each of the three fields: term identification number, label, and search label.  The other methods retrieve the values when called upon.

b. Indexer

First, this class uses a method to create the index that will be used to store the documents from the thesaurus.  Another method calls IndexItems methods and is used to put each document into the index with its corresponding fields.  The other method closes the indexer.

c. Searcher

One of the methods creates a searcher, an analyzer, and three query parsers.  The second method creates an arraylist of returned results after it is searched through the index.  The last method closes the searcher.

d. Main Method

The text file English Thesaurus is read in. The index is created and each item from the thesaurus is stored into it.  Then the tab-separated file containing queries is read in and each one is sent to the searcher.  All of the results from the search are printed into a CSV file.

e. What to Edit

Line 052: Change the file name of the output file
Line 109: Change the file name of the query file that is read
Line 111: Change the size of the array depending on the length of the query list
Line 113: Change the value that k approaches to the same as line 123
Line 057: Change the name of the thesaurus if necessary

The query file is a tab-separated text file that has two columns.  The first column is the query number and the second column is the query.
The thesarus file is a tab-separated text file with three columns.  The first column is the term identification number; the second column is the label; and the third column is the search label.
The output file is a comma-separated text file, which has five columns.  It is query number, term identification number, label, search label, and query.

=============================

2. Examples

English Thesaurus:
685	Domanevka 	Domanevka

Query List:
1	survivor guilt second generation

Output:
1,11735,survivor guilt,survivor guilt,survivor guilt second generation

=============================
