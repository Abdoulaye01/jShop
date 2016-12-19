package shop.main.data.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.main.data.DAO.ProductDAO;
import shop.main.data.DAO.UserDAO;
import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.objects.User;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public void save(User user) {
		userDAO.save(user);
		
	}

	@Override
	public void delete(User user) {
		userDAO.delete(user);
	}

	@Override
	public List<User> listAll() {
		return userDAO.findAll();
	}

	@Override
	public User fingUserById(long id) {
		return userDAO.findOne(id);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = userDAO.findByUserName(username);

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}	
	

}
