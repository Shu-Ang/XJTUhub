<template>
    <div class="myart1">
        <!-- <article-item v-for="blog in blogs" :key="blog.id" v-bind="blog"/> -->
      <div id="ShowBlogs">
        <div v-for="blog in blogs" :key="blog.blogId" class="singleBlog">
          <h2 v-on:click="readBlog(blog.blogId)">{{ blog.title }}</h2>
          <h3 v-on:click="JumpUser">user id: {{ blog.roleId }}</h3>
          <h3>文章号: {{ blog.blogId }}</h3>
          <h3>所属课程: {{ blog.course.courseName }}</h3>
          <article>
            {{ blog.summary }}
          </article>
        </div>
      </div>
      <el-empty
          v-if="blogs.length == 0"
          :image-size="250"
          description="暂未查看过文章或问题"
      ></el-empty>
    </div>
</template>

<script setup>
import userApi from '@/api/user.js';
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import axios from "axios";
import { useRouter, useRoute } from 'vue-router'

const blogs = ref([]);
let myuserId = route.query.myuserId;
let localId = route.query.myuserId;

const myget = () => {
  get('/info/footprints',{
    roleId: myuserId
  })
  .then((res) => {
    console.log(res.data);
    blogs.value = res.data;
  });
};
myget();

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
  