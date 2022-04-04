package carsharing;

import carsharing.dao.CarDaoImpl;
import carsharing.dao.CompanyDaoImpl;
import carsharing.dao.CustomerDaoImpl;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class Main {
    static final String starterModeList = "1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit";
    static final String carModeList = "1. Car list\n2. Create a car\n0. Back";
    static final String managerModeList = "1. Company list\n2. Create a company\n0. Back";
    static final String rentModeList = "1. Rent a car\n2. Return a rented car\n3. My rented car\n0. Back";

    public static int parseIntFromStr(String str) {
        int parsedInput;
        try {
            parsedInput = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return (-1);
        }
        return (parsedInput);
    }

    public static void carMode(BufferedReader reader, List<Company> companyList, int i, boolean flag, Customer customer) throws IOException {
        int parsedInput;
        List<Car> cars;
        Company currentCompany = companyList.get(i);
        System.out.println("\'" + currentCompany.getName() + "\' company");
        while (true) {
            if (flag) {
                System.out.println(carModeList);
                switch (parseIntFromStr(reader.readLine())) {
                    case 1:
                        cars = new CarDaoImpl().getCarsByCompanyId(currentCompany.getId());
                        Car.printCarList(cars, true);
                        break;
                    case 2:
                        System.out.println("Enter the car name:");
                        new CarDaoImpl().addCar(new Car(currentCompany.getId(), reader.readLine()));
                        System.out.println("The car was added");
                        break;
                    case 0:
                        return;
                }
            } else {
                cars = new CarDaoImpl().getCarsByCompanyId(currentCompany.getId());
                Car.printCarList(cars, false);
                System.out.println("0. Back");
                parsedInput = parseIntFromStr(reader.readLine());
                if (0 == parsedInput) {
                    return;
                } else if (parsedInput > 0 && parsedInput <= cars.size()) {
                    Car rentedCar = cars.get(parsedInput - 1);
                    customer.setRentedCarId(rentedCar.getId());
                    System.out.println("\nYou rented \'" + rentedCar.getName() + "\'");
                    rentedCar.setRented(1);
                    new CarDaoImpl().updateCarStatus(rentedCar);
                    new CustomerDaoImpl().updateCustomerRentedCarId(customer);
                    return;
                }
            }
        }

    }

    public static void companyMode(BufferedReader reader, boolean flag, Customer customer) throws IOException {
        int parsedInput;
        CompanyDaoImpl companyDao = new CompanyDaoImpl();
        List<Company> companyList;
        companyList = companyDao.getAllCompanies();
        if (companyList.isEmpty()) {
            System.out.println("The company list is empty");
        } else {
            while (true) {
                System.out.println("Choose the company:");
                new Company().printCompanyList(companyList);
                System.out.println("0. Back");
                parsedInput = parseIntFromStr(reader.readLine());
                if (0 == parsedInput) {
                    return ;
                } else if (parsedInput > 0 && parsedInput <= companyList.size()) {
                    carMode(reader, companyList, parsedInput - 1, flag, customer);
                    return ;
                } else {
                    System.out.println("Incorrect input, try again\n");
                }
                System.out.println();
            }
        }
    }

    public static void managerMode(BufferedReader reader) throws IOException {
        int parsedInput;
        CompanyDaoImpl companyDao = new CompanyDaoImpl();
        while (true) {
            System.out.println(managerModeList);
            parsedInput = parseIntFromStr(reader.readLine());
            switch (parsedInput) {
                case 1 :
                    companyMode(reader, true, null);
                    break;
                case 2 :
                    System.out.println("Enter the company name");
                    companyDao.addCompany(new Company(reader.readLine()));
                    System.out.println("The company was created!\n");
                    break;
                case 0 :
                    return ;
                default :
                    System.out.println("Incorrect input, try again");
                    break;
            }
        }
    }

    public static void createCustomer(BufferedReader reader) throws IOException {
        String input;
        System.out.println("Enter the customer name:");
        input = reader.readLine();
        if (!input.equals("")) {
            new CustomerDaoImpl().addCustomer(new Customer(input));
        }
        System.out.println("The customer was added!");
    }

    public static void printRentedCar(Customer customer) {
        if (customer.getRentedCarId() == null) {
            System.out.println("You didn't rent a car!");
        } else {
            Car car = customer.getRentedCar();
            Company company = customer.getRentedCompany(car);
            System.out.println("You rented car:\n" + car.getName() + "\nCompany:\n" +
                    company.getName());
        }
        System.out.println();
        return;
    }

    public static void rentCar(Customer customer, BufferedReader reader) throws IOException {
        if (customer.getRentedCarId() != null) {
            System.out.println("You've already rented a car!");
            return;
        } else {
            companyMode(reader, false, customer);
        }
    }

    public static void returnRentedCar(Customer customer) {
        Car car = customer.getRentedCar();
        car.setRented(0);
        new CarDaoImpl().updateCarStatus(car);
        customer.setRentedCarId(null);
        new CustomerDaoImpl().updateCustomerRentedCarId(customer);
    }

    public static void carRentMode(Customer currentCustomer, BufferedReader reader) throws IOException {
        int parsedInput;
        while (true) {
            System.out.println(rentModeList);
            parsedInput = parseIntFromStr(reader.readLine());
            switch (parsedInput) {
                case 1 :
                    rentCar(currentCustomer, reader);
                    break;
                case 2 :
                    if (currentCustomer.getRentedCarId() != null) {
                        returnRentedCar(currentCustomer);
                        System.out.println("You've returned a rented car!");
                    } else {
                        System.out.println("You didn't rent a car!");
                    }
                    break;
                case 3 :
                    printRentedCar(currentCustomer);
                    break;
                case 0 :
                    return;
            }
        }
    }

    public static void customerMode(BufferedReader reader) throws IOException {
        List<Customer> customerList = new CustomerDaoImpl().getAllCustomers();
        int parsedInput;
        while (true) {
            if (!Customer.printCustomerList(customerList)) {
                return;
            }
            System.out.println("0. Back");
            parsedInput = parseIntFromStr(reader.readLine());
            if (0 == parsedInput) {
                return;
            } else if (parsedInput > 0 && parsedInput <= customerList.size()) {
                carRentMode(customerList.get(parsedInput - 1), reader);
                return;
            } else {
                System.out.println("Incorrect input, try again");
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        boolean ok = true;
        int parsedInput;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new DataBase();

        while (ok) {
            System.out.println(starterModeList);
            try {
                parsedInput = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input, try again\n");
                continue;
            }
            switch (parsedInput) {
                case 1 :
                    managerMode(reader);
                    break;
                case 2 :
                    customerMode(reader);
                    break;
                case 3 :
                    createCustomer(reader);
                    break;
                case 0 :
                    ok = false;
                    reader.close();
                    break;
                default:
                    System.out.println("Incorrect input, try again\n");
                    break;
            }
        }
//        CarDaoImpl carDao = new CarDaoImpl();
//        CompanyDaoImpl companyDao = new CompanyDaoImpl();
//        Company company = new Company("LOH");
//        companyDao.addCompany(company);
//        List <Company> companyList = companyDao.getAllCompanies();
//        System.out.println(companyList.get(0).getName() + companyList.get(0).getId());
//
//        Car car = new Car(companyList.get(0).getId(), "BUGGATI");
//        carDao.addCar(car);
//        List<Car> list = carDao.getCarsByCompanyId(companyList.get(0).getId());
//        System.out.println(list.get(0).getName());
    }
}