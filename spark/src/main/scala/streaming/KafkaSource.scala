package streaming

import org.apache.spark.sql.SparkSession

/**
  * Created by LHZ on 2016/11/16.
  */
object KafkaSource {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.master("local[2]").appName("kafkaSource").getOrCreate();
    val source = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "172.18.123.190:9092,172.18.123.191:9092,172.18.123.192:9092")
      .option("subscribe", "saas")
      .load()

    val query = source.writeStream
      .outputMode("append")
      .format("console")
      .start()

    query.awaitTermination()
    /*ds1.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .as[(String, String)].show()*/
  }
}
