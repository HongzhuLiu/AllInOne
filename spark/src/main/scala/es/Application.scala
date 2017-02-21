package es

import es.util.ConfUtil
import org.apache.spark.sql.SparkSession
/**
  * Created by LHZ on 2016/8/5.
  */
object Application {
  def main(args: Array[String]) {
    val conf=ConfUtil.getSparkConf("F:\\IDEA_WORKSPACE\\AllInOne\\spark\\src\\main\\resources\\spark-es.properties")
    val spark=SparkSession.builder().config(conf).getOrCreate()
    /*val sparkEngine=new SparkEngine(sqlContext)
    val taskList=ConfUtil.getTaskConf("G:\\git\\spark-es\\src\\main\\resources\\config.xml")

    taskList.foreach(
      taskConfBean=>{
        sparkEngine.startSparkTask(taskConfBean)
      }
    )*/

    val df=spark.read.format("es").options(Map("es.resource" -> "saas_20161122/web")).load().createOrReplaceTempView("web")
    //sqlContext.sql("select threat_severity,method from web where method='GET'").show()
    //es中mapping定义的类型和实际写入的数据类型要一致，不然spark读取的使用会类型不匹配
    spark.sql("select count(*) from web").show()
  }
}
