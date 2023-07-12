<template>
  <div> 
    <h5>{{ favoriteName }}</h5>
    <div class="myart1">
      <!-- <article-item v-for="a in allData" :key="a.id" v-bind="a"/> -->
      <div>
        <div v-for="blog in blogs" :key="blog.blogId" class="singleBlog">
          <el-card>
            <template #header>
              <div class="card-header">
                <h4 v-on:click="blogApi.goToBlog(blog.blogId, userId)">{{ blog.title }}</h4>
              </div>
            </template>
              <h5 v-on:click="infoApi.goToInfo(blog.roleId, userId)">作者: {{ blog.roleId }}
                所属课程: {{ blog.course.courseName }}</h5>
              <div class="favorite_button">
                  <el-popconfirm
                    v-if="userId == localId"
                    confirm-button-text="是"
                    cancel-button-text="否"
                    :icon="InfoFilled"
                    icon-color="#626AEF"
                    @confirm="removeBlog(blog.blogId)"
                    @cancel="cancelEvent"
                    title="确定从收藏夹中移除吗？">
                    <template #reference>
                      <el-button type="danger" @click="deleteFavorite(blog.blogId)" plain size="small">移除文章</el-button>
                    </template>
                
                </el-popconfirm>
              </div>
          </el-card>
        </div>
      </div>
      <el-empty
          v-if="blogs.length == 0"
          :image-size="250"
          description="暂未收藏任何文章"
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
  </div> 
</template>

<script setup>
import  userApi from '@/api/user.js';
import infoApi from '@/api/info'
import blogApi from '@/api/blog'
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import axios from "axios";
import { useRoute, useRouter } from 'vue-router';

const router =useRouter();
const route = useRoute(); // 路由信息，包含路由参数

let faroviteId = route.query.favorite
let userId = route.query.user
const blogs = ref([]);

//请求文章
const params = reactive({
  favoriteId: "",
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const changePageSize = (val) => {
  params.pageSize = val
  getFavoriteBlog();
}
const changePageNum = (val) => {
  params.pageNum = val
  getFavoriteBlog();
}

const getFavoriteBlog = () => {
  params.favoriteId = faroviteId
  get('/info/favoriteBlog', params)
  .then((res) => {
    console.log(res.data);
    blogs.value = res.data.resultList;
    params.totalNum = res.data.totalNum;
  });
};

getFavoriteBlog();


const removeBlog = (blogId) => {
  post('/info/deleteFavoriteBlog', {
    favoriteId: faroviteId,
    blogId: blogId
  })
  .then(res => {
    location.reload();
  })
}

</script>
  
<style scoped>
.el-card {
  border-radius: 0;
}

.el-card:not(:first-child) {
  margin-top: 5px;

}
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
  