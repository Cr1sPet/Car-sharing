package carsharing.dao;

import carsharing.DataBase;
import carsharing.entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    @Override
    public void addCar(Car car) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "insert into car (name, company_id) values (?, ?)";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompanyId());
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
    public void updateCarStatus(Car car) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql =  "UPDATE CAR SET IS_RENTED=? where ID=?";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setObject(1, car.isRented());
            preparedStatement.setInt(2, car.getId());
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
    public Car getCarById(int id) {
        Car               car = null;
        ResultSet         resultSet = null;
        Connection        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "select id, name, company_id, IS_RENTED from car where id=?";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            car = new Car(resultSet.getInt("id"), resultSet.getInt("company_id"), resultSet.getString("name"),
                  resultSet.getInt("IS_RENTED"));
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
        return car;
    }


    @Override
    public List<Car> getCarsByCompanyId(int id) {
        List<Car> cars = new ArrayList<>();
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "select id, name, company_id, is_rented from car where company_id=? and is_rented=0";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(new Car(resultSet.getInt("id"), resultSet.getInt("company_id"),
                        resultSet.getString("name"), resultSet.getInt ("IS_RENTED")));
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
        return cars;
    }
}
