<template>
  <div class="myart1">
    <h4>{{ userId }}的浏览记录</h4>
    <div>
      <div v-for="blog in blogs" :key="blog.blogId">
        <el-card class="box-card">
          <template #header>
            <div class="card-header"  v-on:click="blogApi.goToBlog(blog.blogId, userId)">
              <h4 v-on:click="blogApi.goToBlog(blog.blogId, userId)"
                style="cursor: pointer"
                >{{ blog.title }}</h4>
              <div class="blog_button">
                <el-button v-on:click="blogApi.goToBlog(blog.blogId, userId)" type="primary" plain size="small" >
                <el-icon><Pointer /></el-icon>文章详情</el-button>
              </div>
            </div>
          </template>
          <h4 v-on:click="infoApi.goToInfo(blog.roleId, userId)" style="cursor: pointer">
            作者: {{ blog.roleId }} 所属课程: {{ blog.course.courseName }}</h4>
        </el-card>
      </div>
    </div>
    <el-empty
        v-if="blogs.length == 0"
        :image-size="250"
        description="暂未浏览任何文章或问题"
    ></el-empty>
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
import blogApi from '@/api/blog';
import { ref} from 'vue';
import {get, tip} from "@/common";
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

const blogs = ref([]);
let localId = route.query.info;
let userId = route.query.user;

//请求
const params = reactive({
  roleId: "",
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const changePageSize = (val) => {
  params.pageSize = val
  getFootPrints();
}
const changePageNum = (val) => {
  params.pageNum = val
  getFootPrints();
}

const getFootPrints = () => {
  params.roleId = localId
  get('/info/footprints',params)
  .then((res) => {
    blogs.value = res.data.resultList;
    params.totalNum = res.data.totalNum;
  });
};
getFootPrints();

</script>

<style scoped>
.myart1{
    line-height: 30px;
}
.box-card :hover {
  border-width: 1px;
  border-color: deepskyblue;
  background-color: lightGray;
  /* box-shadow: 0 2px 10px 0 rgba(37, 38, 39, 0.664); */
}
.box-card{
  margin-bottom: 10px;
  height: 90px;
  
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 5px;
}
.blog_button {
  margin-left: 400px
}
</style>
  