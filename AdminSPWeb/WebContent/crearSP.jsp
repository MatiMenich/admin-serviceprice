<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nuevo Servicio Precio</title>
	
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/site.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/css/pygments.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="http://fortawesome.github.io/Font-Awesome/assets/font-awesome/css/font-awesome.min.css">
	
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script>
	$(document).ready(function() {
        $("#tipoSP").change(function(){
                if($(this).val() == "RecSMS" || $(this).val() == "RecMMS"){
                	$("#canal-div").slideUp();
                    $("#cache-div").slideUp();
                    $("#estrategia-div").slideUp();
                    $("#args-div").slideUp();
                    $("#la-div").slideDown();
                    $("#sp-div").slideDown();
                }
                else if($(this).val() == "EnvSMS" || $(this).val() == "EnvMMS" || $(this).val() == "EnvVSMS" || $(this).val() == "Bill"){
                	$("#la-div").slideUp();
                	$("#canal-div").slideDown();
                	$("#sp-div").slideDown();
                	$("#estrategia-div").slideDown();
                	$("#cache-div").slideDown();
                }
                else{
                	 $("#la-div").slideUp();
                     $("#sp-div").slideUp();
                     $("#canal-div").slideUp();
                     $("#cache-div").slideUp();
                     $("#estrategia-div").slideUp();
                     $("#args-div").slideUp();
                }
        });
        
        
        //TODO: comportameinto default = Desplegar argumentos
        $("#tipoEstrategia").change(function(){
        	if($(this).val() == "fp" || $(this).val() == "asc" || $(this).val() == "dsc"){
        		$("#args-div").slideDown();
        	}
        	else{
        		$("#args-div").slideUp();
        	}
        });
        
        $("#la-div").hide();
        $("#sp-div").hide();
        $("#canal-div").hide();
        $("#cache-div").hide();
        $("#estrategia-div").hide();
        $("#args-div").hide();
        
        
	});
	</script>
	
	<style type="text/css">	body{background-color : #f9f9f9;}</style>
	
	
</head>


<body>

  	<form class="form-horizontal" method="post" action="SPController">
			<fieldset>
				<legend>Nuevo Servicio Precio</legend>

				
				<div class="control-group">
					<label class="control-label">Seleccione Operador: </label>
					<div class="controls">
						  <select id="selectOperador" name="selectOperador" class="input-xlarge">
							  	<c:forEach var="op" items="${opList}">
							  		<option>${op.toString() }</option>
						  		</c:forEach>
						  </select>
					</div>
				</div>
				
				 
				<div class="control-group">
					  <label class="control-label">Tipo SP: </label>
					  <div class="controls">
					    <select id="tipoSP" name="tipoSP" class="input-xlarge">
					      <option>Seleccione Tipo...</option>
					      <option  value="RecSMS">Recepción SMS</option>
					      <option  value="RecMMS">Recepción MMS </option>
					      <option  value="EnvSMS">Envío SMS/WP</option>
					      <option  value="EnvMMS">Envío MMS</option>
					      <option  value="Bill">Billing</option>
					    </select>
					  </div>
					</div>
				 
				
				<div id="la-div" class="control-group">
					<label class="control-label">LA:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span3">
								<input id="la" type="text" class="input-block-level" autocomplete="off"  >
							</div>
						</div>
					</div>
				</div>
				 
				
				
				<div id="canal-div" class="control-group">
					<label class="control-label">Canal:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span10">
								<input id="canal" type="text" class="input-block-level" autocomplete="off"  >
							</div>
						</div>
					</div>
				</div>
				 
				
				

				<div id="sp-div" class="control-group">
					<label class="control-label">Servicio/Precio:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span9">
								<input id="servicio" type="text" class="input-block-level" autocomplete="off"   >
							</div>
							<div class="span3">
								<input id="precio" type="text" class="input-block-level" autocomplete="off"   >
							</div>
						</div>
					</div>
				</div>
				
				
				<div id="estrategia-div" class="control-group">
					<label class="control-label">Estrategia:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span12">
								<select id="tipoEstrategia" name="tipoEstrategia" class="input-xlarge">
							      <option>Seleccione Estrategia...</option>
							      <option  value="fp">Full Price</option>
							      <option  value="asc">Ascendente </option>
							      <option  value="dsc">Descendente</option>
							      <option  value="fin">Finance</option>
							    </select>
							</div>
						</div>
					</div>
				</div>
						
				
				
				<div id="args-div" class="control-group">
					<label class="control-label">Argumentos:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span12">
								<input id="args" type="text" class="input-block-level" autocomplete="off"  >
							</div>
						</div>
					</div>
				</div>
				 	 
				
				
			
				<div id="cache-div" class="control-group">
					<label class="control-label">Cache</label>
					<div class="controls">
						<div class="row-fluid">
							 <input type="checkbox" name="checkboxes" id="checkboxes-0" value="Option one">
						</div>
					</div>
				</div>
				 

				

				<div class="form-actions">
					<button type="submit" class="btn btn-primary">Crear SP</button>
					<button type="button" class="btn">Cancelar</button>
				</div>
			</fieldset>
		</form>


</body>
</html>