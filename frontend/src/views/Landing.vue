<template>
  <!-- landing background -->
  <div class="background-div">
    <!-- header -->
    <div class="header clear-fix">
      <!-- logo and name -->
       <div class="box left">
          <el-icon :size="40" >
            <!-- logo svg -->
            <svg width="278" height="279" viewBox="0 0 278 279" fill="none" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
              <g filter="url(#filter0_d_35_1598)">
              <rect x="4" width="270" height="271" fill="url(#pattern0)" shape-rendering="crispEdges"/>
              </g>
              <defs>
              <filter id="filter0_d_35_1598" x="0" y="0" width="278" height="279" filterUnits="userSpaceOnUse" color-interpolation-filters="sRGB">
              <feFlood flood-opacity="0" result="BackgroundImageFix"/>
              <feColorMatrix in="SourceAlpha" type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" result="hardAlpha"/>
              <feOffset dy="4"/>
              <feGaussianBlur stdDeviation="2"/>
              <feComposite in2="hardAlpha" operator="out"/>
              <feColorMatrix type="matrix" values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"/>
              <feBlend mode="normal" in2="BackgroundImageFix" result="effect1_dropShadow_35_1598"/>
              <feBlend mode="normal" in="SourceGraphic" in2="effect1_dropShadow_35_1598" result="shape"/>
              </filter>
              <pattern id="pattern0" patternContentUnits="objectBoundingBox" width="1" height="1">
              <use xlink:href="#image0_35_1598" transform="translate(-0.00185185) scale(0.00557613 0.00555556)"/>
              </pattern>
              <image id="image0_35_1598" width="180" height="180" xlink:href="data:image/png;base64,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"/>
              </defs>
            </svg>
          </el-icon>
          <span class="logo-name">JoinTeams</span>
      </div>
      <!-- btns -->
      <div class="btns right">
        <el-button class="register-btn" @click="handleSignUp">Sign up</el-button>
        <el-button type="primary" @click="handleLogIn">Log in</el-button>
      </div>
    </div>
    <!-- slogan -->
    <div class="slogan">
      <p>JoinTeams</p>
    </div>
  </div>
  <!-- advantages -->
  <div class="content">
    <p class="title">What can you do with <b>JoinTeams</b>?</p>
    <div class="advantages">
      <!-- advantage 1 -->
      <div class="card">
        <img src="../assets/talking.jpg" alt="friends talking">
        <p>Talk to the past students for constructive advice</p>
      </div>
      <!-- advantage 2 -->
      <div class="card">
        <img src="../assets/teamwork.jpg" alt="teamwork">
        <p>Form the group with the best people you can trust</p>
      </div>
      <!-- advantage 3 -->
      <div class="card">
        <img src="../assets/goodGrade.jpg" alt="have good grade">
        <p>Escort for your excellent academic performance</p>
      </div>
    </div>
    <el-button class="try-btn" type="warning" size="large" plain @click="handleSignUp"><el-icon><Pointer /></el-icon>&nbsp;&nbsp;Try JoinTeams</el-button>
  </div>
</template>

<script>

export default {
  name: 'Landing',
  components: {
  },
  methods: {
    handleSignUp() {
      this.$router.push('/sign-up')
    },
    handleLogIn() {
      this.$router.push('/sign-in')
    }
  }
}
</script>

<style lang="scss" scoped>
.clear-fix::after {
  display: block;
  content: '';
  clear: both;
}
.left {
  float: left;
}
.right {
  float: right;
}
.background-div {
  background-image: url('../assets/landing.jpg');
  background-size: cover;
  background-attachment: fixed;
  height: 100%;
  width: 100%;
  min-width: 1000px;
  box-shadow: 0 -20px 50px 0px black;

  & .box {
    box-sizing: border-box;
    height: 60px;
    padding: 10px 15px;

    & .logo-name {
      position: relative;
      top: -12px;
      left: 12px;
      color: white;
      font-size: 20px;
      font-weight: 500;
      cursor: pointer;
    }
    & .el-icon {
      cursor: pointer;
    }
  }

  & .btns {
    padding-top: 25px;
    padding-right: 33px;
    & .register-btn {
      margin-right: 5px;
      background-color: transparent;
      color: white;
      border-color: white;
      transition: 0.5s;
    }
    & .register-btn:hover {
      color: #3BF3F0;
      border-color: #3BF3F0;
    }
  }

  & .slogan {
    margin-top: 19%;
    font-family: "Courier New", monospace;
    text-align: center;
    color: white;
    font-size: 90px;
    font-weight: 100;
    letter-spacing: 20px;
  }
}
.content {
  height: 650px;
  width: 1000px;
  margin: 0 auto;

  & .title {
    padding-top: 55px;
    text-align: center;
    letter-spacing: 1px;
    font-size: 28px;
  }

  & .advantages {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    padding-top: 35px;
    & .card {
      width: 300px;

      & img {
        height: 230px;
        display: block;
        margin: 0 auto;
      }

      & p {
        text-align: center;
        font-size: 19px;
      }
    }
  }

  & .try-btn {
    display: block;
    margin: 0 auto;
    margin-top: 100px;
    width: 300px;
    height: 50px;
    font-size: 20px;
  }
}
</style>
