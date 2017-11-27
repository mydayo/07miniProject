package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.model2.mvc.service.product.ProductService;

//==> 회원관리 Controller

@Controller
@RequestMapping("/product/*")
public class ProductController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	// setter Method 구현 않음

	public ProductController() {
		System.out.println(this.getClass());
	}

	@Value("#{commonProperties['pageUnit']}")

	int pageUnit;

	@Value("#{commonProperties['pageSize']}")

	int pageSize;
	
	
	@RequestMapping(value="addProduct",method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/addProduct.do 메소드 시작" + product);
		// Business Logic
		product.setManuDate((product.getManuDate()).replaceAll("-", ""));
		productService.addProduct(product);

		return "forward:/product/addProduct.jsp";
	}

	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search, @ModelAttribute("priceList") String priceList,
																		Model model, HttpServletRequest request) throws Exception {

		System.out.println("/listProduct.do 메소드 시작");
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		if(request.getParameter("priceList") != null) {
			 priceList = request.getParameter("priceList");
			}
		
		
		Map<String, Object> map = productService.getProductList(search, priceList);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println(resultPage);

		// Model 과 View 연결
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("menu",request.getParameter("menu"));
		model.addAttribute("listProduct",map.get("listProduct"));
		model.addAttribute("priceList", request.getParameter("priceList"));

		return "forward:/product/listProduct.jsp";
	}

	
	 @RequestMapping(value="getProduct",method=RequestMethod.GET)
	 public String getProduct( @RequestParam("prodNo") int prodNo , Model model, HttpServletRequest request,HttpServletResponse response ) throws Exception {
	 
	 System.out.println("/getProduct.do 메소드에 진입하였습니다. "); 
	 
	 Product product =  productService.getProduct(prodNo); 
	 model.addAttribute("product",product);
	 System.out.println("ProductService에서 가져온 Product 정보 : " + product);
	 
	 String history = null;
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					history = cookie.getValue();
					
				}
			}
		}
		
		history += "," + prodNo;
		
		Cookie cookie =  new Cookie("history",history);
		response.addCookie(cookie);
		
	 
		return "forward:/product/getProduct.jsp";
		}
	 
	 @RequestMapping(value="updateProductView",method=RequestMethod.GET)
		public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		 System.out.println("/updateProductView.do 메소드에 진입하였습니다. "); 
		 
		 Product product =  productService.getProduct(prodNo); 
		 //원래는 ProductService service=new ProductServiceImpl(); 식으로 HAS_A관계를 가지고 있었음
		 //또한, 클래스에다 비즈니스 로직 등을 구현하여 네비게이션 하는 방식이었다. 
		 
		model.addAttribute("product", product);
			
			return "forward:/product/updateProductView.jsp";
		}
		
	 @RequestMapping(value="updateProduct",method=RequestMethod.GET)
		public String updateProduct( @ModelAttribute("product") Product product , Model model ) throws Exception{

			System.out.println("/updateProduct.do에 진입하였습니다.");
			
			
			product.setManuDate((product.getManuDate()).replaceAll("-", ""));
			productService.updateProduct(product);
			product = productService.getProduct(product.getProdNo());
			System.out.println("update된 product 정보는 ? " + product);
			
			model.addAttribute("product",product);		
			
			return "forward:/product/updateProduct.jsp";
		}
	 
}
