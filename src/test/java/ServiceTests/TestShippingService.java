package ServiceTests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import shop.main.config.AppContextConfig;
import shop.main.data.service.ShippingCostService;
import shop.main.utils.sqltracker.AssertSqlCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppContextConfig.class })
@WebAppConfiguration
@Sql({ "classpath:shippingCost.sql" })
public class TestShippingService {

	private Pageable pageable;
	private final Integer CURRENT = 0;
	private final Integer PAGE_SIZE = 10;
	private final String DUMMY_STRING = "DUMMY_STRING";
	private static final Logger LOGGER = LoggerFactory.getLogger(TestShippingService.class);

	@Before
	public void before() {
		AssertSqlCount.reset();
		pageable = new PageRequest(CURRENT, PAGE_SIZE);
	}

	@Autowired
	@Qualifier("shippingCostService")
	private ShippingCostService service;

	// void saveSize(ParcelSize size);

	// void saveCountry(Country country);
	//
	// void deleteSizeById(Long id);
	//
	// void deleteCountryById(Long id);
	//
	// Country findCountryById(Long id);
	//
	// ParcelSize findSizeById(Long id);
	//
	// List<Country> listAllCountries();
	//
	// List<ParcelSize> listAllSizez();
	//
	// BigDecimal getShippingCost(String countryName, String sizeName);
	//
	// Country getCountryByName(String name);
	//
	// ParcelCost findOneByCountryAndSize(Country country, ParcelSize size);
	//
	// List<Country> listAllCountries(Pageable pageable);
	//
	// List<ParcelSize> listAllSizez(Pageable pageable);
	//
	// long countCountries();
	//
	// long countSizes();

}
