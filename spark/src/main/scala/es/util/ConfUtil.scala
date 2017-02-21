package es.util

import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util
import java.util.Properties

import es.common.TaskConfBean
import org.apache.spark.SparkConf
import scala.collection.JavaConversions._
import scala.xml.XML

/**
  * Created by LHZ on 2016/8/4.
  */
object ConfUtil{
  def loadConf(confPath:String): util.ArrayList[TaskConfBean]={
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val conf=XML.loadFile(confPath)
    val taskConf=conf\"task"
    val taskList=new util.ArrayList[TaskConfBean]()
    taskConf.iterator.foreach(
      node=>{
        val taskConfBean=new TaskConfBean
        val input=node\"input"
        taskConfBean.setInIndex((input\"index").text)
        taskConfBean.setInType((input\"type").text)
        taskConfBean.setTable((input\"table").text)
        taskConfBean.setQuery((input\"query").text)
        val startTime=(input\"startTime").text
        if(startTime.isEmpty){
          taskConfBean.setStartTime(0)
        }else{
          taskConfBean.setStartTime(dateFormat.parse(startTime).getTime)
        }
        val endTime=(input\"endTime").text
        if(endTime.isEmpty){
          taskConfBean.setEndTime(0)
        }else{
          taskConfBean.setEndTime(dateFormat.parse(endTime).getTime)
        }
        taskConfBean.setSql((node\"sql").text)
        val window=node\"window"
        taskConfBean.setWindowLength((window\"windowLength").text.toInt)
        taskConfBean.setSlideIntervar((window\"slideInterval").text.toInt)
        val output=node\"output"
        taskConfBean.setOutIndex((output\"index").text)
        taskConfBean.setOutType((output\"type").text)
        taskList.add(taskConfBean)
      }
    )
    return taskList
  }

  def getTaskConf():  util.ArrayList[TaskConfBean] ={
    return loadConf("config.xml")
  }

  def getTaskConf(confPath:String):  util.ArrayList[TaskConfBean] ={
    return loadConf(confPath)
  }


  def getSparkConf(confPath:String): SparkConf ={
    val conf = new SparkConf()
    val prop = new Properties()
    prop.load(new FileInputStream(confPath))
    prop.foreach(
      x=>{
        conf.set(x._1,x._2)
      }
    )
    return conf
  }

  def getSparkConf(): SparkConf ={
    val conf = new SparkConf()
    val prop = new Properties()
    prop.load(new FileInputStream("spark-es.properties"))
    prop.foreach(
      x=>{
        conf.set(x._1,x._2)
      }
    )
    return conf
  }
}
