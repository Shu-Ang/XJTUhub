<template>
    <el-form ref="ruleFormRef" :model="registerForm" status-icon :rules="rules" label-width="120px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="roleId">
            <el-input v-model="registerForm.roleId" type="roleId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input v-model="registerForm.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="请再次输入密码" prop="checkPassword">
            <el-input v-model="registerForm.checkPassword" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱" prop="Email">
            <el-input v-model="registerForm.Email" type="Email" autocomplete="off" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)">注册</el-button>
            <RouterLink to="/login"><el-button>去登录</el-button></RouterLink>
        </el-form-item>
    </el-form>

    <el-dialog v-model="centerDialogVisible"  width="30%" align-center>
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
const ruleFormRef = ref<FormInstance>()
const centerDialogVisible = ref(false)
const content = ref()
const validateRoleId = (rule: any, value: any, callback: any) => {
    if (!value) {
        return callback(new Error('请输入用户名'))
    } else {
        callback()
    }

}

const validatePass = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else {
        if (registerForm.checkPassword !== '') {
            if (!ruleFormRef.value) return
            ruleFormRef.value.validateField('checkPass', () => null)
        }
        callback()
    }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value !== registerForm.password) {
        callback(new Error("两次密码不一致！"))
    } else {
        callback()
    }
}

const validEmail = (rule: any, value: any, callback: any) => {
    if (value === '') {
        return callback(new Error('请输入邮箱'))
    } else {
        callback()
    }

}
const registerForm = reactive({
    roleId: '',
    password: '',
    checkPassword: '',
    Email: '',
})

const rules = reactive<FormRules<typeof registerForm>>({
    roleId: [{ validator: validateRoleId, trigger: 'blur' }],
    password: [{ validator: validatePass, trigger: 'blur' }],
    checkPassword: [{ validator: validatePass2, trigger: 'blur' }],
    Email: [{ validator: validEmail, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            console.log('submit!')
            await axios.post(
                "/dev/register", {
                roleId: registerForm.roleId,
                password: registerForm.password,
                email: registerForm.Email
            }
            ).then(result => {
                content.value = result.data.msg
                centerDialogVisible.value = true
            })
            
        } else {
            console.log('error submit!')
            return false
        }
    })
}

</script>