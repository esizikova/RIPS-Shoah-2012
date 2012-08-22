import dictionary

# this is the script that calls the dictionary and translates the lines in the file given into the languages specified
# the original lines and resulting translations are stored in the file called inputLanguage+outputLanguage+Translation.txt
# ex. EnFrTranslation.txt


inputFile = raw_input("Please enter the name of an input file: ")
originalWords = open(inputFile, 'r').read().split("\n")[:-1]
inputLanguage = raw_input("Please enter the name of the input language: ")
outputLanguage = raw_input("Please enter the name of the output language: ")


translationsFile = open(inputLanguage.capitalize()+outputLanguage.capitalize()+"Translation.txt",'w')


for word in originalWords:
	translationsFile.write(word + " | ");
	translationsFile.write(dictionary.translate(word, inputLanguage, outputLanguage) + "\n")



translationsFile.close()
