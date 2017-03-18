<%--
 * register.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="customerForm">
	<jstl:if test="${customerForm.id==0 || customerForm.username == pageContext.request.remoteUser}">
	<form:hidden path="id"/>

	<jstl:choose>
		<jstl:when test="${tipe eq 'personal' }">
			<fieldset>
				<legend align="left"><spring:message code="customer.personal.info"/></legend>
				<br/>
				<acme:textbox code="customer.name" path="name" />
				<br/>
				<acme:textbox code="customer.surname" path="surname"/>
				<br/>
				<acme:textbox code="customer.email" path="email"/>
				<br/>
				<acme:textbox code="customer.phone" path="phone"/>
				<br/>
			</fieldset>
		</jstl:when>
		<jstl:otherwise>
			<fieldset>
				<legend align="left"><spring:message code="customer.account.info"/></legend>
				<acme:textbox code="customer.username" path="username" />
				<br/>
				<acme:password code="customer.password" path="password"/>
				<br/>
				<acme:password code="customer.password2" path="password2"/>
			
				<br/>
				<form:checkbox path="agreed"/>
				<form:label path="agreed">
					<spring:message code="customer.register.agree" />
					<a href="misc/lopd.do"><spring:message code="customer.register.agree.2"/></a>
				</form:label>
				<form:errors path="agreed" cssClass="error" />
				<br/>
			</fieldset>
			<fieldset>
				<legend align="left"><spring:message code="customer.personal.info"/></legend>
				<br/>
				<acme:textbox code="customer.name" path="name" />
				<br/>
				<acme:textbox code="customer.surname" path="surname"/>
				<br/>
				<acme:textbox code="customer.email" path="email"/>
				<br/>
				<acme:textbox code="customer.phone" path="phone"/>
				<br/>
			</fieldset>
			
		</jstl:otherwise>
		
	</jstl:choose>
	
	<br/>
	<acme:submit name="save" code="customer.save"/>
	<acme:cancel code="customer.cancel" url="welcome/index.do" />
	</jstl:if>
</form:form>