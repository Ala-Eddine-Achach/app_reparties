


import java.sql.DriverManager;
import java.sql.PreparedStatement;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

public class BO2 {
    public static void main(String[] args) {
        final String url = "jdbc:mysql://localhost:3306/TP2_sysrep_BO2";
        final String user = "root";
        final String password = "";

        final String insertQuery = "INSERT INTO sales (date, region, product, qty, cost, amt, tax, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        final String rabbitMQHost = "localhost";
        final int rabbitMQPort = 5672;
        final String rabbitMQQueue = "sales_queue";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);
        factory.setPort(rabbitMQPort);

        

        try (
                java.sql.Connection sqlConnection = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = sqlConnection.prepareStatement(insertQuery);
                com.rabbitmq.client.Connection rabbitMQConnection = factory.newConnection();
                com.rabbitmq.client.Channel channel = rabbitMQConnection.createChannel();
        ) {
            String message = "2024-04-30,Region2,Product2,15,40,600,60,660";

            // Persisting the sale in the local database
            String[] data = message.split(",");
            preparedStatement.setString(1, data[0]); // date
            preparedStatement.setString(2, data[1]); // region
            preparedStatement.setString(3, data[2]); // product
            preparedStatement.setInt(4, Integer.parseInt(data[3])); // qty
            preparedStatement.setFloat(5, Float.parseFloat(data[4])); // cost
            preparedStatement.setFloat(6, Float.parseFloat(data[5])); // amt
            preparedStatement.setFloat(7, Float.parseFloat(data[6])); // tax
            preparedStatement.setFloat(8, Float.parseFloat(data[7])); // total

            preparedStatement.executeUpdate();

            System.out.println("Sale inserted successfully!");

            channel.queueDeclare(rabbitMQQueue, true, false, false, null);

            byte[] saleBytes = message.getBytes("UTF-8");

            channel.basicPublish("", rabbitMQQueue, null, saleBytes);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
