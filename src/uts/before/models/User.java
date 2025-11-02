package uts.before.models;

public class User {
	public static final int MEMBER = 0;
	public static final int LIBRARIAN = 1;
	
	protected String firstName;
	protected String lastName;
	protected Address address;
	protected int userCategory;
	
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
	
	public String getUserCategory() {
		switch(userCategory) {
		case MEMBER:
			return "Member";
		case LIBRARIAN:
			return "Librarian";
	}
		return null;
	}
	public boolean setUserCategory(int userCategory) {
		if(userCategory == MEMBER || userCategory == LIBRARIAN) {
			this.userCategory = userCategory;
			return true;
		}
		return false;
	}

}
