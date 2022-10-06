import {ElMessage} from "element-plus";

const outputError = function(err) {
    ElMessage({
        type: 'error',
        message: err.message,
    })
    return null;
}
const outputInfo = function(errMsg) {
    ElMessage({
        type: 'info',
        message: errMsg,
    })
    return null;
}

export default {
    name: 'catchError',
    outputInfo,
    outputError
}