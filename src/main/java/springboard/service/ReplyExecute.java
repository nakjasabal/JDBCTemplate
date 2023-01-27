package springboard.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBoardDTO;

public class ReplyExecute implements IBoardService {

	@Override
	public void execute(Model model) 
	{
		//Model객체로부터 요청 가져오기
		Map<String, Object> map = model.asMap();
		HttpServletRequest req = (HttpServletRequest)map.get("req");
		
		//일련번호를 파라미터로 받아온다
		String idx = req.getParameter("idx");
		
		//기존 게시물을 가져온다. 
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		SpringBoardDTO dto = dao.view(idx);
		/*
		답변글이므로 제목의 경우 [RE]를 추가하고, 내용에는 [원본글]을 추가한다. 
		특히 내용은 약간의 줄바꿈이 필요하므로 \n\r을 적당히 추가해준다. 
		 */
		dto.setTitle("[RE]"+ dto.getTitle());
		dto.setContents("\n\r\n\r---[원본글]---\n\r"+dto.getContents());
		//수정된 내용을 model객체에 저장한다. 
		model.addAttribute("replyRow", dto);
		//dao.close();
	}
}


