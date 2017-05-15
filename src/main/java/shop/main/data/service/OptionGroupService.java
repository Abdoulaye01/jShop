package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.OptionGroup;

public interface OptionGroupService {
	void save(OptionGroup optionGroup);
	void delete(OptionGroup optionGroup);
	List<OptionGroup> listAll();	
	void deleteById(Long id);
	OptionGroup fingOptionById(long id);
}
