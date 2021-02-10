<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="board.BoardDAO" %>
<%@ page import="board.BoardDTO" %>
<%@ page import="chat.ChatDAO" %>
<%@ page import="chat.ChatDTO" %>

<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		String pageNumber = "1";
		ArrayList<BoardDTO> boardList = new BoardDAO().getList(pageNumber);
		ArrayList<BoardDTO> boardImageList = new BoardDAO().getImageList(pageNumber);
		ArrayList<ChatDTO> boxList = new ChatDAO().getBox(userID);
		int sizeCount = 0;
		int imageSizeCount = 0;
		int boxCount = 0;

		if(boardList != null) {
			if(boardList.size() > 5) {
				sizeCount = 5;
			} else {
				sizeCount = boardList.size();
			}
		}

		if(boardImageList != null) {
			if(boardImageList.size() > 5) {
				imageSizeCount = 5;
			} else {
				imageSizeCount = boardImageList.size();
			}
		}
		
		if(boxList != null) {
			if(boxList.size() > 5) {
				boxCount = 5;
			} else {
				boxCount = boxList.size();
			}
		}

	%>
<head>
<meta http-equiv="Content-Type" content="text/html; meta charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>커뮤니티</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
	function getUnread(){
		$.ajax({
			type: "POST",
			url: "./chatUnread",
			data:{
				userID: encodeURIComponent('<%= userID %>'),
			},
			success: function(result){
				if(result >= 1){
					showUnread(result);
				}	else{
					showUnread('');
				}
			}
		});
	}
	function getInfiniteUnread(){
		setInterval(function(){
			getUnread();
		}, 4000);
	}
	function showUnread(result){
		$('#unread').html(result);
	}
</script>
</head>

<body>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">커뮤니티</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="index.jsp">메인</a>
				<li><a href="find.jsp">친구찾기</a></li>
				<li><a href="box.jsp">메시지함<span id="unread" class="label label-info"></span></a></li>
				<li><a href="boardView.jsp">게시판</a></li>
			</ul>
			<%
				if(userID == null){
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="buton" aria-haspopup="true"
					aria-expanded="false">접속하기<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul></li>
			</ul>
			<%
				} else{
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="buton" aria-haspopup="true"
					aria-expanded="false">회원관리<span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
						<li><a href="update.jsp">회원정보수정</a>
						<li><a href="profileUpdate.jsp">프로필 수정</a>
						<li><a href="logoutAction.jsp">로그아웃</a></li>
				</ul>
				</li>
			</ul>
			<%
				}
			%>
		</div>
	</nav>
	<div class="container" style="vertical-align:top;">
		<table class="table table-bordered table-hover" style="vertical-align:top; text-align: center; display:inline-block; width:49.8%; border-spacing: 0px;">
			<thead>
				<tr>
					<th colspan="3"><h4>최근 올라온 게시물</h4></th>
				</tr>
			</thead>
			<tbody>
			<%
				for(int i = 0; i < sizeCount; i++){
					BoardDTO board = boardList.get(i);
			%>
				<tr onclick="location.href='boardShow.jsp?boardID=<%= board.getBoardID() %>'">
					<td style="width: 110px;"><%= board.getUserID() %></td>
					<td style="width: 300px;"><%= board.getBoardTitle() %></td>
					<td style="width: 200px;"><%= board.getBoardDate() %></td>

				</tr>
			<%
				}
			%>
				<tr>
					<td colspan="3"><button class="btn btn-primary pull-right" onclick="location.href='boardView.jsp'">게시물 더보기</button></td>
				</tr>
			</tbody>
		</table>
		<table class="table table-bordered table-hover" style="vertical-align:top; text-align: center; display:inline-block; width:49.8%; border-spacing: 0px;">
			<thead>
				<tr>
					<th colspan="3"><h4>받은 메시지함</h4></th>
				</tr>
			</thead>
			<tbody>
			
			<%
			if (session.getAttribute("userID") != null) {
				for(int i = 0; i < boxCount; i++){
					ChatDTO chat = boxList.get(i);
			%>
				<tr onclick="location.href='chat.jsp?toID=<%= chat.getFromID() %>'">
					<td style="width: 110px;"><%= chat.getFromID() %></td>
					<td style="width: 300px;"><%= chat.getChatContent() %></td>
					<td style="width: 200px;"><%= chat.getChatTime() %></td>

				</tr>
			<%
				}
			} else {
			%>	
				<tr><td style="width:600px">로그인 되어있지 않습니다.</td></tr>
			<%	
			}
			%>
				<tr>
					<td colspan="3"><button class="btn btn-primary pull-right"  onclick="location.href='box.jsp'">메시지함</button></td>
				</tr>
			</tbody>
		</table>
		<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; border-spacing: 0px;">
			<thead>
				<tr>
					<th colspan="5"><h4>최근 올라온 사진</h4></th>
				</tr>
			</thead>
			<tbody>
				<tr>
			<%
				for(int i = 0; i < sizeCount; i++){
					BoardDTO board = boardImageList.get(i);
			%>
					<td onclick="location.href='boardShow.jsp?boardID=<%= board.getBoardID() %>'">
						<div><img src="boardDownload.jsp?boardID=<%= board.getBoardID() %>" style="width:200px; height:200px;"></div>
						<div><%= board.getBoardTitle() %></div>
					</td>
			<%
				}
			%>

				</tr>
				<tr>
					<td colspan="5"><button class="btn btn-primary pull-right"  onclick="location.href='boardView.jsp'">게시물 더보기</button></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<% 
		String messageContent = null;
		if(session.getAttribute("messageContent") != null){
			messageContent = (String) session.getAttribute("messageContent");
		}
		String messageType = null;
		if(session.getAttribute("messageType") != null){
			messageType = (String) session.getAttribute("messageType");
		}
		if (messageContent != null){
	%>
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
			<div class="modal-content <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success"); %>">
				<div class="modal-header panel-heading">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times</span>
						<span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">
						<%= messageType %>
					</h4>
				</div>
				<div class="modal-body">
					<%= messageContent %>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script>
		$('#messageModal').modal("show");
	</script>
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
		}
	%>
	<%
		if(userID != null){
	%>
		<script type="text/javascript">
			$(document).ready(function() {
				getUnread();
				getInfiniteUnread();
			});
		</script>
	<%
		}
	%>
</body>
</html>