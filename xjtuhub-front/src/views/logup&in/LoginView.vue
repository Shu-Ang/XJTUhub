<template>
    <div class="login">
        <el-card class="box-card" shadow="always">
            <div style="
            padding: 40px 0px 50px 0px;
            text-align: center;
            color: #409eff;
            font-size: 40px;
            font-weight: bold;
            letter-spacing: 3px;
            ">XJTUHUB
            </div>
            <el-form ref="ruleFormRef" :model="loginForm" status-icon :rules="rules" label-width="120px"
                class="demo-ruleForm">
                <el-form-item label="用户名" prop="roleId">
                    <el-input v-model="loginForm.userId" type="roleId" autocomplete="off"
                        placeholder="Please input username" />
                </el-form-item>
                <el-form-item label="密码" prop="pass">
                    <el-input v-model="loginForm.userPwd" type="password" autocomplete="off"
                        placeholder="Please input password" show-password />
                </el-form-item>
                <el-form-item label="验证码" prop="verificationCode">
                    <el-input v-model="loginForm.verificationCode">
                        <template #suffix>
                            <el-image v-if="flag" style="overflow: visible; position: relative; left: 16px;" :src="codeSrc"
                                @click="codeSrc.value = 'data:image/png;base64,' + getCode()"></el-image>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <div style="text-align: justify; width: 360px; margin: 40px auto 0px">
                        <el-button type="primary" size="large" style="width: 200px" @click="login()">登录</el-button>
                    </div>
                    <div style="text-align: justify; width: 360px; margin: 20px auto 0px">
                        <RouterLink to="/register"><el-button size="large" style="width: 200px">去注册</el-button></RouterLink>
                    </div>
                </el-form-item>
            </el-form>


        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import router from '../../router'
import { get, post, tip, setLocalToken } from '../../common'
const ruleFormRef = ref()
const centerDialogVisible = ref(false)
const content = ref()
const flag = ref(false)
// 验证码后台接口
const codeSrc = ref()
const getCode = async () => {
    await get('/captcha/captchaImage').then(result => {
        // 图片的src属性显示为：前缀+后台传递的Base64验证码图片编码
        // 验证码可以正常显示
        codeSrc.value = 'data:image/jpg;base64,' + result.data.imgUrl
        loginForm.verificationKey = result.data.imgKey // redis存储验证码的key
        flag.value = true
    })

}

codeSrc.value = 'data:image/png;base64,' + getCode()

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

const validateKey = (rule: any, value: any, callback: any) => {
    if (value === '') {
        return callback(new Error('请输入验证码'))
    } else {
        callback()
    }
}
const loginForm = reactive({
    userId: '',
    userPwd: '',
    verificationCode: '',
    verificationKey: ''
})

const rules = reactive<FormRules<typeof loginForm>>({
    userId: [{ validator: validateRoleId, trigger: 'blur' }],
    userPwd: [{ validator: validatePass, trigger: 'blur' }],
    verificationCode: [{ validator: validateKey, trigger: 'blur' }],
})


const login = async () => {
    ruleFormRef.value.validate(async (valid) => {
        if (valid) {
            await post("/login",
                loginForm).then(result => {
                    setLocalToken(result.data)
                    if (loginForm.userId == 'admin') {
                        tip.success("欢迎")
                        router.push("/admin/dashboard")
                    }
                    else {
                        tip.success("欢迎")
                        router.push({ path: '/', query: { user: loginForm.userId } });
                    }
                })
        } else {
            console.log('error submit!')
            return false
        }
    })
}

// 回车登录
window.onkeydown = ($event) => {
    if ($event.key && $event.key == 'Enter') {
        login();
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