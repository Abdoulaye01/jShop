package shop.main.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import shop.main.data.mongo.Order;
import shop.main.data.mongo.OrderProduct;
import shop.main.data.mongo.OrderRepository;

@Controller
public class MongoController {

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value = "/mongo")
	public ModelAndView displayOrders(Principal principal) {

//		orderRepository.deleteAll();
//
//		Order order = new Order();	
//		
//		order.setSum(new BigDecimal(335+395));
//		String orderCount = String.valueOf(orderRepository.findAll().size());
//		if(orderCount.length()<8){
//			orderCount="0"+orderCount;
//		}
//		order.setNumber(Calendar.getInstance().get(Calendar.YEAR)+orderCount);
//		
//		OrderProduct prod1= new OrderProduct("Lovely thing", new BigDecimal(33.5), 10, "987654L");
//		prod1.setProductId(987654L);		
//		OrderProduct prod2= new OrderProduct("Pink thing", new BigDecimal(39.5), 10,"8765490L");
//		prod2.setProductId(8765490L);
//
//		Map<String, OrderProduct> products = new HashMap<String, OrderProduct>();
//		products.put(prod1.getProduct_SKU(), prod1);
//		products.put(prod2.getProduct_SKU(), prod2);
//		order.setProduct_list(products);		
//		
//        orderRepository.save(order);
        List<Order> data = orderRepository.findAll();        
				
		return new ModelAndView("db_test/order_test", "orders", data);
	}
}
