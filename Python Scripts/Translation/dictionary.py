import json
import re  

# this is the dictionary script that is called by translateScript.py. Both files should be kept in the same folder
# this script required the installation of the Requests library, available here:
# docs.python-requests.org/en/latest/index.html

# adds plus signs (+) between consecutive words
def formatString(text): 
	''' format "hello sir" as "hello+sir" for server queries '''
	words = text.split();
        newText = ''
        for item in words:
		newText+=item + '+'
        newText = newText[:-1]
        return newText



# retrieves the text between two given tag
# for example <script>Hello</script> will be converted to Hello via retrieve(<script>, </script>, text)
def retrieve(startTag, endTag, text):
        ''' retrieve text between two tags in text '''
	r = re.compile(startTag+ '(.*?)' + endTag)
	m = r.search(text)
        lyrics = ""
	if m:
    		lyrics = m.group(1)

	return lyrics



# main function that calls Google Translate
def translate(text, fromLang, toLang): 
	import requests    # the module that is required to make the HTTP requests to Google API
        formattedText = formatString(text)  # format text in the desired form
	r1 = requests.get('https://www.googleapis.com/language/translate/v2?key=AIzaSyAkSkBNjNyZES-6ntjzf0CDcCx9GQoYF2I&source='+fromLang+'&target='+toLang+'&q='+formattedText)   # send the HTTP request to server to translate the text




	textOriginalTr = r1.json['data']['translations'][0]['translatedText']   # the result is returned in the form of 
										#a JSON object, which is then parsed


	return textOriginalTr.encode("utf-8")   # the output is encoded to account for non-Roman alphabet characters






