#This function creates a dictionary from a .csv file using the segmentsID as
#the dictionaries Key and a set of the keywordsID that are attached to the
#segment as the values.


#In
#file_name.- is the name of the csv file
#key._ is the column number where the key is located
#value.- is the column number where the value is located

#Out
#dic.- is the dictionary

#Obs it would be useful if the primary key was an integer

def diccionario(file_name,key,value):
    import csv
    
    #First we create a new dictionary
    dic = {}
    

    #The csv file is opened 
    f = open(file_name,"rb")



    #A csv reader is created
    cr = csv.reader(f)
    


    #for each line in the csv file the key_id is set to the
    #value of the column that contains the primary key.
    #The same is done with the value key
    for line in cr:
        try:
            value_id = line[value]
        except:
            value_id = 'error'

        try:
            key_id = line[key]
        except:
            key_id = 'error'




     #   if len(line) < value + 1:
      #      value_id ='error' 
       # else:
        #    value_id=line[value]
         #   
        #if len(line) < key +1:
         #   key_id='error'
          #              
        #else:
         #   key_id = line[key]

        #The program then tries to access the dictionary in the location
        #specified by the key_id. If it doesn't exist it creates a new set
        #If anything unexpected ocurres it simply catches the error.
        try:
            dic[int(key_id)].add(value_id)
        except:
            try:
                dic[int(key_id)] = set([value_id])
            except:
                key_id=0
                
                


                
    #The file is closed and the dictionary is returned
    f.close()
    return dic

