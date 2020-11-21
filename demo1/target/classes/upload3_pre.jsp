<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div>
                <div class="form-group">
                    <label>请选择文件</label>
                    <input type="file" id="file" />
                </div>
                <div class="progress" style="width: 300px; height: 10px;background-color: #0000FE;">
                    <div class="progress-bar" style="height:100%; width: 0%; background-color: #00FF00;">0%</div>
                </div>
            </div>
            <script>
                // 获取文件选择空间
                var file = document.getElementById("file")
                // 获取进度条元素
                var pro = document.querySelector('.progress-bar')
                file.onchange = function(){
                    // 创建空的formData对象
                    var formData = new FormData();
                    // 将用户选择的文件追加到formData表单对象中
                    formData.append('arrtName',file.files[0])
                    // console.log(formData)
                    // 创建ajax对象
                    var xhr = new XMLHttpRequest();
                    // 对ajax对象进行配置
                    xhr.open('post','http://localhost:8080/upload2/post')
                    // 在文件上传过程中持续触发
                    xhr.upload.onprogress = function(ev){
                        // ev.loaded 文件已经上传了多少
                        // ev.total 上传文件的总大小
                        var result = (ev.loaded/ev.total)*100 +'%'
                        pro.style.width = result;
                        pro.innerHTML = result;
                    }
                    xhr.send(formData)
                    // 监听响应
                    xhr.onload = function(){
                        if(xhr.status == 200){
                            console.log(xhr.responseText)
                        }
                    }
                }
            </script>
            
          </body>
</html>