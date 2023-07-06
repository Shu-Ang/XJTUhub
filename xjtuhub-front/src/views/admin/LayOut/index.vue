<template>
  <div>
    <el-container>
      <el-aside :width="$store.state.base.navCollapse?'auto':'200px'">
        <common-aside/>
      </el-aside>
      <el-container>
        <el-header>
          <common-head/>
        </el-header>
        <el-main>
          <nav-tabs/>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import CommonAside from './components/CommonAside.vue'
import CommonHead from './components/CommonHead.vue'
export default {
  name: 'LayOutPage',
  components: {
    CommonAside,
    CommonHead,

  }
}
</script>

<style lang="scss" scoped>
.el-container{
  height: 100vh;
  .el-header{
    background-color: #fff;
  }
  .el-aside{
    background-color: #304156;
  }
  .el-main{
    background-color: #f0f2f5;
  }
}
</style>
