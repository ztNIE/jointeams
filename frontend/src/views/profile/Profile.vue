<template>
  <div class="wrapper">
    <el-row :gutter="20" class="half-height">
      <!-- basic user info -->
      <div class="info-box full-height">
        <el-card class="full-height info-card">
          <el-row :gutter="0" class="full-height">
            <!-- avatar & tag -->
            <el-col :span="8" class="full-height avatar-tag">
              <el-avatar :size="50" :src="avatarURL" />
            </el-col>
            <!-- basic info -->
            <el-col :span="16" class="full-height">
            </el-col>
          </el-row> 
        </el-card>
      </div>
    </el-row>
    <el-row :gutter="20" class="half-height">
      <!-- description -->
      <el-col :span="8" class="full-height">
        <el-card class="full-height">
          <template #header>
            <div class="card-header">
              <span class="card-title">Description</span>
            </div>
          </template>
          <div class="full-height"></div>
        </el-card>
      </el-col>
      <!-- comments -->
      <el-col :span="16" class="full-height">
        <el-card class="full-height">
          <template #header>
            <div class="card-header">
              <span class="card-title">Comments</span>
            </div>
          </template>
          <div class="full-height"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

// import authenticateIdentity from "@/util/login";

export default {
  name: 'Profile',
  // beforeCreate() {
  //   authenticateIdentity("ROLE_USER")
  // }
}
</script>

<style lang="scss" scoped>
.wrapper {
  height: 100%;
  min-width: 1200px;
}
.full-height {
  height: 100%;
}
.half-height {
  height: 50%;
}
:deep(.el-card__body) {
  height: 100%;
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
.info-box {
  box-sizing: border-box;
  width: 100%;
  padding: 0 10px 15px 10px;
  & .info-card {
    width: 100%;
    & .avatar-tag {
      border-right: solid #E8E8E8 1.5px;
    }
  }
}
</style>