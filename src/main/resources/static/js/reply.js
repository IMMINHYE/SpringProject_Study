async function addReply(replyObj) {
    console.log(replyObj)
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}
// 특정 게시글의 댓글 리스트 paging(x)
async function get1(bno) {
    const result = await axios.get(`/replies/list/${bno}`)
    console.log(result)
    return result;
}
//paging 사용
async function getList({bno, page, size, goLast}){
    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size})
    }
    return result.data
}

async function getReply(rno) {
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

async function modifyReply(replyObj) {
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}
async function removeReply(rno) {
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}








