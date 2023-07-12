<template>
  <div class="fanorfollow_box">
    <h4>{{ userId }}的粉丝列表</h4>
    <div class="fanorfollow" v-for="(fan, index) in fanList" :key="index">
      <div class="fanorfollow_left">
        <img class="fanorfollow_img" v-image-preview :src="fan.faceAddr" />
      </div>
      <div class="fanorfollow_info">
        <div class="fanorfollow_info_top">
          <span
            style="color: #666; max-width: 180px"
            @click="userApi.goToInfo(fan.roleId, userId)"
            >{{ fan.roleId }}</span
          >
        </div>
        <div class="fanorfollow_info_bottom">
          <span>{{ fan.sign }}</span>
        </div>
      </div>
    </div>
    <div>
      <el-empty
        v-if="fanList.length == 0"
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
import infoApi from '@/api/info'
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import { useRouter, useRoute } from 'vue-router';
import { h } from 'vue'
import { ElMessage } from 'element-plus'

const router = useRouter();
const route = useRoute();
let localId = route.query.info;
let userId = route.query.user;
const fanList = ref([]);

//请求
const params = reactive({
  roleId: "",
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const changePageSize = (val) => {
  params.pageSize = val
  getFanList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getFanList();
}

const getFanList = () => {
  params.roleId = localId
  get('/info/selectFanList', params)
  .then((res) => {
    fanList.value = res.data.resultList;
    params.totalNum = res.data.totalNum;
  })
};
getFanList();

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
  