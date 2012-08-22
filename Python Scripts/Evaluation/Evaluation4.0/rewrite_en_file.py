import csv

f= open('English2.csv','rb')
nf= open('English.csv','wb')
reader=csv.reader(f)
writer=csv.writer(nf)

for line in reader:
    aux=line[0:2]
    n=len(line)
    aux.append(line[n-1])
    writer.writerow(aux)

f.close()
nf.close()
