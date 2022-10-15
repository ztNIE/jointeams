<template>
  <auth-layout hide-sign-up>
    <el-dialog title="Error"
               v-model="showError"
               width="30%"
    >
      <span>{{ errorMsg }}</span>
      <template #footer>
        <el-button @click="handleError">Confirm</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="isRegisterSuccess"
               title="Register Success"
               width="40%"
               center
    >
      <span>Activation link has sent to your university email, please activate your account first.</span>
      <template #footer>
        <el-button @click="confirmSuccess">Go Activate</el-button>
      </template>
    </el-dialog>
    <el-card class="box-card">
      <el-form label-position="top"
               :rules="rules"
               :model="formModel"
               ref="registerForm"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Firstname" prop="firstname">
              <el-input v-model.trim="formModel.firstname"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Lastname" prop="lastname">
              <el-input v-model.trim="formModel.lastname"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="University" prop="university">
              <el-select v-model="formModel.university">
                <el-option v-for="givenUni in universities"
                           :key="givenUni.id"
                           :label="givenUni.name"
                           :value="givenUni.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Faculty" prop="faculty">
              <el-select v-model="formModel.faculty">
                <el-option v-for="faculty in faculties"
                           :key="faculty.name"
                           :label="faculty.name"
                           :value="faculty.name"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="E-mail" prop="email">
          <el-input v-model.trim="formModel.email"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="Password" prop="password">
              <el-input type="password" show-password v-model="formModel.password"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Confirm Password" prop="confirmPassword">
              <el-input type="password" show-password v-model="formModel.confirmPassword"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="Degree" prop="degree">
          <el-input v-model="formModel.degree"
                    maxlength="45"
                    show-word-limit
                    placeholder="Master of Information Technology"
          ></el-input>
        </el-form-item>

        <el-form-item label="Major">
          <el-input v-model="formModel.major"
                    show-word-limit
                    maxlength="45"
                    placeholder="Data Science"
          ></el-input>
        </el-form-item>
        <el-button class="register-btn" type="primary"
                   @click="submitForm('registerForm')">Register
        </el-button>
      </el-form>
    </el-card>
  </auth-layout>
</template>

<script>
import AuthLayout from "@/views/layout/AuthLayout";
import {getEmailExist, postRegister, postReCaptchaToken} from "@/api/auth";
import {mapActions, mapGetters} from "vuex";
import authUtil from '@/util/authUtil'

export default {
  name: 'Register',
  components: {
    AuthLayout,
  },
  computed: {
    ...mapGetters(["isUser"]),
    ...mapGetters('university', ['universities']),
    showError() {
      return !!this.errorMsg;
    }
  },
  beforeCreate() {
    authUtil.skipLogin()
  },
  created() {
    this.loadUniversities()
  },
  methods: {
    ...mapActions('university', ['loadUniversities']),
    async submitForm(formName) {

      let isValid = false;
      await this.$refs[formName].validate((valid) => {
        if (valid) {
          isValid = true;
        } else {
          return false;
        }
      })
      if (isValid) {
        try {
          await this.$recaptchaLoaded()
          const token = await this.$recaptcha('register')
          let reCaptchaResult = await postReCaptchaToken(token, this.captAction)
          if (!reCaptchaResult.data || reCaptchaResult.data.msg !== 'success') {
            throw new Error("Please try again later")
          }
          await postRegister({
            firstName: this.formModel.firstname,
            lastName: this.formModel.lastname,
            degree: this.formModel.degree,
            email: this.formModel.email,
            faculty: this.formModel.faculty,
            universityId: this.formModel.university,
            password: this.formModel.password
          })
          this.isRegisterSuccess = true;
          this.emailUrl = this.universities.find(university => university.id === this.formModel.university).emailUrl
        } catch (error) {
          this.errorMsg = error.message;
        }
      }
    },
    handleError() {
      this.errorMsg = null;
    },
    confirmSuccess() {
      this.isRegisterSuccess = false
      window.open(this.emailUrl);
      this.$router.replace("/landing")
    }
  },
  data() {
    let validateEmail = (_, value, callback) => {
      if (!this.formModel.university) {
        callback(new Error('Please select your university first'))
      } else {
        let regex = this.universities.find(university => university.id === this.formModel.university).regex;
        if (!value.match(regex)) {
          callback(new Error('Invalid university email address'))
        } else {
          getEmailExist(value).then((result) => {
            if (result.data.data.result) {
              callback(new Error('Email already registered'))
            }
            callback()
          }).catch(() => {
            callback()
          })
        }
      }
    };
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
      errorMsg: null,
      faculties: require('../faculties.json'),
      isRegisterSuccess: false,
      rules: {
        firstname: [
          {required: true, message: 'Please input your firstname', trigger: 'blur'},
          {max: 50, message: '0-50 characters', trigger: 'blur'}
        ],
        lastname: [
          {required: true, message: 'Please input your lastname', trigger: 'blur'},
          {max: 50, message: '0-50 characters', trigger: 'blur'}
        ],
        university: [
          {required: true, message: 'Please select your university', trigger: 'blur'},
        ],
        email: [
          {required: true, message: 'Please input your email', trigger: 'blur'},
          {validator: validateEmail, trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please input your password', trigger: 'blur'},
          {validator: validatePassword, trigger: ['blur', 'change']}
        ],
        confirmPassword: [
          {required: true, message: 'Please confirm your password', trigger: 'blur'},
          {validator: validateConfirmPassword, trigger: 'blur'}
        ],
        degree: [
          {required: true, message: 'Please enter your degree', trigger: 'blur'}
        ],
        faculty: [
          {required: true, message: 'Please choose your faculty'}
        ]
      },
      formModel: {
        firstname: '',
        lastname: '',
        email: '',
        password: '',
        confirmPassword: '',
        university: null,
        faculty: null,
        degree: '',
        major: '',
      },
      emailUrl: null,
      captAction: "register"
    }
  }
}
</script>

<style lang="scss" scoped>
.box-card {
  width: 500px;
  text-align: center;
  padding: 15px 20px;
}

.register-btn {
  margin-top: 10px;
}
</style>