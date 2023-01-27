package springboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBoardDTO;

public class ReplyActionExecute implements IBoardService {
	
	@Override
	public void execute(Model model) {
		
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
		SpringBoardDTO dto = (SpringBoardDTO)paramMap.get("SpringBoardDTO");
				 
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		dao.reply(dto);				
		//dao.close();
	}
}