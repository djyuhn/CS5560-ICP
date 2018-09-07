# KDM-ICP3

CS5560 Knowledge Discovery Management - In Class Programming 3



# Introduction

Before starting this ICP, the abstracts for the 10 papers needed to be extracted. The source code provided by the instructor was modified by me for my use case for the extraction.

Once the abstracts were extracted, the triplets for the sentences of each abstract were obtained and the synonyms for each word in every abstract was found.




## Abstraction Steps:

- Retrieve Abstracts using HttpURLConnection to Pubmed for an xml file.

  - Use the [efetch xml example](https://dataguide.nlm.nih.gov/eutilities/utilities.html#efetch) from Pubmed and alter to get the abstract.

    ![Abstract_extraction_xml](../docs/KDM-ICP3/Abstract_extraction_xml.gif)

- From the XML file, put the abstracts into a text file for easier readability.

  ![Abstract_xml_to_txt](../docs/KDM-ICP3/Abstract_xml_to_txt.gif)



## Getting Triplets:

To retrieve the triplets, again I used source code provided by the instructor. The SparkOpenIE.scala file was used and modified to accommodate my abstracts.txt file.

![get_triplets](../docs/KDM-ICP3/get_triplets.gif)

The purpose of this is to identify the subject, predicate, object triplets as keywords alone cannot provide meaningful data. With these triplets we are able to see syntactic relationships of each sentence.



## Getting Synonyms:

To get the synonym for every word in the 10 abstracts, again I used source code provided by the instructor. The WordNetSpark.scala file was used and modified to accommodate my abstracts.txt file.

![get_synonyms](../docs/KDM-ICP3/get_synonyms.gif)

The purpose of identifying synonyms for every word in all of the abstracts is to find words that are truly synonymous with the particular word found in that instance of the text. For example, if the word *'process'* was found in the text, what is actually meant by that word? Is *'process'* a noun or is it a verb? If it is a noun, then synonyms related to process would be words such as *job, task, activity* and so on. If it is a verb, such as "I will process it", then synonyms would be words such as *handle, operate, engage* and so on.

# Source Code

The source code for this ICP was provided by the class instructor Mayanka ChandraShekar: [mckw9@mail.umkc.edu](mckw9@mail.umkc.edu)