package com.byteslounge.spring.tx.model;

public class User {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name="ID", nullable = false)
//	private int ID;
	
//	@Column(name="USERNAME", nullable = false)
//	private String username;
	

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String socialSecurityNumber;

    private String countryOfBirth;

	
//	public int getId() {
//		return ID;
//	}
	
//	public void setId(int id) {
//		this.ID = id;
//	}

//	public String getUsername() {
//		return username;
//	}
	
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

    public String getDateOfBirth() {
		return dateOfBirth;
	}

    public void setDateOfBirth(String name) {
        this.dateOfBirth = name;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

	public void setSocialSecurityNumber(String ssn) {
		this.socialSecurityNumber = ssn;
	}

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}
	
}
