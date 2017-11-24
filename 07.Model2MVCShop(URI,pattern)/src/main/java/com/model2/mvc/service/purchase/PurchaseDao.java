package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;




public interface PurchaseDao {
	
	public Purchase getPurchase(int tranNo) ;

	public Purchase getPurchase2(int prodNo);
	
	public List<Purchase> getPurchaseList(Map<String,Object> map);

	public void addPurchase(Purchase purchase);

	public void updatePurchase(Purchase purchase);

	public void updateTranCode(Purchase purchase);

	public  int getTotalCount(Search search);
	
}

