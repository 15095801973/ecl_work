# demo1 
实现了IOC容器, MVC Web框架, 构建了简单的Web应用, 实现REST形式的Web服务, 能用JSP做视图, 能够处理文件上传 
### 使用方法
本地运行HttpServer.java 
打开浏览器
### 主要可用的网址有: 
1. /upload/get 
此页面可以通过表单提交上传文本数据
2. /upload2/get 
此页面的表单使用了multiparty/form-data, 可以上传各种文件,返回json格式的数据 
3. /upload3/get 
此页面使用了ajax加multiparty/form-data, 可以异步上传文件,不离开当前页面的情况下, 返回响应消息 
4. /servlet/get 
访问jsp时动态编译jsp文件成java的servlet类,然后动态加载并通过反射调用运行servlet服务 
5. /Button.png 
实现了将服务器上的文件资源以字节流的形式返回. 用户可以直接访问那些允许访问的资源, 返回的是二进制流, 如果浏览器可以解析的话会直接显示, 不然会提示下载. 前面上传文件成功的话会返回新的添加了uid的文件名, 可以拿着去访问,下载. 这也为页面显示图片和显示网页图标的功能提供了基础
