<template>
  <div class="wrapper">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-row :gutter="20">
          <el-col :span="24">
            <!-- current course -->
            <el-card class="box-card">
              <template #header>
                <div class="card-header">
                  <span class="card-title">Current Courses</span>
                  <span class="semester">{{year}}&nbsp;Semester&nbsp;{{semester}}</span>
                </div>
              </template>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <!-- past course -->
          <el-col :span="12">
            <el-card class="box-card">
              <template #header>
                <div class="card-header">
                  <span class="card-title">Past Courses</span>
                </div>
              </template>
            </el-card>
          </el-col>
          <!-- interested course -->
          <el-col :span="12">
            <el-card class="box-card">
              <template #header>
                <div class="card-header">
                  <span class="card-title">Interested Courses</span>
                </div>
              </template>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <!-- all courses -->
      <el-col :span="8">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span class="card-title">All Courses</span>
            </div>
          </template>
          <div style="height:500px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>


export default {
  name: 'Dashboard',
  data() {
    return {
      year: 2022,
      semester: 2
    }
  }
}
</script>

<style lang="scss" scoped>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  & .card-title {
    font-size: 20px;
    font-weight: 600;
  }
  & .semester {
    font-size: 14px;
    color: gray;
  }
}
// .box-card {
//   width: 480px;
// }
.wrapper {
  width: 1200px;
}
</style>
