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
                  <span class="card-title">Current Course</span>
                  <el-button class="button" text>Operation button</el-button>
                </div>
              </template>
            </el-card>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <!-- past course -->
          <el-col :span="12"><div class="grid-content ep-bg-purple" /></el-col>
          <!-- interested course -->
          <el-col :span="12"><div class="grid-content ep-bg-purple" /></el-col>
        </el-row>
      </el-col>
      <!-- all courses -->
      <el-col :span="8"><div class="grid-content ep-bg-purple" /></el-col>
    </el-row>
  </div>
</template>

<script>


export default {
  name: 'Dashboard',
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
}
// .box-card {
//   width: 480px;
// }
.wrapper {
  width: 1200px;
}
</style>
