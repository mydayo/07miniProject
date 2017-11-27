package com.model2.mvc.web.purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;


//==> 회원관리 Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	//setter Method 구현 않음
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping(value = "addPurchaseView",method=RequestMethod.GET)
	public String addPurchaseView(@RequestParam("prodNo") int prodNo,Model model,
			HttpServletRequest request,HttpSession session) throws Exception {

		System.out.println("/addPurchaseView.do 메소드에 진입하였습니다.");
		
		Product product =  productService.getProduct(prodNo); 
		session=request.getSession();
		User user = (User) session.getAttribute("user");
		
		model.addAttribute("product",product);
		model.addAttribute("userId",user.getUserId());
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	

	@RequestMapping(value="addPurchase",method=RequestMethod.POST)
	public String addPurchase( @ModelAttribute("purchase") Purchase purchase,
									@RequestParam(value="buyerId",required=false) String  buyerId ,
									@RequestParam("prodNo") int prodNo, Model model) throws Exception {

		System.out.println("/addPurchase.do 메소드에 진입하였습니다.");

		purchase.setDivyDate((purchase.getDivyDate()).replaceAll("-",""));
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setBuyer(userService.getUser(buyerId));
		purchaseService.addPurchase(purchase);	
		model.addAttribute("purchase",purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping(value="getPurchase",method=RequestMethod.GET)
	public String getPurchase( @RequestParam("tranNo") int tranNo , Model model  ) throws Exception {
		
		System.out.println("/getPurchase.do");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(tranNo);
	
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping(value="listPurchase")
	public String listPurchase(@ModelAttribute(value="search") Search search, 
								Model model, HttpServletRequest request,HttpSession session) throws Exception {

		
		System.out.println("/listPurchase.do 메소드 시작");
		
	
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
		
		session = request.getSession();
		User user = (User)session.getAttribute("user");
		String buyerId = user.getUserId();
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		
		
		model.addAttribute("list",map.get("list"));
		model.addAttribute("resultPage",resultPage);
		model.addAttribute("search",search);

		return "forward:/purchase/listPurchase.jsp";
	}
	
	
	
	@RequestMapping(value="/purchase/updatePurchaseView",method=RequestMethod.GET)
	public String updatePurchaseView( @RequestParam("tranNo") int tranNo , Model model ) throws Exception{

		System.out.println("/updatePurchaseView.do 진입하였습니다.");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		model.addAttribute("purchase", purchase);
		model.addAttribute("tranNo",tranNo);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping(value="/purchase/updatePurchase")
	public String updatePurchase( @ModelAttribute("purchase") Purchase purchase,
			@RequestParam("tranNo") int tranNo, 
			Model model ) throws Exception{

		System.out.println("/updatePurchase.do 진입하였습니다.");
		
		purchaseService.updatePurchase(purchase);

		
		System.out.println("Update된 Purchase정보 : " + purchase);
		
		model.addAttribute("purchase", purchase);
		model.addAttribute("tranNo",tranNo);
		
		return "forward:/purchase/updatePurchase.jsp";
	}
	
	@RequestMapping(value="/purchase/updateTranCode")
	public String updateTranCode(
			@RequestParam("prodNo") int prodNo,
			@RequestParam("proTranCode") String proTranCode, 
			@RequestParam("menu") String menu,
			HttpServletRequest request,
			HttpServletResponse response, 
			@ModelAttribute("search") Search search) throws Exception{

		
		System.out.println("TEST>>>>>>>>>: " +prodNo+"ll"+proTranCode + "ll"+menu);
		
		Product product = productService.getProduct(prodNo);
		System.out.println("productTest :  " +product);
		
		
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		
		System.out.println("1.prodNO" + prodNo );
		System.out.println("2.proTranCode" + proTranCode);
		System.out.println("3.search" + search);
		System.out.println("4.menu " + menu);
		
		System.out.println("purchase Test 2 : " + purchase);
		
		if(proTranCode.equals("01")) {
			purchase.setTranCode("02");
			product.setProTranCode("02");
			purchaseService.updateTranCode( purchase );
		}
		
		if(proTranCode.equals("02")) {
			product.setProTranCode("03");
			purchase.setTranCode("03");
			purchaseService.updateTranCode( purchase );
		}
		
		if(proTranCode.equals("02")) {
			return "forward:/purchase/listPurchase";
		}
		return "forward:/product/listProduct";
	}
}
	
	/*
	 * @RequestMapping("/updateTranCode.do")
	
	public String updateTranCode(@RequestParam("prodNo") int prodNo , 
			@RequestParam("proTranCode") String proTranCode, 
			@RequestParam("menu") String menu) throws Exception{
		
		Product product = productService.getProduct(prodNo);
		Purchase purchase = purchaseService.getPurchase2(prodNo);
		
		if(proTranCode.equals("01")) {
			purchase.setTranCode("02");
			product.setProTranCode("02");
			purchaseService.updateTranCode( purchase );
		}
		
		if(proTranCode.equals("02")) {
			product.setProTranCode("03");
			purchase.setTranCode("03");
			purchaseService.updateTranCode( purchase );
		}
		
		System.out.println("들어올까요오오오오");
		if(proTranCode.equals("02")) {
			return "forward:/listPurchase.do";
		}
		return "forward:/listProduct.do";
	}
	
	*/
	
