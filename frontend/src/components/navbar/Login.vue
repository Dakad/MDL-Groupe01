<template>
  <section class="login">
    <div>
      <form novalidate class="md-layout" @submit.prevent="validateLogin">
        <md-content class="md-elevation-3">
          <div class="form">
            <md-field
              :class="[getValidationClass('username'), {'md-invalid': invalid['username'] != null}]"
            >
              <label for="username">Username</label>
              <md-input
                name="username"
                id="username"
                v-model.lazy.trim="login.username"
                :disabled="sending"
                autofocus
              ></md-input>
              <span class="md-error" v-if="!$v.login.username.required">The username is required</span>
              <span class="md-error" v-if="!$v.login.username.minLength">At least, 2 characters</span>
              <span class="md-error" v-if="invalid['username'] != null">{{invalid['username']}}</span>
            </md-field>

            <md-field md-has-password :class="[getValidationClass('password')]">
              <label for="password">Password</label>
              <md-input
                type="password"
                name="password"
                id="password"
                v-model.lazy.trim="login.password"
                :disabled="sending"
                autofocus
              ></md-input>

              <span class="md-error" v-if="!$v.login.password.required">The password is required</span>
              <span
                class="md-error"
                v-if="!$v.login.password.sameAsUsername"
              >Cannot be the same as the username</span>

              <!-- <span class="md-error" v-if="invalid['password']">Invalid password</span> -->
            </md-field>
          </div>

          <div class="actions md-layout md-alignment-center-space-between">
            <!-- <a href="/resetpassword">Reset password</a> -->
            <b-button type="submit" variant="outline-info" :disabled="sending">Log me in</b-button>
          </div>

          <div class="loading-overlay" v-if="sending">
            <!-- <md-progress-bar md-mode="indeterminate" v-if="sending"/> -->
            <md-progress-spinner md-mode="indeterminate" :md-stroke="2"></md-progress-spinner>
          </div>
        </md-content>
      </form>
    </div>
  </section>
</template>

<script>
import { validationMixin } from "vuelidate";
import {
  required,
  email,
  minLength,
  maxLength,
  not,
  sameAs
} from "vuelidate/lib/validators";
import { postLogin } from "@/services/api-user";

export default {
  name: "Login",
  mixins: [validationMixin],
  props: {},
  data: function() {
    return {
      login: {
        username: null,
        password: null
      },
      sending: false,
      invalid: {}
    };
  },
  validations: {
    login: {
      username: {
        required,
        minLength: minLength(2)
      },
      password: {
        required,
        sameAsUsername: not(sameAs("username"))
      }
    }
  },
  methods: {
    getValidationClass(fieldName) {
      const field = this.$v.login[fieldName];
      if (field) {
        return {
          "md-invalid": field.$invalid && field.$dirty
        };
      }
    },
    clearForm() {
      this.$v.$reset();
      this.login.username = null;
      this.login.password = null;
    },
    validateLogin() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        this.invalid = {};
        this.auth();
      } else {
        this.$emit("error", this.msg);
      }
    },
    auth() {
      this.sending = true;
      postLogin(this.login)
        .then(res => {
          this.sending = false;
          this.$emit("success", this.login.username);
          this.clearForm();
        })
        .catch(err => {
          this.msg = "Authentication denied - Please correct your credentials";
          this.invalid = Object.assign({}, err.body["validation"]);
          // this.msg = "Authentication denied - " + err.body["error"];
          // }
          console.error(err);
          this.sending = false;
          this.$emit("error", this.msg);
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
.md-content {
  z-index: 1;
  padding: 40px;
  width: 100%;
  max-width: 400px;
  position: relative;
}
.loading-overlay {
  z-index: 10;
  top: 0;
  left: 0;
  right: 0;
  position: absolute;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.9);
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
