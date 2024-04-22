import com.rabbitmq.client.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HO  {
    public static void main(String[] args) {
        final String url = "jdbc:mysql://localhost:3306/TP2_sysrep_HO";
        final String user = "root";
        final String password = "";

        final String insertQuery = "INSERT INTO sales (date, region, product, qty, cost, amt, tax, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        final String rabbitMQHost = "localhost";
        final String rabbitMQQueue = "sales_queue";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQHost);

        try{
                java.sql.Connection sqlConnection = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = sqlConnection.prepareStatement(insertQuery);
                com.rabbitmq.client.Connection rabbitMQConnection = factory.newConnection();
                com.rabbitmq.client.Channel channel = rabbitMQConnection.createChannel();

            channel.queueDeclare(rabbitMQQueue, true, false, false, null);

            // Start consuming messages from the queue
            channel.basicConsume(rabbitMQQueue, true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received message from BO: '" + message + "'");

                try{// Persisting the sale in the local database
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

                System.out.println("Sale inserted successfully!");}
                catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }, consumerTag -> {});
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
