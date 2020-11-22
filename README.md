# ecl_work
#demo1
实现了IOC容器, MVC Web框架, 构建了简单的Web应用, 实现REST形式的Web服务, 能用JSP做视图, 能够处理文件上传
运行HttpServer.java
主要可用的网址有:
#http://localhost:8080/upload/get
此页面可以通过表单提交上传文本数据
#http://localhost:8080/upload2/get
此页面的表单使用了multiparty/form-data, 可以上传各种文件,返回json格式的数据
#http://localhost:8080/upload3/get
此页面使用了ajax加multiparty/form-data, 可以上传文件,不离开当前页面,返回响应消息
#http://localhost:8080/servlet/get
访问时动态编译jsp文件成java的servlet类,然后动态加载并运行servlet服务
#http://localhost:8080/Button.png
可以直接访问那些允许访问的资源, 返回的是直接流, 如果浏览器可以解析的话会直接显示, 不然会提示下载.
前面上传成功的话会返回新的添加了uid的文件名, 可以拿着去访问,下载
