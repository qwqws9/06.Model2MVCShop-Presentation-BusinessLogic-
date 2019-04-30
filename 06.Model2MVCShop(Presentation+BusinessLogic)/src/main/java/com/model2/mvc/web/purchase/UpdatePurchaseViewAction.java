package com.model2.mvc.web.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		
		PurchaseService ps = new PurchaseServiceImpl();
		
		Purchase pVo = new Purchase();
		pVo = ps.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		
		request.setAttribute("pVo", pVo);
		
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

	
}
