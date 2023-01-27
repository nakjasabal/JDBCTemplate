<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
	function checkValidate(f){
		if(f.name.value==""){
			alert("이름을 입력하세요");
			f.name.focus();
			return false;
		}
		if(f.pass.value==""){
			alert("패스워드를 입력하세요");
			f.pass.focus();
			return false;
		}
		if(f.title.value==""){
			alert("제목을 입력하세요");
			f.title.focus();
			return false;
		}
		if(f.contents.value==""){
			alert("내용을 입력하세요");
			f.contents.focus();
			return false;
		}
	}
</script>
</head>
<body>
<div class="container">

	<h2>비회원제 게시판 - 답변글쓰기 폼</h2>
	<!--  
	답글쓰기 폼은 기본적으로 수정하기와 동일하다. 
	단, 답글은 새로운 게시물이므로 작성자와 패스워드는 새롭게 입력되어야 한다. 
	제목과 내용은 원본글을 조금 수정한 상태로 삽입한다. 
	-->	
	<form name="writeFrm" method="post" action="replyAction.do" 
		onsubmit="return checkValidate(this);">
	<!-- 
	답변글 처리를 위해 원본글에 대한 idx값과 추가적으로 bgroup, bstep, bindent값이
	필요하다. 해당 값을 통해 게시물을 하나의 그룹으로 묶어주고, 그룹내에서 정렬 및 
	목록 출력시 들여쓰기 처리를 하게된다. 
	-->
	<input type="hid den" name="idx" value="${replyRow.idx }" >
	<input type="hid den" name="nowPage" value="${param.nowPage }" >
	<input type="hid den" name="bgroup" value="${replyRow.bgroup }" >
	<input type="hid den" name="bstep" value="${replyRow.bstep }" >
	<input type="hid den" name="bindent" value="${replyRow.bindent }" >	
		
	<table class="table table-bordered" width=800>
	<colgroup>
		<col width="25%"/>
		<col width="*"/>
	</colgroup>
	<tr>
		<td>작성자</td>
		<td>
			<input type="text" name="name" style="width:50%;" />
		</td>
	</tr>
	<tr>
		<td>패스워드</td>
		<td>
			<input type="password" name="pass" style="width:30%;"/>
		</td>
	</tr> 
	<tr>
		<td>제목</td>
		<td>
			<input type="text" name="title" style="width:90%;" 
				value="${replyRow.title }"  />
		</td>
	</tr>
	<tr>
		<td>내용</td>
		<td>
			<textarea name="contents" 
				style="width:90%;height:200px;">${replyRow.contents }</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<button type="submit">작성완료</button>
			<button type="reset">RESET</button>
			<button type="button" onclick="location.href='./list.do';">
				리스트바로가기
			</button>
		</td>
	</tr>
	</table>	
	</form>

</div>
</body>
</html>