import {get, post} from "@/common"
import router from "../router"
export default{

    async goToEdit(userId){
        router.push({
            path: '/edit',
            query:{
                user: userId
            }
        })
    },
    async goToEditDraft(blogId, userId){
        router.push({
            path: '/draft',
            query:{
                user: userId,
                draft: blogId
            }
        })
    },
    
}