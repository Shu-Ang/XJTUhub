<template>
    <el-dialog v-model="centerDialogVisible" width="30%" align-center>
        <span>{{ content }}</span>
        <template #footer>
            <span class="dialog-footer">
                <RouterLink to="/login"><el-button type="primary" @click="centerDialogVisible = false">
                        确认
                    </el-button></RouterLink>
            </span>
        </template>
    </el-dialog>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import {getLocalToken, post} from "../../common"
import axios from 'axios';
const centerDialogVisible = ref(false)
const content = ref()
const activate = async () => {
    centerDialogVisible.value = true
    const currentUrl = window.location
    const urlString: string = currentUrl.toString()
    const array = urlString.split('/')
    const uuid = array[array.length - 1]
    //console.log(getLocalToken())
    await axios.post("/dev/activate/" + uuid, uuid).then((result)=>{
        content.value = result.data.msg
        centerDialogVisible.value = true
    })
}
activate();
</script>
<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>