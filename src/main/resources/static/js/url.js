function GetRequest() {
   var url = location.search; //获取url中"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function(fmt)
{ //author: meizz
   var o = {
      "M+" : this.getMonth()+1,                 //月份
      "d+" : this.getDate(),                    //日
      "h+" : this.getHours(),                   //小时
      "m+" : this.getMinutes(),                 //分
      "s+" : this.getSeconds(),                 //秒
      "q+" : Math.floor((this.getMonth()+3)/3), //季度
      "S"  : this.getMilliseconds()             //毫秒
   };
   if(/(y+)/.test(fmt))
      fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
   for(var k in o)
      if(new RegExp("("+ k +")").test(fmt))
         fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
   return fmt;
}
//封装axios请求  post
function toPost(url,_self,_callback){
    toPost(url,null,_self,_callback);
}
function toPost(url,data,_self,_callback){
    axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    axios.post(url,data).then(function(response){
       if(response.data == "unlogin"){
           toLogin(_self);
       }else{
           _callback(response);
       }
    });
}

//get
function toGet(url,_self,_callback){
   toGet(url,null,_self,_callback);
}
function toGet(url,data,_self,_callback){
    axios.get(url,data).then(function(response){
        if(response.data == "unlogin"){
            toLogin(_self);
        }else{
            _callback(response);
        }
    });
}

function toLogin(_self){
    _self.$notify.error({
        title: '错误',
        message: '登录失效，正在跳转到登录页面...'
    });
    setTimeout(function () {
        //刷新iframe父页面
        parent.location.reload();
    },2000);
}