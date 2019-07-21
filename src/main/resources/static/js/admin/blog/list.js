var Main = {
    methods: {
        deleteComment:function(commentId){
            var self = this;
            var params = new URLSearchParams();
            params.append("commentId",commentId);
            toPost("/index/comment/delete",params,self,function (response) {
                self.$message({
                    showClose: true,
                    message: response.data.success ? "删除成功" : "删除失败",
                    type: response.data.success ? 'success' : 'error'
                });
                self.getComment();
            });
        },
        restComment:function(){
            this.commentList = [];
            this.commentForm.blogId = "";
            this.commentForm.pageNo = 1;
            this.commentForm.pageSize = 10;
            this.commentForm.totals = 0;
        },
        expandChange:function(row,expandedRows){
            this.restComment();
            if(this.expands.indexOf(row.id)>=0){
                //收起当前行
                this.expands.shift();
                return;
            }
            this.commentForm.blogId = row.id;
            this.getComment();
            if (expandedRows.length > 1) {
                //只展开当前选项
                expandedRows.shift();
            }
        },
        getRowKeys:function(row) {
            return row.id
        },
        getComment:function(){
            var self = this;
            self.commentList = [];
            toPost("/index/comment/getComment",self.commentForm,self,function (response) {
                if(response.data.list != null){
                    for(var i = 0;i<response.data.list.length;i++){
                        var comment = new Object();
                        comment.id = response.data.list[i].id;
                        comment.commentTime = response.data.list[i].createTime;
                        comment.content = response.data.list[i].content;
                        comment.statu = response.data.list[i].statu == 1 ? "有效" : "已删除";
                        self.commentList.push(comment);
                    }
                    self.commentForm.totals = response.data.total;
                    self.commentForm.pageSize = response.data.pageSize;
                    self.commentForm.pageNo = response.data.pageNum;
                }
            })
        },
        look:function(id){
            window.open("/blog/blog.html?id="+id);
        },
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
                        info.commentNum = list[i].commentNum == null ? 0 : list[i].commentNum;
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
            toPost("/adminBlog/initData",self,function (response) {
                if(value.data.content != null){
                    self.menus = value.data.content.menus;
                    self.tags = value.data.content.tags;

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
        handleCommentSizeChange:function(val) {
            this.commentForm.pageSize = val;
            this.getComment();
        },
        handleCommentCurrentChange:function(val) {
            this.commentForm.pageNo = val;
            this.getComment();
        }
    },
    data() {
        return {
            expands:[],
            commentList:[], //评论内容
            tableData: [],
            totals:0,
            menus:[],
            tags:[],
            commentForm:{
                blogId:"",
                pageNo:1,
                pageSize:10,
                totals:0,
                isAdmin:1
            },
            form:{
                pageSize:10,
                pageNo:1,
                menuId:null,
                tagId:null,
                blogTitle:null
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