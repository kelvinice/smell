package uts.after.models;

public class Address {
	private String street;
	private String village;
	private String district;
	private String municipality;
	
	public Address(String street, String village, String district, String municipality) {
		this.street = street;
		this.village = village;
		this.district = district;
		this.municipality = municipality;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	
	// Refactored: Moved address formatting logic here (Feature Envy fix)
	public String getFullAddress() {
		return street + ", " + village + ", " + district + ", " + municipality;
	}
}


