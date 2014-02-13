<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
	<div class="col-sm-6 col-md-6">
		<div class="thumbnail">
			<img data-src="holder.js/300x200" alt="300x200"
				style="width: 300px; height: 200px;"
				src="<s:property value="#session.options[0].imagePath" />">
			<div class="caption">
				<h3>
					<s:property value="#session.options[0].name" />
				</h3>
				<h4>
					<s:property value="#session.options[0].description" />
				</h4>
				<p>
					<a
						href="javascript:replaceContext('<s:property value="#request.get('javax.servlet.forward.context_path')" />/home?optionId=<s:property value="#session.options[0].id" />')"
						class="btn btn-success col-sm-12" role="button"
						style="font-size: 20px;">Votar!</a>
				</p>
			</div>
		</div>
	</div>
	<div class="col-sm-6 col-md-6">
		<div class="thumbnail">
			<img data-src="holder.js/300x200" alt="300x200"
				style="width: 300px; height: 200px;"
				src="<s:property value="#session.options[1].imagePath" />">
			<div class="caption">
				<h3>
					<s:property value="#session.options[1].name" />
				</h3>
				<h4>
					<s:property value="#session.options[1].description" />
				</h4>
				<p>
					<a
						href="javascript:replaceContext('<s:property value="#request.get('javax.servlet.forward.context_path')" />/home?optionId=<s:property value="#session.options[1].id" />')"
						class="btn btn-success col-sm-12" style="font-size: 20px;"
						role="button">Votar!</a>
				</p>
			</div>
		</div>
	</div>
</div>
