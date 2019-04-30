package com.model2.mvc.web.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		Purchase pVo = new Purchase();
		System.out.println(request.getParameter("prodNo") + "상품번호");
		System.out.println(request.getParameter("code") + " 트랜코드");
		
		
		PurchaseService ps = new PurchaseServiceImpl();
		
		pVo = ps.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));
		pVo.setTranCode(request.getParameter("code"));
		
		ps.updateTranCode(pVo);
		
		HttpSession session = request.getSession();
		
		User uVo = new User();
		uVo = (User)session.getAttribute("user");
		
		if(uVo.getRole().equals("user")) {
			return "redirect:/listPurchase.do";
		}
		
		
		return "redirect:/listProduct.do?menu=manage";
	}

}
