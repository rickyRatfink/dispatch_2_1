package org.faithfarm.domain;

import java.io.Serializable;

public class Donation implements Serializable {

	private Long donationId;
	private Long donorId;
	
	private String status;
	private String dispatchDate;
	private String specialFlag;
	private String callRequirements;
	private String confirmation;
	private String ac;
	private String bedding;
	private String beddingQtyType;
	private String books;
	private String booksQtyType;
	private String clothing;
	private String clothingQtyType;
	private String computer;
	private String desk;
	private String chest;
	private String armoire;
	private String dresser;
	private String mirror;
	private String nightstand;
	private String headboard;
	private String footboard;
	private String rails;
	private String lamp;
	private String lawnFurniture;
	private String mattress;
	private String mattressQtyType;
	private String miscHouseholdItems;
	private String refridgerator;
	private String stove;
	private String recliner;
	private String sofa;
	private String loveseat;
	private String wallUnit;
	private String table;
	private String chair;
	private String television;
	private String televisionSize;
	private String electronics;
	private String washer;
	private String dryer;
	private String exerciseEquipment;
	private String specialNotes;
	private String creationDate;
	private String createdBy;
	private String lastUpdatedDate;
	private String updatedBy;
	private String farmBase;
	
	private Donor donor;
	private Address address;
	
	
	
	
	public String getFarmBase() {
		return farmBase;
	}
	public void setFarmBase(String farmBase) {
		this.farmBase = farmBase;
	}
	public Donor getDonor() {
		return donor;
	}
	public void setDonor(Donor donor) {
		this.donor = donor;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	public Long getDonationId() {
		return donationId;
	}
	public void setDonationId(Long donationId) {
		this.donationId = donationId;
	}
	public Long getDonorId() {
		return donorId;
	}
	public void setDonorId(Long donorId) {
		this.donorId = donorId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getSpecialFlag() {
		return specialFlag;
	}
	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}
	public String getCallRequirements() {
		return callRequirements;
	}
	public void setCallRequirements(String callRequirements) {
		this.callRequirements = callRequirements;
	}
	public String getAc() {
		return ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public String getBedding() {
		return bedding;
	}
	public void setBedding(String bedding) {
		this.bedding = bedding;
	}
	public String getBeddingQtyType() {
		return beddingQtyType;
	}
	public void setBeddingQtyType(String beddingQtyType) {
		this.beddingQtyType = beddingQtyType;
	}
	public String getBooks() {
		return books;
	}
	public void setBooks(String books) {
		this.books = books;
	}
	public String getBooksQtyType() {
		return booksQtyType;
	}
	public void setBooksQtyType(String booksQtyType) {
		this.booksQtyType = booksQtyType;
	}
	public String getClothing() {
		return clothing;
	}
	public void setClothing(String clothing) {
		this.clothing = clothing;
	}
	public String getClothingQtyType() {
		return clothingQtyType;
	}
	public void setClothingQtyType(String clothingQtyType) {
		this.clothingQtyType = clothingQtyType;
	}
	public String getComputer() {
		return computer;
	}
	public void setComputer(String computer) {
		this.computer = computer;
	}
	public String getDesk() {
		return desk;
	}
	public void setDesk(String desk) {
		this.desk = desk;
	}
	public String getChest() {
		return chest;
	}
	public void setChest(String chest) {
		this.chest = chest;
	}
	public String getArmoire() {
		return armoire;
	}
	public void setArmoire(String armoire) {
		this.armoire = armoire;
	}
	public String getDresser() {
		return dresser;
	}
	public void setDresser(String dresser) {
		this.dresser = dresser;
	}
	public String getMirror() {
		return mirror;
	}
	public void setMirror(String mirror) {
		this.mirror = mirror;
	}
	public String getNightstand() {
		return nightstand;
	}
	public void setNightstand(String nightstand) {
		this.nightstand = nightstand;
	}
	public String getHeadboard() {
		return headboard;
	}
	public void setHeadboard(String headboard) {
		this.headboard = headboard;
	}
	public String getFootboard() {
		return footboard;
	}
	public void setFootboard(String footboard) {
		this.footboard = footboard;
	}
	public String getRails() {
		return rails;
	}
	public void setRails(String rails) {
		this.rails = rails;
	}
	public String getLamp() {
		return lamp;
	}
	public void setLamp(String lamp) {
		this.lamp = lamp;
	}
	public String getLawnFurniture() {
		return lawnFurniture;
	}
	public void setLawnFurniture(String lawnFurniture) {
		this.lawnFurniture = lawnFurniture;
	}
	public String getMattress() {
		return mattress;
	}
	public void setMattress(String mattress) {
		this.mattress = mattress;
	}
	public String getMattressQtyType() {
		return mattressQtyType;
	}
	public void setMattressQtyType(String mattressQtyType) {
		this.mattressQtyType = mattressQtyType;
	}
	public String getMiscHouseholdItems() {
		return miscHouseholdItems;
	}
	public void setMiscHouseholdItems(String miscHouseholdItems) {
		this.miscHouseholdItems = miscHouseholdItems;
	}
	public String getRefridgerator() {
		return refridgerator;
	}
	public void setRefridgerator(String refridgerator) {
		this.refridgerator = refridgerator;
	}
	public String getStove() {
		return stove;
	}
	public void setStove(String stove) {
		this.stove = stove;
	}
	public String getRecliner() {
		return recliner;
	}
	public void setRecliner(String recliner) {
		this.recliner = recliner;
	}
	public String getSofa() {
		return sofa;
	}
	public void setSofa(String sofa) {
		this.sofa = sofa;
	}
	public String getLoveseat() {
		return loveseat;
	}
	public void setLoveseat(String loveseat) {
		this.loveseat = loveseat;
	}
	public String getWallUnit() {
		return wallUnit;
	}
	public void setWallUnit(String wallUnit) {
		this.wallUnit = wallUnit;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getChair() {
		return chair;
	}
	public void setChair(String chair) {
		this.chair = chair;
	}
	public String getTelevision() {
		return television;
	}
	public void setTelevision(String television) {
		this.television = television;
	}
	public String getTelevisionSize() {
		return televisionSize;
	}
	public void setTelevisionSize(String televisionSize) {
		this.televisionSize = televisionSize;
	}
	public String getElectronics() {
		return electronics;
	}
	public void setElectronics(String electronics) {
		this.electronics = electronics;
	}
	public String getWasher() {
		return washer;
	}
	public void setWasher(String washer) {
		this.washer = washer;
	}
	public String getDryer() {
		return dryer;
	}
	public void setDryer(String dryer) {
		this.dryer = dryer;
	}
	public String getExerciseEquipment() {
		return exerciseEquipment;
	}
	public void setExerciseEquipment(String exerciseEquipment) {
		this.exerciseEquipment = exerciseEquipment;
	}
	public String getSpecialNotes() {
		return specialNotes;
	}
	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
	
	
	
}
