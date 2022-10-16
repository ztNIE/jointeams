<template>
  <auth-layout hide-log-in>
    <el-dialog title="Error"
               v-model="showError"
               width="30%"
    >
      <span v-if="showError">{{ error.message }}</span>
      <template #footer>
        <el-button @click="handleError">Confirm</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="resetPasswordVisible" title="Reset Password">
      <el-form :model="resetPasswordForm"
               ref="resetPassword"
               :rules="resetRules">
        <el-form-item label="Email" prop="email">
          <el-input v-model="resetPasswordForm.email"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="resetPasswordVisible = false">Cancel</el-button>
        <el-button type="primary" @click="submitResetPassword"
        >Confirm</el-button
        >
      </span>
      </template>
    </el-dialog>
    <el-card class="box-card">
      <el-form label-position="top"
               :model="formModel"
               ref="signInForm"
               :rules="rules"
      >
        <el-form-item label="Email" prop="email">
          <el-input v-model.trim="formModel.email"/>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="formModel.password"
                    type="password"
                    show-password
          />
        </el-form-item>
        <el-form-item>
          <div class="btn">
            <el-button type="primary"
                       class="login-btn"
                       @click="submitForm('signInForm')">Login
            </el-button>
            <el-button @click="resetPasswordVisible = true"
                       class="reset-btn">Reset Password
            </el-button>
          </div>
        </el-form-item>

      </el-form>
    </el-card>
  </auth-layout>
</template>

<script>


import AuthLayout from "@/views/layout/AuthLayout";
import {getEmailActivate, getEmailExist, postBeginResetPassword} from "@/api/auth";
import {mapActions, mapGetters} from "vuex";
import {ElMessage} from "element-plus";
import authUtil from "@/util/authUtil";

export default {
  name: 'Login',
  components: {AuthLayout},
  computed: {
    ...mapGetters(['isUser']),
    showError() {
      return !!this.error
    }
  },
  beforeCreate() {
    authUtil.skipLogin()
  },
  data() {
    let emailValidator = (_, value, callback) => {
      if (!value || value.length === 0) {
        callback(new Error('Please enter email'))
        return
      }
      getEmailExist(value).then((response) => {
        const result = response.data.data.result;
        if (!result) {
          callback(new Error('email not exist, please check again'))
        }
        getEmailActivate(value).then((response) => {
          const result = response.data.data.result;
          if (!result) {
            callback(new Error('account not activated'))
          } else {
            callback()
          }
        })
      })
    };
    let validatePassword = (_, value, callback) => {
      const regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,60}$";
      if (value && (value.length < 8 || value.length > 60)) {
        callback(new Error('Length should between 8 and 60'))
      } else if (!value.match(regex)) {
        callback(new Error('Not a valid password, please try again'))
      } else {
        callback()
      }
    };
    return {
      error: null,
      formModel: {
        email: '',
        password: ''
      },
      resetPasswordForm: {
        email: '',
      },
      resetPasswordVisible: false,
      rules: {
        email: [{validator: emailValidator, trigger: 'blur'}],
        password: [{validator: validatePassword, trigger: 'blur'}]
      },
      resetRules: {
        email: [{validator: emailValidator, trigger: 'blur'}]
      }
    };
  },
  methods: {
    ...mapActions(['login']),
    async submitResetPassword() {
      let isValid = false
      await this.$refs['resetPassword'].validate((valid) => {
        if (valid) {
          isValid = true
        } else {
          return false
        }
      })
      if (!isValid) {
        return
      }
      try {
        await postBeginResetPassword(this.resetPasswordForm.email)
        ElMessage({
          message: `Reset password link sent to ${this.resetPasswordForm.email}`,
          type: 'success'
        })
        this.resetPasswordVisible = false
      } catch (error) {
        this.resetPasswordVisible = false
      }
    },
    async submitForm(formName) {
      let isValid = false;
      await this.$refs[formName].validate((valid) => {
        if (valid) {
          isValid = true;
        } else {
          return false;
        }
      })
      if (!isValid) {
        return;
      }
      try {
        let role = await this.login({
          email: this.formModel.email,
          password: this.formModel.password
        })
        ElMessage({
          message: 'You have successfully logged in!',
          type: 'success'
        })

        if (role === 'ROLE_USER'){
          this.$router.replace('/dashboard')
        } else {
          this.$router.replace('/comment')
        }
        
      } catch (error) {
        this.error = {message: "Invalid password"}
      }
    },
    handleError() {
      this.error = null
    },
  }
}

</script>

<style lang="scss" scoped>
.box-card {
  width: 480px;
  padding: 15px 10px;
}

.btn {
  text-align: right;
  width: 100%;
}

</style>
