package com.byteslounge.spring.tx.dao.impl;


import com.byteslounge.spring.tx.dao.UserDAO;
import com.byteslounge.spring.tx.model.User;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.QueryResult;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void deleteUser(String socialSecurityNumber) throws Exception{

	}
    @Override
	public void deleteUser(User user) throws Exception{
        deleteUser du = new deleteUser(user.getSocialSecurityNumber());
        du.execute();

	}
    @Override
	public void insertUser(User user) throws Exception {
        insertUser iu = new insertUser(user);
        iu.execute();

	}

	@Override
	public User getUserById(int userId) {
		return new User();
	}
	
	@Override
    @Cacheable(value="Users", key="#socialSecurityNumber")
	public User getUser(String socialSecurityNumber) {
        User user = new User();
        GetSliceForUser gs = new GetSliceForUser(socialSecurityNumber);
        QueryResult<ColumnSlice<String,String>> queryResult = gs.execute();
        QueryResult<?> qr = (QueryResult)queryResult;
        //QueryResult<?> qr = (QueryResult)result;

        ColumnSlice columnSlice = (ColumnSlice)qr.get();

            HColumn hc = columnSlice.getColumnByName("socialSecurityNumber");
        if (hc==null){return null;}
            user.setSocialSecurityNumber((String)(hc.getValue()));
            hc = columnSlice.getColumnByName("firstName");
            user.setFirstName((String)(hc.getValue()));
            hc = columnSlice.getColumnByName("lastName");
            user.setLastName((String)(hc.getValue()));
            hc = columnSlice.getColumnByName("dateOfBirth");
            user.setDateOfBirth((String)(hc.getValue()));
            hc = columnSlice.getColumnByName("countryOfBirth");
            user.setCountryOfBirth((String)(hc.getValue()));
            //user.setSocialSecurityNumber();
            //log.info("| Row key: {}", row.getKey());
//            for ( Iterator iter = row.getColumnSlice().getColumns().iterator(); iter.hasNext();) {
//                //log.info("|   col: {}", iter.next());
//            }


        //user.setSocialSecurityNumber(queryResult.get());
		return user;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return new ArrayList<User>();
	}

}
