<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Probar MMS</title>
	
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/site.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/pygments.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	
	<style type="text/css">	
		body{background-color : #f9f9f9;}
		form div.upload { overflow:hidden; }

		form div.upload label { font-weight:bold; display:block; margin-bottom:0.25em; }
		
		form div.upload div.file-preview {
		    background:#ccc;
		    border:1px solid #000;
		    display:inline-block;
		    float:left;
		    margin-right:1em;
		    width:60px;
		    height:60px;
		    text-align:center;
		}
	
	</style>
	
	<script>
	$(document).ready(function() {
		
		$('input[type=file]').change(function(e) {
		    if(typeof FileReader == "undefined") return true;

		    var elem = $(this);
		    var files = e.target.files;

		    for (var i=0, file; file=files[i]; i++) {
		        if (file.type.match('image.*')) {
		            var reader = new FileReader();
		            reader.onload = (function(theFile) {
		                return function(e) {
		                    var image = e.target.result;
		                    previewDiv = $('.file-preview', elem.parent());
		                    bgWidth = previewDiv.width() * 2;
		                    previewDiv.css({
		                        "background-size":bgWidth + "px, auto",
		                        "background-position":"50%, 50%",
		                        "background-image":"url("+image+")",
		                    });
		                };
		            })(file);
		            reader.readAsDataURL(file);
		        }
		    }
		});
		
		
	});
	</script>
	
</head>
<body>

<form class="form-horizontal">
<fieldset>

<!-- Form Name -->
<legend>Probar MMS</legend>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="nmovil">Ingrese N° Móvil:</label>
  <div class="controls">
    <input id="nmovil" name="nmovil" type="text" placeholder="ej: 99988777" class="input-xlarge" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="la">Ingrese LA:</label>
  <div class="controls">
    <input id="la" name="la" type="text" placeholder="ej: 4433" class="input-xlarge" required="">
    
  </div>
</div>


<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="url">Ingrese Asunto:</label>
  <div class="controls">
    <input id="url" name="url" type="text" placeholder="ej: Imagen" class="input-xlarge">
    
  </div>
</div>

<!-- File Button --> 

<div class="control-group">
	
	<label class="control-label" for="archivo">Ingrese Archivo:</label>
	<div class="controls">
	  	<div class="upload">
		    
		    <input type="file" name="file" id="file">
		    <div class="file-preview"></div>
		</div>
	</div>
</div>


<!-- Button (Double) -->
<div class="control-group">
  <label class="control-label" for="probar"></label>
  <div class="controls">
    <button id="probar" name="probar" class="btn btn-primary">Probar SP</button>
    <button id="canclear" name="canclear" class="btn btn-danger">Cancelar</button>
  </div>
</div>

</fieldset>
</form>

</body>
</html>