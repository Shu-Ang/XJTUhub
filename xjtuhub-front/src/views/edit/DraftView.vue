<template>
	<div class="editor">
		<el-container>
			<el-header style="text-align: right; font-size: 20px">
				<span>XJTUHUB</span>
			</el-header>
			<el-main>
				<el-form :model="form" :rules="rules" ref="formRef" label-position="top">
					<el-form-item label="标题" prop="title">
						<el-input v-model="form.title" placeholder="请输入标题"></el-input>
					</el-form-item>
					<el-form-item label="摘要(简单描述你的文章/问题，请勿上传图片)" prop="summary">
						<mavon-editor v-model="form.summary" ref="md" />
					</el-form-item>

					<el-form-item label="正文（文章/问题正文部分，支持上传图片）" prop="content">
						<mavon-editor v-model="form.content" ref="md" @imgAdd="handleEditorImgAdd"
							@imgDel="handleEditorImgDel" />
					</el-form-item>

					<el-row :gutter="20">
						<!-- <el-col :span="12">
							<el-form-item label="所属课程" prop="cate">
								<el-select v-model="form.courseId" placeholder="请选择分类（输入可添加新分类）" :allow-create="true"
									:filterable="true" style="width: 100%;">
									<el-option :label="item.name" :value="item.id" v-for="item in categoryList"
										:key="item.id"></el-option>
								</el-select>
							</el-form-item>
						</el-col> -->
						<el-col :span="12">
							<el-form-item label="类型" prop="category">
								<el-radio-group v-model="form.category" size="large">
									<el-radio-button label=0>文章</el-radio-button>
									<el-radio-button label=1>问答</el-radio-button>
								</el-radio-group>

							</el-form-item>
						</el-col>
					</el-row>
				</el-form>
			</el-main>
			<el-footer>
				<el-button type="primary" @click="release()">发布</el-button>
				<el-button @click="save()">保存为草稿</el-button>
				<el-button type="info" @click="cancel()">取消</el-button>
			</el-footer>
		</el-container>
	</div>

	<el-dialog v-model="centerDialogVisible" title="Warning" width="30%" center>
		<span>
			博客未保存，确定要退出吗？
		</span>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="centerDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="exit()">
					确定
				</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>

import 'mavon-editor/dist/css/index.css'
import { reactive, ref } from 'vue'
import type { FormRules } from 'element-plus'
import { post, tip } from '../../common'
import router from '../../router'
import { useRoute } from 'vue-router'
import blogApi from '../../api/blog'
import axios from 'axios'
const route = useRoute();
const categoryList = ref()
const formRef = ref()
const content = ref()
const centerDialogVisible = ref(false)
const form = reactive({
    blogId: 0,
	roleId: null,
	title: '',
	summary: '',
	content: '',
	courseId: 1,
	category: 0,
	isDraft: 0
})

const validateTitle = (rule: any, value: any, callback: any) => {
	if (value === '') {
		return callback(new Error('请输入标题'))
	} else {
		callback()
	}
}
const validateSummary = (rule: any, value: any, callback: any) => {
	if (value === '') {
		return callback(new Error('请输入摘要'))
	} else {
		callback()
	}
}
const validateContent = (rule: any, value: any, callback: any) => {
	if (value === '') {
		return callback(new Error('请输入正文'))
	} else {
		callback()
	}
}


const rules = reactive<FormRules<typeof form>>({
	title: [{ validator: validateTitle, required: true, message: '请输入标题', trigger: 'change' }],
	// courseId: [{ required: true, message: '请选择所属课程', trigger: 'change' }],
	summary: [{ validator: validateSummary, required: true, message: '请输入摘要', trigger: 'change' }],
	content: [{ validator: validateContent, required: true, message: '请输入正文', trigger: 'change' }],
})

const release = async () => {
	formRef.value.validate(async (valid) => {
		if (valid) {
			form.isDraft = 0
			await post("/blog/editBlog", form).then(result => {
				tip.success(result.msg)
			})
		} else {
			tip.error("发布失败，请检查信息是否完整")
		}
	})
}
const save = async () => {
	formRef.value.validate(async (valid) => {
		if (valid) {
			form.isDraft = 1
			await post("/blog/editBlog", form).then(result => {
				tip.success(result.msg)
			})
		} else {
			tip.error("保存失败，请检查信息是否完整")
		}
	})
}

const handleEditorImgAdd = async (pos, $file) => {
	const formdata = new FormData();
	formdata.append('image', $file)
	console.log(formdata)
	await axios.post("/dev/blog/uploadImg", formdata, {
		headers: {
			'Content-Type': 'multipart/form-data'
		}
	}).then((result) => {
		console.log(result)
		if (result.data.status === 200) {
			console.log(result)
			tip.success("上传成功")
			console.log(result.data)
			console.log(result.data.data.imgUrl)
			let url = result.data.data.imgUrl
			let name = $file.name
			if (name.includes('-')) {
				name = name.replace(/-/g, '')
			}
			let content = form.content
			// 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)  这里是必须要有的
			if (content.includes(name)) {
				let oStr = `(${pos})`
				let nStr = `(${url})`
				let index = content.indexOf(oStr)
				let str = content.replace(oStr, '')
				let insertStr = (soure, start, newStr) => {
					return soure.slice(0, start) + newStr + soure.slice(start)
				}
				form.content = insertStr(str, index, nStr)
			}
		} else {
			tip.error("上传失败")
		}
	})

}

const handleEditorImgDel = (pos) => {

}

const cancel = () => {
	centerDialogVisible.value = true
}

const exit = () => {
	router.push("/home")
}

const loadDraft = async () => {
	let id = route.query.blog
	await blogApi.getBlogDetails({ blogId: id }).then(res => {
        form.blogId = res.data.blogId
		form.title = res.data.title
		form.content = res.data.content
		form.summary = res.data.summary
        form.category = res.data.category
        form.courseId = res.data.courseId
        form.isDraft = res.data.isDraft
        form.roleId = res.data.roleId
	})
}
loadDraft();
console.log(route.query.draft)
</script>

<style scoped>
.editor .el-header {
	position: relative;
	background-color: var(--el-color-primary-light-7);
	color: var(--el-text-color-primary);
}
</style>
