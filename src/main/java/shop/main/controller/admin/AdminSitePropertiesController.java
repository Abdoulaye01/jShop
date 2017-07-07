package shop.main.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import shop.main.data.entity.Block;
import shop.main.data.entity.MenuItem;
import shop.main.data.entity.SitePropertiesWrapper;
import shop.main.data.entity.StaticPage;
import shop.main.data.service.BlockService;
import shop.main.data.service.MenuItemService;
import shop.main.data.service.SitePropertyService;
import shop.main.data.service.StaticPageService;
import shop.main.utils.Constants;
import shop.main.utils.URLUtils;

@Controller
public class AdminSitePropertiesController {

	@Autowired
	private MenuItemService menuService;

	@Autowired
	private BlockService blockService;

	@Autowired
	private SitePropertyService sitePropertyService;

	@Autowired
	private StaticPageService staticPageService;

	@Autowired
	ServletContext context;

	/********* Menu Items pages ***/

	@RequestMapping(value = "/a/menu")
	public String menuList(Model model) {

		model.addAttribute("menuItemList", menuService.listAll());
		return "../admin/menu/menu_items";
	}

	@RequestMapping(value = "/a/menu", method = RequestMethod.POST)
	public String saveMenuItem(@ModelAttribute("menuItem") @Valid MenuItem item, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/menu/edit_menu";
		} else {
			if (item.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}

			menuService.save(item);
			return "redirect:/a/menu";
		}

	}

	@RequestMapping(value = "/a/menu/add", method = RequestMethod.GET)
	public String addMenuItem(Model model) {
		// TODO add menuTypeList
		model.addAttribute("menuItem", new MenuItem());
		model.addAttribute("menuTypeList", getMenuTypes());
		return "../admin/menu/edit_menu";
	}

	@RequestMapping(value = "/a/menu/{id}/update", method = RequestMethod.GET)
	public String editMenuIten(@PathVariable("id") long id, Model model) {

		model.addAttribute("menuItem", menuService.findById(id));
		model.addAttribute("menuTypeList", getMenuTypes());

		return "../admin/menu/edit_menu";
	}

	@RequestMapping(value = "/a/menu/{id}/delete", method = RequestMethod.GET)
	public String deleteMenuItem(@PathVariable("id") long id, Model model,
			final RedirectAttributes redirectAttributes) {
		menuService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");

		return "redirect:/a/menu";
	}

	private String[] getMenuTypes() {

		return Constants.menuTypes;
	}

	/************* Blocks ***/
	@RequestMapping(value = "/a/blocks")
	public String blockList(Model model) {

		model.addAttribute("blockList", blockService.listAll());
		return "../admin/blocks/blocks";
	}

	@RequestMapping(value = "/a/block", method = RequestMethod.POST)
	public String saveBlock(@ModelAttribute("block") @Valid Block block, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			return "../admin/blocks/edit_block";
		} else {
			if (block.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Item added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Item updated successfully!");
			}

			blockService.save(block);
			return "redirect:/a/blocks";
		}

	}

	@RequestMapping(value = "/a/block/add", method = RequestMethod.GET)
	public String addBlock(Model model) {
		// TODO add menuTypeList
		model.addAttribute("block", new Block());
		model.addAttribute("blockTypeList", getBlockTypes());
		return "../admin/blocks/edit_block";
	}

	@RequestMapping(value = "/a/block/{id}/update", method = RequestMethod.GET)
	public String editBlock(@PathVariable("id") long id, Model model) {

		model.addAttribute("block", blockService.findById(id));
		model.addAttribute("blockTypeList", getBlockTypes());

		return "../admin/blocks/edit_block";
	}

	@RequestMapping(value = "/a/block/{id}/delete", method = RequestMethod.GET)
	public String deleteBlock(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		blockService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Item deleted successfully!");

		return "redirect:/a/blocks";
	}

	private String[] getBlockTypes() {

		// return Constants.blockTypes;
		return Stream.of(Constants.BlockType.values()).map(Constants.BlockType::name).toArray(String[]::new);

	}

	/** Properties **/
	@RequestMapping(value = "/a/mainpage", method = RequestMethod.GET)
	public String editMainPage(Model model) {

		model.addAttribute("images", URLUtils.getMinPageImages(context));

		return "../admin/mainpageProperties";
	}

	@RequestMapping(value = "/a/properties", method = RequestMethod.GET)
	public String propertiesList(Model model) {

		model.addAttribute("propertyWrapper", new SitePropertiesWrapper(sitePropertyService.listAll()));
		return "../admin/properties";
	}

	@RequestMapping(value = "/a/properties", method = RequestMethod.POST)
	public String propertiesSave(@ModelAttribute("propertyWrapper") SitePropertiesWrapper propertyWrapper, Model model,
			BindingResult result, final RedirectAttributes redirectAttributes) {

		System.out.println("*");
		System.out.println("*");
		System.out.println(propertyWrapper.getPropertyList().size());
		System.out.println("*");

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			model.addAttribute("propertyList", sitePropertyService.listAll());
			return "../admin/properties";
		} else {
			redirectAttributes.addFlashAttribute("flashMessage", "Properties updated successfully!");
			propertyWrapper.getPropertyList().stream().forEach(p -> {
				System.out.println("*");
				System.out.println("*");
				System.out.println(p.getName() + " " + p.getContent() + " " + p.getId());
				System.out.println("*");
				sitePropertyService.save(p);
			});
			return "redirect:/a/properties";
		}
	}

	/* **********************Static pages ****** */
	@RequestMapping(value = "/a/pages")
	public String pagesList(Model model) {

		model.addAttribute("pagesList", staticPageService.listAll());
		return "../admin/staticPages/pages";
	}

	@RequestMapping(value = "/a/page", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("page") @Valid StaticPage page, Model model, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("errorSummary", result.getFieldErrors().stream()
					.map(e -> e.getField() + " error - " + e.getDefaultMessage() + " ").collect(Collectors.toList()));
			redirectAttributes.addFlashAttribute("errorMessage", "URL is not unique!");

			return "../admin/staticPages/edit_page";
		} else if (page.isNew() && !staticPageService.checkUniqueURL(page)) {
			model.addAttribute("errorSummary", new ArrayList<String>(Arrays.asList("URL is not unique!")));
			model.addAttribute("urlError", "has-error");
			return "../admin/staticPages/edit_page";
		} else {
			if (page.isNew()) {
				redirectAttributes.addFlashAttribute("flashMessage", "Category added successfully!");
			} else {
				redirectAttributes.addFlashAttribute("flashMessage", "Category updated successfully!");
			}

			staticPageService.save(page);
			return "redirect:/a/pages";
		}

	}

	@RequestMapping(value = "/a/page/add", method = RequestMethod.GET)
	public String addPage(Model model) {

		model.addAttribute("page", new StaticPage());
		model.addAttribute("urlError", "");
		return "../admin/staticPages/edit_page";
	}

	@RequestMapping(value = "/a/page/{id}/update", method = RequestMethod.GET)
	public String editPage(@PathVariable("id") long id, Model model) {

		model.addAttribute("page", staticPageService.findById(id));
		model.addAttribute("urlError", "");

		return "../admin/staticPages/edit_page";
	}

	@RequestMapping(value = "/a/page/{id}/delete", method = RequestMethod.GET)
	public String deletePage(@PathVariable("id") long id, Model model, final RedirectAttributes redirectAttributes) {
		staticPageService.deleteById(id);
		redirectAttributes.addFlashAttribute("flashMessage", "Category deleted successfully!");
		return "redirect:/a/pages";
	}

}