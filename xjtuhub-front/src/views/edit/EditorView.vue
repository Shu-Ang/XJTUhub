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
					<el-form-item label="摘要" prop="description">
						<mavon-editor v-model="form.description" />
					</el-form-item>

					<el-form-item label="正文" prop="content">
						<mavon-editor v-model="form.content" />
					</el-form-item>

					<el-row :gutter="20">
						<el-col :span="12">
							<el-form-item label="所属课程" prop="cate">
								<el-select v-model="form.cate" placeholder="请选择分类（输入可添加新分类）" :allow-create="true"
									:filterable="true" style="width: 100%;">
									<el-option :label="item.name" :value="item.id" v-for="item in categoryList"
										:key="item.id"></el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="12">
							<el-form-item label="类型" prop="tagList">
								<el-radio-group v-model="form.tagList" size="large">
									<el-radio-button label="文章" />
									<el-radio-button label="问答" />
								</el-radio-group>

							</el-form-item>
						</el-col>
					</el-row>
				</el-form>
			</el-main>
			<el-footer>
				<el-button type="primary" @click="submit()">上传</el-button>
				<el-button @click="save()">保存到草稿</el-button>
				<el-button type="info" @click="cancel()">取消</el-button>
			</el-footer>
		</el-container>
	</div>
</template>

<script lang="ts" setup>

import 'mavon-editor/dist/css/index.css'
import { reactive, ref } from 'vue'
import type { FormRules } from 'element-plus'
const categoryList = ref()
const formRef = ref()
const content = ref()
const form = reactive({
	title: '',
	description: '',
	content: '',
	cate: null,
	tagList: [],
})

const rules = reactive<FormRules<typeof form>>({
	title: [{ required: true, message: '请输入标题', trigger: 'change' }],
	cate: [{ required: true, message: '请选择分类', trigger: 'change' }],
	tagList: [{ required: true, message: '请选择标签', trigger: 'change' }],
})

const submit = ()=>{

}
const save = ()=>{

}
const cancel = ()=>{

}

</script>

<style scoped>
.editor .el-header {
	position: relative;
	background-color: var(--el-color-primary-light-7);
	color: var(--el-text-color-primary);
}
</style>
