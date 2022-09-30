<template>
  <auth-layout>
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
          <el-button @click="login">Login</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </auth-layout>
</template>

<script>


import AuthLayout from "@/views/layout/AuthLayout";

export default {
  name: 'Login',
  components: {AuthLayout},
  data() {
    return {
      formModel: {
        email: '',
        password: ''
      },
    };
  },
  methods: {
    login() {
      return;
    }
  }
}
</script>

<style lang="scss" scoped>
.box-card {
  width: 480px;
}

</style>
