package logistic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 В JPA (Или  Hibernate), Entity является представительным классом (соответствующим) таблице в базе данных. 
 Поля (field) в данном классе будут соответствовать столбцам в таблице.
 Мы создадим класс  Accounts, чтобы представить таблицу  ACCOUNTS в базе данных.  
 JPA Annotation будут использованы для аннотаций на полях (field), чтобы 
 	описать сопоставление (mapping) между полями и столбцами таблицы. 
 Эти сопоставления являются 1-1, каждое поле соответствует одному столбцу в таблице.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

	private static final long serialVersionUID = 4144360264403689174L;
	
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_COUNTERPARTY = "COUNTERPARTY";
	
	@Id
	@Column(name = "USER_NAME", length = 20, nullable = false)
	private String userName;
	
	@Column(name = "ACTIVE")
	private boolean active = true;
	
	@Column(name = "ENCRYTED_PASSWORD", length = 128, nullable = false)
	private String encryptedPassword;
	
	@Column(name = "USER_ROLE", length = 20, nullable = true)
	private String userRole;
	

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "[" + this.userName + "," + this.encryptedPassword + "," + this.userRole + "]";
    }

}
