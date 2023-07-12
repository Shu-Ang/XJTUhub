<template>
  <div class="myart1">
    <div id="ShowBlogs">
      <div v-for="blog in blogs" :key="blog.blogId" class="singleBlog">
        <h2>{{ blog.title }}</h2>
        <h3>所属课程: {{ blog.course.courseName }}</h3>
        <el-button v-if="myuserId==localId" type="primary" plain>编辑稿件</el-button>
        <el-button v-if="myuserId==localId" type="danger" plain>删除稿件</el-button>
        <article>
          {{ blog.summary }}
        </article>
      </div>
    </div>
    <el-empty
        v-if="blogs.length == 0"
        :image-size="250"
        description="暂未创建任何草稿"
      ></el-empty>
  </div>
</template>
  
<script setup>
import  userApi from '@/api/user.js';
import { ref, reactive } from 'vue';
import {get, post, setLocalToken, getLocalToken, removeLocalToken, tip} from "@/common";
import axios from "axios";
import { useRouter, useRoute } from 'vue-router'

const router = useRouter();
const route = useRoute();

let myuserId = route.query.myuserId;

let localId = route.query.localId;

const blogs = ref([]);

const myget = () => {
  get('/info/draft', {
    roleId: myuserId
  })
  .then((res) => {
    blogs.value = res.data;
  });
};
myget();


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
.singleBlog {
  padding: 20px;
  margin: 20px 0;
  background: rgba(255,250,245, 0.9);
  /* background-color: ; */
}


</style>
    