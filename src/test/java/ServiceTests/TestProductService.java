package ServiceTests;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.entity.Product;
import shop.main.data.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
public class TestProductService {
	private final String PRODUCT_NAME = "";
	private final String PRODUCT_URL = "ra";
	private final Integer CURRENT = 1;
	private final Integer PAGE_SIZE = 2;

	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	@Test
	public void findByNameAndURL() {
		Pageable pageable = new PageRequest(CURRENT, PAGE_SIZE);
		List<Product> products = productService.findPageable(PRODUCT_NAME, PRODUCT_URL, "", pageable);
		// Assert.assertEquals(products.get(0).getName(), PRODUCT_NAME);

		products.stream().forEach(p -> System.out.println(p.getName() + " " + p.getUrl()));
		System.out.println("*");
		System.out.println("*****************found products " + products.size());
		System.out.println("*");
	}
}
