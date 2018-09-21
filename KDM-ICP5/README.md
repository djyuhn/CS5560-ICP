# KDM-ICP5

CS5560 Knowledge Discovery Management - In Class Programming 5



# Introduction

I will perform Term-Frequency (TF) and Inverse Document Frequency (IDF) on the retrieved documents from previous ICPs.



# Getting Top 20 Lemmatized Words and Words

To obtain the top 20 lemmatized words of the 15 abstracts, the abstracts were read in as whole text files using Spark. From here, the abstracts were obtained as an RDD. Each of these abstracts were mapped from the RDD and processed through the CoreNLP class using the returnLemma method. Once completed, the lemmatized abstracts were then put through the hashingTF class to obtain the TFs, following by a transform to create the IDFs.

The above process was repeated to obtain the top 20 Unlemmatized words.

![getTop20_Words_LemWords](../docs/KDM-ICP5/getTop20_Words_LemWords.gif)



# Source Code

The source code for this ICP was provided by the class instructor Mayanka ChandraShekar: [mckw9@mail.umkc.edu](mckw9@mail.umkc.edu)