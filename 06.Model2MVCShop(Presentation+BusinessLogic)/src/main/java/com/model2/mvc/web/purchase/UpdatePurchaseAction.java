package com.model2.mvc.web.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Purchase pVo = new Purchase();
		
		User uVo = new User();
		
		uVo.setUserName(request.getParameter("userName"));
		uVo.setPhone(request.getParameter("userPhone"));
		uVo.setAddr(request.getParameter("userAddr"));
		
		pVo.setBuyer(uVo);
		pVo.setTranNo(Integer.parseInt(request.getParameter("hidden")));
		pVo.setPaymentOption(request.getParameter("paymentOption"));
		pVo.setDivyDate(request.getParameter("delDate"));
		pVo.setDivyRequest(request.getParameter("userReq"));
		
		PurchaseService ps = new PurchaseServiceImpl();
		
		ps.updatePurcahse(pVo);
		
		
		
		
		return "redirect:/getPurchase.do?tranNo="+request.getParameter("hidden");
	}

}
