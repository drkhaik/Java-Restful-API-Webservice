package com.in28minutes.rest.webservices.restful_webservice.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	private MappingJacksonValue createMappingJacksonValue(Object value, String... fields) {
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
		
		SimpleBeanPropertyFilter filter = 
				SimpleBeanPropertyFilter.filterOutAllExcept(fields);
		
		FilterProvider filters = 
				new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
		
	}
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		SomeBean someBean = new SomeBean("Valu1", "value2", "value3");
		
		MappingJacksonValue mappingJacksonValue = createMappingJacksonValue(someBean, "field1", "field2");
		
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		
		List<SomeBean> asSomeBeanList = Arrays.asList(new SomeBean("Valu1", "value2", "value3"),
				new SomeBean("Value4", "value5", "value6"));
		
		MappingJacksonValue mappingJacksonValue = createMappingJacksonValue(asSomeBeanList, "field1", "field3");
		
		
		return mappingJacksonValue;
	}
}
