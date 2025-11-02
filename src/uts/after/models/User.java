package uts.after.models;

public abstract class User {
	protected String firstName;
	protected String lastName;
	protected Address address;
	
	public User(String firstName, String lastName, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	// Refactored: Replaced switch statement with polymorphism (Switch Statements fix)
	public abstract String getUserCategory();
	
	// Refactored: Moved formatting logic here (Feature Envy fix)
	public String getFormattedInfo() {
		return "Name: " + firstName + " " + lastName 
			   + ", Type: " + getUserCategory() 
			   + ", Address: " + address.getFullAddress();
	}
}


