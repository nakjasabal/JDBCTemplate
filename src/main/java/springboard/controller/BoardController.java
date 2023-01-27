package springboard.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springboard.model.JdbcTemplateConst;
import springboard.model.SpringBoardDTO;
import springboard.service.DeleteActionExecute;
import springboard.service.EditActionExecute;
import springboard.service.EditExecute;
import springboard.service.IBoardService;
import springboard.service.ListExecute;
import springboard.service.PasswordActionExecute;
import springboard.service.ReplyActionExecute;
import springboard.service.ReplyExecute;
import springboard.service.ViewExecute;
import springboard.service.WriteActionExecute;
 
/*
기본패키지로 설정한 곳에 컨트롤러를 선언하면 요청이 들어왔을때 
Auto scan된다. 이를 통해 요청을 전달할 메서드를 찾는다. 해당 설정은
servlet-context.xml에서 추가한다.  
 */
@Controller
public class BoardController {
	
	private JdbcTemplate template; 
	@Autowired 
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		System.out.println("@Autowired=>JdbcTemplate 연결성공");
		/*
		template을 sevice객체에서 사용하기 위해 static으로 선언한 변수에
		할당한다. static 변수는 프로그램 시작시에 로딩되어 언제든 접근할 수 있는
		특징이 있다. 
		 */
		JdbcTemplateConst.template = this.template;
	}
	
	/*
	멤버변수로 선언하여 클래스에서 전역적으로 사용할 수 있다. 해당 클래스의 
	모든 Command(서비스)객체는 해당 인터페이스를 구현하여 정의한다. 
	 */
	IBoardService service = null;	
	 
	@RequestMapping("/board/list.do")
	public String list(Model model, HttpServletRequest req) {
		/*
		사용자로부터 받은 모든 요청은 request객체에 저장되고, 이를 
		ListCommand객체로 전달하기 위해 Model객체에 저장한 후 매개변수로
		전달한다. 
		 */
		//request객체 자체를 Model에 저장한다. 
		model.addAttribute("req", req);
		//Service객체인 ListCommand를 생성한다. 
		service = new ListExecute();
		//Service객체로 Model을 전달한다. 
		service.execute(model);
		
		return "07Board/list";
	}

	//글쓰기 페이지로 진입하기 위한 매핑처리	
	@RequestMapping("/board/write.do")
	public String write(Model model) {
		return "07Board/write";
	}
	
	//전송방식이 post  이므로 value, method까지 같이 기술해서 매핑한다.
	@RequestMapping(value="/board/writeAction.do", method=RequestMethod.POST)
	public String writeAction(Model model, HttpServletRequest req, 
			SpringBoardDTO SpringBoardDTO) {
		/*
		글쓰기 페이지에서 전송된 모든 폼값은 SpringBoardDTO객체를 통해 한번에
		전송 받을 수 있다. 이와같은 클래스를 커맨드객체라고 표현한다. 
		 */
		//request, DTO객체를 Model에 저장
		model.addAttribute("req", req);
		model.addAttribute("SpringBoardDTO", SpringBoardDTO);
		//Service객체 생성 후 매개변수로 전달
		service = new WriteActionExecute();
		service.execute(model);
		
		//View를 반환하지 않고, 지정된 URL(요청명)로 이동한다. 
		return "redirect:list.do?nowPage=1";
	}
	
	//글 내용보기
	@RequestMapping("/board/view.do")
	public String view(Model model, HttpServletRequest req)
	{
		//사용자의 요청을 저장한 request객체를 Model객체에 저장한 후 전달한다. 
		model.addAttribute("req", req);
		service = new ViewExecute();
		service.execute(model);

		return "07Board/view";
	}
	
	//패스워드 검증 페이지 매핑	
	@RequestMapping("/board/password.do")
	public String password(Model model,	HttpServletRequest req)
	{
		//일련번호는 request를 통해 전달받은후 Model에 저장
		model.addAttribute("idx", req.getParameter("idx"));
		return "07Board/password";
	}
	
	//패스워드 검증 후 수정 혹은 삭제 처리
	@RequestMapping("/board/passwordAction.do")
	public String passwordAction(Model model, HttpServletRequest req)
	{		
		model.addAttribute("req", req);		
		service = new PasswordActionExecute();
		service.execute(model);
		
		Map<String, Object> paramMap = model.asMap();
		int existIdx = (Integer)paramMap.get("existIdx");
		System.out.println("existIdx="+ existIdx);
		
		String mode = req.getParameter("mode");
		String idx = req.getParameter("idx");
		String modePage = null;
		if(existIdx<=0) {
			//패스워드 검증 실패시에는 이전페이지로 돌아간다. 
			model.addAttribute("isCorrMsg", "패스워드가 일치하지 않습니다");
			model.addAttribute("idx", idx);
			
			//패스워드 검증 페이지를 반환한다. 
			modePage = "07Board/password";
		}
		else {
			//검증에 성공한 경우 수정 혹은 삭제 처리를 한다. 
			System.out.println("검증완료");
			
			if(mode.equals("edit")){
				/*
				mode가 수정인 경우 수정페이지로 이동한다. 
				 */
				model.addAttribute("req", req);
				service = new EditExecute();
				service.execute(model);

				modePage = "07Board/edit";
			}
			else if(mode.equals("delete")){
				//mode가 delete인 경우 즉시 삭제 처리
				model.addAttribute("req", req);
				service = new DeleteActionExecute();
				service.execute(model);
				
				//삭제 후에는 리스트페이지로 이동한다. 
				model.addAttribute("nowPage", req.getParameter("nowPage"));
				modePage = "redirect:list.do";			
			}
		} 

		return modePage;
	}	
	
	//수정 처리
	@RequestMapping("/board/editAction.do")
	public String editAction(HttpServletRequest req, Model model, 
			SpringBoardDTO SpringBoardDTO){
		/*
		request내장객체와 수정페이지에서 전송한 모든 폼값을 저장한 DTO객체를 
		Model에 저장한후 서비스 객체로 전달한다. 
		 */
		model.addAttribute("req", req);
		model.addAttribute("SpringBoardDTO", SpringBoardDTO);
		service = new EditActionExecute();
		service.execute(model);
		
		/*
		수정처리가 완료되면 상세페이지로 이동하게 되는데 이때 idx와 같은
		파라미터가 필요하다. Model객체에 저장한 후 redirect하면 자동으로
		쿼리스트링 형태로 만들어 준다. 
		 */
		model.addAttribute("idx", req.getParameter("idx"));
		model.addAttribute("nowPage", req.getParameter("nowPage"));
		return "redirect:view.do";
	}

	//답변글 작성폼
	@RequestMapping("/board/reply.do")
	public String reply(HttpServletRequest req, Model model){

		model.addAttribute("req", req);
		service = new ReplyExecute();
		service.execute(model);

		model.addAttribute("idx", req.getParameter("idx"));
		return "07Board/reply";
	}
	
	//답변글 쓰기 처리
	@RequestMapping("/board/replyAction.do")
	public String replyAction(HttpServletRequest req, Model model, 
			SpringBoardDTO SpringBoardDTO){
		
		model.addAttribute("SpringBoardDTO", SpringBoardDTO);
		model.addAttribute("req", req);
		service = new ReplyActionExecute();
		service.execute(model);

		model.addAttribute("nowPage", req.getParameter("nowPage"));
		return "redirect:list.do";
	} 
}
