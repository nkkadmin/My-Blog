var Main = {
    methods: {
        dianzan:function(){
            var self = this;
            axios.get("/index/blog/dianZan/"+self.id,null).then(function(response){
                if(response.data > -1 ){
                    self.$message({
                        showClose: true,
                        message: '点赞成功',
                        type: 'success'
                    });
                }
                self.like = true
            });
        },
        getInfo:function(){
            var self = this;
            self.tableData = [];
            self.isAppend = false;
            axios.get("/index/blog/info/"+self.id,null).then(function(response){
                if(response.data != null && response.data != null){
                    self.info = response.data;
                    $("title").text(self.info.title);
                }
                self.loadComment();
            });
        },
        addLookNum:function(){ //添加浏览量
            axios.get("/index/blog/look/"+this.id,null).then(function(response){
                if(response.data != null && response.data != null){
                    //...
                }
            });
        },
        initParam:function(){
            var param = GetRequest();
            this.id = param.id;
            this.commentForm.blogId = param.id;
        },
        onSubmit:function(){
            var self = this;
            if(self.form.commonDesc == null || self.form.commonDesc === ""){
                this.$message({
                    showClose: true,
                    message: '请填写评论内容',
                    type: 'warning'
                });
                return;
            }
            var params={
                blogId:self.id,
                content:self.form.commonDesc
            }
            axios.post("/index/comment/save",params).then(function(response){
                self.$message({
                    showClose: true,
                    message: response.data.success ? "评论成功" : "评论失败",
                    type: response.data.success ? "success" : "error"
                });
                if(response.data.success){
                    self.form.commonDesc = null;
                    self.appendComent = false;
                    self.loadComment();
                }
            });
        },
        loadComment:function(){
            var self = this;
            if(!self.appendComent){
                self.commentList.splice(0,self.commentList.length);
                self.appendComent = true;
            }

            axios.post("/index/comment/getComment",this.commentForm).then(function(response){
                if(response.data != null){
                    var contentList = response.data.list;
                    for(var index in contentList){
                        self.commentList.push(contentList[index]);
                    }
                    self.commentForm.totalPage = response.data.pages;
                    self.commentNum = response.data.total;
                }
            });
        },
        more:function(){
            if(this.commentForm.totalPage > this.commentForm.pageNo){
                this.commentForm.pageNo = this.commentForm.pageNo + 1;
                this.loadComment();
            }
        }
    },
    data() {
        return {
            id:"",
            info:null,
            commentNum:0,
            form:{
                commonDesc:null
            },
            commentForm:{
                blogId:"",
                pageNo:1,
                pageSize:10,
                totalPage:0
            },
            commentList:[],
            appendComent:true,
            like: false
        }
    },
    mounted:function(){
        this.initParam();
        this.getInfo();
        this.addLookNum();
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')