<template>
  <div> 
    <h5>{{ favoriteName }}</h5>
    <div class="myart1">
      <!-- <article-item v-for="a in allData" :key="a.id" v-bind="a"/> -->
      <div id="ShowBlogs">
          <div v-for="blog in blogs" :key="blog.blogId" class="singleBlog">
            <h2 v-on:click="readBlog(blog.blogId)">{{ blog.title }}</h2>
            <h3 v-on:click="personal(blog.roleId)">作者: {{ blog.roleId }}</h3>
            <h3>所属课程: {{ blog.course.courseName }}</h3>
            <h3>id: {{ blog.blogId }}</h3>
            <div class="favorite_button">
              <el-popconfirm
                v-if="myuserId == localId"
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
            <article>
              {{ blog.summary }}
            </article>
          </div>
        </div>
      <el-empty
          v-if="blogs.length == 0"
          :image-size="250"
          description="暂未收藏任何文章"
      ></el-empty>
    </div>
  </div> 
</template>

<script setup>
import  userApi from '@/api/user.js';
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import axios from "axios";
import { useRoute, useRouter } from 'vue-router';

const router =useRouter();
const route = useRoute(); // 路由信息，包含路由参数

let faroviteId = route.query.favoriteId
let favoriteName = route.query.favoriteName
const blogs = ref([]);

//用于测试显示文章功能
const myget = () => {
  get('/info/favoriteBlog', {
    favoriteId: faroviteId
  })
  .then((res) => {
    console.log(res.data);
    blogs.value = res.data;
  });
};
// setTimeout(function () { //防止get重复请求被拦截
myget();
// }, 2);

const removeBlog = (blogId) => {
  post('/info/deleteFavoriteBlog', {
    favoriteId: faroviteId,
    blogId: blogId
  })
  .then(res => {
    location.reload();
  })
}


const personal = (id) => {
  router.push({ path: '/user1/personal/', query: {'myuserId': id, 'localId': localId}});
  setTimeout(function () {
    location.reload();
  },100)
};
</script>
  
<style>
.el-card {
    border-radius: 0;
  }

  .el-card:not(:first-child) {
    margin-top: 5px;

  }
  .myart1{
    line-height: 30px;
  }
</style>
  