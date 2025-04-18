package sem2;

public class Main {
  public static void main(String[] args) {
    var carService = new CarService();
    var customStorage = new CustomerStorage();
    var hseCarService = new HseCarService(carService, customStorage);
    var pedalCarFactory = new PedalCarFactory();
    var handCarFactory = new HandCarFactory();
    var levCarFactory = new LevitatingCarFactory();

    customStorage.addCustomer(new Customer("Bob1", 6, 4, 0));
    customStorage.addCustomer(new Customer("Bob2", 4, 6, 100));
    customStorage.addCustomer(new Customer("Bob3", 6, 6, 300));
    customStorage.addCustomer(new Customer("Bob4", 4, 4, 300));

    carService.addCar(pedalCarFactory, new PedalEngineParams(10));
    carService.addCar(pedalCarFactory, new PedalEngineParams(20));
    carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    carService.addCar(handCarFactory, EmptyEngineParams.DEFAULT);
    carService.addCar(levCarFactory, EmptyEngineParams.DEFAULT);

    customStorage.getCustomers().forEach((customer) -> { System.out.println(customer); });

    hseCarService.sellCars();

    System.out.println("---------------");

    customStorage.getCustomers().forEach((customer) -> { System.out.println(customer); });
  }
}
