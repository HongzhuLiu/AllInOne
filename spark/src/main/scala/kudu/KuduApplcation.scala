package kudu
import java.util.ArrayList

import org.apache.kudu.client.CreateTableOptions
import org.apache.kudu.spark.kudu._
import org.apache.spark.sql.SparkSession
/**
  * Created by LHZ on 2016/11/24.
  */
object KuduApplcation {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.master("local[2]").appName("kafkaSource").getOrCreate();
    import spark.implicits._

    // Read a table from Kudu
    val df = spark.read.options(Map("kudu.master" -> "172.18.123.130:7051","kudu.table" -> "test")).kudu

    // Query using the Spark API...
    df.select("*").filter($"c4_long" >= 5).show()

    // ...or register a temporary table and use SQL
    df.createOrReplaceTempView("kudu_table")
    val filteredDF = spark.sql("select key from kudu_table where c4_long=0")
    filteredDF.show()
    // Use KuduContext to create, delete, or write to Kudu tables
    val kuduContext = new KuduContext("172.18.123.130:7051")

    // Create a new Kudu table from a dataframe schema
    // NB: No rows from the dataframe are inserted into the table
    if(kuduContext.tableExists("test_table")){
      // Delete a Kudu table
      kuduContext.deleteTable("test_table")
    }


    val columns =new ArrayList[String]()
    columns.add("key")
    kuduContext.createTable("test_table", df.schema, Seq("key"), new CreateTableOptions().setRangePartitionColumns(columns).setNumReplicas(1))

    // Insert data
    kuduContext.insertRows(df, "test_table")

    // Delete data
    kuduContext.deleteRows(filteredDF, "test_table")

    // Upsert data
    kuduContext.upsertRows(df, "test_table")

    // Update data
    val alteredDF = df.select($"key",$"c4_long"+1 as "c4_long")
    kuduContext.updateRows(alteredDF, "test_table")

      // Data can also be inserted into the Kudu table using the data source, though the methods on KuduContext are preferred
      // NB: The default is to upsert rows; to perform standard inserts instead, set operation = insert in the options map
      // NB: Only mode Append is supported
    df.write.options(Map("kudu.master"-> "172.18.123.130:7051", "kudu.table"-> "test_table")).mode("append").kudu

    spark.stop()
  }
}
