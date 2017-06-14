package shop.main.data.service;

import java.util.List;

import shop.main.data.entity.User;
import shop.main.validation.EmailExistsException;

public interface UserService {
	void save(User user);

	void delete(User user);

	List<User> listAll();

	void deleteById(long id);

	User fingUserById(long id);

	User findByUserName(String username);

	User registerNewUserAccount(User accountDto) throws EmailExistsException;
}
