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
          <el-button @click="submitForm('signInForm')">Login</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </auth-layout>
</template>

<script>


import AuthLayout from "@/views/layout/AuthLayout";
import {getEmailExist} from "@/api/auth";
import {mapActions} from "vuex";
import {ElMessage} from "element-plus";

export default {
  name: 'Login',
  components: {AuthLayout},
  computed: {
    showError() {
      return !!this.error
    }
  },
  data() {
    let emailValidator = (_, value, callback) => {
      if (!value || value.length === 0) {
        callback(new Error('Please enter email'))
        return
      }
      getEmailExist(value).then((response) => {
        const result = response.data.data.result;
        console.log(result)
        if (!result) {
          callback(new Error('email not exist, please check again'))
        } else {
          callback()
        }
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
      rules: {
        email: [{validator: emailValidator, trigger: 'blur'}],
        password: [{validator: validatePassword, trigger: 'blur'}]
      }
    };
  },
  methods: {
    ...mapActions(['login']),
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
        await this.login({
          email: this.formModel.email,
          password: this.formModel.password
        })
        ElMessage({
          message: 'Success login',
          type: 'info'
        })
        this.$router.replace('/dashboard')
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
}

</style>
