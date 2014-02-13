<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

    <div class="media">
        <a class="pull-left" href="#"> <img class="media-object"
            style="width: 64px; height: 64px;"
            src="<s:property value="imagePath" />">
        </a>
        <div class="media-body">
            <h5 class="media-heading">
                <s:property value="name" />
                -
                <s:property value="votes" />
                VOTOS
            </h5>
            <s:property value="description" />
        </div>
    </div>
