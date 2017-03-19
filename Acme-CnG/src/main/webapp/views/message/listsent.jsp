<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="isAuthenticated()">

	<display:table name="messages" id="row" class="displaytag" pagesize="5" requestURI="message/sent.do" >
	
	<spring:message code = "message.sender" var = "senderHeader"/>
	<display:column property="sender.userAccount.username" title="${senderHeader}" sortable="true"/>
	
	<spring:message code = "message.recipient" var = "recipientHeader"/>
	<display:column property="recipient.userAccount.username" title="${recipientHeader}" sortable="true"/>
	
	<spring:message code = "message.moment" var = "momentHeader"/>
	<display:column title="${momentHeader}"	sortable="false"><fmt:formatDate value="${row.moment }" pattern="dd/MM/yyyy" /></display:column>
	
	<spring:message code = "message.title" var = "titleHeader"/>
	<display:column property="title" title="${titleHeader}" sortable="true"/>
	
	<spring:message code = "message.text" var = "textHeader"/>
	<display:column property="text" title="${textHeader}" sortable="true"/>
	
	<spring:message code = "message.attachment" var = "attachementHeader"/>
	<display:column property="attachment" title="${attachmentHeader}" sortable="true"/>
	
	<display:column><a href="message/view.do?messageId=${row.id }"><spring:message code="message.view"/></a></display:column>
	
	<display:column><a href="message/forward.do?messageId=${row.id }" ><spring:message code="message.forward" /></a></display:column>
		
	</display:table>
	
</security:authorize>
