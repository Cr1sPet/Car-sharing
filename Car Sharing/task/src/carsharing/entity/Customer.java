package carsharing.entity;

import carsharing.dao.CarDaoImpl;
import carsharing.dao.CompanyDaoImpl;

import java.util.List;

public class Customer {
    Integer id;
    Integer rentedCarId;
    String name;
    Car    rentedCar;
    Company rentedCompany;

    public Customer(Integer id, Integer rentedCarId, String name) {
        this.id = id;
        this.rentedCarId = rentedCarId;
        this.name = name;
    }

    public Customer(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(Integer rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getRentedCar() {
        return new CarDaoImpl().getCarById(this.rentedCarId);
    }

    public void setRentedCar(Car rentedCar) {
        this.rentedCar = rentedCar;
        this.rentedCompany = rentedCar.getCompany();
    }

    public Company getRentedCompany(Car car) {
        return new CompanyDaoImpl().getByCompanyId(car.getCompanyId());
    }

    public static boolean printCustomerList(List<Customer> customerList) {
        int i = 1;
        System.out.println();
        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
            return false;
        } else {
            System.out.println("Customer list:");
            for (Customer a : customerList) {
                System.out.println(i++ + ". " + a.getName());
            }
        }
        return true;
    }
}
