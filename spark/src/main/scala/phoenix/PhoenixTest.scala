package phoenix

import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.phoenix.spark
/**
  * Created by LHZ on 2016/8/15.
  */
//注意：集群安装时要指定hostname
object PhoenixTest{
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setMaster("local[2]").setAppName("phoenix-test")
    val spark=SparkSession.builder().config(conf).getOrCreate();
    val configuration = new Configuration()
    configuration.set("hbase.zookeeper.quorum","localhost")
    configuration.set("hbase.zookeeper.property.clientPort","2181")
    //使用这样方式才能再谓词下推
    val df=spark.read.format("org.apache.phoenix.spark").options(Map("table" -> "TEST","zkUrl" -> "localhost:2181")).load()
    df.createOrReplaceTempView("TEST")
    spark.sql("select * from TEST where ID=1 OR ID=2").show()
    spark.stop()
  }
}
