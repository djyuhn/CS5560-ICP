package openie

import java.io.{BufferedWriter, File, FileWriter, PrintWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  * Created by Mayanka on 27-Jun-16.
  */
object SparkOpenIE {

  def main(args: Array[String]) {

    // For Windows Users
    System.setProperty("hadoop.home.dir", "C:\\winutils")

    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val inputf = sc.wholeTextFiles("mental_illness_abstracts", 50)
    val input = inputf.map(abs => {
      abs._2
    }).cache()

//    val allTriplets = input.flatMap(abstracts=> {abstracts.split("\n")}).map(singleAbs => {
//      CoreNLP.returnSentences(singleAbs)
//    }).map(sentences => {
//      CoreNLP.returnTriplets(sentences)
//    }).cache()

    val allTriplets = input.map(singleAbs => {
      CoreNLP.returnTriplets(singleAbs)
    }).cache()

    allTriplets.saveAsTextFile("output/triplets")

    val collect = allTriplets.distinct().collect()

    val predicateWriter = new BufferedWriter(new FileWriter("data/ontology/predicates/predicates.txt"))
    val subjectWriter = new BufferedWriter(new FileWriter("data/ontology/subjects/subjects.txt"))
    val objectWriter = new BufferedWriter(new FileWriter("data/ontology/objects/objects.txt"))
    val allTripletsWriter = new BufferedWriter(new FileWriter("data/triplets/allTriplets.txt"))


    val predicates = new mutable.HashSet[String]()
    val subjects = new mutable.HashSet[String]()
    val objects = new mutable.HashSet[String]()

    var counter = 0
    collect.distinct.foreach(triplets => {
      val tripletWriter = new BufferedWriter(new FileWriter("data/triplets/triplets_abstract_" + counter + ".txt"))
      val separateTriplets = triplets.split("\n")

      separateTriplets.distinct.foreach(triplet => {
        val splitTriplet = triplet.split("\t")
        if (splitTriplet.size > 2) {
          predicates.add(splitTriplet(1))
          subjects.add(splitTriplet(0))
          objects.add(splitTriplet(2))
          tripletWriter.append(triplets)
          allTripletsWriter.append(triplets)
        }
      })
      tripletWriter.close()
      counter = counter + 1
      })

    for (unique <- predicates) {
      predicateWriter.append(unique).append("\n")
    }
    for (unique <- subjects) {
      subjectWriter.append(unique).append("\n")
    }
    for (unique <- objects) {
      objectWriter.append(unique).append("\n")
    }
    predicateWriter.close()
    objectWriter.close()
    subjectWriter.close()
  }

  // Stop words
//  val stopwords = sc.textFile("data/stopwords.txt").collect()
//  val stopWordBroadCast = sc.broadcast(stopwords)
//
//  val input2 = sc.textFile("data/sentenceSample").map( f => {
//    val afterStopWordsRemoval = f.split(" ").filter(!stopWordBroadCast.value.contains(_))
//  })

}
