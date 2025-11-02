package uts.after.models;

public class Member extends User {
	
	public Member(String firstName, String lastName, Address address) {
		super(firstName, lastName, address);
	}

	@Override
	public String getUserCategory() {
		return "Member";
	}
}


