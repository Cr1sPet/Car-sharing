package carsharing.dao;

import carsharing.DataBase;
import carsharing.entity.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao{

    @Override
    public void addCompany(Company company) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "insert into company (name) values (?)";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, company.getName());
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
    public Company getByCompanyId(int id) {
        Company           company = null;
        ResultSet         resultSet = null;
        Connection        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "select id, name from company where id=?";
            preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            company = new Company(resultSet.getInt("id"), resultSet.getString("name"));
        } catch (SQLException e) {
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
        return company;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company>     companies = new ArrayList<>(10);
        ResultSet         resultSet = null;
        Connection        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DataBase.getConnection();
            String Sql = "select id, name from company";
            preparedStatement = connection.prepareStatement(Sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                companies.add(new Company(resultSet.getInt("id"), resultSet.getString("name")));
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
        return companies;
    }
}
