package org.faithfarm.domain;

import java.io.Serializable;

public class Address implements Serializable {
	
	private Long addressId;
	private Long donorId;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zipcode;
	private String majorIntersection;
	private String structureType;
	private String subdivision;
	private String streetSuffix;
	private String unit;
	private String building;
	private String floor;
	private String elevatorFlag;
	private String gateFlag;
	private String gateInstructions;
	private String createdBy;
	private String lastUpdatedBy;
	
	
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
	public Long getDonorId() {
		return donorId;
	}
	public void setDonorId(Long donorId) {
		this.donorId = donorId;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMajorIntersection() {
		return majorIntersection;
	}
	public void setMajorIntersection(String majorIntersection) {
		this.majorIntersection = majorIntersection;
	}
	public String getStructureType() {
		return structureType;
	}
	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public String getStreetSuffix() {
		return streetSuffix;
	}
	public void setStreetSuffix(String streetSuffix) {
		this.streetSuffix = streetSuffix;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getElevatorFlag() {
		return elevatorFlag;
	}
	public void setElevatorFlag(String elevatorFlag) {
		this.elevatorFlag = elevatorFlag;
	}
	public String getGateFlag() {
		return gateFlag;
	}
	public void setGateFlag(String gateFlag) {
		this.gateFlag = gateFlag;
	}
	public String getGateInstructions() {
		return gateInstructions;
	}
	public void setGateInstructions(String gateInstructions) {
		this.gateInstructions = gateInstructions;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	

}
