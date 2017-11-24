package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl	implements PurchaseService {

		@Autowired
		@Qualifier("purchaseDaoImpl")
		private PurchaseDao purchaseDao;
		
		
		
		public PurchaseServiceImpl() {
			System.out.println(this.getClass());
		}

		public void addPurchase(Purchase purchase) throws Exception{
			purchaseDao.addPurchase(purchase);
		}
		
		public Purchase getPurchase(int tranNo) throws Exception{
			return purchaseDao.getPurchase(tranNo);
		}
		
		public Purchase getPurchase2(int prodNo) throws Exception{
			return purchaseDao.getPurchase2(prodNo);
		}
		
		public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("search", search);
			map.put("buyerId", buyerId);
			
			List<Purchase> list = purchaseDao.getPurchaseList(map);
			int totalCount = purchaseDao.getTotalCount(search);
			
			map.put("list", list);
			map.put("totalCount", totalCount);
			
			return map;
		}
		
		public void updatePurchase(Purchase purchase) throws Exception{
			purchaseDao.updatePurchase(purchase);
		}
		
		public void updateTranCode(Purchase purchase) throws Exception{
			purchaseDao.updateTranCode(purchase);
		}


		
	}