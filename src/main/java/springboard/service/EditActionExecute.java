package springboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBoardDTO;

 
public class EditActionExecute implements IBoardService {
 
	
	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
		
		//커맨드객체를 통해 모든 폼값을 저장한 DTO를 가져온다. 
		HttpServletRequest req = (HttpServletRequest)map.get("req");
		SpringBoardDTO SpringBoardDTO = (SpringBoardDTO)map.get("SpringBoardDTO");

		JDBCTemplateDAO dao = new JDBCTemplateDAO();	
		/*
		//폼값을 개별적으로 받음
		String idx = req.getParameter("idx");				
		String name = req.getParameter("name");
		String title = req.getParameter("title");
		String contents = req.getParameter("contents");
		String pass = req.getParameter("pass");*/
		//dao.edit(idx, name, title, contents, pass);
		
		//DTO객체를 DAO로 전달한다. 
		dao.edit(SpringBoardDTO);
	}
}



