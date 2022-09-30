<template>
  <auth-layout>
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
                <el-option v-for="givenUni in givenUniversities"
                           :key="givenUni.id"
                           :label="givenUni.name"
                           :value="givenUni.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Faculty">
              <el-select v-model="formModel.faculty">
                <el-option v-for="givenFaculty in givenFaculties"
                           :key="givenFaculty.id"
                           :label="givenFaculty.name"
                           :value="givenFaculty.id"
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

        <el-form-item label="Degree">
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
        <el-form-item>
          <el-button @click="submitForm('registerForm')">Register</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </auth-layout>
</template>

<script>
import AuthLayout from "@/views/layout/AuthLayout";
import { postRegister } from "@/api/auth";

export default {
  name: 'Register',
  components: {AuthLayout},
  methods: {
    async submitForm(formName) {
      let isValid = false;
      await this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log('Valid register input');
          console.log(this.formModel);
          isValid = true;
        } else {
          console.log('Submit failed');
          console.log(this.formModel);
          return false;
        }
      })
      if (isValid) {
        try {
          await postRegister({
            firstName: this.formModel.firstname,
            lastName: this.formModel.lastname,
            degree: this.formModel.degree,
            email: this.formModel.email,
            faculty: this.getFacultyNameById(this.formModel.faculty),
            universityId: this.formModel.university,
            password: this.formModel.password
          })
        } catch (error) {
          console.log(error);
          this.error = error;
        }
      }
    },
    getFacultyNameById(facultyId) {
      return this.givenFaculties.find(faculty => faculty.id === facultyId);
    }
  },
  data() {
    let validateEmail = (_, value, callback) => {
      if (!this.formModel.university) {
        callback(new Error('Please select your university first'))
      } else {
        let regex = this.givenUniversities.find(university => university.id === this.formModel.university).regex;
        if (!value.match(regex)) {
          callback(new Error('Invalid university email address'))
        } else {
          callback()
        }
      }
    };
    let validatePassword = (_, value, callback) => {
      const regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,60}$";
      if (value && (value.length < 8 || value.length > 60)) {
        callback(new Error('Length should between 8 and 60'))
      } else if (!value.match(regex)) {
        callback(new Error('At least one uppercase, one lowercase and one number'))
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
      error: null,
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
          {validator: validatePassword, trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: 'Please confirm your password', trigger: 'blur'},
          {validator: validateConfirmPassword, trigger: 'blur'}
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
      givenUniversities: [
        {
          id: 1,
          name: 'University of Sydney',
          regex: '^[a-z]{4}[0-9]{4}@uni.sydney.edu.au$'
        },
        {
          id: 2,
          name: 'Dummy University',
          regex: '^.+$'
        }
      ],
      givenFaculties: [
        {
          id: 1,
          name: "School of Computer Science"
        },
        {
          id: 2,
          name: "School of Electrical Engineering"
        }
      ],
    }
  }
}
</script>

<style lang="scss" scoped>
//el-form {
//  margin: 1rem;
//  padding: 1rem;
//}
//
//el-card {
//  padding: 1rem;
//  margin: 2rem auto;
//  max-width: 40rem;
//}
.box-card {
  width: 500px;
}
</style>