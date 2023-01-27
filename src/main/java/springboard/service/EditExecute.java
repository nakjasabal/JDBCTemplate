package springboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBoardDTO;

public class EditExecute implements IBoardService {
	
	@Override
	public void execute(Model model) {
	
		//Model에 저장된 request내장객체를 얻어온다. 
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
		
		//일련번호를 파라미터로 받은 후
		String idx = req.getParameter("idx");
	 
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		
		//기존의 게시물을 얻어온다.
		SpringBoardDTO dto = dao.view(idx);
		model.addAttribute("viewRow", dto);
		//dao.close();
	}
}





