package shop.main.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import shop.main.captcha.ReCaptchaService;
import shop.main.captcha.ReCaptchaServiceImpl;
import shop.main.data.service.CategoryService;
import shop.main.data.service.CategoryServiceImpl;
import shop.main.data.service.ContactUsMessageService;
import shop.main.data.service.ContactUsMessageServiceImpl;
import shop.main.data.service.DiscountService;
import shop.main.data.service.DiscountServiceImpl;
import shop.main.data.service.OptionGroupService;
import shop.main.data.service.OptionGroupServiceImpl;
import shop.main.data.service.OptionService;
import shop.main.data.service.OptionServiceImpl;
import shop.main.data.service.OrderService;
import shop.main.data.service.OrderServiceImpl;
import shop.main.data.service.ProductOptionService;
import shop.main.data.service.ProductOptionServiceImpl;
import shop.main.data.service.ProductService;
import shop.main.data.service.ProductServiceImpl;
import shop.main.data.service.ReviewService;
import shop.main.data.service.ReviewServiceImpl;
import shop.main.data.service.ShippingCostService;
import shop.main.data.service.ShippingCostServiceImpl;
import shop.main.data.service.SitePropertyService;
import shop.main.data.service.SitePropertyServiceImpl;
import shop.main.data.service.StaticPageService;
import shop.main.data.service.StaticPageServiceImpl;
import shop.main.data.service.UserRoleService;
import shop.main.data.service.UserRoleServiceImpl;
import shop.main.data.service.UserService;
import shop.main.data.service.UserServiceImpl;
import shop.main.data.service.WishListService;
import shop.main.data.service.WishListServiceImpl;

@EnableJpaRepositories(basePackages = { "shop.main.data.DAO" })
@EnableTransactionManagement
@Configuration
public class DataConfig<DatabasePopulator> {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSourceMysql() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("jdbc.driverClass"));
		dataSource.setUrl(environment.getProperty("jdbc.url"));
		dataSource.setUsername(environment.getProperty("jdbc.username"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));
		return dataSource;

	}

	// hibernate related beans

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

		// DatabasePopulatorUtils.execute(databasePopulator(),
		// dataSourceMysql());

		return jpaTransactionManager;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {

		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setShowSql(true);
		return jpaVendorAdapter;

	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSourceMysql());
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("shop.main.data.entity");

		Properties jpaProperties = new Properties();
		// jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		jpaProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		jpaProperties.setProperty("hibernate.session_factory.statement_inspector",
				"shop.main.utils.sqltracker.StatementInspectorImpl");
		jpaProperties.setProperty("hibernate.format_sql", "true");
		jpaProperties.setProperty("hibernate.show_sql", "false");
		jpaProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf, DataSource dataSource) {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(emf);
		tm.setDataSource(dataSource);
		return tm;
	}

	// database populator

	// private ResourceDatabasePopulator databasePopulator() {
	//
	// ResourceDatabasePopulator databasePopulator = new
	// ResourceDatabasePopulator();
	// databasePopulator.setContinueOnError(true);
	// databasePopulator.addScript(new
	// ClassPathResource("test-data-populator.sql"));
	// System.out.println("Populated database");
	// return databasePopulator;
	//
	// }

	@Bean
	public ProductService productService() {
		return new ProductServiceImpl();
	}

	@Bean
	public CategoryService categoryService() {
		return new CategoryServiceImpl();
	}

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public UserRoleService userRoleService() {
		return new UserRoleServiceImpl();
	}

	@Bean
	public OptionService optionService() {
		return new OptionServiceImpl();
	}

	@Bean
	public ProductOptionService productOptionService() {
		return new ProductOptionServiceImpl();
	}

	@Bean
	public OptionGroupService optionGroupService() {
		return new OptionGroupServiceImpl();
	}

	@Bean
	public ReviewService reviewService() {
		return new ReviewServiceImpl();
	}

	@Bean
	public SitePropertyService sitePropertyService() {
		return new SitePropertyServiceImpl();
	}

	@Bean
	public StaticPageService staticPageService() {
		return new StaticPageServiceImpl();
	}

	@Bean
	public ReCaptchaService reCaptchaService() {
		return new ReCaptchaServiceImpl();
	}

	@Bean
	public ShippingCostService shippingCostService() {
		return new ShippingCostServiceImpl();
	}

	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public DiscountService discountService() {
		return new DiscountServiceImpl();
	}

	@Bean
	public WishListService wishListService() {
		return new WishListServiceImpl();
	}

	@Bean
	public ContactUsMessageService contactUsMessageService() {
		return new ContactUsMessageServiceImpl();
	}

	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl();
	}

}
