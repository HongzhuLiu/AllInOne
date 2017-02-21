package es;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Created by LHZ on 2016/12/13.
 */
public class EsSQL {
    public static void main(String[] args) throws Exception {
        String index="winlogbeat-2016.09.19";
        Properties properties = new Properties();
        properties.put("url", "jdbc:elasticsearch://172.16.100.130:9300/" + index);
        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        Connection connection = dds.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT count(*) from  " + index);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getObject(0));
        }

        ps.close();
        connection.close();
        dds.close();
    }
}
