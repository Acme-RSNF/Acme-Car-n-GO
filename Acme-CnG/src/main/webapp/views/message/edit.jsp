<%--
 * edit.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">

	<form:form action="${requestURI}"	modelAttribute="messageForm">
	
		<acme:textbox code="message.sender" path="sender.userAccount.username" readonly="true" />
		<br/>
		<acme:select items="${actors }" itemLabel="userAccount.username" code="message.recipient" path="recipient" />
		<br/>
		<acme:textbox code="message.title" path="title" />
		<br/>
		<acme:textarea code="message.text" path="text" />
		<br/>
		<acme:textarea code="message.attachment" path="attachment" />
		<br/>
		<acme:submit name="save" code="message.send"/>
		<acme:cancel code="message.cancel" url="welcome/index.do" />
	
	</form:form>

</security:authorize>