Vue.use(VueQuillEditor)
new Vue({
    el: '#vueapp',
    data: {
        form:{
            id:"",
            title:"",
            content:"",
            menuId:"",
            tagId:"",
            coverPic:""
        },
        base64:"",
        dialogImageUrl:"",
        dialogVisible: false,
        menus:[],
        tags:[],
        editorOption: {
            theme: 'snow'
        }
    },
    components: {
        LocalQuillEditor: VueQuillEditor.quillEditor
    },
    methods: {
        goBack:function(){
          window.location.href = "list.html";
        },
        initData(){
            var self = this;
            toPost("/adminBlog/initData",null,self,function (response) {
                if(response.data.content.menus != null){
                    for(var i = 0;i<response.data.content.menus.length;i++){
                        var info = response.data.content.menus;
                        var menu = new Object();
                        menu.name = info[i].name;
                        menu.id = info[i].id;
                        self.menus.push(menu);
                    }
                }
                if(response.data.content.tags != null){
                    for(var i = 0;i<response.data.content.tags.length;i++){
                        var info = response.data.content.tags;
                        var tag = new Object();
                        tag.name = info[i].name;
                        tag.id = info[i].id;
                        self.tags.push(tag);
                    }
                }
            });

        },
        save(){//保存
            var self = this;
            toPost("/adminBlog/edit",this.form,self,function (response) {
                self.$message({
                    showClose: response.data.success,
                    message: response.data.msg,
                    type: response.data.success ? 'success' : 'error'
                });
                if(response.data.success){
                    setTimeout(function () {
                        window.location.href = "list.html";
                    },1000);
                }
            });
        },
        findOne(){
            var self = this;
            toGet("/adminBlog/selectById",{params:{"id":this.form.id}},self,function (response) {
                if(response.data != null){
                    var info = response.data;
                    self.form.title = info.title;
                    self.form.content = info.content;
                    self.form.menuId = info.menuId;
                    self.form.tagId = info.tagId;
                    self.form.coverPic = info.coverPic;
                }
            })
        },
        changeFile:function(file, fileList){
            console.log(file);
        },
        uploadSuccess(response, file, fileList){
            this.form.coverPic = response;
        },
        beforeAvatarUpload(file) {
            var isJPG = file.type === 'image/jpeg';
            var isLt2M = file.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        }
    },
    mounted:function(){
        this.initData();
        var param = GetRequest();
        if(Object.keys(param).length > 0){
            this.form.id = param.id;
            this.findOne();
        }
    }
})