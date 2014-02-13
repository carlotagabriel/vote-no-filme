<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/commons/header.jsp" />

<s:iterator value="orderedMovies" status="movie">
<jsp:include page="/item_fragment.jsp" />
</s:iterator>

<jsp:include page="/commons/footer.jsp" />
