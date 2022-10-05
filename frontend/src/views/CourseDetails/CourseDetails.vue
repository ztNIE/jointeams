<template>
  <el-card class="box-card">
    <template #header>
<!--      <el-row :gutter="20">-->
<!--        <el-col :span="20">-->
<!--          <span>{{ this.courseDetail.code }}</span> {{ this.courseDetail.name }}-->
<!--        </el-col>-->
<!--        <el-col :span="4">-->
<!--          <el-button type="info">Drop</el-button>-->
<!--          <el-button type="warning">Enroll</el-button>-->
<!--        </el-col>-->
<!--      </el-row>-->
      {{this.courseDetail.code}} {{this.courseDetail.name}}
    </template>
    <el-row :gutter="20" class="full-height">
      <el-col :span="12">
        <el-empty description="No previous student" v-if="isPreviousStudentEmpty"/>
        <base-card v-for="student in previousStudents" :key="student.id" v-else>
          <user-card :first-name="student.firstName"
                     :last-name="student.lastName"
                     :email="student.email"
                     :id="student.id"></user-card>
        </base-card>
      </el-col>
      <el-col :span="12">
        <el-empty description="No current student" v-if="isCurrentStudentEmpty"/>
        <el-row :gutter="20">
          <el-col :span="18">
            <div class="student-header">Current Students</div>
            <div v-if="!isPreviousTeammateEmpty" class="previous-teammate">You have worked with
              <span class="name" v-for="(teammate, index) in previousTeammates" :key="teammate.id">
              {{ teammate.firstName }} {{ teammate.lastName }}<span
                  v-if="index !== Object.keys(previousTeammates).length - 1">, </span>
              </span>
            </div>
          </el-col>
          <el-col :span="6">
            <el-button type="warning">Find All Groups</el-button>
          </el-col>
        </el-row>
        <base-card v-for="student in currentStudents" :key="student.id">
          <user-card :first-name="student.firstName"
                     :last-name="student.lastName"
                     :email="student.email"
                     :id="student.id"></user-card>
        </base-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import courseDetailAPI from '@/api/courseDetail.js'
import {mapGetters} from "vuex";
import BaseCard from "@/views/CourseDetails/components/BaseCard";
import UserCard from "@/views/CourseDetails/components/UserCard";

export default {
  name: "CourseDetails",
  components: {UserCard, BaseCard},
  props: ['courseId'],
  data() {
    return {
      previousStudents: null,
      currentStudents: null,
      courseDetail: null,
      previousTeammates: null,
      error: null,
    }
  },
  computed: {
    ...mapGetters(['userId']),
    isPreviousStudentEmpty() {
      return !this.previousStudents || this.previousStudents.length === 0;
    },
    isCurrentStudentEmpty() {
      return !this.currentStudents || this.currentStudents.length === 0;
    },
    isPreviousTeammateEmpty() {
      return !this.previousTeammates || this.previousTeammates.length === 0;
    }
  },
  methods: {
    manageResponse(response) {
      if (response.status === 200) {
        return response.data
      } else {
        throw Error(response.data.msg)
      }
    }
  },
  created() {
    let _this = this
    try {
      courseDetailAPI.getAllPreviousStudent(this.courseId)
          .then((response) => {
            const previousStudent = response.data.previousStudent
            if (previousStudent.length === 0) {
              return
            }
            _this.previousStudents = previousStudent.map(student => ({...student}))
          })
      courseDetailAPI.getAllCurrentStudent(this.courseId)
          .then((response) => {
            const currentStudent = response.data.currentStudent
            if (currentStudent.length === 0) {
              return
            }
            _this.currentStudents = response.data.currentStudent.map(student => ({...student}))
          })
      courseDetailAPI.getAllPreviousTeammate(this.courseId, this.userId)
          .then((response) => {
            const previousTeammate = response.data.teammate
            if (previousTeammate.length === 0) {
              return
            }
            _this.previousTeammates = previousTeammate.map(teammate => ({...teammate}))
          })
      courseDetailAPI.getCourseById(this.courseId)
          .then((response) => {
            const course = response.data
            _this.courseDetail = {...course}
            console.log(_this.courseDetail)
          })
    } catch (error) {
      console.log(error)
    }
  },
}
</script>

<style scoped lang="scss">
.box-card {
  min-height: 550px;
}

.full-height {
  height: 100%;
}

.student-header {
  font-weight: bold;
  font-size: 20px;
}

.previous-teammate {
  color: #787878;

  & .name {
    font-weight: normal;
    color: #232323;
  }
}
</style>