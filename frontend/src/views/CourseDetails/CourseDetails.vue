<template>
  <el-card class="box-card">
    <template #header>
      <el-row :gutter="20">
        <el-col :span="20">
          <el-row>
            <el-col :span="24" class="course-name">
              <span class="bold">{{ courseDetail.code }}</span> {{ courseDetail.name }}
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" class="tutorial">
              <div>
                My Tutorial/Lab:
                <el-input v-model="tutorialNumber"
                          style="width: 150px"
                          size="small"
                          :maxlength="2"
                >
                  <template #prepend>
                    <el-select v-model="tutorialType"
                               placeholder="Select"
                               style="width: 60px; font: inherit"
                               size="small"
                    >
                      <el-option label="RE" value="RE" />
                      <el-option label="CC" value="CC" />
                    </el-select>
                  </template>
                  <template #append>
                    <el-button size="small">Edit</el-button>
                  </template>
                </el-input>
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="4" class="right">
          <el-button type="info" @click="tryDropCourse" v-if="isEnrolled">Drop</el-button>
          <el-button type="warning" v-else @click="enrollCourse">Enroll</el-button>
          <el-button :type="markButtonType" @click="toggleMark">{{ markButtonCaption }}</el-button>
        </el-col>
      </el-row>
    </template>
    <el-row :gutter="20" class="full-height">
      <el-col :span="12">
        <el-empty description="No previous student" v-if="isPreviousStudentEmpty"/>
        <base-card v-for="student in previousStudents" :key="student.id" v-else>
          <user-card :full-name="student.fullName"
                     :email="student.email"
                     :id="student.id"></user-card>
        </base-card>
      </el-col>
      <el-col :span="12">
        <el-empty description="No current student" v-if="isCurrentStudentEmpty"/>
        <el-row :gutter="20" v-else>
          <el-col :span="18">
            <div class="student-header">Current Students</div>
            <div v-if="!isPreviousTeammateEmpty" class="previous-teammate">You have worked with
              <span class="name" v-for="(teammate, index) in previousTeammates" :key="teammate.id">
              {{ teammate.fullName }}<span
                  v-if="index !== Object.keys(previousTeammates).length - 1">, </span>
              </span>
            </div>
          </el-col>
          <el-col :span="6">
            <el-button type="warning">Find All Groups</el-button>
          </el-col>
        </el-row>
        <base-card v-for="student in currentStudents" :key="student.id">
          <user-card :full-name="student.fullName"
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
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: "CourseDetails",
  components: {UserCard, BaseCard},
  props: ['course_id'],
  data() {
    return {
      previousStudents: null,
      currentStudents: null,
      courseDetail: {
        code: null,
        name: null
      },
      previousTeammates: null,
      error: null,
      isEnrolled: false,
      isMarked: false,
      tutorial: null,
      tutorialType: null,
      tutorialNumber: null
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
    },
    courseId() {
      return this.course_id;
    },
    markButtonCaption() {
      return this.isMarked ? "Unmark" : "Mark";
    },
    markButtonType() {
      return this.isMarked ? "warning" : "primary";
    },
  },
  methods: {
    async toggleMark() {
      let func = this.isMarked ? courseDetailAPI.delUnmarkCourse : courseDetailAPI.postMarkCourse
      let response = await func(this.courseId, this.userId)
      try {
        if (response.msg === 'success') {
          let message = this.isMarked ? "Unmarked successfully" : "Marked successfully"
          this.isMarked = !this.isMarked
          ElMessage({
            type: 'success',
            message
          })
        } else {
          throw new Error(response.msg)
        }
      } catch (err) {
        console.log(err)
        ElMessage("Please try again later")
      }
    },
    async enrollCourse() {
      let response = await courseDetailAPI.postEnrollCourse(this.courseId, this.userId)
      if (response.status === 200 && response.msg === "success") {
        ElMessage({
          message: "Enrolled Successfully",
          type: "success"
        })
        this.isEnrolled = true
      } else {
        ElMessage({
          message: "Please try again later",
        })
      }
    },
    async tryDropCourse() {
      try {
        await ElMessageBox.confirm(
            "Do you confirm to drop this course?",
            "Info",
            {
              confirmButtonText: 'Confirm',
              cancelButtonText: 'Cancel',
              type: 'info'
            }
        )
        await this.dropCourse()
      } catch {
        ElMessage("Drop canceled")
      }
    },
    async dropCourse() {
      let response = await courseDetailAPI.delDropCourse(this.courseId, this.userId)
      if (response.status === 200 && response.msg === "success") {
        ElMessage({
          message: "Dropped Successfully",
          type: "success"
        })
        this.isEnrolled = false
      } else {
        ElMessage("Please try again later")
      }
    }
  },
  created() {
    let _this = this
    courseDetailAPI.getAllPreviousStudent(this.courseId)
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.msg)
          }
          let data = response.data
          const previousStudent = data.previousStudent
          if (previousStudent.length === 0) {
            return
          }
          _this.previousStudents = previousStudent.map(student => ({...student}))
        })
        .catch((err) => {
          _this.error = err
        })

    courseDetailAPI.getAllCurrentStudent(this.courseId)
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.msg)
          }
          let data = response.data
          const currentStudent = data.currentStudent
          if (currentStudent.length === 0) {
            return
          }
          _this.currentStudents = currentStudent.map(student => ({...student}))
        })
        .catch((err) => {
          _this.error = err
        })

    courseDetailAPI.getAllPreviousTeammate(this.courseId, this.userId)
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.msg)
          }
          let data = response.data
          const previousTeammate = data.teammate
          if (previousTeammate.length === 0) {
            return
          }
          _this.previousTeammates = previousTeammate.map(teammate => ({...teammate}))
        })
        .catch((err) => {
          _this.error = err
        })

    courseDetailAPI.getCourseById(this.courseId)
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.msg)
          }
          let course = response.data
          _this.courseDetail = {
            code: course.code,
            id: course.id,
            isLocked: course.isLocked,
            name: course.name,
            nextGroupNameId: course.nextGroupNameId
          }
        })
        .catch((err) => {
          _this.error = err
        })
    console.log("after get course")
    console.log(this.courseDetail)
    courseDetailAPI.getCheckEnrollment(this.courseId, this.userId)
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.msg)
          }
          let data = response.data
          _this.isEnrolled = data.enrolled
        })
        .catch((err) => {
          _this.error = err
        })
    courseDetailAPI.getCheckMarkedCourse(this.courseId, this.userId)
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.msg)
          }
          let data = response.data
          _this.isMarked = data.marked
        })
        .catch((err) => {
          _this.error = err
        })
    courseDetailAPI.getTutorial(this.courseId, this.userId)
        .then((response) => {
          if (response.status !== 200) {
            throw new Error(response.msg)
          }
          let data = response.data
          _this.tutorial = data.tutorial
        })
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

.bold {
  font-weight: bold;
}

.course-name {
  font-size: 24px;
}

.tutorial {
  font-size: 16px;
}

.previous-teammate {
  color: #787878;

  & .name {
    font-weight: normal;
    color: #232323;
  }
}


//.icon-expand {
//  width: 30px;
//  color: #B5B5B5;
//  cursor: pointer;
//  margin-top: 7px;
//}

//.star {
//  width: 100%;
//  height: auto;
//}
//
//.star:hover {
//  //cursor: pointer;
//  //color: #2EC4B6;
//}
</style>