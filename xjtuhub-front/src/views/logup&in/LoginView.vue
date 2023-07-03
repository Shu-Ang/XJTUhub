<template>
    <el-form ref="ruleFormRef" :model="loginForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="roleId">
            <el-input v-model="loginForm.roleId" type="roleId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="pass">
            <el-input v-model="loginForm.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)">登录</el-button>
            <RouterLink to="/register"><el-button>去注册</el-button></RouterLink>

        </el-form-item>
    </el-form>

    <el-dialog v-model="centerDialogVisible" width="30%" align-center>
        <span>{{ content }}</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="centerDialogVisible = false">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import axios from 'axios';

import router from '../../router';
const ruleFormRef = ref<FormInstance>()
const centerDialogVisible = ref(false)
const content = ref()

const validateRoleId = (rule: any, value: any, callback: any) => {
    if (value === '') {
        return callback(new Error('请输入用户名'))
    } else {
        callback()
    }

}

const validatePass = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else {
        callback()
    }
}


const loginForm = reactive({
    roleId: '',
    password: '',
})

const rules = reactive<FormRules<typeof loginForm>>({
    roleId: [{ validator: validateRoleId, trigger: 'blur' }],
    password: [{ validator: validatePass, trigger: 'blur' }],
})

const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            console.log('submit!')
            await axios.post("/dev/login",
                loginForm).then(result => {
                    if (result.data.status == 508) {
                        router.push('')
                    } else {
                        content.value = result.data.msg
                        centerDialogVisible.value = true
                    }
                })
        } else {
            console.log('error submit!')
            return false
        }
    })
}

</script>