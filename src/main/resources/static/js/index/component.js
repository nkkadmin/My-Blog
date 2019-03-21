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
                    'background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">' +
                    '<el-menu-item @click="hrefTo(item.url,index)" :class="\'menuItem\'+index" v-for="(item,index) in menuList" ' +
                    ':index="\'\'+index" :menu-id="item.id" >{{item.name}}</el-menu-item>' +
                    '<el-menu-item style="float: right;margin-right: 100px;" index="10" disabled>博客名称</el-menu-item>' +
                '</el-menu>' +
              '</el-header>'
});
Vue.component('footer-content', {
    template:'<el-footer>Copyright © 2019</el-footer>'
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
   template:'<div v-show="showTop" class="top" @click="goTop"><img src="../../img/index/top-icon.png"/></div>'
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
