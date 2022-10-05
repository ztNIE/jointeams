<template>
  <el-card class="box-card">
    <!--    <user-card v-for="student in currentStudents" :key="student.id"-->
    <!--    :firstName="student.firstName"-->
    <!--    :lastName="student.lastName"-->
    <!--    :id="student.id"-->
    <!--    :email="student.email"></user-card>-->
    <template #header>
      {{ this.courseDetail.code }} {{ this.courseDetail.name }}
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
        <base-card v-for="student in currentStudents" :key="student.id" v-else>
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
          })
    } catch (error) {
      console.log(error)
    }
  },
}
</script>

<style scoped>
.box-card {
  min-height: 550px;
}

.full-height {
  height: 100%;
}
</style>