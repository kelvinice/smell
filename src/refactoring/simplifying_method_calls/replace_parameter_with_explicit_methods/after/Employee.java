package refactoring.simplifying_method_calls.replace_parameter_with_explicit_methods.after;

public class Employee {
	private double salary;
	
	public Employee(double initialSalary) {
		this.salary = initialSalary;
	}
	
	public double getSalary() {
		return salary;
	}
	
	// Explicit methods yang lebih jelas
	public void setSalary(double amount) {
		this.salary = amount;
	}
	
	public void increaseSalary(double amount) {
		this.salary = this.salary + amount;
	}
	
	public void decreaseSalary(double amount) {
		this.salary = this.salary - amount;
	}
}

