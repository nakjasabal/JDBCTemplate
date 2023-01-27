package springboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBoardDTO;

public class WriteActionExecute implements IBoardService {
	
	@Override
	public void execute(Model model) {
		//Model객체에 저장된 값을 Map컬렉션으로 변환한다. 
		Map<String, Object> paramMap = model.asMap();
		//첫번째로 request내장객체를 가져온다. 
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
		//두번째로 작성페이지에서 전송한 모든 폼값이 저장된 DTO객체를 가져온다. 
		SpringBoardDTO SpringBoardDTO = (SpringBoardDTO)paramMap.get("SpringBoardDTO");		
		System.out.println("SpringBoardDTO.title="+SpringBoardDTO.getTitle());
		
		//DAO 객체 생성 및 쓰기처리를 위해 write()메서드를 호출한다. 
		JDBCTemplateDAO dao = new JDBCTemplateDAO();		
		int affected = dao.write(SpringBoardDTO);
		System.out.println("입력된결과:"+affected);
		//dao.close();		
	}
}
