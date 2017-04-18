/****
 * 该插件依赖于 jquery-1.3.2.min.js 和 jquery.blockUI.js
 */
Edo.util.loading = new (function(){
	//打开遮罩
	this.open = function(){
		return function(){
			$.blockUI({
				message:'<h1>请稍后...<h1>',
				css: { 
		            border: 'none', 
		            padding: '15px', 
		            backgroundColor: '#000', 
		            '-webkit-border-radius': '10px', 
		            '-moz-border-radius': '10px', 
		            opacity: .5,
		            color: '#fff' 
		        }
	        });
		}
	}();
	//关闭遮罩
	this.close = function(){
		return function(){
			$.unblockUI();
		}
	}();
})();
Edo.util.Loading = Edo.util.loading;