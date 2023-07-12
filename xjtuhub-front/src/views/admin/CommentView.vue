
<template>
  <div class="page-comment">
    <h1>评论管理</h1>
    <div>
      <el-table :data="commentPageList">
        <el-table-column prop="commentId" label="评论ID" show-overflow-tooltip></el-table-column>
        <el-table-column prop="roleId" label="评论人" width="140"></el-table-column>
        <!-- <el-table-column prop="commentContent" label="评论内容" width="140"></el-table-column> -->
        <el-table-column prop="commentDate" label="评论时间" width="200"></el-table-column>
        <el-table-column prop="blog.title" label="标题">
          <template #default='scope'>
            <el-link type="primary" @click="BlogApi.goToBlog(scope.row.blogId)" :underline="true">{{ scope.row.blog.title
            }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="operate" label="操作" width="200">
          <template #default='scope'>
            <el-button type="danger" size="small" :icon="Delete" @click="deleteComment(scope.row.commentId)"
              circle></el-button>
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


import { Delete } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue';
import { get, post, tip } from "@/common";
import BlogApi from '@/api/blog'
import Message from '@/common/request/request-message'
// 分页模糊查询数据
const params = reactive({
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})

const comment = reactive({
  commentId: 0
})
// 表格数据
const commentPageList = ref();

// 获取分页查询结果
const getCommentList = () => {
  get("/admin/comment/commentPage", params).then(result => {
    commentPageList.value = result.data.resultList;
    params.totalNum = result.data.totalNum;
  });
}
getCommentList();

const deleteComment = async (commentId) => {
  let flag = await Message.confirm(
    {
      title: "警告",
      content: "确定要删除这条评论吗？"
    }
  );
  if (flag) {
    comment.commentId = commentId
    post("/admin/comment/deleteComment", comment).then(result => {
      tip.success(result.msg)
      getCommentList();
    })
  }
}

const changePageSize = (val) => {
  params.pageSize = val
  getCommentList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getCommentList();
}

</script>
<style lang="scss">
div.page-comment {
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