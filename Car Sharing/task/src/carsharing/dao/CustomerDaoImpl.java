package carsharing.dao;

import carsharing.DataBase;
import carsharing.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public void addCustomer(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "insert into customer (name) values (?)";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in getting connection to DB");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                }
            }
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer>     customers = new ArrayList<>();
        ResultSet resultSet = null;
        Connection        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "select id, rented_car_id, name from customer";
            preparedStatement = connection.prepareStatement(Sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers.
                        add(new Customer(resultSet.getInt("id"),
                                (Integer) resultSet.getObject("rented_car_id"),
                                resultSet.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Error in getting connection to DB");
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                }
            }
        }
        return customers;
    }

    @Override
    public void updateCustomerRentedCarId(Customer customer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql =  "UPDATE CUSTOMER SET RENTED_CAR_ID=? where ID=?";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setObject(1, customer.getRentedCarId());
            preparedStatement.setInt(2, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in getting connection to DB");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                }
            }
        }
    }

    @Override
    public Customer getCustomerById(int id) {
        Customer          customer = null;
        ResultSet         resultSet = null;
        Connection        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "select id, name, rented_car_id from customer where id=?";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            customer = new Customer(resultSet.getInt("id"),
                    (Integer) resultSet.getObject("rented_car_id"),
                    resultSet.getString("name"));
        } catch (SQLException e) {
            System.out.println("Error in getting connection to DB");
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing connection");
                }
            }
        }
        return customer;
    }
}
