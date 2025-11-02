package uas.after;

import java.util.*;

// ========================================
// REFACTORED: Missing Abstraction
// StaffData removed, integrated into Staff/Employee
// ========================================

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
	// REFACTORED: Missing Encapsulation - serviceType now private with getter/setter
	private ServiceType serviceType; 

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

	// REFACTORED: Encapsulation - added getter and setter for serviceType
	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
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

// ========================================
// REFACTORED: Missing Hierarchy
// Created Employee hierarchy to unify Technician and Staff
// ========================================
abstract class Employee {
	// REFACTORED: Missing Abstraction & Missing Encapsulation
	// Removed StaffData, integrated id and name as private fields
	private String id;
	private String name;

	public Employee(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class Technician extends Employee {
	private List<String> servicedVehicles = new ArrayList<>();
	private int pendingTasks;

	public Technician(String id, String name) {
		super(id, name);
	}

	public void assignTask(String vehiclePlate) {
		servicedVehicles.add(vehiclePlate);
	}

	public void addPendingTask() {
		pendingTasks++;
	}

	public int getPendingTasks() {
		return pendingTasks;
	}

	public List<String> getServicedVehicles() {
		return new ArrayList<>(servicedVehicles); // defensive copy
	}
}

// REFACTORED: Staff now extends Employee (Missing Hierarchy fix)
abstract class Staff extends Employee {
	public Staff(String id, String name) {
		super(id, name);
	}

	public abstract void performDuties();
}

class Manager extends Staff {
	public Manager(String id, String name) {
		super(id, name);
	}

	public void approveMaintenance(String vehicle) {
		System.out.println("Approved maintenance for vehicle: " + vehicle);
	}

	@Override
	public void performDuties() {
		System.out.println("Manager " + getName() + " reviewing reports and approving service tasks...");
	}
}

class Cleaner extends Staff {
	public Cleaner(String id, String name) {
		super(id, name);
	}

	@Override
	public void performDuties() {
		System.out.println("Cleaner " + getName() + " cleaning the facility...");
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

// ========================================
// REFACTORED: Hub-like Modularization
// Extracted managers to reduce coupling
// ========================================
class VehicleManager {
	private List<Vehicle> vehicles = new ArrayList<>();

	public void addVehicle(Vehicle v) {
		vehicles.add(v);
	}

	public List<Vehicle> getVehicles() {
		return new ArrayList<>(vehicles); // defensive copy
	}
}

class TechnicianManager {
	private List<Technician> technicians = new ArrayList<>();
	private BillingService billingService = new BillingService();
	private NotificationService notificationService = new NotificationService();

	public void registerTechnician(String id, String name) {
		technicians.add(new Technician(id, name));
	}

	public void assignService(String technicianName, String vehiclePlate) {
		Technician tech = findTechnicianByName(technicianName);
		if (tech != null) {
			tech.assignTask(vehiclePlate);
		}
	}

	public void chargeForService(String technicianName, int hours) {
		Technician tech = findTechnicianByName(technicianName);
		if (tech != null) {
			double fee = billingService.calculateServiceCharge(hours);
			System.out.println(tech.getName() + " charged: $" + fee);
		}
	}

	public void notifyTechnicians() {
		notificationService.notifyTechnicians(technicians);
	}

	private Technician findTechnicianById(String id) {
		for (Technician tech : technicians) {
			if (tech.getId().equals(id)) {
				return tech;
			}
		}
		return null;
	}

	public Technician findTechnicianByName(String name) {
		for (Technician tech : technicians) {
			if (tech.getName().equals(name)) {
				return tech;
			}
		}
		return null;
	}

	public List<Technician> getTechnicians() {
		return new ArrayList<>(technicians); // defensive copy
	}
}

class StaffManager {
	private List<Staff> staffList = new ArrayList<>();

	public void addStaff(Staff staff) {
		staffList.add(staff);
	}

	public List<Staff> getStaffList() {
		return new ArrayList<>(staffList); // defensive copy
	}
}

// REFACTORED: FleetSystem now acts as coordinator, not hub
class FleetSystem {
	private VehicleManager vehicleManager = new VehicleManager();
	private TechnicianManager technicianManager = new TechnicianManager();
	private StaffManager staffManager = new StaffManager();

	public void addVehicle(Vehicle v) {
		vehicleManager.addVehicle(v);
	}

	public void registerTechnician(String id, String name) {
		technicianManager.registerTechnician(id, name);
	}

	public void addStaff(Staff staff) {
		staffManager.addStaff(staff);
	}

	public void assignService(String technicianName, String vehiclePlate) {
		technicianManager.assignService(technicianName, vehiclePlate);
	}

	public void chargeForService(String technicianName, int hours) {
		technicianManager.chargeForService(technicianName, hours);
	}

	public void notifyTechnicians() {
		technicianManager.notifyTechnicians();
	}

	// Getters for managers if needed
	public VehicleManager getVehicleManager() {
		return vehicleManager;
	}

	public TechnicianManager getTechnicianManager() {
		return technicianManager;
	}

	public StaffManager getStaffManager() {
		return staffManager;
	}
}

public class FleetApplication {
	public static void main(String[] args) {
		FleetSystem system = new FleetSystem();

		system.addVehicle(new Truck("Volvo FH", "ABC-123", 150000, 20000));
		system.addVehicle(new Van("Ford Transit", "XYZ-789", 30000, 12));

		system.registerTechnician("T001", "Alice");
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

