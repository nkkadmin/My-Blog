var Main = {
    methods: {
        save(formName) {
            var self = this;
            self.$refs[formName].validate((valid) => {
                if (valid) {
                    toPost("/admin/tags/edit", this.editForm,self,function (response) {
                        self.$message({
                            showClose: response.data,
                            message: response.data ? '编辑成功' : '编辑失败',
                            type: 'success'
                        });
                        if (response.data) {
                            self.dialogVisible = false;
                            self.loadData();
                        }
                    })
                }else{
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        handleClose:function(){
            this.dialogVisible = false;
        },
        clear:function(){
            this.editForm.name = null;
            this.editForm.id = null;
        },
        add:function(){
            this.clear();
            this.dialogVisible = true;
        },
        edit(id){
            this.clear();
            this.findOne(id);
            this.dialogVisible = true;
        },
        findOne(id){
            var self = this;
            toGet("/admin/tags/selectById",{params:{"id":id}},self,function(response){
                if(response != null && response.data != null){
                    var info = response.data;
                    self.editForm.name = info.name;
                    self.editForm.id = info.id;
                }
            });
        },
        loadData(){
            var self = this;
            self.tableData = [];
            toPost("/admin/tags/findAll",{"pageNo":this.pageNo,"pageSize":this.pageSize},self,function(response){
                if(response != null && response.data != null){
                    var list = response.data.list;
                    for(var i = 0;i<list.length;i++){
                        var info = new Object();
                        info.id = list[i].id;
                        info.name = list[i].name;
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
        deleteById(id){
            var self = this;
            toGet("/admin/tags/deleteById",{params:{"id":id}},self,function(response){
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
        recoverById(id){
            var self = this;
            toGet("/admin/tags/recoverById",{params:{"id":id}},self,function (response) {
                if(response.data){
                    self.$message({
                        showClose: true,
                        message: '操作成功',
                        type: 'success'
                    });
                    self.loadData();
                }
            });
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.loadData();
        },
        handleCurrentChange(val) {
            this.pageNo = val;
            this.loadData();
        }
    },
    data() {
        return {
            tableData: [],
            totals:0,
            pageSize:10,
            pageNo:1,
            dialogVisible:false,
            editForm:{
                name:null,
                id:null
            },
            rules:{
                name:[
                    { required: true, message: '请输入标签名称', trigger: 'blur,change' },
                ]
            }
        }
    },
    mounted:function(){
        this.loadData();
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')