import io.agroal.api.AgroalDataSource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class AgroalExampleTest {

    @Inject
    AgroalDataSource dataSource;

    @Test
    public void testPool() throws InterruptedException, SQLException {
        String value = "";
        Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 'test' ");

            while (resultSet.next()) {
                value = resultSet.getString(1);
            }


        Long activeCount = dataSource.getMetrics().activeCount();
        Long availableCount = dataSource.getMetrics().availableCount();
        System.out.println("####################################### active count: "+ activeCount);
        System.out.println("####################################### available count: "+availableCount);

        Thread.sleep(1000 * 60);
        activeCount = dataSource.getMetrics().activeCount();
        availableCount = dataSource.getMetrics().availableCount();
        System.out.println("####################################### active count: "+ activeCount);
        System.out.println("####################################### available count: "+availableCount);
        statement.close();
        resultSet.close();
        connection.close();
        assertTrue(0 == availableCount);
    }
}
