from diccionario import diccionario
import csv
import math
csv.field_size_limit(10000000)

#This function will meassure the difference between sets by using
#the F- measure.
#If A, B are two sets and c(A) its the cardinality of A
#Then d= 1 / ((1-a)(1/P)+a(1/R)). Where P is Rresicion and R is Recall.
#This is P = c(A intersection B)/c(B).
#R = c(A intersection B)/c(A)
#In order to numerically calculate this distance, we will transforme it
# into d= (b+1)(P*R)/(P*b+R)
#where b= a/(1-a)

#In
#arg1 and arg2 are both sets

#Out
#d is the distance between the two sets


    

def distance(arg1,arg2,b):
    i = float(len(arg1.intersection(arg2)))
    ca = float(len(arg1))
    cb = float(len(arg2))
    R = i/ca
    P = i/cb
    d = P*R*(b+1)/(P*b+R)

#This function will meassure the difference between sets by using
#the Recall.
#R = c(A intersection B)/c(A)


#In
#arg1 and arg2 are both sets

#Out
#d is the distance between the two sets



def distance2(arg1,arg2):
    en = len(arg1)
    if en != 0:
        d = float(len(arg1.intersection(arg2)))/float(en)
    else:
        d=10000
    return d

 
    



#The next function takes the file where the relation keywordID and segmentID is
#stored and the language queries files and returns the evaluation from each
#language inside two csv files with names according to nfile

#In
#key.- Is the name of the file where the keywordID and segmentID are stored
#lang.- Is a list of strings that is composed of the languages to be analiced
#nfile.- Is a list of strings with the names of the output files. The first
#must correspond to the Keyword Evaluation and the second to the Video evaluation


def evaluation(key,lang,nfile):

    #key_dic.- Is the dictionary that relates KeywordID's with VideoSegmentID's
    key_dic = diccionario(key,1,0)

    #query.- Is a dictionary whose primary key depends on  the language
    # and its value is the dictionary that relates
    # the query with the keyword lucene gives back for that language
    query={}


    #First the files Keyword_Evaluation and Video_Evaluation will be opened
    #key_writer.- is a csv writer for the Keyword_Evaluation file
    #vid_writer.- is a csv writer for the Video_Evaluation file
    with open(nfile[0],'wb') as key_wfile:
        key_writer=csv.writer(key_wfile,delimiter=',')
        with open(nfile[1],'wb') as vid_wfile:
            vid_writer=csv.writer(vid_wfile,delimiter=',')     


            #aux.- is an auxiliar variable that will help to writing the file
            #in order for the first line to look like:
            #QueryID    Lang1   Lang2    Lang3
            aux=['Query ID','Query Name']

            #A dictionary will be created using the name of the query
            names=diccionario(lang[0],0,2)

            #For every language in lang. l takes the name of the language
            for l in lang:
              
                
                #A dictionary is created for every language lang
                #this dictionary contains the relationship between
                #queryID and keywordID             
                query[l] = diccionario(l,0,1)

                
                #The name of the language is added to aux followed by the value
                #that will be presented in the final matrix
                #_#Res is the number of results returned by the query in language l
                #_inter is the cardinality of the intersection
                #_union is the cardinality of the union
                #_diff is the cardinality of the difference
                #_F2 is the value of the F2 measure
                #_Recall is the Recall value
                aux.append(l[0:len(l)-4] + '_#Res')
                aux.append(l[0:len(l)-4] + '_inter')
                aux.append(l[0:len(l)-4] + '_union')
                aux.append(l[0:len(l)-4] + '_diff')
                aux.append(l[0:len(l)-4] + '_F2')
                aux.append(l[0:len(l)-4] + '_Recall')

                
            #This part will get rid of the English vs English comparison
            for i in range(5):
                pop = aux.pop(3)
            key_writer.writerow(aux)
            vid_writer.writerow(aux)    




            
            # For every query that returned an English result, a Python dictionary
            # look for all the videos attached to it. Then for every language, this
            # process is repeated
            
            for q in query[lang[0]]:

                

               
                #key_en.- is the list of keywords returned by query q in english
                #The english query dictionary will be accesed in order
                #to extract the words it contains from query q.
                #An empty set '0' is added to every query as to avoid 0 results
  
                key_en = query[lang[0]][q]
                key_en.add('0')

                #video_en.- is a set that will contain the videos attached to the
                #keywords in the english query

                video_en = set('0')
                

                #For every keyword resulting from a query
                for keyword in key_en:
                    #The program will try to find the keyword in the key_dic
                    #dictionary. If it doesn't find it, the keyword will be
                    #stored in the error list
                    #If it finds it, the video segment ID will be stored in
                    # the video_en set
                        
                    try:
                        b=key_dic[int(keyword)]
                        video_en=video_en.union(b)
                    except:
                        b=0

                # key.- is an auxiliary vector that will save the keyword evaluation line
                # vid.- is an auxiliary vector that will save the keyword evaluation line
                
                key=[]
                vid=[]

                
                #For each language the distance will be calculated and stored in
                #that list along with all the information of the queries
                  

                
                
                for l in lang:
                    
                    #key_for.- is the list of keywords returned by query q in language l          

                    #The foraign query dictionary will try to be accesed in order
                    #to extract the words it contains from query q. If the
                    #dictionary can't be accesed it means key_en is an empty set                    
                        
                    try:
                        key_for = query[l][q]
                        key_for.add('0')
                    except:
                        key_for = set('0')

                    #KEYWORD EVALUATION
                    
                    #The next instruction inserts the keyword evaluation
                    #Also it introduces all the information for that query
                    #Cardinality of the foreign set
                    key.append(len(key_for)-1)
                    #Cardinality of the intersection
                    key.append(len(key_en.intersection(key_for))-1)
                    #Cardinality of the union
                    key.append(len(key_en.union(key_for))-1)
                    #Cardinality of A - B
                    key.append(len(key_en.difference(key_for)))
                    #F - measure
                    key.append(distance(key_en,key_for,2))
                    #Recall
                    key.append(distance2(key_en,key_for))

                    #VIDEO EVALUATION
                    
                    
                    #video_for.- is a set that will contain the videos attached to the
                    #keywords in the foraign query
                    
                    video_for = set('0')

                            

                    for keyword in key_for:
                        #The part will try to find the keyword in the key_dic
                        #dictionary. 
                        #If it finds it, the video segment ID will be stored in
                        # the video_for set
                        try:
                            b=key_dic[int(keyword)]
                            video_for=video_for.union(b)
                        except:
                            b=0
                        
                    #The next instruction inserts the video evaluation statistics
                    
                    #Cardinality of the foreign set    
                    vid.append(len(video_for)-1)
                    #Cardinality of the intersection
                    vid.append(len(video_en.intersection(video_for))-1)
                    #Cardinality of the union
                    vid.append(len(video_en.union(video_for))-1)
                    #Cardinality of A-B
                    vid.append(len(video_en.difference(video_for)))
                    #F2 Measure
                    vid.append(distance(video_en,video_for,2))
                    #Recall
                    vid.append(distance2(video_en,video_for))
                    

                #PRINTING TO FILE
                   
                
                key.insert(0,list(names[q])[0])
                key.insert(0,q)
                for i in range(5):
                    pop = key.pop(3)
                key_writer.writerow(key)
                
                vid.insert(0,list(names[q])[0])
                vid.insert(0,q)
                for i in range(5):
                    pop = vid.pop(3)
                vid_writer.writerow(vid)
                print q
    return 'done'
            
