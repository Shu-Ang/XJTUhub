<template>
  <el-container>
    <el-main>
      <div class="content">
        <div class="PersonTop">
          <div class="PersonTop_img">
            <img v-image-preview :src="userInfo.avatar" />
          </div>
          <div class="PersonTop_text">
            <div class="user_text">
              <div class="userId">
                <span>{{ localId }}</span>
              </div>
              <div class="userSign">
                <span>{{ userInfo.sign }}</span>
              </div>
              <div class="userEmail">
                <span>{{ userInfo.email }}</span>
              </div>
              <div class="userEdit">
                <el-button class="el-icon-edit" v-if="getLocalToken() && isMypage" type="primary" size="medium" plain
                  @click="showDia"><el-icon>
                    <EditPen />
                  </el-icon> 编辑
                </el-button>
                <el-button v-if="getLocalToken() && !isMypage" @click="changeFollow" type="primary" size="medium"
                  icon="el-icon-check" v-text="isFollow ? '已关注' : '关注'
                    ">
                </el-button>
                <el-upload v-if="isMypage" class="avatar-uploader" :show-file-list="false"
                  :before-upload="beforeAvatarUpload" :http-request="handleAvatarUpload">

                  <el-button class="avatar-uploader-icon" type="info" plain>
                    <Plus />
                    <el-icon>
                      <Upload />
                    </el-icon>上传头像
                  </el-button>
                  <!-- <el-button class="avatar-uploader-icon"><Plus /></el-button> -->
                </el-upload>
              </div>
            </div>
            <div class="user_num">
              <div style="cursor: pointer" @click="toMyFan">
                <div class="num_number">{{ fanCounts }}</div>
                <span class="num_text">粉丝</span>
              </div>
              <div style="cursor: pointer" @click="toMyArticle">
                <div class="num_number">{{ blogCounts }}</div>
                <span class="num_text">文章</span>
              </div>
              <div style="cursor: pointer" @click="toMyQuestion">
                <div class="num_number">{{ questionCounts }}</div>
                <span class="num_text">问题</span>
              </div>
              <div style="cursor: pointer" @click="toMyCollect">
                <div class="num_number">{{ collectCounts }}</div>
                <span class="num_text">收藏</span>
              </div>
              <div style="cursor: pointer" @click="countView">
                <div class="num_number">{{ viewCounts }}</div>
                <span class="num_text">浏览</span>
              </div>
            </div>
          </div>
        </div>
        <div class="person_body">
          <div class="person_body_left">
            <el-card class="box-card" :body-style="{ padding: '0px' }">
              <div slot="header" class="clearfix">
                <span class="person_body_list" style="border-bottom: none">个人中心
                </span>
              </div>
              <el-menu router active-text-color="#00c3ff" class="el-menu-vertical-demo">
                <el-menu-item index="myarticle" @click="toMyArticle">
                  <el-icon>
                    <Edit />
                  </el-icon>
                  <span slot="title">发帖</span>
                </el-menu-item>
                <el-menu-item index="myquestion" @click="toMyQuestion">
                  <el-icon>
                    <Help />
                  </el-icon>
                  <span slot="title">问题</span>
                </el-menu-item>
                <el-menu-item index="mycollect" @click="toMyCollect">
                  <el-icon>
                    <Star />
                  </el-icon>
                  <span slot="title">收藏</span>
                </el-menu-item>
                <el-menu-item index="myDraft" @click="toMyDraft" v-if="isMypage">
                  <el-icon>
                    <Document />
                  </el-icon>
                  <span slot="title">草稿</span>
                </el-menu-item>
                <el-menu-item index="myfan" @click="toMyFan">
                  <el-icon>
                    <Bowl />
                  </el-icon>
                  <span slot="title">粉丝</span>
                </el-menu-item>
                <el-menu-item index="myfollow" @click="toMyFollow">
                  <el-icon>
                    <CirclePlus />
                  </el-icon>
                  <span slot="title">关注</span>
                </el-menu-item>
                <el-menu-item index="myfootprint" @click="toFootprint">
                  <el-icon>
                    <Clock />
                  </el-icon>
                  <span slot="title">历史记录</span>
                </el-menu-item>
              </el-menu>
            </el-card>
          </div>
          <div class="person_body_right">
            <router-view></router-view>
          </div>
        </div>
      </div>
    </el-main>
    <el-dialog title="修改个人信息" width="60%" :before-close="handleClose" v-model="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" label-width="150px">
        <div class="updateinfo">
          <div class="left">
            <el-form-item label="个性签名" prop="sign">
              <el-input v-model="userInfo.sign"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="userInfo.email"></el-input>
            </el-form-item>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submit">提 交</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>


