package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.Given;
import manage.QueryManage;
import utilities.DB_Utilities.DBUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DB {
    PreparedStatement preparedStatement;
    QueryManage queryManage = new QueryManage();
    Faker faker = new Faker();
    String query;
    ResultSet resultSet;
    protected int id;
    String name;
    int state_id;
    int status;
    int user_id;
    String device_token;
    int rowsAffected;
    int rowCount;
    String email;
    int query_type;
    String message;

    @Given("Connected to the Database")
    public void connected_to_the_database() {
        DBUtils.createConnection();
    }

    @Given("Query08 is prepared and executed")
    public void query08_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQuery08();
        resultSet = DBUtils.getStatement().executeQuery(query);

    }

    @Given("The ResultSet08 results are processed")
    public void the_result_set08_results_are_processed() throws SQLException {
        String[] expectedOrder = {"Shipped", "Recieved", "Processing", "Pending", "Delivered"};

        int index = 0;
        while (resultSet.next() && index < expectedOrder.length) {
            String name = resultSet.getString("name");
            System.out.println(name);
            assertEquals(expectedOrder[index], name);
            index++;
        }

    }

    @Given("The database connection is closed")
    public void the_database_connection_is_closed() {
        DBUtils.closeConnection();
    }

    @Given("Query026 is prepared and executed")
    public void query026_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQuery26();
        resultSet = DBUtils.getStatement().executeQuery(query);
    }

    @Given("The ResultSet026 results are processed")
    public void the_result_set026_results_are_processed() throws SQLException {
        List<Double> amountList = new ArrayList<>();
        List<Double> expectedtList = new ArrayList<>();
        expectedtList.add(1.903996755E7);
        expectedtList.add(9164.0);
        while (resultSet.next()) {
            String paymentMethod = resultSet.getString("payment_method");
            double totalAmount = resultSet.getDouble("total_amount");
            amountList.add(totalAmount);
            for (int i = 0; i < amountList.size(); i++) {
                assertEquals(expectedtList.get(i), amountList.get(i));
            }
        }

    }

    @Given("Query02 is prepared and executed")
    public void query02_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQueryUS_02();
        id = faker.number().numberBetween(111111111, 222222222);
        name = faker.name().firstName();
        state_id = faker.number().numberBetween(1, 20);
        status = faker.number().numberBetween(1, 20);
        Date created_at = Date.valueOf(LocalDate.now());
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, state_id);
        preparedStatement.setInt(4, status);
        preparedStatement.setDate(5, created_at);

    }

    @Given("The ResultSet02 results are processed")
    public void the_result_set02_results_are_processed() throws SQLException {
        int rowCount = preparedStatement.executeUpdate();
        assertEquals(1, rowCount);
    }

    @Given("Query11 is prepared and executed")
    public void query11_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQueryUS_011();
        resultSet = DBUtils.getStatement().executeQuery(query);

    }

    @Given("The ResultSet11 results are processed")
    public void the_result_set11_results_are_processed() throws SQLException {
        resultSet.next();
        int actualtotal_amount = resultSet.getInt("total_amount");
        int expectedtotal_amount = 10;
        assertEquals(expectedtotal_amount, actualtotal_amount);

    }

    @Given("Query14 is prepared and executed")
    public void query14_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQueryUS_14();
        resultSet = DBUtils.getStatement().executeQuery(query);
    }

    @Given("The ResultSet14 results are processed")
    public void the_result_set14_results_are_processed() throws SQLException {
        resultSet.next();
        String reason = resultSet.getString("COUNT(*)");
        int expectedCount = 15;
        assertEquals(expectedCount, reason);
    }

    @Given("Query20 is prepared and executed")
    public void query20_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQueryUS_020();
        rowsAffected = 0;
        int initialId = 1;
        int idIncrement = 1;
        for (int i = 10; i < 20; i++) {
            id = initialId + i * idIncrement;
            user_id = faker.number().numberBetween(3000, 4000);
            device_token = faker.internet().password();
            Date created_at = Date.valueOf(LocalDate.now());
            preparedStatement = DBUtils.getPraperedStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setString(3, device_token);
            preparedStatement.setDate(4, created_at);
            rowsAffected += preparedStatement.executeUpdate();
        }

    }

    @Given("The ResultSet20 results are processed")
    public void the_result_set20_results_are_processed() {
        assertEquals(10, rowsAffected);
    }

    @Given("Query23 is prepared and executed")
    public void query23_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQueryUS_23();
        resultSet = DBUtils.getStatement().executeQuery(query);
    }

    @Given("The ResultSet23 results are processed")
    public void the_result_set23_results_are_processed() throws SQLException {
        resultSet.next();
        int actualCount=resultSet.getInt("type_count");
        int expCount=6;
        assertEquals(expCount,actualCount);
    }

    @Given("Query01 is prepared and executed")
    public void query01_is_prepared_and_executed() throws SQLException {
        query=queryManage.getQueryUS_01();
        resultSet=DBUtils.getStatement().executeQuery(query);
    }
    @Given("The ResultSet01 results are processed")
    public void the_result_set01_results_are_processed() throws SQLException {
        resultSet.next();
        String expectedName="Fashion";
        String actualName=resultSet.getString("name");
        assertEquals(expectedName,actualName);
    }

    @Given("Query03Insert is prepared and executed")
    public void query03_insert_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQueryUS_03Insert();
        id = faker.number().numberBetween(110000, 220000);
        name = faker.name().firstName();
        state_id=faker.number().numberBetween(1,5);
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3,state_id);

    }
    @Given("The ResultSet03Insert results are processed")
    public void the_result_set03__insert_results_are_processed() throws SQLException {
        rowCount =preparedStatement.executeUpdate();
        int verify = 0;
        if (rowCount > 0) {
            verify++;
        }
        assertEquals(1, verify);
    }

    @Given("Query03Del is prepared and executed")
    public void query03_del_is_prepared_and_executed() throws SQLException {
        query = queryManage.getQueryUS_03Del();
        preparedStatement = DBUtils.getPraperedStatement(query);
        System.out.println(id);
        System.out.println(name);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);

    }
    @Given("The ResultSet03Del results are processed")
    public void the_result_set03_del_insert_results_are_processed() throws SQLException {
        rowCount =preparedStatement.executeUpdate();
        assertEquals(1,rowCount);
    }

    @Given("Query04Insert is prepared and executed")
    public void query04_insert_is_prepared_and_executed() throws SQLException {
        query=queryManage.getQueryUS_04Insert();
        id=faker.number().numberBetween(230,430);
        name=faker.name().firstName();
        email=faker.internet().emailAddress();
        query_type=faker.number().numberBetween(10,30);
        message=faker.lorem().sentence(1);
        preparedStatement = DBUtils.getPraperedStatement(query);
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,email);
        preparedStatement.setInt(4,query_type);
        preparedStatement.setString(5,message);
    }
    @Given("The ResultSet04 results are processed")
    public void the_result_set04_results_are_processed() throws SQLException {
        rowCount=preparedStatement.executeUpdate();
        assertEquals(1,rowCount);
    }

    @Given("Query04Update is prepared and executed")
    public void query04_update_is_prepared_and_executed() throws SQLException {
        query=queryManage.getQueryUS_04Update();
        preparedStatement=DBUtils.getPraperedStatement(query);
        preparedStatement.setString(1,message);
    }
    @Given("The ResultSet04Update results are processed")
    public void the_result_set04__update_results_are_processed() throws SQLException {
        int result=preparedStatement.executeUpdate();
        int verify=0;
        if (result>0){
            verify++;
        }
        assertEquals(1,verify);

    }
    @Given("Query04Del is prepared and executed")
    public void query04del_is_prepared_and_executed() throws SQLException {
        query=queryManage.getQueryUS_05Del();
        preparedStatement=DBUtils.getPraperedStatement(query);
        preparedStatement.setString(1,email);
    }
    @Given("The ResultSet04Del results are processed")
    public void the_result_set04del_results_are_processed() throws SQLException {
        rowCount =preparedStatement.executeUpdate();
        assertEquals(1,rowCount);
    }
}
