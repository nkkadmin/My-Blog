
new Vue({
    el: '#vueapp',
    data: {
        form:{
            id:"",
            title:"",
            tags:"",
            imagesList:[], //图片
            addOrDelImgs:[], //编辑时，用于记录删除以及添加的图片
        },
        isEnlargeImage:false,//是否显示放大图片
        enlargeImage: '',//放大图片地址
        base64:"",
        dialogImageUrl:"",
        dialogVisible: false,
        progress: 0,//上传进度
        pass: null,//是否上传成功
        updateOper:false //是否为更新操作
    },
    computed: {
        proStatus(){//上传状态
            if(this.pass){
                return 'success'
            }else if(this.pass == false){
                return 'exception'
            }else{
                return ''
            }
        }
    },
    components: {

    },
    methods: {
        uploadOnProgress(e,file){//开始上传
            this.progress = Math.floor(e.percent)
        },
        uploadOnChange(file){
            if(file.status == 'ready'){
                //重置progress组件
                this.pass = null;
                this.progress = 0;
            }else if(file.status == 'fail'){
                this.$message.error("图片上传出错，请刷新重试！")
            }
        },
        uploadSuccess(response, file, fileList){
            var self = this;
            this.pass = true;
            this.$message.success("上传成功")
            var obj = new Object();
            obj.url = file.response;
            obj.id = file.uid;
            this.form.imagesList.push(obj);
            if(self.updateOper){
                var operObj = new Object();
                operObj.id = file.id;
                operObj.type = "ADD";
                operObj.url = file.response;
                self.form.addOrDelImgs.push(operObj);
            }
        },
        uploadOnError(e,file){
            console.log(e)
            this.pass = false;
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
        handleFileRemove(file) {
            var self = this;
            var removeUid = file.id;
             for (var i = 0; i < self.form.imagesList.length; i++) {
                 if(removeUid == self.form.imagesList[i].id){
                     self.form.imagesList.splice(i,1);
                     break;
                 }
             }
            toGet("/file/delFile",{params:{"filePath":file.url}},self,function(response){
            });
             if(self.updateOper){
                 var obj = new Object();
                 obj.id = file.id;
                 obj.type = "REMOVE";
                 self.form.addOrDelImgs.push(obj);
             }
        },
        handleFileEnlarge(_url){//放大图片
            if(_url){
                this.enlargeImage = _url;
                this.isEnlargeImage = !this.isEnlargeImage;
            }
        },
        handleFileCover(file){//设为封面
            var self = this;
            var uid = file.id;
            for (var i = 0; i < self.form.imagesList.length; i++) {
                if(uid == self.form.imagesList[i].id){
                    self.form.imagesList[i].cover = '1';
                }else{
                    self.form.imagesList[i].cover = '0';
                }
            }
        },
        initData(){
            var self = this;
            axios.post("/adminBlog/initData").then(function(response){
                console.log(response);

            })
        },
        save(){//保存
            var self = this;
            if(!self.checkSaveParam()){
                return false;
            }
            toPost("/admin/camera/save",this.form,self,function(response){
                if(response.data.success){
                    self.$message({
                        showClose: true,
                        message: '保存成功',
                        type: 'success'
                    });
                    setTimeout(function () {
                        window.location.href = "list.html";
                    },1000);
                }else{
                    self.$message.error("保存失败");
                }

            })
        },
        checkSaveParam:function(){
            var self = this;
            if(self.form.title == null || self.form.title == ''){
                this.$message.error("请输入标题");
                return false;
            }

            if(self.form.tags == null || self.form.tags == ''){
                this.$message.error("请输入标签");
                return;
            }

            if(self.form.imagesList.length <= 0){
                this.$message.error("请上传图片");
                return false;
            }
            var hasCover = false;
            for (var i = 0; i < self.form.imagesList.length; i++) {
                if(self.form.imagesList[i].cover == '1'){
                    hasCover = true;
                    break;
                }
            }
            if(!hasCover){
                this.$message.error("请设置封面图");
                return false;
            }
            return true;
        },
        findOne(){
            var self = this;
            toGet("/admin/camera/queryById/"+this.form.id,null,self,function(response){
                if(response.data != null){
                    var info = response.data;
                    self.form.title = info.title;
                    self.form.tags = info.tags;
                    self.form.imagesList = info.imagesList;
                }
            });
        }
    },
    mounted:function(){
        var self = this;
        this.initData();
        var param = GetRequest();
        if(Object.keys(param).length > 0){
            this.form.id = param.id;
            if(typeof(param.id) != "undefined" ){
                self.updateOper = true;
            }
            self.findOne();
        }
    }
})