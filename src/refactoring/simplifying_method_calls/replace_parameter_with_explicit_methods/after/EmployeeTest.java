package refactoring.simplifying_method_calls.replace_parameter_with_explicit_methods.after;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeTest {

	@Test
	void testSalaryMethods() {
		Employee employee = new Employee(1000.0);
		
		employee.setSalary(1500.0);
		assertEquals(1500.0, employee.getSalary());
		
		employee.increaseSalary(200.0);
		assertEquals(1700.0, employee.getSalary());
		
		employee.decreaseSalary(100.0);
		assertEquals(1600.0, employee.getSalary());
	}

}

