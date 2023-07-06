import {get} from "@/common"

export async function getBlogById(blog) {
	return await get("/blog/detail",blog)
}

export function getSearchBlogList(query) {
	return get("/searchBlog", query)
}