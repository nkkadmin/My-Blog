
var Main = {
    methods: {
        loadCream:function () {
            var self = this;
            var param = {
                "pageNo":self.pageNo,
                "pageSize":self.pageSize
            }
            axios.post("/index/camrea/queryAll", param).then(function (response) {
                var cameraList = response.data.cameraIndexVos;
                var tagList = response.data.allInTags;
                if(self.appendList){
                    for(var i = 0;i<cameraList.length;i++){
                        self.listData.push(cameraList[i]);
                    }
                    for(var i = 0;i<tagList.length;i++){
                        self.tagList.push(tagList[i]);
                    }
                }else{
                    self.listData = cameraList;
                    self.tagList = tagList;
                }

                if(response.data.pageNo < response.data.pages){
                    self.hasNextPage = true;
                }else{
                    self.hasNextPage = false;
                }
                setTimeout(function () {
                    $('#list>li').picEyes();
                },500)
            });
        },
        nextPage:function () {
            this.pageNo++;
            this.loadCream();
        },
        selectImg:function (id) {
            var self = this;
            self.dialogList.splice(0,self.dialogList.length);
            axios.get("/index/camrea/queryImgByCamId/"+id).then(function (response) {
                if(response.data != null){
                    self.dialogList = response.data;
                }
            })
        },
        showMore:function(){
            if(this.hasNextPage){
                this.appendList = true;
                this.pageNo++;
                this.loadCream();
            }else{
                $('#isotopeShowMore').hide();
            }
        },
    },
    data() {
        return {
            pageNo:1,
            pageSize:10,
            hasNextPage:false,
            currentMenuId: '',
            menuList: [],
            listData: [],
            tagList:[],
            dialogList:[],
            appendList:false,
        }
    },
    mounted: function () {
        this.loadCream();
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')