<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Probar SMS/WP</title>
	
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	<script src="js/jquery.js"></script>
	
	<style type="text/css">	body{background-color : #f9f9f9;}</style>
</head>
<body>

<form class="form-horizontal">
<fieldset>

<!-- Form Name -->
<legend>Probar SMS/WP</legend>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="nmovil">N° Móvil:</label>
  <div class="controls">
    <input id="nmovil" name="nmovil" type="text" placeholder="ej: 99988777" class="input-xlarge" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="la">LA:</label>
  <div class="controls">
    <input id="la" name="la" type="text" placeholder="ej: 4433" class="input-xlarge" required="">
    
  </div>
</div>


<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="url">URL:</label>
  <div class="controls">
    <input id="url" name="url" type="text" placeholder="ej: http://name.domain" class="input-xlarge">
    
  </div>
</div>

<!-- Textarea -->
<div class="control-group">
  <label class="control-label" for="textarea">Texto a Enviar:</label>
  <div class="controls">
  		<div class="span12">                     
    		<textarea id="text" name="text" placeholder="160 Caracteres máximo..."></textarea>
    	</div>
  </div>
</div>

<div class="form-actions">
    <button id="probar" name="probar" class="btn btn-primary">Probar</button>
    <button id="canclear" name="canclear" class="btn btn-danger">Cancelar</button>
</div>

</fieldset>
</form>

</body>
</html>