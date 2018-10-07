package logistic.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import logistic.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AccountDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Account findAccount(String userName) {
		Session session = this.sessionFactory.getCurrentSession();	
		return session.find(Account.class, userName);
	}
	
	public Account findUserAccount() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		return this.findAccount(userName);
	}

}
