import {get, post} from "@/common"
import router from "../router"
export default{

    async getUserId(){
        return await get("/curr-user")
    },
    
}