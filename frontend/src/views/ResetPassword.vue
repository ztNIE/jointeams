<template>
  <AuthLayout hide-sign-up hide-log-in>
    <el-dialog title="Error"
               v-model="showError"
               width="30%"
    >
      <span v-if="!!errorMsg">{{ errorMsg }}</span>
      <template #footer>
        <el-button @click="handleError">Confirm</el-button>
      </template>
    </el-dialog>
    <el-card class="box-card">
      <el-form label-width="135px"
               :model="formModel"
               ref="form"
               :rules="rules"
      >
        <el-form-item label="New Password" prop="password">
          <el-input type="password" show-password v-model="formModel.password"/>
        </el-form-item>
        <el-form-item label="Confirm Password" prop="confirmPassword" class="require-padding">
          <el-input type="password" show-password v-model="formModel.confirmPassword"/>
        </el-form-item>
        <el-button @click="submitForm('form')">Reset</el-button>
      </el-form>
    </el-card>
  </AuthLayout>
</template>

<script>
import AuthLayout from "@/views/layout/AuthLayout";
import {getIsPasswordTokenExist, postSavePassword} from "@/api/auth";
import {ElMessage} from "element-plus";
import authUtil from "@/util/authUtil";

export default {
  name: "ResetPassword",
  components: {AuthLayout},
  methods: {
    async submitForm(formName) {
      let isValid = false
      await this.$refs[formName].validate((valid) => {
        if (valid) {
          isValid = true
        } else {
          return false
        }
      })
      if (isValid) {
        try {
          const response = await postSavePassword(this.$route.params.token, this.formModel.password)
          if (response.status !== 200) {
            this.errorMsg = response.data.msg
            return;
          }
          ElMessage({
            type: "success",
            message: "Password reset successful"
          })
          this.$router.replace("/sign-in")
        } catch (error) {
          console.error(error)
        }
      }
    },
    handleError() {
      this.errorMsg = null
      this.$router.replace("/landing")
    }
  },
  data() {
    let validatePassword = (_, value, callback) => {
      const regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,60}$";
      if (!value.match(regex)) {
        callback(new Error('8-60 chars with uppercase, lowercase & numbers'))
      } else {
        callback()
      }
    };
    let validateConfirmPassword = (_, value, callback) => {
      if (!this.formModel.password) {
        callback(new Error('Please set your password first'))
      } else if (this.formModel.password !== value) {
        callback(new Error('Password not match'))
      } else {
        callback()
      }
    };
    return {
      formModel: {
        password: null,
        confirmPassword: null,
      },
      rules: {
        password: [
          {required: true, message: 'Please input your password', trigger: 'blur'},
          {validator: validatePassword, trigger: ['blur', 'change']}
        ],
        confirmPassword: [
          {required: true, message: 'Please confirm your password', trigger: 'blur'},
          {validator: validateConfirmPassword, trigger: 'blur'}
        ]
      },
      errorMsg: null
    }
  },
  beforeCreate() {
    getIsPasswordTokenExist(this.$route.params.token).then((response) => {
      if (response.status !== 200) {
        ElMessage("Invalid password token")
        this.$router.replace("/landing")
      }
      ElMessage({
        type: "success",
        message: "Validate reset link successfully"
      })
    })
  },
  created() {
    authUtil.skipLogin()
  },
  computed: {
    showError() {
      return !!this.errorMsg
    }
  }
}
</script>

<style scoped>
.require-padding {
  padding-top: 20px;
}

.el-form {
  text-align: center;
}

.el-card {
  width: 400px;
  margin-top: 40px;
  padding-top: 5px;
}
</style>