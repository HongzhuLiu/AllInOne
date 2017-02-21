package es

import java.util.concurrent.TimeUnit

import es.common.TaskConfBean
import es.util.ThreadPoolUtil
import org.apache.spark.sql.SparkSession
import org.elasticsearch.spark.sql._

/**
  * Created by LHZ on 2016/8/3.
  */
class SparkEngine(sparkSession: SparkSession){


  def startSparkTask(taskConfBean:TaskConfBean): Unit ={
    val task=new Runnable(){
      override def run(): Unit = {
        sparkTask(taskConfBean)
      }
    }
    if(taskConfBean.getEndTime==0){
      taskConfBean.setEndTime(System.currentTimeMillis())
      ThreadPoolUtil.scheduleAtFixedRate(task,0,taskConfBean.getSlideIntervar,TimeUnit.MINUTES)
    }else{
      ThreadPoolUtil.start(task)
    }
  }

  def sparkTask(taskConfBean:TaskConfBean): Unit ={
    var query = taskConfBean.getQuery
    var startTime=taskConfBean.getStartTime
    var endTime=taskConfBean.getEndTime
    query=query.replace("startTime",startTime+"")
    query=query.replace("endTime",endTime+"")
    sparkSession.read.format("es").options(Map("es.resource" -> "saas_20161117/web")).load().createOrReplaceTempView("web")
    //sqlContext.esDF(taskConfBean.getInIndex+"/"+taskConfBean.getInType,query).createOrReplaceTempView(taskConfBean.getTable)
    sparkSession.sql(taskConfBean.getSql).saveToEs(taskConfBean.getOutIndex+"/"+taskConfBean.getOutType)
    endTime=endTime+taskConfBean.getSlideIntervar*60000
    startTime=endTime-taskConfBean.getWindowLength*60000
    taskConfBean.setStartTime(startTime)
    taskConfBean.setEndTime(endTime)
  }
}
