package com.javadevelop.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.javadevelop.dto.NewDTO;

public interface INewService {

	List<NewDTO> findAll();
	NewDTO save(NewDTO newDTO);
	void delete(long[] ids);
	
	List<NewDTO> findAll(Pageable pageable);
	int totalItem();
}
