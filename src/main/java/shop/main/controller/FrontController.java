package shop.main.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shop.main.data.objects.Category;
import shop.main.data.objects.Product;
import shop.main.data.service.CategoryService;
import shop.main.data.service.ProductService;
import shop.main.utils.URLUtils;

@Controller
public class FrontController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);

	@Autowired
	@Qualifier("dataSourceMysql")
	private DataSource dataSourceMysql;

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	 private ProductService productService;
	
	@Autowired
	 private CategoryService categoryService;
	
	@Autowired
    ServletContext context;

	@RequestMapping(value = "/displayusersmysql")
	public ModelAndView displayUsers(Principal principal) {
		// mysql database
		jdbcTemplate = new JdbcTemplate(dataSourceMysql);

		List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT*FROM USER");

		List<String> data = new ArrayList<String>();
		for(Map<String, Object> user: users) {
			data.add("username: "+ user.get("username"));
			LOGGER.debug("username: "+ user.get("username"));
		}
		return new ModelAndView("db_test/embeded_db_test", "users", data);
	}
	
//	@RequestMapping(value = "/category")
//	public ModelAndView displayCategory(Principal principal) {
//		List<Product> data = productService.listAll();
//		List<Category> menu = categoryService.findAllParentCategories();
//		return new ModelAndView("category", "products", data);
//	}
	
	@RequestMapping(value = "/category")
	public String categoriesList(Model model) {

		model.addAttribute("products",productService.findAllActiveWithinActiveCategory());
		model.addAttribute("menu", categoryService.findAllParentCategories());
		return "category";
	}
	
	@RequestMapping(value = "/product")
	public String displayProduct(Model model) {
		Product product = productService.fingProductById(0L);
		//System.out.println(data.toString());
		
		addMenuItems(model);
		model.addAttribute("product",product);
		return "product";
	}
	
	@RequestMapping(value = "/products/{url}")
	public String displayProductByUrl(@PathVariable("url") String url, Model model) {
		System.out.println("url is "+url);
		
		Product product = productService.fingProductByUrl(url);
		//System.out.println(data.toString());
		if(product.getCategory()!= null){
			System.out.println("add breadcrumbs");
			List<Category> breadCrumbs = new ArrayList<Category>();
			addBreadCrumb(product.getCategory(), breadCrumbs);
			model.addAttribute("breadCrumbs",breadCrumbs);
		}
		
		addMenuItems(model);
		model.addAttribute("product",product);
		if(product.getMetaTitle()!=null && !product.getMetaTitle().equals("")){
			model.addAttribute("metaTitle", product.getMetaTitle());
		}else{
			model.addAttribute("metaTitle", "JShop - "+product.getName()+" - "+product.getCategory().getCategoryName());
		}
		if(product.getMetaDescription()!=null && !product.getMetaDescription().equals("")){
			model.addAttribute("metaDescription", product.getMetaDescription());
		}else{
			model.addAttribute("metaDescription", "JShop - "+product.getName()+" - "+product.getShortDesc());
		}
		model.addAttribute("images", URLUtils.getProductImages(context, product.getId()));
		model.addAttribute("mainImage", URLUtils.getProductImage(context, product.getId()));
		return "product";
	}
	
	@RequestMapping(value = "/tree")
	public String displayCategoryTree(Model model) {
		
		List<Category> data = categoryService.findAllParentCategories();
		
		model.addAttribute("categories",data);
		return "db_test/category_tree";
	}
	
	private void addMenuItems(Model model){
		List<Category> data = categoryService.findAllParentCategories();
		model.addAttribute("menu",data);
	}
	
	private void addBreadCrumb(Category category, List<Category> breadcrumbList){
		breadcrumbList.add(0, category);
		System.out.println("added category "+category.getCategoryName());
		if(category.getParentCategory()!=null){
			addBreadCrumb(category.getParentCategory(), breadcrumbList);
		}
	}
}
