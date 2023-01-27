package springboard.model;

public class SpringBoardDTO {
	//springboard 테이블의 컬럼과 동일한 멤버변수 선언
    private int idx;
    private String name;
    private String title;
    private String contents;
    private java.sql.Date postdate;
    private int hits;//게시물 조회수
    private int bgroup;//답변글 처리를 위한 컬럼(게시물을 그룹화)
    private int bstep;//원본글과 답변글의 정렬
    private int bindent;//들여쓰기
    private String pass ;
    //게시물의 가상번호 부여를 위한 멤버변수 추가
 	private int virtualNum;
 	//생성자는 필요한 경우에만 정의하므로 여기서는 필요없음
 	//getter/setter정의
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() { 
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public java.sql.Date getPostdate() {
		return postdate;
	}
	public void setPostdate(java.sql.Date postdate) {
		this.postdate = postdate;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	} 
	public int getBgroup() {
		return bgroup;
	}
	public void setBgroup(int bgroup) {
		this.bgroup = bgroup;
	}
	public int getBstep() {
		return bstep;
	}
	public void setBstep(int bstep) {
		this.bstep = bstep;
	}
	public int getBindent() {
		return bindent;
	}
	public void setBindent(int bindent) {
		this.bindent = bindent;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getVirtualNum() {
		return virtualNum;
	}
	public void setVirtualNum(int virtualNum) {
		this.virtualNum = virtualNum;
	}
    
}
