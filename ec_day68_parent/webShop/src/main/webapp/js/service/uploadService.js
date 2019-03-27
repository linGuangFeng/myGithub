app.service("uploadService",function($http){
	
	this.uploadFile = function(){
		// 向后台传递数据:
		var formData = new FormData();
		// 向formData中添加数据:
		formData.append("file",file.files[0]);
		
		return $http({
			method:'post',
			url:'../upload/uploadFile.do',
			data:formData,
            //在提交附件时，浏览器会添加enctype="multipart/form-data"属性的表单
			headers:{'Content-Type':undefined} ,// Content-Type : text/html  text/plain
			//将附件进行序列化
            transformRequest: angular.identity
		});
	}
	
});