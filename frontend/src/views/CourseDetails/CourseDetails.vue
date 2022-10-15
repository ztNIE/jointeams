<template>
  <el-card class="box-card wrapper">
    <template #header>
      <el-row :gutter="20">
        <el-col :span="20">
          <el-row>
            <el-col :span="24" class="course-name">
              <span class="bold">{{ courseDetail.code }}</span> {{ courseDetail.name }}
            </el-col>
          </el-row>
          <el-row v-if="isEnrolled">
            <el-col :span="24" class="tutorial">
              <div>
                My Tutorial/Lab:
                <el-input v-model="tutorialNumber"
                          style="width: 110px"
                          size="small"
                          :maxlength="2"
                          :disabled="isTutorialSelected && !isEditing"
                          class="tut-input"
                >
                  <template #prepend>
                    <el-select v-model="tutorialType"
                               placeholder=" "
                               style="width: 60px; font: inherit"
                               size="small"
                               :disabled="isTutorialSelected && !isEditing"
                    >
                      <el-option label="RE" value="RE"/>
                      <el-option label="CC" value="CC"/>
                    </el-select>
                  </template>
                </el-input>
                <el-button type="warning"
                           size="small"
                           v-if="isTutorialSelected && !isEditing"
                           @click="toggleIsEditing"
                           class="edit-btn"
                >Edit
                </el-button>
                <el-button type="primary"
                           size="small"
                           v-else
                           @click="editTutorial"
                >OK
                </el-button>
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="4" class="right">
          <el-button type="info" @click="tryDropCourse" v-if="isEnrolled">Drop</el-button>
          <el-button type="warning" v-else @click="enrollDialogVisible=true">Enroll</el-button>
          <el-button :type="markButtonType" @click="toggleMark">{{ markButtonCaption }}</el-button>
        </el-col>
      </el-row>
    </template>
    <el-dialog v-model="enrollDialogVisible"
               title="Enter a tutorial/lab"
               width="350px"
               align-center
    >
      <div class="confirm-tutorial-dialog">
        <span class="title">My Tutorial/Lab: </span>
        <el-input v-model="tutorialNumber"
                  style="width: 140px"
                  size="large"
                  :maxlength="2"
        >
          <template #prepend>
            <el-select v-model="tutorialType"
                       placeholder=" "
                       style="width: 80px; font: inherit"
                       size="large"
            >
              <el-option label="RE" value="RE"/>
              <el-option label="CC" value="CC"/>
            </el-select>
          </template>
        </el-input>
      </div>
      <template #footer>
        <el-button @click="enrollDialogVisible = false">Cancel</el-button>
        <el-button @click="handleEnroll" type="primary">Confirm</el-button>
      </template>
    </el-dialog>
    <el-row :gutter="20" class="full-height">
      <el-col :span="12" class="full-height">
        <div class="full-height">
          <div class="student-header">Previous Students</div>
          <div class="search-bar add-padding">
            <el-row :gutter="20">
              <el-col :span="14">
                <el-input
                    v-model="previousStudentSearchName"
                    placeholder="Please enter student's name"
                ></el-input>
              </el-col>
              <el-col :span="4">
                <el-button type="primary"
                           @click="handlePreviousStudentSearch">Search
                </el-button>
              </el-col>
            </el-row>
          </div>
          <el-empty description="No previous student found" v-if="isSearchedPreviousStudentEmpty"/>
          <el-scrollbar v-else>
            <base-card v-for="student in searchedPreviousStudents"
                       :key="student.id"
                       @click="jumpToStudentProfile(student.id)"
            >
              <user-card :full-name="student.fullName"
                         :email="student.email"
                         :id="student.id"
                         :avatar="student.avatar"
              ></user-card>
            </base-card>
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="12" class="full-height">
        <el-row :gutter="20">
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
            <el-button type="warning"
                       v-if="isEnrolled"
                       @click="findAllGroups"
            >Find All Groups
            </el-button>
          </el-col>
        </el-row>
        <div class="search-bar">
          <el-row :gutter="20">
            <el-col :span="14">
              <el-input
                  v-model="currentStudentSearchName"
                  placeholder="Please enter student's name"
              ></el-input>
            </el-col>
            <el-col :span="4">
              <el-button type="primary"
                         @click="handleCurrentStudentSearch">Search
              </el-button>
            </el-col>
          </el-row>
          <el-row :gutter="20" class="tag-lab-select">
            <el-col :span="11">
              <span>Tags: </span>
              <el-select v-model="selectTag"
                         placeholder="select a tag"
              >
                <el-option label="None" value=""></el-option>
                <el-option v-for="tag in tags"
                           :key="tag.value"
                           :label="tag.label"
                           :value="tag.value"
                ></el-option>
              </el-select>
            </el-col>
            <el-col :span="13">
              <span>Tutorial/Labs: </span>
              <el-select v-model="selectLab"
                         placeholder="select a tutorial/lab"
              >
                <el-option label="None" value=""></el-option>
                <el-option v-for="lab in givenLabs"
                           :key="lab"
                           :label="lab"
                           :value="lab"
                ></el-option>
              </el-select>
            </el-col>
          </el-row>
        </div>
        <el-empty description="No current student" v-if="isSearchedCurrentStudentEmpty"/>
        <el-scrollbar height="100%" class="current-student" v-else>
          <div v-for="student in searchedCurrentStudents" :key="student.id">
            <base-card v-if="isStudentHaveTag(student, selectTag) && isStudentInTutorial(student, selectLab)"
                       @click="jumpToStudentProfile(student.id)"
            >
              <user-card :full-name="student.fullName"
                         :email="student.email"
                         :id="student.id"
                         :avatar="student.avatar"
              ></user-card>
            </base-card>
          </div>
        </el-scrollbar>
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
import tags from "@/tags.json"
import userAPI from "@/api/user";

