import {createStore} from "vuex";

import authModule from './auth/index'
import uniModule from './university/index'

const store = createStore({
    modules: {
        auth: authModule,
        university: uniModule
    }
})

export default store