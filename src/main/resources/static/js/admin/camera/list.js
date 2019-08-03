var Main = {
    methods: {
        add:function(){
            window.location.href="add.html";
        },
        edit:function(id){
            window.location.href="add.html?id="+id;
        },
        search:function(){
            this.loadData();
        },
        loadData:function(){
            var self = this;
            self.loading = true;
            self.tableData = [];
            toPost("/admin/camera/findAll",this.form,self,function (response) {
                self.loading = false;
                if(response != null && response.data != null){
                    var list = response.data.list;
                    for(var i = 0;i<list.length;i++){
                        var info = new Object();
                        info.id = list[i].id;
                        info.title = list[i].title;
                        info.tags = list[i].tags;
                        info.createTime = list[i].createTime;
                        info.updateTime = list[i].updateTime;
                        info.statu = list[i].statu === 1 ? "有效" : "删除";
                        info.lookNum = list[i].lookNum;
                        info.zanNum = list[i].zanNum;
                        self.tableData.push(info);
                    }
                    self.totals = response.data.total;
                    self.pageSize = response.data.pageSize;
                    self.pageNo = response.data.pageNum;
                }
            })
        },
        loadParam:function(){
            var self = this;
            toPost("/adminBlog/initData",null,self,function (response) {
                if(response.data.content != null){
                    self.menus = response.data.content.menus;
                    self.tags = response.data.content.tags;
                }
            })
        },
        getImages:function(id){
            var self = this;
            self.images.splice(0,self.images.length);
            toGet("/admin/camera/queryImgByCamId/"+id,null,self,function (response) {
                self.imageShow = true;
                for(var i = 0;i < response.data.length;i++){
                    var obj = new Object();
                    obj.url = response.data[i].url;
                    obj.id = response.data[i].id;
                    self.images.push(obj);
                }
                console.log(self.images);
            })
        },
        deleteById:function(id){
            var self = this;
            toGet("/admin/camera/deleteById/"+id,null,self,function (response) {
                var succ = response.data.success;
                self.$message({
                    showClose: true,
                    message: succ ? "删除成功" : "删除失败",
                    type: succ ? 'success' : 'false'
                });
                if(succ){
                    self.loadData();
                }
            });
        },
        recover:function(id){
            var self = this;
            toGet("/admin/camera/recover/"+id,null,self,function (response) {
                var succ = response.data.success;
                self.$message({
                    showClose: true,
                    message: succ ? "恢复成功" : "恢复失败",
                    type: succ ? 'success' : 'false'
                });
                if(succ){
                    self.loadData();
                }
            });
        },
        handleSizeChange:function(val) {
            this.form.pageSize = val;
            this.loadData();
        },
        handleCurrentChange:function(val) {
            this.form.pageNo = val;
            this.loadData();
        },
    },
    data() {
        return {
            images:[],
            imageShow:false,
            tableData: [],
            totals:0,
            form:{
                pageSize:10,
                pageNo:1,
                tags:""
            },
            loading: true
        }
    },
    mounted:function(){
        this.loadParam();
        this.loadData();
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')