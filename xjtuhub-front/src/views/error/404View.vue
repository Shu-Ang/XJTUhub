<template>
    <el-result icon="warning" title="404 not found" sub-title="抱歉，你访问的页面走丢了~">
        <template #extra>
            <el-button type="primary" @click="$event => $router.push('/home')">返回首页</el-button>
        </template>
    </el-result>
</template>

<style>
    
</style>