var Main = {
    methods: {
        handleClose:function(){
            this.dialogVisible = false;
        },
        save(formName) {
            var self = this;
            self.$refs[formName].validate((valid) => {
                if (valid) {
                    toPost("/admin/menus/edit",self.editForm,self,function (response) {
                        self.$message({
                            showClose: response.data,
                            message: response.data ? '编辑成功' : "编辑失败",
                            type: 'success'
                        });
                        if(response.data){
                            self.dialogVisible = false;
                            self.loadData();
                        }
                    })
                }else{
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        findOne(id){
            var self = this;
            toGet("/admin/menus/selectById",{params:{"id":id}},self,function (response) {
                if(response != null && response.data != null){
                    var info = response.data;
                    self.editForm.name = info.name;
                    self.editForm.url = info.url;
                    self.editForm.sortIndex = info.sortIndex;
                    self.editForm.id = info.id;
                }
            });
        },
        clear:function(){
            this.editForm.name = null;
            this.editForm.sortIndex = null;
            this.editForm.url = null;
            this.editForm.id = null;
        },
        add(){
            this.dialogVisible = true;
            this.clear();
        },
        edit(id){
            this.dialogVisible = true;
            this.clear();
            this.findOne(id);
        },
        loadData(){
            var self = this;
            self.tableData = [];
            toPost("/admin/menus/findAll",{"pageNo":this.pageNo,"pageSize":this.pageSize},self,function (response) {
                if(response != null && response.data != null){
                    var list = response.data.list;
                    for(var i = 0;i<list.length;i++){
                        var info = new Object();
                        info.id = list[i].id;
                        info.name = list[i].name;
                        info.sortIndex = list[i].sortIndex;
                        info.url = list[i].url;
                        info.createTime = list[i].createTime;
                        info.statu = list[i].statu === 1 ? "有效" : "删除";
                        self.tableData.push(info);
                    }
                    self.totals = response.data.total;
                    self.pageSize = response.data.pageSize;
                    self.pageNo = response.data.pageNum;
                }
            });
        },
        deleteById(id){
            var self = this;
            toGet("/admin/menus/deleteById",{params:{"id":id}},self,function(response){
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
            toGet("/admin/menus/recoverById",{params:{"id":id}},self,function(response){
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
                url:null,
                sortIndex:null,
                id:null
            },
            rules:{
                name:[
                    { required: true, message: '请输入菜单名称', trigger: 'blur,change' },
                    { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur,change' }
                ],
                sortIndex:[
                    { required: true, message: '请输入排序值', trigger: 'blur,change' }
                ],
                url:[
                    { required: true, message: '请输入链接地址', trigger: 'blur,change' }
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