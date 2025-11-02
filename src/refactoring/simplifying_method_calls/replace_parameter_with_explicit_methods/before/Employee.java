package refactoring.simplifying_method_calls.replace_parameter_with_explicit_methods.before;

public class Employee {
	private double salary;
	
	public Employee(double initialSalary) {
		this.salary = initialSalary;
	}
	
	public double getSalary() {
		return salary;
	}
	
	// Method dengan parameter yang membingungkan
	public void setSalary(int type, double amount) {
		if (type == 0) {
			this.salary = amount;
		} else if (type == 1) {
			this.salary = this.salary + amount;
		} else if (type == 2) {
			this.salary = this.salary - amount;
		}
	}
}

