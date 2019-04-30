package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
public class PurchaseController {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping("/addPurchase.do")
	public String addPurchase(@ModelAttribute("buyVo") Purchase buyVo,HttpServletRequest request) throws Exception {
		
		
		
		buyVo.setBuyer(userService.getUser(request.getParameter("buyerId")));
		buyVo.setPurchaseProd(productService.getProduct(Integer.parseInt(request.getParameter("prodNo"))));
		buyVo.setTranCode("2");
		
		System.out.println("!"+buyVo);
		purchaseService.addPurchase(buyVo);
		
		//purchaseService.updateTranCode(buyVo);
		
		if(Integer.parseInt(request.getParameter("paymentOption")) == 1) {
			buyVo.setPaymentOption("���ݱ���");
		}else {
			buyVo.setPaymentOption("�ſ뱸��");
		}
		
		//request.setAttribute("buyVo", buyVo);
		
		return "forward:/purchase/addPurchase.jsp";
		
	}
	@RequestMapping("/addPurchaseView.do")
	public String addPurchaseView(@RequestParam(value="prodNo") int prodNo,HttpServletRequest request) throws Exception {
		
		//Product pVo = new Product();
		
		//ProductService ps = new ProductServiceImpl();
		
		Product pVo = productService.getProduct(prodNo);
		
		request.setAttribute("pVo", pVo);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	
	@RequestMapping("/listPurchase.do")
	public String listPurchase( @ModelAttribute("search") Search search,
									HttpSession session,
									Model model) throws Exception {
		
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		User uVo = (User)session.getAttribute("user");
		System.out.println("!"+uVo);
		search.setPageSize(pageSize);
		
		
		Map<String, Object> map = purchaseService.getPurchaseList(search,  uVo.getUserId());
		
		Page page	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("search", search);
		model.addAttribute("page", page);
		
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
