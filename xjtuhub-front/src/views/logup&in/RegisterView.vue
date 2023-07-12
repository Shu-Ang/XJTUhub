<template>
    <div class="login">
        <el-card class="box-card" shadow="always">
            <div style="
            padding: 20px 0px 30px 0px;
            text-align: center;
            color: #409eff;
            font-size: 35px;
            font-weight: bold;
            letter-spacing: 3px;
            ">WELCOME TO XJTUHUB!
            </div>
            <el-form ref="ruleFormRef" :model="registerForm" status-icon :rules="rules" label-width="120px"
                class="demo-ruleForm">
                <el-form-item label="用户名" prop="roleId">
                    <el-input v-model="registerForm.roleId" type="roleId" autocomplete="off"
                        placeholder="Please input username" />
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" autocomplete="off"
                        placeholder="Please input password" show-password />
                </el-form-item>
                <el-form-item label="再次输入密码" prop="checkPassword">
                    <el-input v-model="registerForm.checkPassword" type="password" autocomplete="off"
                        placeholder="Please input password" show-password />
                </el-form-item>
                <el-form-item label="邮箱" prop="Email">
                    <el-input v-model="registerForm.Email" type="Email" autocomplete="off"
                        placeholder="Please input email" />
                </el-form-item>
                <el-form-item>
                    <div style="text-align: justify; width: 360px; margin: 12px auto 0px">
                        <el-button type="primary" size="large" style="width: 200px"
                            @click="register()">注册</el-button>
                    </div>
                    <div style="text-align: justify; width: 360px; margin: 10px auto 0px">
                        <RouterLink to="/login"><el-button type="default" size="large" style="width: 200px">去登录</el-button>
                        </RouterLink>
                    </div>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormRules } from 'element-plus'
import {post, tip} from '../../common'
const ruleFormRef = ref()
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

const register = () => {
    ruleFormRef.value.validate(async (valid) => {
        if (valid) {
            await post(
                "/register", {
                roleId: registerForm.roleId,
                password: registerForm.password,
                email: registerForm.Email
            }
            ).then(result => {
                tip.success(result.msg)
            })

        } else {
            tip.success("注册失败！请检查信息是否完整")
            return false
        }
    })
}

// 回车登录
window.onkeydown = ($event) => {
    if ($event.key && $event.key == 'Enter') {
        register();
    }
}
</script>

<style scoped>
.login {
    background-image: url(@/assets/xjtu1.jpg);
    background-size: 100% 100%;

    position: fixed;
    top: 0px;
    bottom: 0px;
    left: 0px;
    right: 0px;

    display: flex;
    justify-content: center;
    align-items: center;
}

.box-card {
    position: relative;
    left: 250px;
    width: 500px;
    height: 500px;
    opacity: 0.92;
}
</style>

