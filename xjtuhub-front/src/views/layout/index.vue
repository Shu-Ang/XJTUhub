<template>
  <div class="common-layout">
    <!-- <div id="Bgr"></div> -->
    <el-container>
      <el-header style="background-color:white">
        <!-- <h1 align="center" >欢迎访问XJTUhub</h1> -->
        <div id="Bgr"></div>
        <el-row gutter="10" class="demo-avatar demo-basic" justify="center">
          <el-col :span="7" @click="gohome">
            <img src="../../assets/xjtuhub.jpg" width="300" height="70" style="cursor: pointer" /></el-col>
          <el-col :span="5">
            <el-input v-model="searchInput" placeholder="查找文章或问题" maxlength="100" show-word-limit type="text" clearable>
              <template #prefix>
                <el-icon class="el-input__icon">
                  <search />
                </el-icon>
              </template>
            </el-input>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" v-on:click="JumpSearch" :icon="Search">搜索</el-button>
            <!-- <el-button type="primary" v-on:click="ShowSearch">搜索</el-button> -->
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" v-on:click="AddBlog()" :icon="Edit">发布</el-button></el-col>
          <el-col :span="1.5" v-if="!getLocalToken()">
            <el-button type="primary" @click="login()" class="btn btn-success" :icon="Position">登录</el-button>
          </el-col>
          <el-col :span="1.5" v-if="!getLocalToken()">
            <el-button type="primary" @click="register()" :icon="EditPen">注册</el-button></el-col>
          <el-col :span="1.5" v-if="getLocalToken()">
            <el-button type="primary" v-on:click="JumpUser()" :icon="User">个人中心</el-button></el-col>
          <el-col :span="1.5" v-if="!getLocalToken()">
            <div class="demo-basic--circle">
              <div class="block">
                <el-avatar v-on:click="login" shape="square" :size="50" :src="squareUrl" />
              </div>
            </div>
            <div class="sub-title" v-on:click="login">游客身份访问，使用更多功能请登录</div>

          </el-col>
          <el-menu :router="true" mode="horizontal" v-if="getLocalToken()" :ellipsis="false">

            <el-sub-menu>
              <template #title>
                <el-avatar :size="50" :fit="fit" :src="roleAvatar.url" />
                <!-- <img v-image-preview :src="roleAvatar.url" /> -->
              </template>
              <el-menu-item @click="JumpUser">
                {{ userId }}
              </el-menu-item>
              <el-menu-item @click="JumpUser">个人中心</el-menu-item>
              <el-menu-item>
                <el-popconfirm confirm-button-text="是" cancel-button-text="否" :icon="InfoFilled" icon-color="#626AEF"
                  @confirm="logout" @cancel="cancelEvent" title="确定退出登录吗？">
                  <template #reference>
                    退出登录
                  </template>
                </el-popconfirm>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
          <!-- </el-col> -->
        </el-row>
        <el-divider />
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { getLocalToken, removeLocalToken, setLocalToken, tip } from '@/common';
import { get, post } from "@/common";
import { useRouter, useRoute } from 'vue-router';
import { ElTree } from "element-plus";
import { ref, onMounted, reactive, watch, toRefs, computed } from "vue";
import axios from "axios";
import { Edit, EditPen, Position, Search, User } from '@element-plus/icons-vue';
import userApi from '@/api/user';
import infoApi from '@/api/info';
const router = useRouter();
const route = useRoute();

let localId = route.query.user;
const login = () => {
  //指定跳转地址
  router.push({ path: "/login" });
};
const register = () => {
  //指定跳转地址
  router.push({ path: "/register" });
};

//跳转搜索

const searchInput = ref('');

const JumpSearch = () => {
  let routeData = router.resolve({
    path: '/home',
    query: {
      user: localId,
      search: searchInput.value,
    }

  });
  window.open(routeData.href, '_blank')
};


//写文章，提问题
const AddBlog = () => {
  if (getLocalToken()) {
    router.push({
      path: '/edit',
      query:{
        user: localId
      }
    });
  }
  else { router.push({ path: "/login" ,query:{user: localId}}); }
};


let gohome = () => {
  router.push({ path: "/home", query: { user: localId} });
}
//跳转到个人中心
const JumpUser = async () => {
  router.push({ path: "/user", query: { info: localId ,user: localId} });
};

//请求默认头像（游客状态）
const defualtAvatar = reactive({
  circleUrl:
    'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
  squareUrl:
    'https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png',
  sizeList: ['small', '', 'large'],
})

const { circleUrl, squareUrl, sizeList } = toRefs(defualtAvatar)


const userId = ref()
//获取右上角户头像和id
const roleAvatar = reactive({
  fits: 'fill',
  url: '',
})

const getInfo = async () => {
  if(getLocalToken){
    await get("/info/getFuckingInfo",{ roleId: localId }).then(res => {
    if (res.success) {
      roleAvatar.url = res.data.faceAddr
      userId.value = res.data.roleId
    }
  })
  }else{
    return
  }
}
getInfo();

const { fit, url } = toRefs(roleAvatar)

//退出登录
const logout = () => {
  removeLocalToken();
  router.push({ path: '/' });
  setTimeout(function () {  //跳转后刷新
    location.reload();
  }, 5);
}

const cancelEvent = () => { //取消操作
  console.log('cancel!')
}

</script>

<style scoped>
.container {
  width: 100%;
}

.el-row {
  margin-bottom: 20px;
  /* margin-top: ; */
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.el-header {
  height: 100px;
}

.el-aside {
  height: calc(100vh - 100px);
}

.el-main {
  padding: 0;
  height: calc(100vh - 100px);
}

.demo-basic {
  text-align: center;
  display: flex;
  align-items: center;
}

.demo-basic .sub-title {
  margin-bottom: 10px;
  font-size: 1apx;
  color: var(--el-text-color-secondary);
}

.demo-basic .demo-basic--circle,
.demo-basic .demo-basic--square {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.demo-basic .block {
  flex: 1;
}

.demo-fit {
  display: flex;
  text-align: center;
  justify-content: space-between;
}

.demo-fit .block {
  flex: 1;
  display: flex;
  flex-direction: column;
  flex-grow: 0;
}

.demo-fit .title {
  margin-bottom: 10px;
  font-size: 14px;
  color: var(--el-text-color-secondary);
}

.me-title img {
  max-height: 2.4rem;
  max-width: 100%;
}

.me-title {
  margin-top: 10px;
  font-size: 24px;
}

.me-header-picture {
  width: 50px;
  height: 40px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
}
</style>