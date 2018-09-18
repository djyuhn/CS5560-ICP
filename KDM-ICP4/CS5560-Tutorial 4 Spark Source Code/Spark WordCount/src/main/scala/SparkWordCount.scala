

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Mayanka on 09-Sep-15.
 */
object SparkWordCount {

  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir","C:\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]").set("spark.executor.memory", "4g").set("spark.driver.memory", "2g")

    val sc=new SparkContext(sparkConf)

    val inputf = sc.wholeTextFiles("data", 100)
    val input = inputf.map(abs => {
      abs._2
      }).cache()

    // val input=sc.textFile("input", 10)

    val wc=input.flatMap(line=> {line.split(" ")}).map(word=>(word,1)).cache()

    val output = wc.reduceByKey(_+_)

    output.saveAsTextFile("output")

    val o=output.collect()

    var s:String="Words:Count \n"
    o.foreach{case(word,count)=>{

      s+=word+" : "+count+"\n"

    }}

  }

}
