package com.javadevelop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.javadevelop.converter.NewConverter;
import com.javadevelop.dto.NewDTO;
import com.javadevelop.entity.CategoryEntity;
import com.javadevelop.entity.NewEntity;
import com.javadevelop.repository.CategoryRepository;
import com.javadevelop.repository.NewRepository;
import com.javadevelop.service.INewService;


@Service
public class NewService implements INewService {

	@Autowired
	private NewRepository newRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private NewConverter newConverter;

	@Override
	public List<NewDTO> findAll() {
		NewDTO newDTO = new NewDTO();
		List<NewDTO> arrList = new ArrayList<>();
		for(NewEntity newEntity : newRepository.findAll()) {
			newDTO = newConverter.toNewDTO(newEntity);
			arrList.add(newDTO);
		}
//		newDTO = newConverter.toNewDTO(newRepository.findAll());
//		newDTO.setListResult(arrList);
//		return newDTO.getListResult();	// Trả về list trong một NewDTO (vì NewDTO exten BaseDTO nên mỗi NewDTO đều có thuộc tính list) => vòng lập vô hạn
		return arrList; // Trả về list chứa các NewDTO
	}
	
	@Override
	public NewDTO save(NewDTO newDTO) {

		NewEntity newEntity = new NewEntity();
		if (newDTO.getId() == null) {
			newEntity = newConverter.toNewEntity(newDTO);
		} else {
			NewEntity oldNewEntity = newRepository.findOne(newDTO.getId());
			newEntity = newConverter.toNewEntity(newDTO, oldNewEntity);
		}
		CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
		newEntity.setCategory(categoryEntity);
		newEntity = newRepository.save(newEntity);
		return newConverter.toNewDTO(newEntity);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			newRepository.delete(item);
		}
	}

	@Override
	public List<NewDTO> findAll(Pageable pageable) {
		NewDTO newDTO = new NewDTO();
		List<NewDTO> arrList = new ArrayList<>();
		for(NewEntity newEntity : newRepository.findAll(pageable)) {
			newDTO = newConverter.toNewDTO(newEntity);
//			newDTO.setPage(pageable.getPageNumber() + 1);
			arrList.add(newDTO);
		}
		return arrList;
	}

	@Override
	public int totalItem() {
		return (int) newRepository.count();
	}
}
