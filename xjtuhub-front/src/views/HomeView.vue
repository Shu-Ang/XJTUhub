<script setup>
import { getLocalToken } from '../common';
const fun = ()=>{
  const token = getLocalToken();
  console.log(token)
}
</script>

<template>
  <el-row class="mb-4">
    <el-button type="primary" @click=fun() >get token</el-button>
  </el-row>
</template>
