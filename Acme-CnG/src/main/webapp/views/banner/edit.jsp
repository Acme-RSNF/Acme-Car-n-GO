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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize
	access="hasRole('ADMIN')">

	<form:form	action="${requestURI}"	modelAttribute="banner"> 
	
		<form:hidden path="id"/>
		
		<acme:textbox code="banner.image" path="image"/>
		
		
		<acme:submit name="save" code="banner.save"/>
		<jstl:if test="${banner.id != 0 && banner.isPrincipal == false}">
			<acme:submit name="delete" code="banner.delete"/>
		</jstl:if>
		<acme:cancel code="banner.cancel" url="banner/listAdmin.do"/>
		
		
	</form:form>

</security:authorize>