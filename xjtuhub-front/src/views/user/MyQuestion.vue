<template>
  <div class="myart1">
    <h4>{{ localId }}的提问</h4>  
      <div>
      <div v-for="blog in blogs" :key="blog.blogId">
        <el-card class="box-card">
          <template #header>
            <div class="card-header"  v-on:click="blogApi.goToBlog(blog.blogId, userId)">
              <h4 v-on:click="blogApi.goToBlog(blog.blogId, userId)"
                style="cursor: pointer; width: 300px;"
                >{{ blog.title }}</h4>
              <div class="blog_button">
                <el-popconfirm
                  v-if="userId == localId"
                  confirm-button-text="是"
                  cancel-button-text="否"
                  :icon="InfoFilled"
                  icon-color="#626AEF"
                  @confirm="deleteBlog(blog.blogId)"
                  @cancel="cancelEvent"
                  title="确定删除该问题吗？">
                  <template #reference>
                    <el-button type="danger" plain size="small"><el-icon><Delete /></el-icon> 删除提问</el-button>
                  </template>
                </el-popconfirm>
              </div>
            </div>
          </template>
          <h5 v-on:click="infoApi.goToInfo(blog.roleId, userId)" style="cursor: pointer">
            作者: {{ blog.roleId }} 所属课程: {{ blog.course.courseName }}</h5>
        </el-card>
      </div>
    </div>
    <el-empty
        v-if="blogs.length == 0"
        :image-size="250"
        description="暂未提出任何问题"
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
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import axios from "axios";
import { useRouter, useRoute } from 'vue-router'
import blogApi from '@/api/blog'

const router = useRouter();
const route = useRoute();

const blogs = ref([]);
let userId = route.query.user;
let localId = route.query.info;

//请求
const params = reactive({
  roleId: "",
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const changePageSize = (val) => {
  params.pageSize = val
  getQuestionList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getQuestionList();
}

const getQuestionList = () => {
  params.roleId = localId
  get('/info/getQuestionByRole', params)
  .then((res) => {
    blogs.value = res.data.resultList;
    params.totalNum = res.data.totalNum;
  });
};
getQuestionList();

const deleteBlog =  (blogId) => {
  post('/info/deleteBlog', {
    roleId: localId,
    blogId: blogId
  })
  .then(res => {
    location.reload();
  })
}

const cancelEvent = () => { //取消操作

}

</script>

<style scoped>
.myart1{
    line-height: 30px;
}
.box-card{
  margin-bottom: 20px;
  height: 90px;
}
.box-card :hover {
  border-width: 1px;
  border-color: deepskyblue;
  background-color: lightGray;
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
  
  