package springboard.service;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import springboard.model.JDBCTemplateDAO;
import springboard.model.SpringBoardDTO;
import springboard.util.EnvFileReader;
import springboard.util.PagingUtil;

/*
BbsCommandImpl 인터페이스를 구현했으므로 execute()는 반드시 오버라이딩
해야한다. 또한 해당 객체는 부모타입인 BbsCommandImpl 객체로 참조할 수 있다.  
 */
public class ListExecute implements IBoardService {

	@Override
	public void execute(Model model) {
		
		System.out.println("ListCommand > execute() 호출");
	
		/*
		컨트롤러에서 인자로 인달한 Model 객체에는 request객체가 저장되어있다. 
		asMap() 메서드를 통해 Map컬렉션으로 변환한 후 모든 요청을 얻어온다. 
		 */
		Map<String, Object> paramMap = model.asMap();
		/*
		Model객체에 저장될때 Object타입으로 저장되므로, 사용을 위해 원래의
		타입으로 형변환 해야한다. Model객체는 4가지 영역과 동일한 특성을 가진다. 
		 */
		HttpServletRequest req = (HttpServletRequest)paramMap.get("req");
 
		//DAO객체 생성
		JDBCTemplateDAO dao = new JDBCTemplateDAO();
		
		//검색어 처리
		String addQueryString = "";
		//request내장객체를 통해 파라미터를 받아온다. 
		String searchColumn = req.getParameter("searchColumn");				
		String searchWord = req.getParameter("searchWord");
		/*
		list.do					  => searchWord가 null인 상태
		list.do?searchWord=		  => 빈값인 상태
		list.do?searchWord=노트북   => '노트북'이라는 값을 가진 상태
		*/
		//만약 검색어가 있다면...(null이 아니고 동시에 빈값도 아닐때)
		if(searchWord!=null && searchWord!=""){	
			//쿼리스트링 형태의 문자열을 생성한다. 
			addQueryString = String.format("searchColumn=%s"
				+"&searchWord=%s&", searchColumn, searchWord);
			//Map컬렉션에 2개의 폼값을 저장한다. 
			paramMap.put("Column", searchColumn);
			paramMap.put("Word", searchWord);
		}

		//전체 게시물 갯수 카운트
		int totalRecordCount = dao.getTotalCount(paramMap);
		
		//페이징 관련 기능
		/***페이징 추가 코드 S********************/
		//Environment객체를 통한 properties파일을 읽어온다. 
		int pageSize = Integer.parseInt(
				EnvFileReader.getValue("SpringBbsInit.properties", 
						"springBoard.pageSize"));
		int blockPage = Integer.parseInt(
				EnvFileReader.getValue("SpringBbsInit.properties", 
						"springBoard.blockPage"));
		//전체 페이지수를 계산		
		int totalPage = (int)Math.ceil((double)totalRecordCount/pageSize);
		//현제페이지번호. 첫 진입일때는 무조건 1페이지로 지정
		int nowPage = req.getParameter("nowPage")==null ? 1 :
			Integer.parseInt(req.getParameter("nowPage"));
		//리스트에 출력할 게시물의 구간을 계산(select절의 between에 사용)
		int start = (nowPage-1) * pageSize + 1;
		int end = nowPage * pageSize;

		paramMap.put("start", start);
		paramMap.put("end", end);			
		/***페이징 추가 코드 E********************/
		
				
		//실제 출력할 게시물을 select한 후 반환받음(페이징 X)		
		//ArrayList<SpringBoardDTO> listRows = dao.list(paramMap);
				
		//페이징 적용된 쿼리문을 통한 select(페이징O)
		ArrayList<SpringBoardDTO> listRows = dao.listPage(paramMap);
		
		
				
		//출력할 게시물에 가상번호 추가
		int virtualNum = 0;
		int countNum = 0;
		//DAO에서 반환된 List컬렉션을 반복하여 데이터를 가공한다. 
		for(SpringBoardDTO row : listRows) {
			//전체 게시물의 갯수를 하나씩 차감하여 가상번호를 부여한다. 
			//virtualNum = totalRecordCount --;
			
			/***가상번호계산 추가코드S***************************/
			virtualNum = totalRecordCount
					- (((nowPage-1)*pageSize) + countNum++);
			/***가상번호계산 추가코드E***************************/
			
			//가상번호를 setter를 통해 DTO에 저장한다. 
			row.setVirtualNum(virtualNum); 		
			

			//답변글 출력시의 제목 처리부분
			String reSpace = "";
			if(row.getBindent() > 0) {
				for(int i=0 ; i<row.getBindent() ; i++) {
					reSpace += "&nbsp;&nbsp;";
				}
				row.setTitle(reSpace 
						+ "<img src='../images/re3.gif'>" 
						+ row.getTitle());
			}
		}	
		
		//모델에 저장한다. 
		model.addAttribute("listRows", listRows);
		
		//컨트롤러에서 View의 경로를 반환한다. 
		
		/***페이징 처리 코드S******************/
		String pagingImg = PagingUtil.pagingImg(totalRecordCount,
				pageSize, blockPage, nowPage,
				req.getContextPath()+"/board/list.do?"+addQueryString);
		model.addAttribute("pagingImg", pagingImg);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("nowPage", nowPage);
		/***페이징 처리 코드E******************/
		//검색이 있는경우 쿼리스트링을 View로 전달한다. 
		model.addAttribute("addQueryString", addQueryString);
		
		/*
		JdbcTemplate을 사용할때는 자원반납은 하지않는다.
		스피링 컨테이너가 시작될때 자동으로 연결되는 부분이므로 자원이 반납되면
		다시 연결할 수 없기 때문이다. 스프링 컨테이너가 알아서 자원관리를 해준다.
		 */
		//dao.close();
	}
}

