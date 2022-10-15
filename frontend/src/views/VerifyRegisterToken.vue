<template>
  <AuthLayout hide-log-in hide-sign-up></AuthLayout>
  <el-dialog v-model="showDialog"
             title="Message"
             width="40%"
  >
    <span>{{ dialogMsg }}</span>
    <template #footer>
      <el-button @click="closeDialog()">Confirm</el-button>
    </template>
  </el-dialog>
</template>

<script>
import AuthLayout from "@/views/layout/AuthLayout";
import {getRegistrationVerify} from "@/api/auth";
import {ElMessage} from "element-plus";
import authUtil from "@/util/authUtil";

export default {
  name: "VerifyRegisterToken",
  components: {AuthLayout},
  data() {
    return {
      isSuccess: false,
      dialogMsg: null
    }
  },
  methods: {
    closeDialog() {
      this.dialogMsg = null
      this.$router.replace('/sign-in')
    }
  },
  computed: {
    showDialog() {
      return !!this.dialogMsg;
    }
  },
  beforeCreate() {
    const token = this.$route.params.token
    getRegistrationVerify(token).then((response) => {
      if (response.status === 200) {
        this.isSuccess = true
        ElMessage("Account Activated")
        this.$router.replace('/sign-in')
      } else if (response.status === 202) {
        this.dialogMsg = response.data.msg
      }
    }).catch(() => {
      ElMessage("Invalid token")
      this.$router.replace('/landing')
    })
  },
  created() {
    authUtil.skipLogin()
  }
}
</script>

<style scoped>

</style>