import Evaluation4

#This is a script that will ask the user for:
# 1) The number of languages
# 2) The names of the languages
# 3) The name of the Keyword Evaluation File
# 4) The name of the Video Evaluation File

lang=[]
nfile=[]

n = int(raw_input('How many languages will be evaluated?  :'))

for i in range(n):
    lang.append(raw_input('Write the name of the next language file:   '))

nfile.append(raw_input('What is the name of the Keyword Evaluation file:  '))
nfile.append(raw_input('What is the name of the Video Evaluation file:    '))
    
print lang
print nfile
Evaluation4.evaluation('Key.csv',lang,nfile)
