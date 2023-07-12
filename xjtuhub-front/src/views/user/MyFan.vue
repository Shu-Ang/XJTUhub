<template>
  <div class="fanorfollow_box">
    <h4>我的粉丝</h4>
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
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import { useRouter, useRoute } from 'vue-router';
import { h } from 'vue'
import { ElMessage } from 'element-plus'

const router = useRouter();
const route = useRoute();
  
const allData = ref([]);

var isfollow = ref(true);

const followData = ref({
  fanId: '',
  followId: ''
});

const isfanid = ref([]);

const isFollow = (writerId) => {
  get("/info/isFollow", {
    roleId: localId,
    writerId: writerId
  })
  .then((res) => {
    console.log("isFollow(fan)", writerId, res.data)
    if(res.data) return true
    else return false
  })
};

let myuserId = route.query.myuserId; 
let localId = route.query.localId;
console.log("myuserId", myuserId)
console.log("localId", localId)

const getList = () => {
  get('/info/selectFanList', {
    roleId: myuserId
  })
  .then((res) => {
    allData.value = res.data
  })
};

getList();



const addFollow = async () => {
  await userApi.getUserId()
  .then(res => {
      post('/info/follow', {
        roleId: res.data.userId,
        writerId: id
      })
      .then((res) => {
        console.log(res.data);
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
        writerId: id
      })
      .then((res) => {
        console.log(res.data);
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
    deleteFollow(id);
  } else {
    addFollow(id);
  }
}

//跳转到主页
const personal = (id) => {
  router.push({ path: '/user', query: {'myuserId': id, 'localId': localId}});
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
  vertical-align: top;
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
  