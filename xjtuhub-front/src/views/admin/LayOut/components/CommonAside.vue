<template>
  <div class="common-aside">
    <div class="logo"><b style='font-size: 24px;'>XJTUhub</b></div>
    <!--
      10- 高亮等于当前路由path
      12- 是否使用 vue-router 的模式，启用该模式会在激活导航时以 index 作为 path 进行路由跳转
      -->
    <el-menu :default-active="$route.path" class="el-menu-vertical-demo" router :collapse="$store.state.base.navCollapse"
      background-color="rgb(48, 65, 86)" text-color="#fff" active-text-color="#409eff">
      <!-- 一级菜单 一层循环 -->
      <div v-for="nav in $store.state.permission.asideNavs" :key="nav.label">
        <el-menu-item :index="nav.path" v-if="!nav.children">
          <i :class="nav.icon"></i>
          <span slot="title">{{ nav.label }}</span>
        </el-menu-item>
        <el-sub-menu :index="nav.label" v-if="nav.children">
          <template slot="title">
            <i :class="nav.icon"></i>
            <span>{{ nav.label }}</span>
          </template>
          <el-menu-item-group>
            <el-menu-item v-for="subNav in nav.children" :key="subNav.path"
              :index="subNav.path">{{ subNav.label }}</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
      </div>
    </el-menu>
  </div>
</template>
<script>
export default {
  name: 'CommonAside',
  data() {
    return {}
  }
}
</script>
<style lang="scss">
.common-aside {
  .logo {
    width: 100%;
    height: 65px;
    color: #fff;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: auto;
      height: 40px;
      vertical-align: middle;
      margin-right: 12px;

    }
  }

  .el-menu-item.is-active {
    background-color: #fff !important;
  }

  ::v-deep .el-submenu__title {
    &:hover {
      background-color: rgba(38, 52, 69) !important;
    }
  }

  .el-menu-item {
    &:hover {
      background-color: rgba(38, 52, 69) !important;
    }
  }
}

.el-menu-vertical-demo {
  &.el-menu {
    border-right: none;
  }
}
</style>
