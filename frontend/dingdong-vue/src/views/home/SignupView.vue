<script setup>
import LabelFormElement from '@/components/form/LabelFormElement.vue';
</script>

<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row class="mb-15 justify-center">
      <v-col
        class="text-center"
        cols="12"
        sm="6"
        md="4"
      >
        <h2 class="mb-3 font-kaisei-decol-regular">Create account</h2>

        <v-form class="font-kohi-nanum-otf-light">
          <label-form-element
            text="이름"
            required
          >
            <v-text-field
              variant="outlined"
              rounded="lg"
              type="text"
            />
          </label-form-element>

          <label-form-element
            text="이메일"
            required
          >
            <div class="d-flex ga-2">
              <v-text-field
                variant="outlined"
                rounded="lg"
                type="text"
              />

              <v-btn
                height="56"
                variant="outlined"
                rounded="lg"
              >
                인증
              </v-btn>
            </div>
          </label-form-element>

          <label-form-element
            text="성별"
            required
          >
            <v-btn-toggle
              variant="outlined"
              mandatory
              divided
            >
              <v-btn
                value="female"
                class="flex-grow-1"
                rounded="lg"
                color="red"
              >
                공주님
              </v-btn>

              <v-btn
                value="male"
                class="flex-grow-1"
                rounded="lg"
                color="blue"
              >
                왕자님
              </v-btn>
            </v-btn-toggle>
          </label-form-element>
        </v-form>

        <div class="mt-5 text-right">
          <button class="font-kaisei-decol-regular">Next</button>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped></style>
