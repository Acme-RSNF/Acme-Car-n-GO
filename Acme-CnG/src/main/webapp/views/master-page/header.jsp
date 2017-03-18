<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="welcome/index.do"><img src="images/logo.png" alt="Acme-Car'n-Go Co., Inc."></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="request/listAdmin.do"><spring:message code="master.page.request.list" /></a></li>
					<li><a href="offer/listAdmin.do"><spring:message code="master.page.offer.list" /></a></li>
					<li><a href="comment/listAdmin.do"><spring:message code="master.page.comment.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.administrator.banner" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="banner/listAdmin.do"><spring:message code="master.page.banner.list" /></a></li>
					<li><a href="banner/create.do"><spring:message code="master.page.banner.create" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/request/myList.do"><spring:message code="master.page.request.myList" /></a></li>
					<li><a href="customer/offer/myList.do"><spring:message code="master.page.offer.myList" /></a></li>
				</ul>
			</li>
			<li><a href="request/list.do"><spring:message code="master.page.request.list" /></a></li>
			<li><a href="offer/list.do"><spring:message code="master.page.offer.list" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="customer/register.do"><spring:message code="master.page.customer.register" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class = "fNiv"><spring:message code="master.page.messages"/></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/sent.do" ><spring:message code="master.page.message.sent" /></a></li>
					<li><a href="message/received.do" ><spring:message code="master.page.message.received" /></a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

