<style lang="scss">
div.page-gj {
  & h1 {
    font-size: 18px;
    font-weight: bold;
    padding: 20px 0;
  }

  &>div {
    padding: 30px 20px;
    background: #fff;
    box-shadow: 0 0 6px 0 rgba(0, 0, 0, 0.3);
    border-radius: 4px;
  }

  div.page {
    padding: 20px 0 0 0;
  }
}
</style>

<template>
  <div class="page-gj">
    <h1>稿件管理</h1>
    <div>
      <el-table v-loading="loading" :data="blogPageList">
        <el-table-column prop="blogId" label="博客ID"/>
        <el-table-column prop="course.courseName" label="课程" width="220"/>
        <el-table-column prop="category" label="分类" width="140">
          <template #default='scope'>
            <el-tag size="small" :type="scope.row.category === 1 ? 'success' : 'primary'">{{scope.row.category === 1 ? '提问' : '文章'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题">
          <template #default='scope'>
            <el-link type="primary" @click="gotoBlog(scope.row.blogId)" :underline="true">{{ scope.row.title }}</el-link>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="summary" label="摘要" width="220"/> -->
        <el-table-column prop="roleId" label="作者" width="140"/>
        <el-table-column prop="releaseDate" label="发布时间" width="220"/>
        <el-table-column prop="status" label="状态">
          <template #default='scope'>
            <el-tag size="small" :type="scope.row.status === 1 ? 'success' : 'primary'">{{scope.row.status === 1 ? '通过' : '未通过'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default='scope'>
            <el-button type="danger" size="small" :icon="Delete" @click="deleteBlog(scope.row.blogId)" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div class="demo-pagination-block">
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
import { Delete} from '@element-plus/icons-vue'
import { reactive, ref } from 'vue';
import { get, post, tip } from "@/common";
import router from '../../router';
const params = reactive({
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})

// 表格数据
const blogPageList = ref();

// 获取分页模糊查询结果
const getBlogList = () => {
  get("/admin/blog/allBlogPage", params).then(result => {
    blogPageList.value = result.data.resultList;
    params.totalNum = result.data.totalNum;
  });
}
getBlogList();

const blog = reactive({
  blogId: 0
})
const deleteBlog = (blogId) => {
  blog.blogId = blogId
  post("/admin/blog/deleteBlog", blog).then(result => {
    getBlogList()
  })
}

const changePageSize = (val) => {

  params.pageSize = val
  getBlogList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getBlogList();
}
const gotoBlog = (blogId) => {
  const id = blogId + ''
  router.push("/blog/" + id)
}
</script>