export default {
  name: "CourseDetails",
  components: {UserCard, BaseCard},
  props: ['course_id'],
  data() {
    return {
      previousStudents: null,
      searchedPreviousStudents: null,
      previousStudentSearchName: null,
      currentStudents: null,
      currentStudentSearchName: null,
      searchedCurrentStudents: null,
      selectTag: null,
      selectLab: null,
      givenLabs: [],
      tags: tags,
      courseDetail: {
        code: null,
        name: null
      },
      previousTeammates: null,
      error: null,
      isEnrolled: false,
      isMarked: false,
      tutorialType: null,
      tutorialNumber: null,
      isTutorialSelected: false,
      isEditing: false,
      enrollDialogVisible: false
    }
  },
  computed: {
    ...mapGetters(['userId']),
    isSearchedPreviousStudentEmpty() {
      return !this.searchedPreviousStudents || this.searchedPreviousStudents.length === 0;
    },
    isSearchedCurrentStudentEmpty() {
      return !this.searchedCurrentStudents || this.searchedCurrentStudents.length === 0;
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
    tutorial() {
      return this.tutorialType + this.tutorialNumber;
    }
  },
  methods: {
    jumpToStudentProfile(id) {
      this.$router.push(`/userProfile/${id}`)
    },
    processAvatar(fileName) {
      let result = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
      if (!fileName) {
        return result
      }
      userAPI.getAvatar(fileName)
          .then((res) => {
            result = 'data:image/jpeg;base64,' + res.data.data.image
          })
      return result
    },
    handlePreviousStudentSearch() {
      let _this = this
      if (!this.previousStudentSearchName) {
        this.searchedPreviousStudents = this.previousStudents
        return
      }
      this.searchedPreviousStudents = this.previousStudents.filter((item) => {
        if (_this.previousStudentSearchName.toLowerCase().trim() !== '') {
          return item.fullName.toLowerCase().indexOf(_this.previousStudentSearchName.toLowerCase().trim()) !== -1
        } else {
          return true
        }
      })
    },
    handleCurrentStudentSearch() {
      let _this = this
      if (!this.currentStudentSearchName) {
        this.searchedCurrentStudents = this.currentStudents
        return
      }
      this.searchedCurrentStudents = this.currentStudents.filter((item) => {
        if (_this.currentStudentSearchName.toLowerCase().trim() !== '') {
          return item.fullName.toLowerCase().indexOf(_this.currentStudentSearchName.toLowerCase().trim()) !== -1
        } else {
          return true
        }
      })
    },
    isStudentHaveTag(student, tag) {
      if (!tag) {
        return true
      }
      return !!student.tags && student.tags.includes(tag)
    },
    isStudentInTutorial(student, tutorial) {
      if (!tutorial) {
        return true
      }
      return student.tutorial === tutorial
    },
    async editTutorial(external = false) {
      try {
        if (!this.tutorialType || !this.tutorialNumber || this.tutorialNumber.length === 0) {
          throw new Error("Please enter tutorial information")
        }
        if (this.tutorialNumber.length === 1) {
          this.tutorialNumber = "0" + this.tutorialNumber
        }
        await courseDetailAPI.putSetTutorial(this.courseId, this.userId, this.tutorial)
        this.isEditing = false
        ElMessage({
          type: "success",
          message: `Set tutorial successfully`
        })
      } catch (error) {
        if (!external) {
          ElMessage({
            type: "info",
            message: error.message
          })
        } else {
          throw error
        }
      }
    },
    toggleIsEditing() {
      this.isEditing = !this.isEditing
    },
    findAllGroups() {
      this.$router.push(`/courseGroups/${this.courseId}`)
    },
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
        ElMessage("Please try again later")
      }
    },
    async handleEnroll() {
      try {
        if (!this.tutorialType || !this.tutorialNumber || this.tutorialNumber.length === 0) {
          throw new Error("Please enter tutorial information")
        }
        if (this.tutorialNumber.length === 1) {
          this.tutorialNumber = "0" + this.tutorialNumber
        }
        let response = await courseDetailAPI.postEnrollCourse(this.courseId, this.userId)
        await courseDetailAPI.putSetTutorial(this.courseId, this.userId, this.tutorial)
        this.isEditing = false
        this.isTutorialSelected = true
        if (response.status === 200 && response.msg === "success") {
          ElMessage({
            message: "Enrolled Successfully",
            type: "success"
          })
        } else {
          throw new Error("Please try again later")
        }
        this.isEnrolled = true
        this.enrollDialogVisible = false
      } catch (error) {
        ElMessage({
          message: error.message
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
    },
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
          for (let student of _this.previousStudents) {
            if (student.fileName == null) {
              student.avatar = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
            } else {
              userAPI.getAvatar(student.fileName).then((res) => {
                student.avatar = 'data:image/jpeg;base64,' + res.data.data.image
              }).catch(() => {
                ElMessage.error('Failed to load the avatar')
              })
            }
          }
          _this.searchedPreviousStudents = _this.previousStudents
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
          for (let student of _this.currentStudents) {
            if (student.fileName == null) {
              student.avatar = 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
            } else {
              userAPI.getAvatar(student.fileName).then((res) => {
                student.avatar = 'data:image/jpeg;base64,' + res.data.data.image
              }).catch(() => {
                ElMessage.error('Failed to load the avatar')
              })
            }
            if (!student.tutorial || _this.givenLabs.includes(student.tutorial)) {
              continue
            }
            _this.givenLabs.push(student.tutorial)
          }
          _this.currentStudents = _this.currentStudents.filter((student) => {
            return +student.id !== +_this.userId
          })
          _this.searchedCurrentStudents = _this.currentStudents
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
          if (course.isLocked) {
            ElMessage("Course is locked")
            this.$router.replace('/dashboard')
            this.forceUpdate()
          }
          _this.courseDetail = {
            code: course.code,
            id: course.id,
            name: course.name,
            nextGroupNameId: course.nextGroupNameId
          }
        })
        .catch((err) => {
          _this.error = err
        })
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
          let tutorial = response.data.tutorial
          if (tutorial) {
            _this.tutorialType = tutorial.substring(0, 2)
            _this.tutorialNumber = tutorial.substring(2)
            _this.isTutorialSelected = true
          }
        })
        .catch((error) => {
          _this.error = error
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
  padding-top: 10px;
}

.previous-teammate {
  color: #787878;

  & .name {
    font-weight: normal;
    color: #232323;
  }
}

.search-bar {
  padding-top: 10px;
  padding-bottom: 5px;
}

.tag-lab-select {
  padding-top: 10px;
}

.title {
  font-size: 16px;
  padding-right: 20px;
}

.wrapper {
  min-width: 1200px;
  height: 100%;
}

// TODO: fix scroll bar height
:deep(.el-card__body) {
  box-sizing: border-box;
  height: 60%;
  padding-top: 15px;
  padding-bottom: 15px;
}

.edit-btn {
  margin-left: 5px;
}

.right {
  padding-top: 5px;
  text-align: right;
}

.add-padding {
  padding-bottom: 69px;
}
</style>