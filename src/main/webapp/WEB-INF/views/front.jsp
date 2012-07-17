<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cloud Foundry Demo, Crawler</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/base.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery.infinitescroll.css" />" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery.1.7.1.js" />" ></script>
</head>
<body class="front">
	<div class="container">
		<div class="hero-unit mt-20">
			<h1>Crawler</h1>
			<p>This is a sample application for cloud foundry. It will demonstrate to use cloud foundry services, MongoDB and RabbitMQ.</p>
			<p style="font-size: 12px; color: red;">Note: This application only crawl image unit (width &gt; 190px) from below input URL.</p>
		</div>
		<form id="crawl-form" class="well form-inline" 
			action="<c:url value="/crawl" />" method="POST">
			<input class="span8" name="url" type="text" style="height:36px; line-height: 36px; font-size: 16px;" 
				placeholder="Input Target URL...">
			<button id="crawl-submit" class="btn btn-primary btn-large ml-10"
				data-loading-text="Crawling..." autocomplete="off" type="submit">Crawl</button>
			<span id="crawl-ok" style="display: none;"><i class="icon-ok"></i></span>
		</form>
		<div class="page-header">
			<h2>Images Crawled
			<span class="c-ccc fs-16">(Pull new images from server after <span id='pull-ctr'></span> seconds)</span>
			<a id="pull-idc" style="display: none;">pulling</a></h2>
		</div>
		<div id="images-container">
			<ul class="thumbnails">
				<c:import url="/images/0" />
			</ul>
		</div>
		<div id="page-nav">
			<a href="<c:url value="/images/1" />"></a>
		</div>
	</div>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.form.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.masonry.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.imagesloaded.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.infinitescroll.js" />" ></script>
<script type="text/javascript">
	$(function(){
		$('#crawl-form').ajaxForm({ 
	        dataType:  'json', 
	        beforeSubmit: function(formData, $form, options){
	        	var url = $form.find('input').val();
	        	if(!$.trim(url)){
	        		return false;
	        	}
	        	$('#crawl-submit').button('loading');
	        },
	        success:  function(result){
	        	if(!result || result.code !=0) return;
	        	$('#crawl-ok').show();
	        },
	        complete: function(jqXHR, textStatus){
	        	$('#crawl-submit').button('reset');
          	}
	    });
		
		var $thumbnails = $('#images-container .thumbnails');
		
		$thumbnails.masonry({
			itemSelector : '.pin',
		    columnWidth : 220,
		    gutterWidth: 18,
		    isAnimated: true,
		    isFitWidth: true
		});
		
		var couter = 0;
		var holder = setInterval(pull, 1000);
		var step = 5;
		function pull(){
			couter++;
			counter=couter%step;
			$('#pull-ctr').text((counter==0? 0: step-counter)+'');
			if(counter!=0){
				return;
			}
			var ts = $('#images-container .thumbnails li[data-ts]:first-child').attr('data-ts'); 
			var params = {
				ts: ts
			};
			$.ajax({
				url: '<c:url value="/pull" />',
				type: 'POST',
				data: params,
				beforeSend: function ( xhr ) {
				    $('#pull-idc').show();
				    clearInterval(holder);
				},
				success: function(data){
					$('#images-container .thumbnails').prepend($.trim(data));
					$thumbnails.masonry( 'reload' );
					holder = setInterval(pull, 1000);
				},
				complete: function(){
					$('#pull-idc').hide();
				}
			});
		};
		
		$thumbnails.infinitescroll(
				{
					navSelector : '#page-nav', // selector for the paged navigation
					nextSelector : '#page-nav a', // selector for the NEXT link (to page 2)
					itemSelector : '.pin', // selector for all items you'll retrieve
					debug        : false,
					isAnimated	 : true,
					animationOptions: {
					    duration: 750,
					    easing: 'linear',
					    queue: true
					},
					loading: {
						selector: '#images-container',
						finishedMsg: 'No more images to load.',
						img: 'http://i.imgur.com/6RMhx.gif',
						msgText: '',
						speed: 0
					},
					state : {
						currPage: 0,
					},
					pathParse: function() {
				        return ['<c:url value="/images/" />',''];
				    }
				},
				// trigger Masonry as a callback
				function( newElements ) {
					// hide new items while they are loading
					var $newElems = $( newElements ).css({ opacity: 0 });
					// ensure that images load before adding to masonry layout
					// show elems now they're ready
					$newElems.imagesLoaded(function(){
						// show elems now they're ready
						$newElems.animate({ opacity: 1 });
						$thumbnails.masonry( 'appended', $newElems, true);
					});
				}
			);
	});
</script>
</body>
</html>