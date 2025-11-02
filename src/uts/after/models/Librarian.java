package uts.after.models;

public class Librarian extends User {
	
	public Librarian(String firstName, String lastName, Address address) {
		super(firstName, lastName, address);
	}

	@Override
	public String getUserCategory() {
		return "Librarian";
	}
}


