<template>
    <div class="myart1">
        <!-- <article-item v-for="blog in blogs" :key="blog.id" v-bind="blog"/> -->
      <div id="ShowBlogs">
        <div v-for="blog in blogs" :key="blog.blogId" class="singleBlog">
          <h2 v-on:click="goToBlog(blog.blogId)">{{ blog.title }}</h2>
          <h3 v-on:click="goToInfo">user id: {{ blog.roleId }}</h3>
          <div class="blog_button">
            <el-popconfirm
              v-if="myuserId == localId"
              confirm-button-text="是"
              cancel-button-text="否"
              :icon="InfoFilled"
              icon-color="#626AEF"
              @confirm="deleteBlog(blog.blogId)"
              @cancel="cancelEvent"
              title="确定删除该文章吗？">
              <template #reference>
                <el-button type="danger" plain>删除文章</el-button>
              </template>
            </el-popconfirm>
          </div>
          <h3>所属课程: {{ blog.course.courseName }}</h3>
          <article>
            {{ blog.summary }}
          </article>
        </div>
      </div>
      <el-empty
          v-if="blogs.length == 0"
          :image-size="250"
          description="暂未发表任何文章"
      ></el-empty>
    </div>
</template>

<script setup>
import userApi from '@/api/user.js';
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import axios from "axios";
import { useRouter, useRoute } from 'vue-router'

const router = useRouter();
const route = useRoute();

const blogs = ref([]);
let myuserId = route.query.myuserId;
let localId = route.query.myuserId;
console.log("myuserId", myuserId)

//用于测试显示文章功能
const myget = () => {
  get('/info/getArticleByRole', {
    roleId: myuserId
  })
  .then((res) => {
    console.log("article", res.data);
    blogs.value = res.data;
  });
};
myget();

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
  console.log('cancel!')
}


</script>

<style>
.myart1{
    line-height: 30px;
}
.singleBlog {
  padding: 20px;
  margin: 20px 0;
  background: rgba(255,250,240, 0.9);
  /* background-color: ; */
}
</style>
  