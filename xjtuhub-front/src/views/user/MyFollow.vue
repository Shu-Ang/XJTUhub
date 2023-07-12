<template>
  <div class="fanorfollow_box">
    <h4>我的关注</h4>
    <div class="fanorfollow" v-for="(item, index) in allData" :key="index">
      <div class="fanorfollow_left">
        <img class="fanorfollow_img" v-image-preview :src="item.faceAddr" />
      </div>
      <div class="fanorfollow_info">
        <div class="fanorfollow_info_top">
          <span
            style="color: #666; max-width: 180px"
            @click="personal(item.roleId)"
            >{{ item.roleId }}</span
          >
        </div>
        <div class="fanorfollow_info_bottom">
          <span @click="personal(item.roleId)">{{ item.sign }}</span>
        </div>
      </div>
      <div class="fanorfollow_botton">
        <el-button
          @click="follow(item.roleId)"
          type="primary"
          size="small"
          round
          icon="el-icon-check"
          v-text="isfollowid.indexOf(item.roleId) > -1 ? '已关注' : '关注'"
        ></el-button>
      </div>
    </div>
    <div>
      <el-empty
        v-if="allData.length == 0"
        :image-size="250"
        description="这里什么都没有"
      ></el-empty>
    </div>
  </div>
</template>

<script setup>
import userApi from '@/api/user.js';
import { ref, reactive, defineProps } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import { useRouter, useRoute } from 'vue-router';
import { h } from 'vue'
import { ElMessage } from 'element-plus'

const router = useRouter();
const route = useRoute();

const allData = ref([]);

// var isfollow = ref(true);
const isFollow = (writerId) => {
  get("/info/isFollow", {
    roleId: localId,
    writerId: writerId
  })
  .then((res) => {
    console.log("isFollow", writerId, res.data)
    if(res.data) return true
    else return false
  })
};

const followData = ref({
  fanId: '',
  followId: ''
});

const isfollowid = ref([]);

let myuserId = route.query.myuserId; 
let localId = route.query.localId;

const getList = () => {
  get('/info/selectFollowList', {
    roleId: myuserId
  })
  .then((res) => {
    allData.value = res.data;
    res.data.forEach((element) => {
      isfollowid.value.push(element.roleId);
    });
  })
  console.log("FollowArray", isfollowid.value);
};

getList();



const addFollow = async (id) => {
  await userApi.getUserId(getLocalToken)
  .then(res => {
      post('/info/follow', {
        roleId: res.data.userId,
        writerId: id
      })
      .then((res) => {
        console.log(res.data);
        // isfollow = true;
        ElMessage({
          showClose: true,
          message: "已成功关注",
          type: "success",
        });
        location.reload();
    })
  })
}

const deleteFollow = async (id) => {
  await userApi.getUserId(getLocalToken)
  .then(res => {
      post('/info/unFollow', {
        roleId: res.data.userId,
        writerId: id
      })
      .then((res) => {
        console.log(res.data);
        // isfollow = false;
        ElMessage({
          showClose: true,
          message: "已取消关注",
          type: "success",
        });
        location.reload();
    })
  })
}

const follow = (id) => {
  if (!getLocalToken) {
    ElMessage({
      showClose: true,
      message: "请登录后再进行操作",
      type: "warning",
    });
    return;
  }
  if (localId != myuserId) {
    ElMessage({
      showClose: true,
      message: "此页面不是你的个人中心",
      type: "warning",
    });
    return;
  }
  followData.followId = id;
  followData.fanId = myuserId;
  if (!isFollow(id)) {
    console.log("isFollow(followData.followId)",isFollow(followData.followId))
    console.log("已取消关注")
    deleteFollow(id);
  } else {
    console.log("已关注")
    addFollow(id);
  }
}

//跳转到主页
const personal = (id) => {
  router.push({ path: '/user1/personal/', query: {myuserId: id}});
  setTimeout(function () {
    location.reload();
  },100)
};


</script>

<style>
.fanorfollow_box :hover {
  border-width: 1px;
  border-color: deepskyblue;
}
.fanorfollow {
  padding: 15px 40px 15px 30px;
  height: 70px;
  display: flex;
  align-items: center;
  border: 1px solid #ebebeb;
}
.fanorfollow :hover {
  border-width: 1px;
  border-color: deepskyblue;
}
.fanorfollow_left {
  width: 60px;
  height: 60px;
}
.fanorfollow_img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 1px solid #ebebeb;
  vertical-align: center;
}
.fanorfollow_info {
  display: inline-block;
  margin-left: 20px;
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1;
  overflow: hidden;
}
.fanorfollow_info_top {
  display: inline-block;
  font-size: 10;
  line-height: 14px;
  vertical-align: top;
  cursor: pointer;
}
.fanorfollow_info_top :hover {
  color: deepskyblue;
}
.fanorfollow_info_bottom {
  line-height: 14px;
  color: #999;
  margin-top: 5px;
  cursor: pointer;
}
.fanorfollow_info_bottom :hover {
  color: deepskyblue;
}
</style>
  