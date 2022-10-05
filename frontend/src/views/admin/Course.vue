<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">Course</div>
      </template>
      <div class="search-course">
        <el-input
            style=""
            id="search-input"
            v-model=searchInput
            class="w-50 m-2"
            placeholder="Course code OR name"
        />
        <el-button type="primary" @click="searchCourses()" >Search</el-button>
      </div>
      <div class="divider_space"></div>
      <div class="add-course">
        <el-select v-model="universityId" class="m-2" placeholder="University">
          <el-option
              v-for="university in universities"
              :key="university.id"
              :label="university.name"
              :value="university.id"
          />
        </el-select>
        <el-input
            style=""
            id="search-input"
            v-model=courseCodeInput
            class="w-50 m-2"
            placeholder="Course Code"
        />
        <el-input
            style=""
            id="search-input"
            v-model=courseNameInput
            class="w-50 m-2"
            placeholder="Course Name"
        />
        <el-button type="primary" @click="addACourse()" >Add Course</el-button>
      </div>
      <el-main>
        <el-scrollbar max-height="450px" v-if="coursesAfterSearch.length !== 0">
          <div v-for="course in coursesAfterSearch" :key="course.id" class="text item">
            <el-card class="course-card">
              <div class="card-content">
                <div>
                  <label class="label">Course Code: </label>
                  <span class="content">{{course.code}}</span>
                  <br>
                  <label class="label">Course Name: </label>
                  <span class="content">{{course.name}}</span>
                  <br>
                  <label class="label">University: </label>
                  <span class="content">{{course.universityName}}</span>
                </div>
                <div class="actionButtons">
                  <el-button type="warning" @click="changeACourseLockStatus(course)" >{{ getLockButtonText(course) }}</el-button>
                  <el-button type="info" @click="deleteACourse(course)" >Delete</el-button>
                </div>
              </div>
            </el-card>
            <div class="divider_space"></div>
          </div>
        </el-scrollbar>
        <el-empty description="No course" v-else />
      </el-main>
    </el-card>
  </div>
</template>


<script >
import { parseTime } from '@/util/ParseTime'
import adminAPI from '../../api/admin.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import authUtil from "@/util/authUtil";


export default {
  name: 'Comment',
  beforeCreate() {
    authUtil.authenticateIdentity("ROLE_ADMIN")
  },
  data() {
    return {
      universityId: '',
      universities: [],
      searchInput: '',
      universityIdSelected: '',
      courseCodeInput: '',
      courseNameInput: '',
      courses: [],
      coursesAfterSearch: []
    }
  },
  mounted() {
    adminAPI.findAllCourses().then((res) => {
      this.courses = res.data.data.CourseResponseDataList
      this.coursesAfterSearch = this.courses
    })
    adminAPI.findAllUniversities().then((res) => {
      this.universities = res.data.data.UniversityList
      console.log(this.universities)
    })
  },
  methods: {
    parseTime(notification)
    {
      return parseTime(notification.timestamp)
    },
    deleteACourse(course){
      ElMessageBox.confirm(
          'You are going to delete this course?',
          'Warning',
          {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        adminAPI.deleteACourse(course.id).then((res) => {
          if(res !== null)
          {
            ElMessage({
              type: 'success',
              message: res.data.msg,
            })
            let index = this.courses.indexOf(course)
            this.courses.splice(index, 1)
            let index2 = this.coursesAfterSearch.indexOf(course)
            if(index2 !== -1)
              this.coursesAfterSearch.splice(index2, 1)
          }
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Delete is cancelled',
        })
      })
    },
    searchCourses(){
      this.coursesAfterSearch = this.courses.filter(
          course => course.code.toLowerCase().match(this.searchInput.toLowerCase())
                    || course.name.toLowerCase().match(this.searchInput.toLowerCase()))
      console.log(this.coursesAfterSearch)
    },
    getLockButtonText(course){
      let status = course.isLocked
      if(status === true)
        return 'Unlock'
      else
        return 'Lock'
    },
    changeACourseLockStatus(course){
      ElMessageBox.confirm(
          'You are going to lock/unlock this course?',
          'Warning',
          {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
      ).then(() => {
        adminAPI.changeACourseLockStatus(course.id, 1-course.isLocked).then((res) => {
          if(res !== null)
          {
            ElMessage({
              type: 'success',
              message: res.data.msg,
            })
            let index = this.courses.indexOf(course)
            this.courses[index].isLocked = (course.isLocked) ? false : true
          }
        })
      }).catch(() => {
        ElMessage({
          type: 'info',
          message: 'Delete is cancelled',
        })
      })
    },
    addACourse(){
      if(this.universityId === -1)
      {
        ElMessage({
          type: 'error',
          message: 'Please select an university.',
        })
      }
      if(this.courseCodeInput === '')
      {
        ElMessage({
          type: 'error',
          message: 'Please input a course code.',
        })
      }
      if(this.courseNameInput === '')
      {
        ElMessage({
          type: 'error',
          message: 'Please input a course name.',
        })
      }
      adminAPI.addACourse(this.courseCodeInput, this.courseNameInput, this.universityId )
          .then(res =>{
            ElMessage({
              type: 'success',
              message: res.data.msg,
            })
            location.reload()
          })
    }
  }
}
</script>

<style lang="scss" scoped>

.card-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

#name {
  font-size: large;
  color: #FF9F1C;
  font-weight: bold;
}

.divider_space {
  height: 3px;
}

.main_header {
  color: black;
  font-weight: bold;
  font-size: xx-large;
}

.box-card {
  min-height: 580px;
}

.comment-card:hover {
  background-color: #CBF3F0;
}

.comment-card{
  min-height: 120px;
}

.el-select{
  width: 300px;
  margin-right: 20px;
}

.el-input{
  width: 300px;
  margin-right: 20px;
}

.label{
  font-size: 22px;
  min-width: 180px;
  color: #f99827;
  display:inline-block;
}

.content{
  font-size: 22px;
  min-width: 180px;
}

.actionButtons > .el-button{
  font-size: 16px;
  min-width: 100px;
}
</style>