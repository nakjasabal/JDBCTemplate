package springboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;

public class PasswordActionExecute implements IBoardService {
	
	@Override
	public void execute(Model model) {
		
		//Model에 저장된 값을 Map컬렉션으로 변환한 후 가져온다. 
		Map<String, Object> paramMap = model.asMap();
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
			
		String mode = req.getParameter("mode");
		String idx = req.getParameter("idx");
		String nowPage = req.getParameter("nowPage");
		String pass = req.getParameter("pass");
		
		//DAO에서 일련번호와 패스워드를 통해 검증
		JDBCTemplateDAO dao = new JDBCTemplateDAO();		
		int existIdx = dao.password(idx, pass);
		
		model.addAttribute("existIdx", existIdx);
	}
}
