package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YKL on 2018/4/16.
 * @version 1.0
 *          spark：
 *          梦想开始的地方
 */
@SpringBootApplication(scanBasePackages = "controller")
public class RunApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(RunApplication.class, args);
//        DataSource druidDataSource = new DruidConfig().druidDataSource();
//        Connection connection = druidDataSource.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT videoName FROM video");
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//        System.out.println("****");
//        while (resultSet.next()) {
//            System.out.println("****");
//            System.out.println(resultSet.getObject(0).toString());
//        }
//
//        resultSet.close();
//        preparedStatement.close();
//        connection.close();

    }

}
