package shop.main.data.service;

import java.util.List;
import java.util.OptionalDouble;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.main.data.DAO.ProductDAO;
import shop.main.data.DAO.ProductOptionDAO;
import shop.main.data.entity.Product;
import shop.main.data.entity.Review;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ProductOptionDAO productOptionDAO;

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private ProductOptionService productOptionService;

	@Override
	public void saveProduct(Product product) {
		LOGGER.info("ProductServiceImpl: save product is called");
		productDAO.save(product);

	}

	@Override
	public void deleteProduct(Product product) {

		LOGGER.info("ProductServiceImpl: delete product is called");
		productDAO.delete(product);

	}

	@Override
	public Product findProductById(long id) {

		return productDAO.findOne(id);
	}

	@Override
	public boolean checkUniqueURL(Product product) {
		Product result = productDAO.findOneByUrl(product.getUrl());
		if (result == null) {
			return true;
		} else if (result.getId().equals(product.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteProductById(long id) {

		productDAO.delete(id);

	}

	@Override
	public Product findProductByUrl(String url) {

		return productDAO.findOneByUrl(url);
	}

	@Transactional
	@Override
	public List<Product> findAllFeatured() {
		Session session = (Session) entityManager.getDelegate();

		String hql = "from Product product where product.status = true AND product.featured=true AND product.category.status = true";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@Transactional
	@Override
	public void updateRating(Long productId) {
		Session session = (Session) entityManager.getDelegate();

		List<Review> reviews = productDAO.findOne(productId).getReviews();
		OptionalDouble rating = reviews.stream().mapToInt(a -> a.getRating()).average();
		String hql = "update Product set rating = :rating where id = :productId";
		Query query = session.createQuery(hql);
		query.setParameter("rating", (Math.round(rating.getAsDouble())));
		query.setParameter("productId", productId);
		query.executeUpdate();
		// return query.list();
	}

	@Transactional
	@Override
	public List<Product> findFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories,
			Pageable pageable) {
		Session session = (Session) entityManager.getDelegate();
		String hql = "SELECT p FROM Product p,ProductOption o, Category c "
				+ "where p.id=o.product AND p.category=c.id " + "AND p.status = true AND c.status = true ";
		if (filterIds == null || filterIds.isEmpty()) {
			if (listOfCategories == null || listOfCategories.isEmpty()) {
				hql += "ORDER by p.id";
			} else {
				hql += "AND c.id in :listOfCategories  ORDER by p.id";
			}
		} else {
			hql += "AND (o.id in :filterIds)" + " AND (c.id in :listOfCategories)" + " ORDER by p.id";
		}

		Query query = session.createQuery(hql);
		if (filterIds != null && !filterIds.isEmpty())
			query.setParameterList("filterIds", filterIds);
		if (listOfCategories != null && !listOfCategories.isEmpty())
			query.setParameterList("listOfCategories", listOfCategories);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return query.list();

	}

	@Override
	public long countFilteredProductsInCategory(List<Long> filterIds, List<Long> listOfCategories) {
		if (filterIds == null || filterIds.isEmpty()) {
			if (listOfCategories == null || listOfCategories.isEmpty()) {
				return productDAO.countFilteredProductsInCategory();
			} else {
				return productDAO.countFilteredProductsInCategory(listOfCategories);
			}
		} else {
			return productDAO.countFilteredProductsInCategory(filterIds, listOfCategories);
		}

	}

	@Override
	public Product findProductBySKU(String sku) {

		return productDAO.findOneBySku(sku);
	}

	@Override
	public boolean checkUniqueSKU(Product product) {
		Product result = productDAO.findOneBySku(product.getSku());
		if (result == null) {
			return true;
		} else if (result.getId().equals(product.getId())) {
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public List<Product> findPageable(String name, String url, String searchSKU, Pageable pageable) {
		if (name != null)
			name = "%" + name + "%";
		if (url != null)
			url = "%" + url + "%";
		if (searchSKU != null)
			searchSKU = "%" + searchSKU + "%";
		// DAO query jenerated 2 queries to DB
		// return productDAO.findPageable(name, url, searchSKU,
		// pageable).getContent();
		Session session = (Session) entityManager.getDelegate();

		String hql = "FROM Product item " + "WHERE (:name is NULL OR item.name LIKE :name) "
				+ "AND (:url is NULL OR item.url LIKE :url) " + "AND (:searchSKU is NULL OR item.sku LIKE :searchSKU) "
				+ "GROUP BY item.id ORDER BY item.id";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("searchSKU", searchSKU);
		query.setParameter("url", url);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return query.list();
	}

	@Transactional
	@Override
	public long countPageable(String name, String url, String searchSKU) {
		if (name != null)
			name = "%" + name + "%";
		if (url != null)
			url = "%" + url + "%";
		if (searchSKU != null)
			searchSKU = "%" + searchSKU + "%";
		// productDAO.countPageable(name, url, searchSKU);

		Session session = (Session) entityManager.getDelegate();
		String hql = "SELECT count(*) FROM Product item " + "WHERE (:name is NULL OR item.name LIKE :name) "
				+ "AND (:url is NULL OR item.url LIKE :url) " + "AND (:searchSKU is NULL OR item.sku LIKE :searchSKU) "
				+ "GROUP BY item.id ORDER BY item.id";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		query.setParameter("searchSKU", searchSKU);
		query.setParameter("url", url);
		Long count = ((Long) query.uniqueResult());
		return count == null ? 0 : count;
	}

}
