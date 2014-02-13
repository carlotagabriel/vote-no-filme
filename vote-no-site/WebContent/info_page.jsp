<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="error">
    <jsp:include page="/commons/header.jsp" />
</s:if>

<h4>Obrigado por votar nos filmes! <br> Preencha os campos abaixo e saiba a contagem dos votos!</h4>
<s:form action="user" cssClass="form_div">
		<s:textfield cssClass="form-control" label="Nome" key="user.name"/>
		<s:textfield cssClass="form-control" label="E-Mail" key="user.emailAdress"/>
		<s:submit cssClass="btn btn-success col-md-12 form_button" value="Enviar"/>
</s:form>
<s:if test="error">
    <jsp:include page="/commons/footer.jsp" />
</s:if>

