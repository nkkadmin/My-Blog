
new Vue({
    el: '#vueapp',
    data: {
        form:{
            id:"",
            title:"",
            tags:"",
            imagesList:[] //图片
        },
        base64:"",
        dialogImageUrl:"",
        dialogVisible: false
    },
    components: {

    },
    methods: {
        uploadSuccess(response, file, fileList){
            var obj = new Object();
            obj.url = file.response;
            obj.uid = file.uid;
            this.form.imagesList.push(obj);
            $(".el-upload-list").find("li").eq(this.form.imagesList.length-1).prepend("<el-button type='primary' class='set-cover' on-click='setCover'>封面</el-button>");
        },
        setCover:function(){
            console.log("...")
        },
        beforeUpload(file) {
            var isJPG = file.type === 'image/jpeg';
            var isLt2M = file.size / 1024 / 1024 < 2;
            if (!isJPG) {
                this.$message.error('图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        handleRemove(file, fileList) {
            var self = this;
            var removeUid = file.uid;
             for (var i = 0; i < self.form.imagesList.length; i++) {
                 if(removeUid == self.form.imagesList[i].uid){
                     self.form.imagesList.splice(i,1);
                     break;
                 }
             }
            toGet("/file/delFile",{params:{"filePath":file.response}},self,function(response){

            });

        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        initData(){
            var self = this;
            axios.post("/adminBlog/initData").then(function(response){
                console.log(response);

            })
        },
        save(){//保存
            var self = this;
            console.log(this.form);
           /* axios.post("/admin/camera/save",this.form).then(function(response){
                if(response.data){
                    self.$message({
                        showClose: true,
                        message: '保存成功',
                        type: 'success'
                    });
                }
            })*/
        },
        findOne(){
            var self = this;
            axios.get("/adminBlog/selectById",{params:{"id":this.form.id}}).then(function(response){
                if(response.data != null){
                    var info = response.data;
                    self.form.title = info.title;
                    self.form.content = info.content;
                    self.form.menuId = info.menu.id;
                    self.form.tagId = info.tags.id;
                }
            });
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