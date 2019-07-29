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
            toPost("/adminBlog/findAll",this.form,self,function (response) {
                self.loading = false;
                if(response != null && response.data != null){
                    var list = response.data.list;
                    for(var i = 0;i<list.length;i++){
                        var info = new Object();
                        info.id = list[i].id;
                        info.title = list[i].title;
                        info.menuName = list[i].menuName;
                        info.tagName = list[i].tagName;
                        info.createTime = list[i].createTime;
                        info.statu = list[i].statu === 1 ? "有效" : "删除";
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
        deleteById:function(id){
            var self = this;
            toGet("/adminBlog/deleteById",{params:{"id":id}},self,function (response) {
                if(response.data){
                    self.$message({
                        showClose: true,
                        message: '删除成功',
                        type: 'success'
                    });
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