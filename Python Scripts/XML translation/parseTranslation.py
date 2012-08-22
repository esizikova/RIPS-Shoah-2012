import re

translationLines0 = open("translations_combined.txt", 'r').read() #.split("\r", "\n")  #just lines from the file


def my_split(s, seps):
    res = [s]
    for sep in seps:
        s, res = res, []
        for seq in s:
            res += seq.split(sep)
    return res


#print my_split(translationLines0, ["\r", "\n"])

translationLines0 = my_split(translationLines0, ["\r", "\n"])

translationLines1 = [] # are of the form word+translation
i = 1 
while i < len(translationLines0):
	line =translationLines0[i-1]+translationLines0[i]
	translationLines1.append(line)
	i+=2


searchLabeltoLabeldic = {}
translationLines2 = []
for item in translationLines1:
	myList = item.split("  | ")
	if (len(myList) == 1):
		myList = myList[0].split(" | ")
	if (len(myList) == 3):
		myList = myList[1:]
	#myList #.reverse()
	translationLines2.append(myList)

#print translationLines2[19]
searchLabeltoLabeldic = dict(translationLines2)   # this dictionary contains search labels as keys and their translations as values

#print searchLabeltoLabeldic.keys() 

#print searchLabeltoLabeldic['Domanevka']




oldXMLFile = open("Thesaurus2_xml_ver2.xml", 'r').read()
oldXMLFile = my_split(oldXMLFile, ["\r", "\n"])
newXMLFile = open("newThesaurus.xml", 'w')

replacedEntries = []

i = 1
j = 0
for line in oldXMLFile:
	if "searchlabel" in line:
		oldLine = line
		#print line
		line = my_split(line, ['<column name="searchlabel">','</column>'])
		#print line
		searchLabel = re.sub(r'\([^)]*\)', '', line[1])
		
		if (len(searchLabel)> 1): 
			while (searchLabel[-1] == " "):
				searchLabel = searchLabel[:-1]


		if (searchLabel in searchLabeltoLabeldic):
			newLine = '        <column name="searchlabel">' + searchLabeltoLabeldic[searchLabel] + '</column>'
			newXMLFile.write(newLine + "\n")
		else:
			j+=1
			actStr = "Domanevka"
		
			#print ("searchLabel: " + searchLabel+ " " + str(len(searchLabel)))
			#print ("actStr: " + actStr+ " " + str(len(actStr)))
			#break
			'''for i in range(len(searchLabel)):
				if (searchLabel[i] == actStr[i]):
					i+=1
				else:
					print i
					break

			break'''
			newXMLFile.write('        <column name="searchlabel">' + searchLabel + '</column>' + "\n")	
	else:
		newXMLFile.write(line + "\n")
	i+=1

print "j: "+ str(j)
newXMLFile.close()
