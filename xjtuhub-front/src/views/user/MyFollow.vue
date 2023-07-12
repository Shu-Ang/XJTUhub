<template>
  <div class="fanorfollow_box">
    <h4>{{ localId }}的关注列表</h4>
    <div class="fanorfollow" v-for="(fo, index) in FollowList" :key="index">
      <div class="fanorfollow_left">
        <img class="fanorfollow_img" v-image-preview :src="fo.faceAddr" />
      </div>
      <div class="fanorfollow_info">
        <div class="fanorfollow_info_top">
          <span
            style="color: #666; max-width: 180px"
            @click="userApi.goToInfo(fo.roleId, userId)"
            >{{ fo.roleId }}</span
          >
        </div>
        <div class="fanorfollow_info_bottom">
          <span >{{ fo.sign }}</span>
        </div>
      </div>
      <div class="fanorfollow_botton">
        <el-button
          @click="follow(fo.roleId)"
          type="primary"
          size="small"
          round
          icon="el-icon-check"
          v-text="isfollowid.indexOf(fo.roleId) > -1 ? '已关注' : '关注'"
        ></el-button>
      </div>
    </div>
    <div>
      <el-empty
        v-if="FollowList.length == 0"
        :image-size="250"
        description="这里什么都没有"
      ></el-empty>
    </div>
    <!-- 分页 -->
    <div class="demo-pagination-block" style="margin-left: 140px;">
    <div class="demonstration"></div>
      <el-pagination v-model:current-page="params.pageNum" v-model:page-size="params.pageSize"
        :page-sizes="[5, 10, 15, 20]" :small="small" :disabled="disabled" :background="background"
        layout="sizes, prev, pager, next" :total="params.totalNum" @size-change="changePageSize"
        @current-change="changePageNum" />
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

let userId = route.query.user; 
let localId = route.query.info;

const FollowList = ref([]);

const params = reactive({
  roleId: "",
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const changePageSize = (val) => {
  params.pageSize = val
  getFollowList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getFollowList();
}

const isFollow = (writerId) => {
  get("/info/isFollow", {
    roleId: userId,
    writerId: writerId
  })
  .then((res) => {
    if(res.data) return true
    else return false
  })
};

const followData = ref({
  fanId: '',
  followId: ''
});

const isfollowid = ref([]);


const getFollowList = () => {
  params.roleId = localId
  get('/info/selectFollowList', params)
  .then((res) => {
    FollowList.value = res.data.resultList;
    params.totalNum = res.data.totalNum;
    res.data.resultList.forEach((element) => {
      isfollowid.value.push(element.roleId);
    });
  })
};

getFollowList();



const addFollow = async (id) => {
  await userApi.getUserId()
  .then(res => {
      post('/info/follow', {
        roleId: res.data.userId,
        writerId: id
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

const deleteFollow = async (id) => {
  await userApi.getUserId()
  .then(res => {
      post('/info/unFollow', {
        roleId: res.data.userId,
        writerId: id
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

const follow = (id) => {
  if (!getLocalToken) {
    ElMessage({
      showClose: true,
      message: "请登录后再进行操作",
      type: "warning",
    });
    return;
  }
  if (localId != userId) {
    ElMessage({
      showClose: true,
      message: "此页面不是你的个人中心",
      type: "warning",
    });
    return;
  }
  followData.followId = id;
  followData.fanId = userId;
  if (!isFollow(id)) {
    deleteFollow(id);
  } else {
    addFollow(id);
  }
}


</script>

<style>
.fanorfollow_box :hover {
  border-width: 1px;
  border-color: deepskyblue;
  background-color: lightGray;
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
  