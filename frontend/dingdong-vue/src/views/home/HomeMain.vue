<script setup></script>

<template>
  <v-container
    class="d-flex fill-height"
    fluid
  >
    <v-row class="justify-center row-height">
      <v-col
        class="d-flex flex-column justify-space-between"
        cols="5"
      >
        <v-btn
          class="text-none"
          variant="text"
        >
          budget plan
        </v-btn>

        <v-btn
          class="text-none"
          variant="text"
        >
          schedule
        </v-btn>

        <v-btn
          class="text-none"
          variant="text"
        >
          etc
        </v-btn>
      </v-col>

      <v-divider
        class="mx-2"
        vertical
        opacity="0"
      />

      <v-col
        class="d-flex flex-column align-start justify-center"
        cols="5"
      >
        <p>
          <span class="d-block">기록하며 기억하는 우리의 '웨딩일기'</span>
          <span class="d-block">WE ARE GETTING MARRIED</span>
          <span class="d-block">
            결혼 준비는 '웨딩일기'에서 한 번에 끝내자! 결혼을 준비하는 모든 커플을 응원합니다
          </span>
        </p>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
.row-height {
  height: 50%;
}
</style>
