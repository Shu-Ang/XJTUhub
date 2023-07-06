<template>
  <div>
    <editor :value="emailForm.msg" @updateValue="getMsg"></editor>
    <el-button type="primary">发表</el-button>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import editor from '../../components/editor/editor.vue';
//富文本相关的内容
const emailForm = reactive({
  test_msg: ''
})
const getMsg = (val) => {
  console.log("获取到的富文本内容", val);
  emailForm.msg = val
}

</script>

<style  scoped></style>