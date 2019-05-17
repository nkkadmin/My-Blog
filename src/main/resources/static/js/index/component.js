Vue.component('header-content', {
    data: function () {
        return {
            menuList:[],
            defaultIndex:'0'
        }
    },
    methods: {
        loadData:function(){
            var self = this;
            axios.post("/index/blog/index",null).then(function(response){
                if(response.data != null && response.data.menu != null){
                    self.menuList = response.data.menu;
                }
            });
        },
        hrefTo:function(url,index){
            window.location.href=url + "?index=" + index;
        },
        setIndex:function(){
            var param = GetRequest();
            if(Object.keys(param).length > 0 && param.index != null){
                this.defaultIndex = param.index+"";
            }else{
                this.defaultIndex = '0';
            }

        }
    },
    mounted:function(){
        this.setIndex();
        this.loadData();
    },
    template: '<el-header>' +
                '<el-menu :default-active="defaultIndex" class="el-menu-demo x_right" mode="horizontal"' +
                    'text-color="#6e7da2" active-text-color="#db996c">' +
                    '<el-menu-item @click="hrefTo(item.url,index)" :class="\'menuItem\'+index" v-for="(item,index) in menuList" ' +
                    ':index="\'\'+index" :menu-id="item.id" >{{item.name}}</el-menu-item>' +
                    '<el-menu-item style="float: right;" index="10" disabled>不加班的程序员</el-menu-item>' +
                '</el-menu>' +
              '</el-header>'
});
Vue.component('footer-content', {
    template:'<el-footer>Copyright © 2019 LUKE</el-footer>'
});
Vue.component('top',{
    data:function(){
        return {
            showTop:false
        }
    },
    methods:{
      goTop:function () {
          console.log($(document).scrollTop())
          window.scroll(0,0);
      },
      handleScroll:function(){
          var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
          if(scrollTop >= 300){
              this.showTop = true;
          }else{
              this.showTop = false;
          }
      },
    },
    mounted () {
        window.addEventListener('scroll', this.handleScroll)
    },
   template:'<div v-show="showTop" class="top" @click="goTop"><svg class="icon" style="width: 30px; height: 30px; color: #aedadd;vertical-align: middle;fill: currentColor;overflow: hidden;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="7925"><path d="M421.888 28.672l249.856 320.512c18.944 22.528 18.944 59.392 0 81.92s-49.664 22.528-68.608 0L435.2 208.896v746.496c0 32.256-22.016 58.368-48.64 58.368s-48.64-26.112-48.64-58.368V68.608C337.92 36.352 359.936 10.24 386.56 10.24c14.336 0.512 26.624 7.168 35.328 18.432z" p-id="7926"></path></svg></div>'
});
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
