<template>
  <div class="common-layout" >
    <el-card class="box-card">
      <template #header>
        <div class="main_header">Semester</div>
      </template>
      <el-main id="container">
        <el-card class="semester-card">
          <div id="content">
            <label>Current Year</label>
            <br>
            <el-select v-bind:disabled="Boolean(1 - isEditActive)" v-model="currentYear" class="m-2">
              <el-option
                  v-for="year in yearList"
                  :key="year"
                  :label= "year"
                  :value= "year"
              />
            </el-select>
          </div>
          <div id="content">
            <label>Semester Number</label>
            <br>
            <el-select v-bind:disabled="Boolean(1 - isEditActive)" v-model="currentSemesterNo" class="m-2">
              <el-option
                  v-for="semesterNo in semesterNoList"
                  :key="semesterNo"
                  :label= "semesterNo"
                  :value= "semesterNo"
              />
            </el-select>
          </div>
          <div id="content">
            <label id="comment-function-label">Enable the comment function</label>
            <el-switch v-bind:disabled="Boolean(isEditActive)"
                v-model="isCommentAvailable"
                size="large"
                active-text="Enable"
                inactive-text="Disable"
                @click="changeIsCommentAvailableStatus()"
            />
          </div>
          <div class="footer">
            <el-button type="primary" @click="changeEditActiveStatus()">{{ buttonContext[isEditActive] }}</el-button>
          </div>
        </el-card>
      </el-main>
    </el-card>
  </div>
</template>

<script>
// import authUtil from "@/util/authUtil";
import adminAPI from "@/api/admin";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: 'Semester',
  beforeCreate() {
    // authUtil.authenticateIdentity("ROLE_ADMIN")
  },
  data() {
    return {
      isEditActive: 0,
      buttonContext: ['Edit','Confirm'],
      universityId: '',
      universities: [],
      currentYear: null,
      yearList: [],
      currentSemesterNo: '',
      semesterNoList: ['semester 1','semester 2'],
      isCommentAvailable: false,
    }
  },
  mounted() {
    adminAPI.getCurrentSemester().then((res) => {
      if(res.data.data['Null'] !== null)
      {
        this.currentYear = res.data.data.Semester.year
        this.currentSemesterNo = this.semesterNoList[res.data.data.Semester.semesterNumber - 1]
        this.isCommentAvailable = res.data.data.IsCommentAvailable
      }
    })
    for(let year = 2000; year < 2055; year++)
    {
      this.yearList.push(year)
    }
  },
  methods: {
    changeEditActiveStatus() {
      if(this.isEditActive === 0)
        this.isEditActive = 1 - this.isEditActive
      else
      {
        ElMessageBox.confirm(
            'Are you sure to reset the current semester?',
            'Warning',
            {
              confirmButtonText: 'Confirm',
              cancelButtonText: 'Cancel',
              type: 'warning',
            }
        ).then(() => {
          adminAPI.changeCurrentSemester(this.currentYear, 1+this.semesterNoList.indexOf(this.currentSemesterNo)).then((res) => {
            if(res !== null)
            {
              ElMessage({
                type: 'success',
                message: res.data.msg,
              })
              location.reload()
            }
            else
              this.isEditActive = 1 - this.isEditActive
          })
        }).catch(() => {
          ElMessage({
            type: 'info',
            message: 'Delete is cancelled',
          })
        })
      }
    },
    changeIsCommentAvailableStatus() {
      if (!this.isEditActive) {
        const action = (this.isCommentAvailable === true) ? 'Enable' : 'Disable'
        ElMessageBox.confirm(
            'Are you sure to ' + action.toLowerCase() + ' the comment feature?',
            'Warning',
            {
              confirmButtonText: 'Confirm',
              cancelButtonText: 'Cancel',
              type: 'warning',
            }
        ).then(() => {
          adminAPI.changeIsCommentAvailableStatus(this.isCommentAvailable).then((res) => {
            if (res !== null) {
              ElMessage({
                type: 'success',
                message: res.data.msg,
              })
            }
          })
        }).catch(() => {
          ElMessage({
            type: 'info',
            message: action + ' is cancelled',
          })
          this.isCommentAvailable = (this.isCommentAvailable === false) ? true : false
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.common-layout {
  height: 100%;
}
.box-card {
  height: 100%;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.main_header {
  color: black;
  font-weight: bold;
  font-size: xx-large;
  font-weight: 500;
  font-size: 23px;
}
#container{
  display: flex;
  flex-direction: column;
  text-align:right;
}
.semester-card {
  width: 60%;
  align-self: center;
  margin-bottom: 30px;
  text-align:left;
}
#content {
  margin-left: 15%;
  margin-top: 20px;
  margin-bottom: 50px;
}
.el-select {
  width: 80%;
}
#comment-function-label {
  width: 50%;
  margin-right: 20%;
}
.footer {
  text-align: right;
}
</style>