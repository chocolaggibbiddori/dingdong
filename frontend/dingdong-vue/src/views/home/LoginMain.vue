<script setup></script>

<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row class="mb-15 justify-center">
      <v-col
        class="text-center"
        cols="12"
        sm="3"
      >
        <h1 class="mb-3">Login</h1>

        <v-form>
          <v-text-field
            label="아이디"
            variant="outlined"
            rounded="lg"
            type="text"
          />

          <v-text-field
            label="비밀번호"
            variant="outlined"
            rounded="lg"
            type="password"
          />

          <v-btn
            size="x-large"
            block
            rounded="lg"
            elevation="0"
            color="#c8e6c9"
          >
            로그인
          </v-btn>
        </v-form>

        <div class="mt-2">
          <button class="mx-4 dingdong-text-button">아이디 찾기</button>
          <button class="mx-4 dingdong-text-button">비밀번호 찾기</button>
          <button class="mx-4 dingdong-text-button">회원가입</button>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
