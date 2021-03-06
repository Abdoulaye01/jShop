package shop.main.data.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.main.data.entity.Review;

public interface ReviewDAO extends JpaRepository<Review, Long> {
	List<Review> findAll();

}
