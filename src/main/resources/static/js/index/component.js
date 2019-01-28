Vue.component('header-content', {
    data: function () {
        return {
            menuList:[],
            defaultIndex:"0"
        }
    },
    methods: {
        loadData(){
            var self = this;
            axios.post("/index/blog/index",null).then(function(response){
                console.log(response);
                if(response.data != null && response.data.menu != null){
                    self.menuList = response.data.menu;
                }
            });
        },
        hrefTo(url,index){
            window.location.href=url + "?index=" + index;
        },
        setIndex(){
            var param = GetRequest();
            if(Object.keys(param).length > 0 && param.index != null){
                this.defaultIndex = param.index;
            }else{
                this.defaultIndex = 0;
            }
        },
        handleSelect(){}
    },
    mounted:function(){
        this.setIndex();
        this.loadData();
    },
    template: '<el-header>' +
                '<el-menu :default-active="defaultIndex" class="el-menu-demo x_right" mode="horizontal" @select="handleSelect"' +
                    'background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">' +
                    '<el-menu-item @click="hrefTo(item.url,index)" :class="\'menuItem\'+index" v-for="(item,index) in menuList" :index="\'\'+index" :menu-id="item.id" >{{item.name}}</el-menu-item>' +
                    '<el-menu-item style="float: right;margin-right: 100px;">博客名称</el-menu-item>' +
                '</el-menu>' +
              '</el-header>'
});
Vue.component('footer-content', {
    template:'<el-footer>footer</el-footer>'
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