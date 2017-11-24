package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.domain.Product;


@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{

	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;

	///Constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}

	///Method
	@Override
	public void addProduct(Product product) throws Exception {
		productDao.addProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDao.getProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search, String priceList) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("search", search);
		map.put("priceList", priceList);
		
		List<Product> listProduct = productDao.getProductList(map);
		int totalCount = productDao.getTotalCount(search);
		
		map.put("listProduct", listProduct);
		map.put("totalCount", totalCount);
		//Map<String,Object> mapProd = new HashMap<String,Object>();
		//mapProd.put("list", listProduct);
		//mapProd.put("totalCount",totalCount);
		
		return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}


}