package springboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBoardDTO;

public class ViewExecute implements IBoardService {
	
	@Override
	public void execute(Model model) {
		
		//Model에 저장된 값을 Map컬렉션으로 변환한 후 가져온다. 
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
		
		//request내장객체를 통해 전달된 파라미터를 가져온다. 
		String idx = req.getParameter("idx");
		String nowPage = req.getParameter("nowPage");
		
		//DAO객체 생성 및 게시물 가져오기
		JDBCTemplateDAO dao = new JDBCTemplateDAO();					
		SpringBoardDTO dto = new SpringBoardDTO();
		dto = dao.view(idx);
 
		//내용은 줄바꿈 처리를 위해 replace()를 사용한다. 
		dto.setContents(dto.getContents().replace("\r\n", "<br/>"));
		model.addAttribute("viewRow", dto);
		model.addAttribute("nowPage", nowPage);
	}
}
