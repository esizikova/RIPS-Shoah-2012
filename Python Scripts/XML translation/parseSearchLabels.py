searchLabels = open("newSearchLabels", 'r').read().split("\n")
lessSL =  open("lessSLs", 'w')
print searchLabels

i = 142207
while (i < len(searchLabels)):
	lessSL.write(searchLabels[i]+'\n')
	i+=1


lessSL.close()

	

