package ontology_construction

import java.io.{BufferedWriter, FileWriter}

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
  * Created by Mayanka on 27-Jun-16.
  */
object OntologyConstruction {

  def main(args: Array[String]) {

    // For Windows Users
    System.setProperty("hadoop.home.dir", "C:\\winutils")

    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

  /**
    * File Format
    *
    * FileName : Classes
    * Format : ListOfClassNames
    * Person
    * Ethnicity
    * Gender
    *
    * FileName: SubClasses
    * Format : ClassName,SubClassName
    * Person,Daughter
    * Person,Parent
    * Person,OffSpring
    * Person,Son
    * Person,Mother
    * Person,Father
    *
    * FileName: ObjectProperties
    * Format : ObjectPropertyName, DomainName, RangeName, ObjectPropertyType (Functional-Func, InverseOf-InvOf:ObjectPropertyName)
    * hasGender,Person,Gender,Func
    * hasChild,Person,Person,InvOf:hasParent
    *
    * FileName : DataProperties
    * Format : DataPropertyName, DomainName, RangeDataType
    * fullName,Person,string
    * ID,Person,int
    *
    * FileName : Individuals
    * Format : ClassName, IndividualName
    * Gender,Female
    * Gender,Male
    * Person,Mary
    *
    * FileName : Triplets
    * Format : SubjectIndividual, ObjectPropertyName, SubjectIndividual, PropertyType (Obj/Data)
    * Mary,hasGender,Female,Obj
    * Mary,fullName,MaryJost,Data
    */

    // Handle Triplets
    val tripletsFile = sc.textFile("data/triplets/allTriplets.txt")
    val triplets = tripletsFile.map(line => {
      val splitLine = line.split("\t")
      for( a <- 0 until splitLine.size) {
        splitLine(a) = splitLine(a).replaceAll(" ", "_")
      }
      splitLine
    }).cache()

    val tripletsCollect = triplets.collect()

    val tripletsWriter = new BufferedWriter(new FileWriter("data/ontology/construction/Triplets.txt"))

    tripletsCollect.foreach(tripletArr => {
      val outputBuilder = new mutable.StringBuilder(tripletArr.mkString(","))
      outputBuilder.append(",Obj").append("\n")
      tripletsWriter.append(outputBuilder)
    })

    tripletsWriter.close()

    // Handle Predicates
    val predicatesFile = sc.textFile("data/ontology/predicates/predicates.txt")
    val predicates = predicatesFile.map(line => {
      line.replaceAll(" ", "_")
    }).cache()

    val predicatesCollect = predicates.collect()

    val predicatesWriter = new BufferedWriter(new FileWriter("data/ontology/construction/ObjectProperties.txt"))

    predicatesCollect.foreach(predicate => {
      val outputBuilder = new mutable.StringBuilder()
      outputBuilder.append(predicate).append(",Subject,Object,Func").append("\n")
      predicatesWriter.append(outputBuilder)
    })

    predicatesWriter.close()

    // Handle Subjects & Objects
    val subjectsFile = sc.textFile("data/ontology/subjects/categorizedSubjects.txt")
    val subjects = subjectsFile.map(line => {
      val splitLine = line.split("\t")
      for( a <- 0 until splitLine.size) {
        splitLine(a) = splitLine(a).replaceAll(" ", "_")
      }
      val swapOrder = Array(splitLine(1), splitLine(0))
      swapOrder
    }).cache()

    val objectsFile = sc.textFile("data/ontology/objects/categorizedObjects.txt")
    val objects = subjectsFile.map(line => {
      val splitLine = line.split("\t")
      for( a <- 0 until splitLine.size) {
        splitLine(a) = splitLine(a).replaceAll(" ", "_")
      }
      val swapOrder = Array(splitLine(1), splitLine(0))
      swapOrder
    }).cache()

    val subjectsCollect = subjects.collect()
    val objectsCollect = objects.collect()

    val individualsWriter = new BufferedWriter(new FileWriter("data/ontology/construction/Individuals.txt"))

    subjectsCollect.foreach(subjectArr => {
      val outputBuilder = new mutable.StringBuilder(subjectArr.mkString(","))
      outputBuilder.append("\n")
      individualsWriter.append(outputBuilder)
      individualsWriter.append("Subject," + subjectArr(1) + "\n")
    })

    objectsCollect.foreach(objectArr => {
      val outputBuilder = new mutable.StringBuilder(objectArr.mkString(","))
      outputBuilder.append("\n")
      individualsWriter.append(outputBuilder)
      individualsWriter.append("Object," + objectArr(1) + "\n")
    })

    individualsWriter.close()
  }
}
