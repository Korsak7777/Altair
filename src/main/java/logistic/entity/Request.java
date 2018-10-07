package logistic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REQUEST")
public class Request implements Serializable {

	private static final long serialVersionUID = 7023202856018080732L;

	@Id
	@Column(name = "REQUEST_ID", length = 256, nullable = false)
	private String requestId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NAME", nullable = false, 
		foreignKey = @ForeignKey(name = "REQUEST_FK_ACCOUNT"))
	private Account userName;
	
	@Column(name = "CREATION_DATE", nullable = false)
	private String creationDate;
	
	@Column(name = "AMOUNT", nullable = true)
	private int amount;
	
	@Column(name = "REQUEST_NUM", nullable = false)
	private int requestNum;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Account getUserName() {
		return userName;
	}
	public void setUserName(Account userName) {
		this.userName = userName;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getRequestNum() {
		return requestNum;
	}
	public void setRequestNum(int requestNum) {
		this.requestNum = requestNum;
	}
}
