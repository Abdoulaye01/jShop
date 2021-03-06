package shop.main.controller.admin;

import static shop.main.controller.admin.AdminController.ADMIN_PREFIX;
import static shop.main.controller.admin.AdminController.MANAGER_PREFIX;

import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.Country;
import shop.main.data.mongo.Order;
import shop.main.data.service.OrderService;
import shop.main.data.service.ShippingCostService;
import shop.main.utils.Constants;

@Controller
@RequestMapping(value = { ADMIN_PREFIX, MANAGER_PREFIX })
public class AdminOrderController extends AdminController {

	@Autowired
	public OrderService orderService;

	@Autowired
	ServletContext context;

	@Autowired
	private ShippingCostService shippingCostService;

	@RequestMapping(value = "/orders")
	public String ordersList(Model model) {

		loadTableData("", "", "", 1, PAGE_SIZE, model);
		return "../admin/orders/orders";
	}

	@RequestMapping(value = "/findOrder", method = RequestMethod.POST)
	public String findOrder(@RequestParam String fullname, @RequestParam String phone, @RequestParam String email,
			Integer current, Integer pageSize, Model model) {
		loadTableData(fullname, phone, email, current, pageSize, model);
		return "../admin/orders/_table";

	}

	private void loadTableData(String fullname, String phone, String email, Integer current, Integer pageSize,
			Model model) {
		Pageable pageable = new PageRequest(current - 1, pageSize);

		model.addAttribute("orders", orderService.filter(fullname, phone, email, pageable));
		model.addAttribute("current", current);
		model.addAttribute("pageSize", pageSize);
		addPaginator(model, current, pageSize, orderService.count(fullname, phone, email));
	}

	@RequestMapping(value = "/order/{id}/update", method = RequestMethod.GET)
	public String editOrder(@PathVariable("id") String id, Model model) {
		Order order = orderService.findOne(id);
		model.addAttribute("order", order);
		model.addAttribute("countryList", Constants.getCountryList());

		return "../admin/orders/edit_order";
	}

	@RequestMapping(value = "/order/{id}/delete", method = RequestMethod.GET)
	public String deleteOrder(@PathVariable("id") String id, Model model, final RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		orderService.delete(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Order deleted successfully!");
		return "redirect:" + getUrlPrefix(request) + "orders";
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String saveOrder(@ModelAttribute("order") @Valid Order order, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/orders/edit_order";
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "Order updated successfully!");
			Order prevOrder = orderService.findOne(order.getOrderId());
			order.setDate(prevOrder.getDate());
			if (order.getDate() == null) {
				order.setDate(new Date());
			}
			order.setProduct_list(prevOrder.getProduct_list());
			Country country = shippingCostService.getCountryByName(order.getCountry());
			order.calculateShipping(country);
			orderService.save(order);

			return "redirect:" + getUrlPrefix(request) + "orders";
		}

	}
}
