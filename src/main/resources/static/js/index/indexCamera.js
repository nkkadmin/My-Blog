
var Main = {
    methods: {
        loadCream:function () {
            var self = this;
            self.issuesHeight = 0;

            var param = {
                "pageNo":self.pageNo,
                "pageSize":self.pageSize
            }
            axios.post("/index/camrea/queryAll", param).then(function (response) {
                console.log(response);
                if(response.data.cameraIndexVos != null){
                    self.listData = response.data.cameraIndexVos;
                }
                if(response.data.allInTags != null){
                    self.tagList = response.data.allInTags;
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
        }
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
            dialogList:[]
        }
    },
    mounted: function () {
        this.loadCream();
    }
}
var Ctor = Vue.extend(Main)
new Ctor().$mount('#app')