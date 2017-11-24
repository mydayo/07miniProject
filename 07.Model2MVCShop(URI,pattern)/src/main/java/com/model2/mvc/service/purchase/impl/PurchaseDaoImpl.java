package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;



@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{

	///Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	///Constructor
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}
	
	
	

	@Override
	public Purchase getPurchase(int tranNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getPurchase",tranNo);
	}

	@Override
	public Purchase getPurchase2(int prodNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getPurchase2",prodNo);
	}

	@Override
	public List<Purchase> getPurchaseList(Map<String,Object> map) {
		return sqlSession.selectList("PurchaseMapper.getPurchaseList",map);
	}
	
	@Override
	public void addPurchase(Purchase purchase) {
		sqlSession.insert("PurchaseMapper.addPurchase",purchase);
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updatePurchase",purchase);		
	}

	@Override
	public void updateTranCode(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updateTranCode",purchase);
	}

	@Override
	public int getTotalCount(Search search) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
	}	
}

