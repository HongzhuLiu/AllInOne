package phoenix;

import org.apache.phoenix.query.QueryServices;

import java.sql.*;
import java.util.Properties;

/**
 * Created by lhz on 16-9-13.
 */
public class Test {
    public static final String TABLE="us_population";
    public static void main(String[] args) throws SQLException {
        String driver = "org.apache.phoenix.jdbc.PhoenixDriver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties connectionProperties = new Properties();
        connectionProperties.setProperty(QueryServices.MAX_MUTATION_SIZE_ATTRIB,"100");
        connectionProperties.setProperty(QueryServices.IMMUTABLE_ROWS_ATTRIB,"100");
        Connection connection = DriverManager.getConnection("jdbc:phoenix:localhost:2181", connectionProperties);
        PreparedStatement stmt = connection.prepareStatement("upsert into " + TABLE + " (STATE, CITY, POPULATION) values (?,?,?)");
        try {
            for (int i = 0; i < 10; i++) {
                stmt.setString(1, "C" + i);
                stmt.setString(2, "D" + i);
                stmt.setInt(3,i);
                stmt.addBatch();
                //stmt.executeUpdate();
            }
            stmt.executeBatch();
            connection.commit();
            stmt=connection.prepareStatement("SELECT * FROM "+TABLE+" limit 10");
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (IllegalArgumentException expected) {

        }finally {
            connection.close();
        }

    }
}
