import {ElMessage} from "element-plus";

const outputError = function(err) {
    ElMessage({
        type: 'error',
        message: err.message,
    })
    return null;
}
const outputInfo = function(err) {
    ElMessage({
        type: 'info',
        message: err.message,
    })
    return null;
}

export default {
    name: 'catchError',
    outputInfo,
    outputError
}