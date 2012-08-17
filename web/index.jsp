<%-- 
    Document   : index
    Created on : Aug 8, 2012, 12:31:34 PM
    Author     : arun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title></title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="cssnew/test.css"></link>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
        <script>
         $(document).ready(function(){
            /*if(!(localStorage && localStorage['tree']))*/{
               $.get('http://localhost:8084/Test/test', function(data) {
                   //alert(data);
                    console.log(data) ;
                   // localStorage['tree']=data;
                    msg=document.getElementById('msg')
                msg.innerHTML=data;
                    ///console.log('Load was performed.');
                     //$('#wrap').html(data);
                     //$("#tree").treeview({collapsed:true,persist:'cookie'})
                 });
                 

               
        }});    
        </script>
<!--        <style>
            #msg{
                color:green
            }
        </style>-->
    </head>
    <body>
        <div id="msg">
            
            test
        </div>
    </body>
</html>

