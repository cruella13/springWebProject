<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- jQuery & bootstrap -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<!-- alertify -->
<script src="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/alertify.min.js"></script>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css"/>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/default.min.css"/>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/semantic.min.css"/>

<style>
    div{box-sizing: border-box;}
    #header{
        width:80%;
        height:100px;
        padding-top: 20px;
        margin:auto;
    }
    #header>div{width:100%; margin-bottom:10px}
    #header_1{height:40%;}
    #header_2{height:60%;}

    #header_1>div{
        height:100%;
        float:left;
    }
    #header_1_left{width:30%;position:relative;}
    #header_1_center{width:40%;}
    #header_1_right{width:30%;}

    #header_1_left>img{height:80%;position:absolute;margin:auto;top:0;bottom:0;right: 0;left:0;}
    #header_1_right{text-align: center; line-height:35px; font-size: 12px; text-indent: 35px;}
    #header_1_right>a{margin: 5px;}
    #header_1_right>a:hover{cursor: pointer;}
    #header_1_right img{width:30px; height:30px; border:0.5px solid lightgray; border-radius: 50%;}
    
    #header_2>ul{width:100%; height:100%; list-style-type: none; margin: auto; padding:0;}
    #header_2>ul>li{float:left; width:25%; height:100%; line-height: 55px; text-align:center;}
    #header_2>ul>li a{text-decoration: none; color:black; font-size: 18px; font-weight: 900;}

    #header_2{border-top:1px solid lightgray}

    #header a{text-decoration:none; color:black}
    
    /* ?????? ??????????????? ??????????????? ????????? style */
    .content{
        background-color:rgb(247, 245, 245);
        width:80%;
        margin:auto;
    }
    .innerOuter{
        border:1px solid lightgray;
        width:80%;
        margin:auto;
        padding:5% 10%;
        background:white;
    }
</style>

</head>
<body>

	<c:if test="${ not empty alertMsg }">
		<script>
			alertify.alert("${ alertMsg }");
		</script>
		<c:remove var="alertMsg" scope="session" />
	</c:if>
	<div id="header">
        <div id="header_1">
            <div id="header_1_left">
                <img src="resources/images/logo.png" alt="">
            </div>
            <div id="header_1_center"></div>
            <div id="header_1_right">
            	<c:choose>
            		<c:when test="${ empty loginUser }">
		                <a href="enrollForm.me">????????????</a> | 
		                <a data-toggle="modal" data-target="#loginModal">?????????</a> 
	                </c:when>
					<c:otherwise>
		                <img src="<c:out value='${loginUser.profileImg }' default='resources/profile_images/defaultProfile.png'/>">&nbsp;
		                <span>${ loginUser.userName }??? ???????????????</span> &nbsp;|&nbsp;
		                <a href="myPage.me">???????????????</a>
		                <a href="logout.me">????????????</a>
	                </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div id="header_2">
            <ul>
                <li><a href="">HOME</a></li>
                <li><a href="">????????????</a></li>
                <li><a href="">???????????????</a></li>
                <li><a href="">???????????????</a></li>
            </ul>
        </div>
    </div>

    <!-- ????????? ?????? ??? ?????? ?????? (???????????? ??????????????? ?????? a ????????? ??????) -->
    <div class="modal fade" id="loginModal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Login</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button> 
            </div>

            <form action="login.me" method="post">
                <!-- Modal Body -->
                <div class="modal-body">
                    <label for="userId" class="mr-sm-2">ID :</label>
                    <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Enter ID" id="userId" name="userId"> <br>
                    <label for="userPwd" class="mr-sm-2">Password:</label>
                    <input type="password" class="form-control mb-2 mr-sm-2" placeholder="Enter password" id="userPwd" name="userPwd">
                </div>
                
                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">?????????</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">??????</button>
                </div>
            </form>
            </div>
        </div>
    </div>

    <br clear="both">


</body>
</html>