package uas.before;

import java.util.*;

abstract class ServiceType {
	private String description;

	public ServiceType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}

class InGarage extends ServiceType {
	public InGarage() {
		super("Service performed in garage.");
	}
}

class OnRoad extends ServiceType {
	public OnRoad() {
		super("Mobile on-road service.");
	}
}

abstract class Vehicle {
	private String model;
	private String plateNumber;
	private double value;
	public ServiceType serviceType; 

	public Vehicle(String model, String plateNumber, double value) {
		this.model = model;
		this.plateNumber = plateNumber;
		this.value = value;
	}

	public String getModel() {
		return model;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public double getValue() {
		return value;
	}
}

class Truck extends Vehicle {
	private double loadCapacity;

	public Truck(String model, String plateNumber, double value, double loadCapacity) {
		super(model, plateNumber, value);
		this.loadCapacity = loadCapacity;
	}

	public double getLoadCapacity() {
		return loadCapacity;
	}
}

class Van extends Vehicle {
	private int seatingCapacity;

	public Van(String model, String plateNumber, double value, int seatingCapacity) {
		super(model, plateNumber, value);
		this.seatingCapacity = seatingCapacity;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}
}

class Technician {
	private String name;
	private String id;
	private List<String> servicedVehicles = new ArrayList<>();
	private int pendingTasks;

	public Technician(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void assignTask(String vehiclePlate) {
		servicedVehicles.add(vehiclePlate);
	}

	public void addPendingTask() {
		pendingTasks++;
	}
}
abstract class Staff {
	protected StaffData data = new StaffData();

	public Staff(String id, String name) {
		data.id = id;
		data.name = name;
	}
}

class StaffData {
	public String id;
	public String name;
}

class Manager extends Staff {
	public Manager(String id, String name) {
		super(id, name);
	}

	public void approveMaintenance(String vehicle) {
		System.out.println("Approved maintenance for vehicle: " + vehicle);
	}

	public void performDuties() {
		System.out.println("Manager " + data.name + " reviewing reports and approving service tasks...");
	}
}

class Cleaner extends Staff {
	public Cleaner(String id, String name) {
		super(id, name);
	}

	public void performDuties() {
		System.out.println("Cleaner " + data.name + " cleaning the facility...");
	}
}

class BillingService {
	public double calculateServiceCharge(int hours) {
		return hours * 50.0;
	}
}

class NotificationService {
	public void notifyTechnicians(List<Technician> technicians) {
		for (Technician t : technicians) {
			System.out.println("Notifying technician: " + t.getName());
		}
	}
}

class FleetSystem {
	private List<Vehicle> fleet = new ArrayList<>();
	private List<Technician> technicians = new ArrayList<>();
	private BillingService billingService = new BillingService();
	private List<Staff> staffList = new ArrayList<>();
	private NotificationService notificationService = new NotificationService();

	public void addVehicle(Vehicle v) {
		fleet.add(v);
	}

	public void registerTechnician(String name, String id) {
		technicians.add(new Technician(name, id));
	}

	public void addStaff(Staff staff) {
		staffList.add(staff);
	}

	public void assignService(String technicianName, String vehiclePlate) {
		for (Technician tech : technicians) {
			if (tech.getName().equals(technicianName)) {
				tech.assignTask(vehiclePlate);
				break;
			}
		}
	}

	public void chargeForService(String technicianName, int hours) {
		for (Technician tech : technicians) {
			if (tech.getName().equals(technicianName)) {
				double fee = billingService.calculateServiceCharge(hours);
				System.out.println(tech.getName() + " charged: $" + fee);
				break;
			}
		}
	}

	public void notifyTechnicians() {
		notificationService.notifyTechnicians(technicians);
	}
}

public class FleetApplication {
	public static void main(String[] args) {
		FleetSystem system = new FleetSystem();

		system.addVehicle(new Truck("Volvo FH", "ABC-123", 150000, 20000));
		system.addVehicle(new Van("Ford Transit", "XYZ-789", 30000, 12));

		system.registerTechnician("Alice", "T001");
		system.assignService("Alice", "ABC-123");
		system.chargeForService("Alice", 3);

		Manager manager = new Manager("M001", "Bob");
		system.addStaff(manager);
		manager.approveMaintenance("XYZ-789");
		manager.performDuties();

		Cleaner cleaner = new Cleaner("C001", "Eve");
		system.addStaff(cleaner);
		cleaner.performDuties();

		system.notifyTechnicians();
	}
}
