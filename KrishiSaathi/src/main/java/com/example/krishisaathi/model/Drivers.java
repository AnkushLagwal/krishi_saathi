package com.example.krishisaathi.model;

import jakarta.persistence.*;

@Entity
public class Drivers {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    // Personal Info
	    @Column(nullable = false)
	    private String name;

	    @Column(nullable = false, length = 10)
	    private String mobile;

	    private String email;

	    private String photo;
	    
		@Column(nullable = false)
	    private String password;

	    // Address Info
	    @Column(nullable = false)
	    private String street;

	    @Column(nullable = false, length = 6)
	    private String pincode;

	    @Column(nullable = false)
	    private String tehsil;

	    @Column(nullable = false)
	    private String district;

	    @Column(nullable = false)
	    private String state;

	    // Vehicle & Verification Info
	    @Column(nullable = false)
	    private String ownerName;

	    @Column(nullable = false)
	    private String vehicleModel;

	    @Column(nullable = false)
	    private String vehicleNo;

	    @Column(nullable = false)
	    private Double capacity; // in tons

	    @Column(nullable = false)
	    private String dlNo;

	    
	    
	    private String rcDoc; // store RC document as BLOB

	    private String insuranceDoc; // store insurance document as BLOB
	    
	    
	    //Getters and Setters

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getPincode() {
			return pincode;
		}

		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

		public String getTehsil() {
			return tehsil;
		}

		public void setTehsil(String tehsil) {
			this.tehsil = tehsil;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getOwnerName() {
			return ownerName;
		}

		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}

		public String getVehicleModel() {
			return vehicleModel;
		}

		public void setVehicleModel(String vehicleModel) {
			this.vehicleModel = vehicleModel;
		}

		public String getVehicleNo() {
			return vehicleNo;
		}

		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}

		public Double getCapacity() {
			return capacity;
		}

		public void setCapacity(Double capacity) {
			this.capacity = capacity;
		}

		public String getDlNo() {
			return dlNo;
		}

		public void setDlNo(String dlNo) {
			this.dlNo = dlNo;
		}

		public String getRcDoc() {
			return rcDoc;
		}

		public void setRcDoc(String rcDoc) {
			this.rcDoc = rcDoc;
		}

		public String getInsuranceDoc() {
			return insuranceDoc;
		}

		public void setInsuranceDoc(String insuranceDoc) {
			this.insuranceDoc = insuranceDoc;
		}
	
	    
	    
	    


}
