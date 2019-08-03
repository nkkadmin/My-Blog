
new Vue({
    el: '#vueapp',
    data: {
        form:{
            id:"",
            title:"",
            tags:"",
            imagesList:[], //图片
            addOrDelImgs:[], //编辑时，用于记录删除以及添加的图片
            changeCover:{
                oldCover:null,
                newCover:null
            } //封面变化
        },
        isEnlargeImage:false,//是否显示放大图片
        enlargeImage: '',//放大图片地址
        base64:"",
        dialogImageUrl:"",
        dialogVisible: false,
        progress: 0,//上传进度
        pass: null,//是否上传成功
        updateOper:false, //是否为更新操作
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
        uploadSuccess:function(response, file, fileList){
            var self = this;
            this.pass = true;
            this.$message.success("上传成功")
            var obj = new Object();
            obj.url = response;
            obj.uid = file.uid;
            obj.cover = "0";
            this.form.imagesList.push(obj);
            if(self.updateOper){
                var operObj = new Object();
                operObj.type = "ADD";
                operObj.url = response;
                operObj.uid = file.uid;
                self.form.addOrDelImgs.push(operObj);
            }
        },
        uploadOnError:function(e,file){
            this.$message.error('上传失败!');
        },
        beforeUpload:function(file) {
            var self = this;
            var isJPG = file.type === 'image/jpeg';
            var isGIF = file.type === 'image/gif';
            var isPNG = file.type === 'image/png';
            var isBMP = file.type === 'image/bmp';
            var isLt2M = file.size / 1024 / 1024 < 1;
            if (!isJPG && !isGIF && !isPNG && isBMP) {
                this.$message.error('上传图片必须是JPG/GIF/PNG/BMP 格式!');
                return false;
            }
            console.log("压缩前：" + (file.size / 1024 / 1024) + "M");
            //压缩图片
            if(!isLt2M){
                self.photoCompress(file,{quality:0.5},function (base64Codes) {
                    console.log("压缩后：" + base64Codes.length / 1024 / 1024 + "M");
                    self.uploadFile(base64Codes,file);
                })
            }else{
                self.fileToBase64(file).then(function (response) {
                    self.uploadFile(response,file);
                })
            }
            return (isJPG || isGIF || isPNG || isBMP);
        },
        uploadFile:function(base64Codes,file){
            var self = this;
            toPost("/file/uploadBase64",{base64:base64Codes},self,function(response){
                if(response.data != null && response.data != ""){
                    self.uploadSuccess(response.data,file,null);
                }else{
                    self.uploadOnError(null,file);
                }
            })
        },
        handleFileRemove:function(file) {
            var self = this;
            var removeUid = file.uid;
             for (var i = 0; i < self.form.imagesList.length; i++) {
                 if(removeUid == self.form.imagesList[i].uid){
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
        handleFileEnlarge:function(_url){//放大图片
            if(_url){
                this.enlargeImage = _url;
                this.isEnlargeImage = !this.isEnlargeImage;
            }
        },
        /**
         * 更新封面：
         *      封面在原来的图片数据上更换，此时可得到old封面id和new封面id
         *      new封面在新增的封面图，无法得到new封面id，但封面值可以随新增图片insert
         * @param file
         */
        handleFileCover:function(file){//设为封面
            var self = this;
            var uid = file.uid;
            var inImageslist = false;
            var imgges = self.form.imagesList;
            for (var i = 0; i < imgges.length; i++) {
                if(uid == imgges[i].uid){
                    imgges[i].cover = '1';
                    if(typeof(imgges[i].id) != "undefined"){
                        inImageslist = true;
                        self.form.changeCover.newCover = imgges[i].id;
                    }
                } else{
                    if(imgges[i].cover == "1" && self.form.changeCover.oldCover == null){
                        self.form.changeCover.oldCover = imgges[i].id;
                    }
                    imgges[i].cover = '0';
                }
            }
            if(!inImageslist){
                for(var i = 0;i<self.form.addOrDelImgs.length;i++){
                    if(uid == self.form.addOrDelImgs[i].uid){
                        self.form.addOrDelImgs[i].cover = "1";
                    }
                }
            }
        },
        handleChange:function(file){
          this.beforeUpload(file.raw);
        },
        save:function(){//保存
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
        findOne:function(){
            var self = this;
            toGet("/admin/camera/queryById/"+this.form.id,null,self,function(response){
                if(response.data != null){
                    var info = response.data;
                    self.form.title = info.title;
                    self.form.tags = info.tags;
                    self.form.imagesList = info.imagesList;
                    for(var i = 0;i<self.form.imagesList.length;i++){
                        var obj = self.form.imagesList[i];
                        obj.uid = obj.id;
                    }
                }
            });
        },
        fileToBase64:function(file){
            return new Promise(function (resolve,reject) {
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function(e){
                    resolve(e.target.result);
                }
            });
        },
        /*
        三个参数
        file：一个是文件(类型是图片格式)，
        w：一个是文件压缩的后宽度，宽度越小，字节越小
        objDiv：一个是容器或者回调函数
        photoCompress()
         */
        photoCompress:function (file,w,objDiv) {
            var self = this;
            var ready=new FileReader();
            /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
            ready.readAsDataURL(file);
            ready.onload=function(){
                var re=this.result;
                self.canvasDataURL(re,w,objDiv)
            }
        },
        canvasDataURL:function(path, obj, callback){
            var img = new Image();
            img.src = path;
            img.onload = function(){
                var that = this;
                // 默认按比例压缩
                var w = that.width,
                    h = that.height,
                    scale = w / h;
                w = obj.width || w;
                h = obj.height || (w / scale);
                var quality = 0.7;  // 默认图片质量为0.7
                //生成canvas
                var canvas = document.createElement('canvas');
                var ctx = canvas.getContext('2d');
                // 创建属性节点
                var anw = document.createAttribute("width");
                anw.nodeValue = w;
                var anh = document.createAttribute("height");
                anh.nodeValue = h;
                canvas.setAttributeNode(anw);
                canvas.setAttributeNode(anh);
                ctx.drawImage(that, 0, 0, w, h);
                // 图像质量
                if(obj.quality && obj.quality <= 1 && obj.quality > 0){
                    quality = obj.quality;
                }
                // quality值越小，所绘制出的图像越模糊
                var base64 = canvas.toDataURL('image/jpeg', quality);
                // 回调函数返回base64的值
                callback(base64);
            }
        },
        /**
         * 将以base64的图片url数据转换为Blob
         * @param urlData
         *            用url方式表示的base64图片数据
         */
        convertBase64UrlToBlob(urlData){
            var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while(n--){
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new Blob([u8arr], {type:mime});
        },
        dataURLtoFile:function(dataurl, filename) {//将base64转换为文件
            var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while(n--){
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new File([u8arr], filename, {type:mime});
        }
    },
    mounted:function(){
        var self = this;
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