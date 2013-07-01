<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Clonar SP</title>
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
		
	<script src="js/jquery.js"></script>
	<script src="js/jquery.validate.js"></script>
	<style type="text/css">	body{background-color : #f9f9f9;}</style>
</head>
<body>

	<form class="form-horizontal" method="post" action="SPController">
		<fieldset>
		
			<!-- Form Name -->
			<legend>Clonar Servicio Precio</legend>
			
			<!-- Text input-->
			<div class="control-group">
			  <label class="control-label" for="textinput">SP a Clonar:</label>
			  <div class="controls">
			    <input class="input-xlarge" type="text" id="disabledInput" placeholder="${sp.getServicio()} / ${sp.getPrecio()}" disabled>
			  </div>
			</div>
			
			<!-- Text input-->
			<div class="control-group">
			  <label class="control-label" for="nombre">Nuevo Nombre:</label>
			  <div class="controls">
			    <input id="nombre" name="nombre" type="text" placeholder="Ingrese sólo servicio" class="input-xlarge" required="">
			    
			  </div>
			</div>
			
			<input type="hidden" id="idSP" name="idSP" value="${sp.getId() }">
			<input type="hidden" id="clone" name="clone" value="yes">
			
			
			<div class="form-actions">
					<button type="submit" class="btn btn-primary">Clonar</button>
					<button type="button" class="btn btn-danger" onclick="parent.$.fancybox.close()">Cancelar</button>
			</div>
			
		</fieldset>
	</form>

</body>
</html>