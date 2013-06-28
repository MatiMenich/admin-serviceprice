<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin SP</title>
	
	<!-- CSS -->
	<link href="css/demo_page.css" rel="stylesheet" type="text/css" />
	<link href="css/demo_table.css" rel="stylesheet" type="text/css" />      
	<link href="css/demo_table_jui.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/DT_bootstrap.css">
	<!-- Scripts -->
	<script src="js/jquery.js"></script>
	<script src="js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="js/dataTables.bootstrap.js" type="text/javascript"></script>
	<script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	
	<!-- FancyBox -->
	<script type="text/javascript" src="fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<link rel="stylesheet" href="fancybox/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
	<script type="text/javascript" src="fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	
	<script type="text/javascript" charset="utf-8">
		$(document).ready( function () {
			oTable = $('#example').dataTable({
				"sDom" : 'rtlp<"clear">',
				"sPaginationType": "bootstrap",
				"bAutoWidth": false, // Disable the auto width calculation 
				"aoColumns": [
					{ "sWidth": "8%" },
					{ "sWidth": "5%" },
					{ "sWidth": "15%"}, 
					{ "sWidth": "5%" },
					{ "sWidth": "15%"},
					{ "sWidth": "10%"},
					{ "sWidth": "22%"},
					{ "sWidth": "5%" , "bSortable" : false},
					{ "sWidth": "13%", "bSortable" : false}
				]
			});
			$.extend( $.fn.dataTableExt.oStdClasses, {
			    "sWrapper": "dataTables_wrapper form-inline"
			} );
			
			$(".fancy").fancybox({
				maxWidth	: 600,
				fitToView	: true,
				width		: '70%',
				height		: '70%',
				padding		: 20,
				autoSize	: true,
				closeClick	: true,
				openEffect	: 'elastic',
				closeEffect	: 'fade'
			});
			
			$('#search-box').keyup(function() {
				oTable.fnFilter( $(this).val() );
			});
			
			$("[rel=tooltip]").tooltip({ placement: 'right'});
		} );
		
	</script>
	
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	
	<style>
	        .header{
	            height:70px;
	            background-color:#647687;
	            color: white;
	            font-size:55pt;
	            padding:40px 0px 0px 30px;
	        }
	
	        .myFooter{
	            height:20px;
	            background-color:#647687;
	            color: white;
	            font-size:10pt;
	            padding:5px;
	            text-align:center;
	        }
	 </style>
	 <style type="text/css">
			#example {
				margin: 0 auto;
				clear: both;
				overflow: hidden;
				width: 100%;
				table-layout: fixed;
				
			}
		</style>   
	  
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top hidden-print">
    <div class="navbar-inner">
        <ul class="nav">
            <li class="active"><a href="#">SP</a></li>
            <li><a href="http://admin.movixla.com/scheduling">Scheduling</a></li>
        </ul>
        <ul class="nav pull-right">
            <li><a href="mailto:dave@fontawesome.io"><i class="icon-user icon-white icon-large"></i>&nbsp; MatiMenich</a></li>
        </ul>
    </div>
</div>

<!--div class="jumbotron hidden-print maincolor">
    <div class="container"><h1>ServicePrice</h1></div>
</div-->

<div class="header">Service Price</div>

<div class="navbar navbar-inverse navbar-static-top">
    <div class="navbar-inner">
        <ul class="nav">
            <li class="active"><a href="SPController?type=Env">Envío</a></li>
            <li class="divider-vertical"></li>
            <li><a href="SPController?type=Rec">Recepción</a></li>
            <li class="divider-vertical"></li>
            <li><a href="SPController?type=Bill">Billing</a></li>
            <li class="divider-vertical"></li>

        </ul>
        <div class="navbar-search pull-right">
            <input id="search-box" type="text" class="search-query" placeholder="Buscar...">
        </div>
        
       	<div class="pull-right">
       		<a class="btn btn-inverse" data-loading-text="Loading..." href="SPController?type=Env&action=refresh"> <i class="icon-refresh icon-white"></i> </a>
        	<a class="btn btn-primary fancy" data-fancybox-type="iframe" href="SPController?action=add"> <i class="icon-plus icon-white"></i> Nuevo </a>
		</div>
    </div>
</div>

<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="example">
	<thead>
		<tr>
        <th>Operador</th>
        <th>Tipo</th>
        <th>Servicio</th>
        <th>Precio</th>
        <th>Canal</th>
        <th>Estrategia</th>
        <th>Argumentos</th>
        <th>Cache</th>
        <th>Acciones</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="sp" items="${sps}">
		
		<tr class="odd_gradeA">
        <td><span class="label label-info">${sp.getOperador().getPais().getCodigo().toUpperCase()}</span> ${sp.getOperador().name().split("_")[0].substring(0,1).toUpperCase().concat(sp.getOperador().name().split("_")[0].substring(1).toLowerCase()) }</td>
        <td>${sp.getTipoEnv() }</td>
        <td>${sp.getEstado().toString()=="INACTIVO" ? '<span class="label label-important">' : sp.getEstado().toString()=="TESTING" ? '<span class="label label-warning">' : ""} 
	        ${sp.getServicio() } 
	        ${sp.getEstado().toString()=="INACTIVO" || sp.getEstado().toString()=="TESTING" ? '</span>' : ""}
	    </td>
        <td>${sp.getEstado().toString()=="INACTIVO" ? '<span class="label label-important">' : sp.getEstado().toString()=="TESTING" ? '<span class="label label-warning">' : ""}
        	${sp.getPrecio() } 
        	${sp.getEstado().toString()=="INACTIVO" || sp.getEstado().toString()=="TESTING" ? '</span>' : ""}	
        </td>
        <td>${sp.getCanal() }</td>
        <td>${sp.getEstrategia() }</td>
        <td><div style="overflow:hidden;text-overflow:ellipsis;">${sp.getArgs() }</div></td>
        <td>${sp.hasCache() ? '<i class="icon-ok-sign icon-large"></i>' : '<i class="icon-remove-sign icon-large"></i>'}</td>
        <td class="center">
        <div class="btn-group">
        	<a class="btn fancy disabled" data-fancybox-type="iframe" href="SPController?action=test&idSP=${sp.getId()}"><i class="icon-play icon-large"></i></a> 
        	<a class="btn disabled" href="#"><i class="icon-list-alt icon-large"></i></a> 
        	<a class="btn disabled" href="#"><i class="icon-time icon-large"></i></a> 
        	<a class="btn btn-warning fancy" data-fancybox-type="iframe" href="SPController?action=edit&idSP=${sp.getId()}"><i class="icon-edit icon-large"></i></a> 
        </div>
        </td>
		</tr>
		</c:forEach>
		
	</tbody>
</table>
<div class="myFooter">
   Movix - 2013
</div>

</body>
</html>