<script setup>
import { ref, reactive, toRefs } from 'vue'
import axios from 'axios'
import { useRouter, useRoute } from 'vue-router'
import { get, post, setLocalToken, getLocalToken, removeLocalToken, tip } from "@/common";
import { InfoFilled } from '@element-plus/icons-vue'
import { Plus } from '@element-plus/icons-vue'
import userApi from '@/api/user.js'
import myfollow from './MyFollow.vue'
import { h } from 'vue'
import { ElMessage } from 'element-plus'

const router = useRouter();
const route = useRoute();

let localId = route.query.info;
let userId = route.query.user;

// 上传图片之前做简单验证
const beforeAvatarUpload = rawFile => {
  if (!rawFile.type.startsWith('image/')) {
    tip.error('只能上传图片格式!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 5) {
    tip.error('上传文件不能大于5MB!')
    return false
  }
  return true
}

// 上传图片
const handleAvatarUpload = uploadFile => {
  const formData = new FormData()
  formData.append('image', uploadFile.file)
  axios.post('/dev/blog/uploadImg', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then((res) => {
    post('/info/editInfo', {
      roleId: userId,
      faceAddr: res.data.data.imgUrl
    })
    location.reload();
  })
}


//判断是自己的主页还是别人的主页
var isMypage = ref();

let followCounts = ref();
let fanCounts = ref();
let blogCounts = ref();
let questionCounts = ref();
let collectCounts = ref();
let viewCounts = ref();

//请求主页个人数据；数据统计（粉丝、关注、文章、问题、收藏、浏览数等）
const userInfo = reactive({
  avatar: '',
  sign: '',
  email: ''
});


var isFollow = ref();

const getUserInfo = async () => {
  if (userId == localId) {     //判断是否是自己主页
    isMypage.value = true;
  } else {
    isMypage.value = false;
    await get('/info/isFollow',
      {    //判断是否关注
        roleId: userId,
        writerId: localId
      })
      .then((res) => {
        isFollow = res.data;
      })
  }

  await get('/info/getInfo', {
    roleId: localId
  })
    .then((res) => {
      userInfo.avatar = res.data.faceAddr;
      userInfo.email = res.data.email;
      userInfo.sign = res.data.sign;
    })
  await get("/info/countFans", {
    roleId: localId
  })
    .then((res) => {
      fanCounts.value = res.data;
    })
  await get("/info/countArticle", {
    roleId: localId
  })
    .then((res) => {
      blogCounts.value = res.data;
    })
  await get("/info/countQuestion", {
    roleId: localId
  })
    .then((res) => {
      questionCounts.value = res.data;
    })
  await get("/info/countFavorite", {
    roleId: localId
  })
    .then((res) => {
      collectCounts.value = res.data;
    })
  await get("/info/countViews", {
    roleId: localId
  })
    .then((res) => {
      viewCounts.value = res.data;
    })
}
setTimeout(function () { //防止get重复请求被拦截
  getUserInfo();
}, 2);

//打开文章列表
const toMyArticle = () => {
  router.push({ path: '/user/myarticle', query: { info: localId, user: userId } });
}
//打开问题列表
const toMyQuestion = () => {
  router.push({ path: '/user/myquestion', query: { info: localId, user: userId } });
}
//打开收藏列表
const toMyCollect = () => {
  router.push({ path: '/user/mycollectlist', query: { info: localId, user: userId } });
}
//打开粉丝列表
const toMyFan = () => {
  router.push({ path: '/user/myfan', query: { info: localId, user: userId } });
}
//打开关注列表
const toMyFollow = () => {
  router.push({ path: '/user/myfollow', query: { info: localId, user: userId } });
}
//打开草稿列表
const toMyDraft = () => {
  router.push({ path: '/user/mydraft', query: { info: localId, user: userId } });
}
//打开历史记录
const toFootprint = () => {
  router.push({ path: '/user/myfootprint', query: { info: localId, user: userId } });
}

//打开编辑弹窗按钮
var dialogVisible = ref(false);

const showDia = () => {
  dialogVisible.value = true;
};
const handleClose = () => {
  dialogVisible.value = false;
}

const submit = async () => {
  //提交个人信息修改
  await post("/info/editInfo", {
    roleId: userId,
    faceAddr: userInfo.avatar,
    email: userInfo.email,
    sign: userInfo.sign
  })
    .then((res) => {
      dialogVisible = false;
      location.reload();
    })
}

//关注操作

const addFollow = async () => {
  await userApi.getUserId()
    .then(res => {
      post('/info/follow', {
        roleId: res.data.userId,
        writerId: localId
      })
        .then((res) => {
          ElMessage({
            showClose: true,
            message: "已成功关注",
            type: "success",
          });
          location.reload();
        })
    })
}

const deleteFollow = async () => {
  await userApi.getUserId()
    .then(res => {
      post('/info/unFollow', {
        roleId: res.data.userId,
        writerId: localId
      })
        .then((res) => {
          ElMessage({
            showClose: true,
            message: "已取消关注",
            type: "success",
          });
          location.reload();
        })
    })
}

const changeFollow = () => {
  if (!isFollow) addFollow();
  else deleteFollow();
}

</script>

<style scoped>
.PersonTop {
  width: 1000px;
  height: 160px;
  padding-top: 0px;
  background-color: white;
  margin-top: 30px;
  margin-bottom: 30px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  border-radius: 10px;
  border: black;
}

.PersonTop_img {
  width: 150px;
  height: 150px;
  background-color: #8c939d;
  /* margin-right: 24px;
  margin-left: 20px; */
  overflow: hidden;
  border-radius: 20px;
  border: black;
}

.PersonTop_img img {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.PersonTop_text {
  height: 120px;
  width: 880px;
  display: flex;
}

.user_text {
  width: 60%;
  height: 100%;
  line-height: 30px;
}

.userId {
  font-weight: bold;
}

F .userSign {
  font-size: 14px;
  color: #999;
}

.user_num {
  width: 40%;
  height: 100%;
  display: flex;
  align-items: center;
}

.user_num>div {
  text-align: center;
  border-right: 1px dotted #999;
  box-sizing: border-box;
  width: 80px;
  height: 40px;
  line-height: 20px;
}

.num_text {
  color: #999;
}

.num_number {
  font-size: 20px;
  color: #333;
}

.el-menu-item>span {
  font-size: 16px;
  color: #999;
}

.person_body {
  width: 1000px;
  margin-top: 210px;
  display: flex;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 5px;
}

.person_body_left {
  width: 27%;
  height: 1000px;
  border-radius: 5px;
  margin-right: 3%;
  text-align: center;
}

.person_body_list {
  width: 100%;
  height: 50px;
  margin-top: 25px;
  font-size: 22px;
  border-bottom: 1px solid #f0f0f0;
  background-image: -webkit-linear-gradient(left,
      rgb(42, 134, 141),
      #e9e625dc 20%,
      #3498db 40%,
      #e74c3c 60%,
      #09ff009a 80%,
      rgba(82, 196, 204, 0.281) 100%);
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
  -webkit-background-size: 200% 100%;
  -webkit-animation: masked-animation 4s linear infinite;
  background-clip: text;
  background-size: 200% 100%;
  animation: masked-animation 4s linear infinite;

}

.el-menu-item {
  margin-top: 22px;
}

.person_body_right {
  width: 70%;
  /* height: 500px; */
  border-radius: 5px;
  background-color: white;
}

.box-card {
  height: 600px;
}

/*ui样式*/
.el-button {
  width: 84px;
}

.content {
  display: flex;
  justify-content: space-between;
  /* justify-content: star; */
  padding: 0 10px;
}

.topuser {
  display: flex;
  padding: 10px;
  justify-content: space-between;

}

.bottomContent {
  padding: 10px;

}

.imgbox {
  width: 120px;
  height: 120px;
  margin-bottom: 20px;
  border: 1px solid black;
}

.imgbox img {
  width: 100%;
  height: 100%;
}

.userInfo {
  width: 80%;
  height: 120px;
  border: 1px solid black;
}

.leftinfo {
  width: 60%;
  border: 1px solid black;
}

.rightEdit {
  width: 30%;
  /* border: 1px solid red; */
}

.datastatis {
  width: 100%;
  height: 120px;
  margin-bottom: 10px;
  border-radius: 2px;
  font-size: 25px;
  color: white;
  text-align: center;
  line-height: 120px;
}

#backg {
  background: url('@/assets/xjtuback.jpg');
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}

.singleBlog {
  padding: 20px;
  margin: 20px 0;
  background: rgba(255, 250, 240, 0.9);
  /* background-color: ; */
}

body {
  margin: 0;
}

.example-showcase .el-loading-mask {
  z-index: 9;
}
</style>
