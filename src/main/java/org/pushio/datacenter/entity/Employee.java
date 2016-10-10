package org.pushio.datacenter.entity;

// Generated 2015-11-3 18:00:20 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 6832660999015757461L;

	private String name;
	private Long defaultPostId;
	private Integer educationBackground;
	private Integer maritalStatus;
	private Integer state;
	private Date dateOfBirth;
	private String idCard;
	private String email;
	private String phone;
	private String gender;
	private Integer fertilityStatus;
	private String graduatedSchool;
	private String speciality;
	private Integer politicsStatus;
	private String residenceAddress;
	private String currentAddress;
	private Date entryTime;
	private Integer isLeave;
	private Date leaveTime;

	
	private Set<EmployeePosts> employeePostses = new HashSet<EmployeePosts>(0);
	
	private Set<Post> posts = new HashSet<Post>(0);
	


	
	public Employee() {
	}

	public Employee(String name) {
		this.name = name;
	}

	
	



	@Column(name = "NAME", nullable = false, length = 16)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DEFAULT_POST_ID")
	public Long getDefaultPostId() {
		return this.defaultPostId;
	}

	public void setDefaultPostId(Long defaultPostId) {
		this.defaultPostId = defaultPostId;
	}


	@Column(name = "EDUCATION_BACKGROUND")
	public Integer getEducationBackground() {
		return this.educationBackground;
	}

	public void setEducationBackground(Integer educationBackground) {
		this.educationBackground = educationBackground;
	}

	@Column(name = "MARITAL_STATUS")
	public Integer getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "STATE")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OF_BIRTH", length = 10)
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "ID_CARD", length = 32)
	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "PHONE", length = 32)
	public String getPhone(){
		return this.phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	@Column(name = "EMAIL", length = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "GENDER", length = 2)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "FERTILITY_STATUS")
	public Integer getFertilityStatus() {
		return this.fertilityStatus;
	}

	public void setFertilityStatus(Integer fertilityStatus) {
		this.fertilityStatus = fertilityStatus;
	}

	@Column(name = "GRADUATED_SCHOOL")
	public String getGraduatedSchool() {
		return this.graduatedSchool;
	}

	public void setGraduatedSchool(String graduatedSchool) {
		this.graduatedSchool = graduatedSchool;
	}

	@Column(name = "SPECIALITY")
	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	@Column(name = "POLITICS_STATUS")
	public Integer getPoliticsStatus() {
		return this.politicsStatus;
	}

	public void setPoliticsStatus(Integer politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	@Column(name = "RESIDENCE_ADDRESS")
	public String getResidenceAddress() {
		return this.residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	@Column(name = "CURRENT_ADDRESS")
	public String getCurrentAddress() {
		return this.currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRY_TIME", length = 0)
	public Date getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	@Column(name = "IS_LEAVE")
	public Integer getIsLeave() {
		return this.isLeave;
	}

	public void setIsLeave(Integer isLeave) {
		this.isLeave = isLeave;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LEAVE_TIME", length = 0)
	public Date getLeaveTime() {
		return this.leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeBySalesmanId")
	public Set<InternalBooking> getInternalBookingsForSalesmanId() {
		return this.internalBookingsForSalesmanId;
	}

	public void setInternalBookingsForSalesmanId(
			Set<InternalBooking> internalBookingsForSalesmanId) {
		this.internalBookingsForSalesmanId = internalBookingsForSalesmanId;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<OperatingHistory> getOperatingHistories() {
		return this.operatingHistories;
	}

	public void setOperatingHistories(Set<OperatingHistory> operatingHistories) {
		this.operatingHistories = operatingHistories;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<PortWhiteList> getPortWhiteLists() {
		return this.portWhiteLists;
	}

	public void setPortWhiteLists(Set<PortWhiteList> portWhiteLists) {
		this.portWhiteLists = portWhiteLists;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cilentServicer")
	public Set<BusOrder> getBusOrdersForCilentServicer() {
		return this.busOrdersForCilentServicer;
	}

	public void setBusOrdersForCilentServicer(
			Set<BusOrder> busOrdersForCilentServicer) {
		this.busOrdersForCilentServicer = busOrdersForCilentServicer;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<ClientBusinessMan> getClientBusinessMans() {
		return this.clientBusinessMans;
	}

	public void setClientBusinessMans(Set<ClientBusinessMan> clientBusinessMans) {
		this.clientBusinessMans = clientBusinessMans;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	public Set<ClientSecondCs> getClientSecondCses() {
		return this.clientSecondCses;
	}

	public void setClientSecondCses(Set<ClientSecondCs> clientSecondCses) {
		this.clientSecondCses = clientSecondCses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modifyUser")
	public Set<Account> getAccountsForModifyUserId() {
		return this.accountsForModifyUserId;
	}

	public void setAccountsForModifyUserId(Set<Account> accountsForModifyUserId) {
		this.accountsForModifyUserId = accountsForModifyUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modifyUser")
	public Set<BusOrder> getBusOrdersForBuserId() {
		return this.busOrdersForBuserId;
	}

	public void setBusOrdersForBuserId(Set<BusOrder> busOrdersForBuserId) {
		this.busOrdersForBuserId = busOrdersForBuserId;
	}*/

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
//	public Set<Client> getClients() {
//		return this.clients;
//	}

//	public void setClients(Set<Client> clients) {
//		this.clients = clients;
//	}

	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "addUser")
	public Set<ExternalBooking> getExternalBookingsForAddUserId() {
		return this.externalBookingsForAddUserId;
	}

	public void setExternalBookingsForAddUserId(
			Set<ExternalBooking> externalBookingsForAddUserId) {
		this.externalBookingsForAddUserId = externalBookingsForAddUserId;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addUser")
	public Set<BusOrder> getBusOrdersForAddUserId() {
		return this.busOrdersForAddUserId;
	}

	public void setBusOrdersForAddUserId(Set<BusOrder> busOrdersForAddUserId) {
		this.busOrdersForAddUserId = busOrdersForAddUserId;
	}


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addUser")
	public Set<VoyagePlan> getVoyagePlansForAddUserId() {
		return this.voyagePlansForAddUserId;
	}

	public void setVoyagePlansForAddUserId(
			Set<VoyagePlan> voyagePlansForAddUserId) {
		this.voyagePlansForAddUserId = voyagePlansForAddUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addUser")
	public Set<InOutBookPlan> getInOutBookPlansForAddUserId() {
		return this.inOutBookPlansForAddUserId;
	}

	public void setInOutBookPlansForAddUserId(
			Set<InOutBookPlan> inOutBookPlansForAddUserId) {
		this.inOutBookPlansForAddUserId = inOutBookPlansForAddUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modifyUser")
	public Set<VoyagePlan> getVoyagePlansForModifyUserId() {
		return this.voyagePlansForModifyUserId;
	}

	public void setVoyagePlansForModifyUserId(
			Set<VoyagePlan> voyagePlansForModifyUserId) {
		this.voyagePlansForModifyUserId = voyagePlansForModifyUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modifyUser")
	public Set<InOutBookPlan> getInOutBookPlansForModifyUserId() {
		return this.inOutBookPlansForModifyUserId;
	}

	public void setInOutBookPlansForModifyUserId(
			Set<InOutBookPlan> inOutBookPlansForModifyUserId) {
		this.inOutBookPlansForModifyUserId = inOutBookPlansForModifyUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeByAuditedById")
	public Set<InternalBooking> getInternalBookingsForAuditedById() {
		return this.internalBookingsForAuditedById;
	}

	public void setInternalBookingsForAuditedById(
			Set<InternalBooking> internalBookingsForAuditedById) {
		this.internalBookingsForAuditedById = internalBookingsForAuditedById;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addUser")
	public Set<InternalBooking> getInternalBookingsForAddUserId() {
		return this.internalBookingsForAddUserId;
	}

	public void setInternalBookingsForAddUserId(
			Set<InternalBooking> internalBookingsForAddUserId) {
		this.internalBookingsForAddUserId = internalBookingsForAddUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modifyUser")
	public Set<ExternalBooking> getExternalBookingsForModifyUserId() {
		return this.externalBookingsForModifyUserId;
	}

	public void setExternalBookingsForModifyUserId(
			Set<ExternalBooking> externalBookingsForModifyUserId) {
		this.externalBookingsForModifyUserId = externalBookingsForModifyUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modifyUser")
	public Set<InternalBooking> getInternalBookingsForModifyUserId() {
		return this.internalBookingsForModifyUserId;
	}

	public void setInternalBookingsForModifyUserId(
			Set<InternalBooking> internalBookingsForModifyUserId) {
		this.internalBookingsForModifyUserId = internalBookingsForModifyUserId;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modifyUser")
	public Set<FileManage> getFileManagesForModifyUser() {
		return this.fileManagesForModifyUser;
	}

	public void setFileManagesForModifyUser(
			Set<FileManage> fileManagesForModifyUser) {
		this.fileManagesForModifyUser = fileManagesForModifyUser;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "addUser")
	public Set<FileManage> getFileManagesForAddUser() {
		return this.fileManagesForAddUser;
	}

	public void setFileManagesForAddUser(Set<FileManage> fileManagesForAddUser) {
		this.fileManagesForAddUser = fileManagesForAddUser;
	}*/

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee",cascade={CascadeType.ALL})
	public Set<EmployeePosts> getEmployeePostses() {
		return this.employeePostses;
	}

	public void setEmployeePostses(Set<EmployeePosts> employeePostses) {
		this.employeePostses = employeePostses;
	}

	@Transient
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}
