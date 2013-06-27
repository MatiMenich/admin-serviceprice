<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nuevo Servicio Precio</title>
	
	
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	
	<script src="js/jquery.js"></script>
	<script src="js/jquery.validate.js"></script>
	<script>
	$(document).ready(function() {
		
		$.validator.addMethod('validPrice',
			    function (value,element) { 
			        return this.optional(element) || /^[X0-9.]{1,8}$/i.test(value); 
		}, "Por favor ingrese un precio válido.");
		
		$.validator.addMethod('validLA',
			    function (value,element) { 
			        return this.optional(element) || /^[a-zA-Z0-9.]{2,8}$/i.test(value); 
		}, "Por favor ingrese un LA válido.");
		
		
		 var RecRules = {
				 
				 	servicio: {
	        	        required: true,
	        	        minlength: 2,
	        	        messages: {
	        	            required: "Ingrese un servicio",
	        	            minlength: "Largo mayor que 2"
	        	        }
	        	    },
	        	    
	        	    la: {
	        	        required: true,
	        	        validLA: true,
	        	        messages: {
	        	        	required: "Ingrese un LA",
	        	            number: "Ingrese un LA válido"
	        	        }
	        	    },
	        	    
	        	    precio: {
	        	    	required:true,
	        	    	validPrice: true,
	        	    	messages: {
	        	        	required: "Ingrese un precio",
	        	            number: "Ingrese un precio válido"
	        	    	}
	        	    }
	        };
		 
		 var EnvRules = {
				 
				 	servicio: {
	        	        required: true,
	        	        minlength: 2,
	        	        messages: {
	        	            required: "Ingrese un servicio",
	        	            minlength: "Largo mayor que 2"
	        	        }
	        	    },
	        	    
	        	     precio: {
	        	    	required:true,
	        	    	validPrice: true,
	        	    	messages: {
	        	        	required: "Ingrese un precio",
	        	            number: "Ingrese un precio válido"
	        	    	}
	        	    },
	        	    
	        	    canal: {
	        	    	required:true,
	        	    	messages: {
	        	    		required: "Ingrese un canal"
	        	    	}
	        	    }
	        };
	        
	        function addRules(rulesObj){
	            for (var item in rulesObj){
	               $('#'+item).rules('add',rulesObj[item]);  
	            } 
	        }

	        function removeRules(rulesObj){
	            for (var item in rulesObj){
	               $('#'+item).rules('remove');  
	            } 
	        }
		
	        $('#SPform').validate({
	        	errorPlacement: function(error, element) {
		           error.insertAfter(element);
		           parent.$.fancybox.update(); 
	        	}
	        });
	    
		
        $("#tipoSP").change(function(){
        	
        	
                if($(this).val() == "RecSMS" || $(this).val() == "RecMMS"){
                	$("#canal-div").hide();
                    $("#cache-div").hide();
                    $("#estrategia-div").hide();
                    $("#args-div").hide();
                    $("#la-div").show();
                    $("#sp-div").show();
                    removeRules(EnvRules);
                    addRules(RecRules);
                }
                else if($(this).val() == "EnvSMS" || $(this).val() == "EnvMMS" || $(this).val() == "EnvVSMS" || $(this).val() == "Bill"){
                	$("#la-div").hide();
                	$("#canal-div").show();
                	$("#sp-div").show();
                	$("#estrategia-div").show();
                	$("#cache-div").show();
                	removeRules(RecRules);
                    addRules(EnvRules);
                }
                else{
                	 $("#la-div").hide();
                     $("#sp-div").hide();
                     $("#canal-div").hide();
                     $("#cache-div").hide();
                     $("#estrategia-div").hide();
                     $("#args-div").hide();
                }
                
               
               

                $('#SPform').validate({
    	        	errorPlacement: function(error, element) {
    		           error.insertAfter(element);
    		           parent.$.fancybox.update(); 
    	        	}
    	        });
               
                parent.$.fancybox.update();	
               
        });
        
        
    
        $("#tipoEstrategia").change(function(){
        	
        	
        	
        	if($(this).val() == "fp" || $(this).val() == "fin"){
        		$("#args-div").show();
        		$("#sps-div").hide();
        		if($("#tipoEstrategia").val() =="Bill"){
	        		$('#args').rules('add',{
	        	        required: true,
	        	        minlength: 2,
	        	        messages: {
	        	            required: "Ingrese argumentos",
	        	            minlength: "Largo mayor que 2"
	        	        }
	                });
        		}
        	}
        	else if($(this).val() == "asc" || $(this).val() == "dsc"){
        		$("#args-div").hide();
        		$("#sps-div").show();
        		$("#args").rules('remove');
        	}
        	
        	else{
        		$("#args-div").show();
        		$("#sps-div").hide();
        		
        		if($("#tipoEstrategia").val() =="Bill"){
	        		$('#args').rules('add',{
	        	        required: true,
	        	        minlength: 2,
	        	        messages: {
	        	            required: "Ingrese argumentos",
	        	            minlength: "Largo mayor que 2"
	        	        }
	                });
        		}
        	}
        	
        	   
        	parent.$.fancybox.update();
             
        });
        
        $("#la-div").hide();
        $("#sp-div").hide();
        $("#canal-div").hide();
        $("#cache-div").hide();
        $("#estrategia-div").hide();
        $("#args-div").hide();
        $("#sps-div").hide();
        
        var i =1;
        
        $("#addSP").click(function() {
            var fieldWrapper = $("<div class=\"fieldwrapper\" id=\"field" + i + "\"/>");
            var fName = $("<select id='newSP' name='newSP' class='input-xlarge' ></select>");
            var removeButton = $(' <a class="remove btn btn-danger" href="#"><i class="icon-remove icon-white"></i></a>');
            removeButton.click(function() {
            	$(this).parent().remove();
                $(this).parent().rules('remove');
                i--;
            });
            
            
            $.ajax({
                type: "GET",
                url: "DropSPs",
                data: {opValue:  $('#selectOperador').val() },
                success: function(data){
                	fName.html(data);
                	fieldWrapper.append(fName);
                    fieldWrapper.append(removeButton);
                    $("#addSPs").append(fieldWrapper);
                }
            });
           
            
            parent.$.fancybox.update();
            
            
            i++;
            
            
        });
        
       
       $('#selectOperador').change ( function() {
                $.ajax({
                    type: "GET",
                    url: "DropCanales",
                    data: {opValue:  $('#selectOperador').val() },
                    success: function(data){
                        $("#canal").html(data);
                    }
                });
        });
       
        
        
	});
	
	
	</script>
	
	<style type="text/css">	body{background-color : #f9f9f9;}</style>
	
	
</head>


<body>

  	<form class="form-horizontal" id="SPform" method="post" action="SPController">
			<fieldset>
				<legend>Nuevo Servicio Precio</legend>

				
				<div class="control-group">
					<label class="control-label">Operador: </label>
					<div class="controls">
						  <select id="selectOperador" name="selectOperador" class="input-xlarge">
							  	<c:forEach var="op" items="${opList}">
							  		<option value="${op.getIdBD() }">${op.getPais().getCodigo().toUpperCase()} ${op.name().split("_")[0].substring(0,1).toUpperCase().concat(op.name().split("_")[0].substring(1).toLowerCase())}</option>
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
					      <option  value="EnvVSMS">Envío VSMS</option>
					      <option  value="Bill">Billing</option>
					    </select>
					  </div>
					</div>
				 
				
				<div id="la-div" class="control-group">
					<label class="control-label">LA:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span3">
								<input id="la" name="la" type="text" class="input-block-level" >
							</div>
						</div>
					</div>
				</div>
				 
				
				
				<div id="canal-div" class="control-group">
					<label class="control-label">Canal:</label>
					<div class="controls">
							<select id="canal" name="canal" class="input-xlarge" >
							</select>
							
					</div>
				</div>
				 
				
				

				<div id="sp-div" class="control-group">
					<label class="control-label">Servicio/Precio:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span9">
								<input id="servicio" name="servicio" type="text" class="input-block-level"  >
							</div>
							<div class="span3">
								<input id="precio" name="precio" type="text" class="input-block-level"  >
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
						
				<div id="sps-div" class="control-group">
					<label class="control-label">Ingrese SP(s): </label>
						<div class="controls">
							
								<fieldset id="addSPs">
								</fieldset>
								
								<input type="button" value="Agregar SP" class="addSP btn" id="addSP" name="addSP"/>
						
						</div>
				</div>	
				
				
				<div id="args-div" class="control-group">
					<label class="control-label">Argumentos:</label>
					<div class="controls">
						<div class="row-fluid">
							<div class="span12">
								<input id="args" name="args" type="text" class="input-block-level"  >
							</div>
						</div>
					</div>
				</div>
				 	 
				
				
			
				<div id="cache-div" class="control-group">
					<label class="control-label">Cache</label>
					<div class="controls">
						<div class="row-fluid">
							 <input type="checkbox" name="cache" id="cache" value="hasCache">
						</div>
					</div>
				</div>
				 

				

				<div class="form-actions">
					<button type="submit" class="btn btn-primary">Crear</button>
					<button type="button" class="btn btn-danger" onclick="parent.$.fancybox.close()">Cancelar</button>
				</div>
			</fieldset>
		</form>


</body>
</html>