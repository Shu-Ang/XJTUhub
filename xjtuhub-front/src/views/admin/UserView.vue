<template>
  <div class="page-user">
    <h1>用户管理</h1>
    <div>
      <el-table v-loading="loading" :data="rolePageList">
        <el-table-column prop="roleId" label="用户名" width="220" >
          <template #default='scope'>
            <el-link type="primary" @click="UserApi.goToInfo(scope.row.roleId)" :underline="true">{{ scope.row.roleId }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="sign" label="个性签名" width="400" />
        <el-table-column prop="email" label="用户邮箱" width="300" />
        <el-table-column prop="status" label="用户状态" width="140">
          <template #default='scope'>
            <el-tag size="small" :type="scope.row.status === 1 ? 'success' : 'danger'">{{ scope.row.status === 1 ? '启用' :
              '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="220" />
        <el-table-column label="操作" width="220">
          <template #default='scope'>
            <el-button :type="scope.row.status == 1 ? 'danger' : 'success'"
              @click="$event => changeRoleStatus(scope.row.roleId)" :icon="scope.row.status == 1 ? 'Close' : 'check'"
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
import {
  Check,
  Close
} from '@element-plus/icons-vue'
import { reactive, ref } from 'vue';
import { get, post, tip } from "@/common";
import Message from '@/common/request/request-message'
import UserApi from '@/api/user'
// 分页模糊查询数据
const params = reactive({
  pageSize: 5,
  pageNum: 1,
  totalNum: 0
})
const config = reactive({
  servMsg: true
})
// 表格数据
const rolePageList = ref();
const role = reactive({
  roleId: ''
})
// 获取分页模糊查询结果
const getRoleList = () => {
  get("/admin/role/rolePage", params).then(result => {
    rolePageList.value = result.data.resultList;
    params.totalNum = result.data.totalNum;
  });
}
getRoleList();

const changeRoleStatus = async (roleId) => {
  let flag = await Message.confirm(
    {
      title: "警告",
      content: "确定要改变该用户的状态吗？"
    }
  );
  if (flag) {
    role.roleId = roleId
    post("/admin/role/changeRoleStatus", role, config).then(result => {
      tip.success(result.msg)
      getRoleList();
    })
  }

}

const changePageSize = (val) => {
  params.pageSize = val
  getRoleList();
}
const changePageNum = (val) => {
  params.pageNum = val
  getRoleList();
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
