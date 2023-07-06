<template>
  <div class="common-head">
    <div class="head-fl">
      <i @click="toggleMenu" :class="$store.state.base.navCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item >{{ $route.meta.title }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
  </div>
</template>

<script>


</script>

<style lang="scss" scoped>
.common-head {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .head-fl {
    display: flex;
    align-items: center;

    i {
      margin-right: 10px;
      font-size: 20px;
      cursor: pointer;
    }
  }

  .head-fr {
    display: flex;
    align-items: center;
    cursor: pointer;

    .user-info {
      display: flex;
      align-items: center;
      height: 60px;
    }

    span {
      margin-right: 10px;
    }
  }
}
</style>
