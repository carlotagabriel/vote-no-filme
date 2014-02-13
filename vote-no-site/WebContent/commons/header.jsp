<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta charset="UTF-8">
<title>Vote no Filme!</title>
<script
    src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link rel="stylesheet"
    href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css">
<script
    src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<link href="css/stylesheet.css" rel="stylesheet" />
</head>
<body>
    <div class="container" id="container">
        <h1 style="color: #d9edf7;">Vote no Filme!</h1>
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="btn-group">
                <s:url action="home.action" var="aHome" />
                <s:a cssClass="btn btn-primary" href="%{aHome}">Home</s:a>
                <s:url action="creditos.action" var="aCreditos" />
                <s:a cssClass="btn btn-primary" href="%{aCreditos}">Créditos</s:a>
                <s:url action="sobre.action" var="aSobre" />
                <s:a cssClass="btn btn-primary" href="%{aSobre}">Sobre</s:a>
                </div>

            </div>
            <div class="panel-body" id="main-content">
            