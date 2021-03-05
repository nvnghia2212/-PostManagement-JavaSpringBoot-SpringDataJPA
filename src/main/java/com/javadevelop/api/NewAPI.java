package com.javadevelop.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javadevelop.dto.BaseDTO;
import com.javadevelop.dto.NewDTO;
import com.javadevelop.service.INewService;

@RestController
public class NewAPI {
	
	@Autowired
	private INewService newService;
	
//	@GetMapping(value = "/new")
//	public List<NewDTO> getNew(){
//		return newService.findAll();
//	}
	
	@GetMapping(value = "/new")
	public BaseDTO<NewDTO> getNew(@RequestParam(value = "page", required = false) Integer page, 
								@RequestParam(value = "iteminpage", required = false) Integer iteminpage ){
		BaseDTO<NewDTO> newBaseDTO = new BaseDTO<>();
		if (page != null) {
			newBaseDTO.setPage(page);
			newBaseDTO.setTotalPage((int)Math.ceil(newService.totalItem()/iteminpage));
			Pageable pageable = new PageRequest(page - 1, iteminpage);
			newBaseDTO.setListResult(newService.findAll(pageable));
		}else {
			newBaseDTO.setListResult(newService.findAll());
		}
		return newBaseDTO;
	}
	
	@PostMapping(value="/new")
	public NewDTO createNew(@RequestBody NewDTO model ) {
		return newService.save(model);
	}
	
	@PutMapping(value="/new/{id}")
	public NewDTO insertNew(@RequestBody NewDTO model, @PathVariable("id") long id ) {
		model.setId(id);
		return newService.save(model);
	}
	
	@DeleteMapping(value="/new")
	public void deleteNew(@RequestBody long[] ids ) {
		newService.delete(ids);
	}
}
