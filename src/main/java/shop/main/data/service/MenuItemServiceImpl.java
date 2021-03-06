package shop.main.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.MenuItemDAO;
import shop.main.data.entity.MenuItem;
import shop.main.utils.Constants;

@Service("menuItemService")
public class MenuItemServiceImpl implements MenuItemService {

	@Autowired
	private MenuItemDAO dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void save(MenuItem item) {
		dao.save(item);

	}

	@Override
	public MenuItem findById(long id) {
		return dao.findOne(id);
	}

	@Override
	public void deleteById(long id) {
		dao.delete(id);

	}

	@Transactional
	@Override
	public List<MenuItem> findLeftMenu() {
		Session session = (Session) entityManager.getDelegate();
		String hql = "from MenuItem m where m.status = true " + " and m.type ='" + Constants.LEFT + "'";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Transactional
	@Override
	public List<MenuItem> findRightMenu() {
		Session session = (Session) entityManager.getDelegate();
		String hql = "from MenuItem m where m.status = true " + " and m.type ='" + Constants.RIGHT + "'";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Transactional
	@Override
	public List<MenuItem> listAll(Pageable pageable) {
		Session session = (Session) entityManager.getDelegate();
		String hql = "from MenuItem m ";
		Query query = session.createQuery(hql);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return query.list();
	}

	@Override
	public long getAllCount() {
		return dao.count();
	}

}
