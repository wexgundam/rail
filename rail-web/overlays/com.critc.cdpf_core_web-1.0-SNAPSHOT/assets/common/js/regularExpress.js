/**
 * @anthor 杨超凡
 * @date 1017/11/3
 * 
 * 本js是对jquery.validator的自定义方法的封装
 * 只能用于正则表达验证
 * 页面必须调用jquery.validator.js
 * 
 * 共两个方法
 * 
 * regular 只添加一个正则自定义函数
 * 
 * regulararr 批量导入，传入对象即可。
 * 
 * 具体信息请看页面展示及函数参数说明
 * 
 * 以下是一些常用的正则表达式。
 */
//用户名验证
	var usernameExp = /^[a-zA-Z0-9_]{4,16}$/;

//姓名验证
	var nameExp = /^[\u4E00-\u9FA5A-Za-z]+$/;

//密码验证
	var pwdExp = /^[0-9a-zA-Z_#!@$%^&*]{6,16}$/;

//年龄验证
	var ageExp = /^(?:0|[1-9][0-9]?|120)$/;

//邮箱验证
	var emailExp = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;

//固定电话验证
	var telExp = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;

//手机号码验证
	var phoneExp = /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;

//英文名验证
	var enameExp = /^[a-zA-Z]+$/;

//邮政编码验证
	var postalcodeExp = /^[0-9][0-9]{5}$/;

//QQ验证
	var qqExp = /^\d{5,10}$/;

//非法字符验证
	var ffzfExp = //;

//IP验证
	var ipExp = /(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))/;

//非负整数验证
	var ffzsExp = /^(0|[1-9]\d*)$/;

//正负小数验证
	var zfxsExp = /^[+-]?\d*\.\d{1,3}$/;

//正负整数和小数验证
	var zfzshxsExp = /^(-)?\d+(\.\d+)?$/;

//身份证号验证
	var idcardExp = /^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|201[0-9]|202[0-9]|203[0-9])(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/i;

//限制输入
	var limitExp = /^((?!test).)*$/;
	
	
	/**
	 * 自定义正则匹配方法
	 * @param funName 自定义方法名 
	 * @param Exp 正则表达式
	 * @anthor 杨超凡
	 */
	function regular(funName,Exp){
		jQuery.validator.addMethod(funName, function(value, element) {
			return this.optional(element) || Exp.test(value);
		}, "不通过");
	}
	/**
	 * 批量添加自定义正则匹配方法
	 * @param objExp 对象
	 * 例如{	"postalcodeCheck":postalcodeExp}
	 * 
	 * @anthor 杨超凡
	 */
	function regularArr(objExp){
		$.each(objExp, function(i) {
			jQuery.validator.addMethod(i, function(value, element) {
				return this.optional(element) || objExp[i].test(value);
			}, "不通过");         
		});   
	}