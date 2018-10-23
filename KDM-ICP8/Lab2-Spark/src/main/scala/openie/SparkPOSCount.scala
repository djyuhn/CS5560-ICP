package openie

import java.io.{BufferedWriter, File, FileWriter}

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by DJ Yuhn on 9/17/18.
 */
object SparkPOSCount {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","C:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]").set("spark.executor.memory", "4g").set("spark.driver.memory", "2g")

    val sc=new SparkContext(sparkConf)

    val inputf = sc.wholeTextFiles("mental_illness_abstracts", 50)
    val input = inputf.map(abs => {
      abs._2
    }).cache()

    val wc=input.flatMap(abstracts=> {abstracts.split("\n")}).map(singleAbs => {
      CoreNLP.returnSentences(singleAbs)
    }).map(sentences => {
      CoreNLP.returnPOS(sentences)
    }).flatMap(wordPOSLines => {
      wordPOSLines.split("\n")
    }).cache()

    wc.saveAsTextFile("output/POS")

    val o=wc.collect()

    val nounWriter = new BufferedWriter(new FileWriter("data/POS/allNouns.txt"))
    val verbWriter = new BufferedWriter(new FileWriter("data/POS/allVerbs.txt"))

    var nouns = 0
    var verbs = 0

    o.foreach{case(pair)=> {
      val splitPair = pair.split("\t")
      if(splitPair(1).contains("NN")) {
        println(splitPair(0))
        nounWriter.append(splitPair(0) + "\n")
        nouns = nouns + 1
      }
      else if(splitPair(1).contains("VB")) {
        verbWriter.append(splitPair(0) + "\n")
        verbs = verbs + 1
      }
    }}
    nounWriter.close()
    verbWriter.close()

    val nounVerbWriter = new BufferedWriter(new FileWriter("data/POS/nouns&verbs.txt"))
    nounVerbWriter.write("Total Nouns: " + nouns + "\nTotal Verbs: " + verbs)
    nounVerbWriter.close()


  }

}
