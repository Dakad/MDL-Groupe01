<template>
<section class="navappregister">
    <div>
    <!-- Create the login dialog -->
    <md-dialog :md-active.sync="login">
      <md-dialog-title>Login</md-dialog-title>
      <!--Field to get the username, place it in var username-->
      <md-field>
        <label>Username</label>
        <md-input v-model="usernameLogin"></md-input>
        <span class="md-helper-text">Type your Username</span>
      </md-field>
      <!--Field to get the password, place it in var password-->
      <md-field>
        <label>Password</label>
        <md-input v-model="passwordLogin" type="password"></md-input>
      </md-field>
      <!--Button to close the dialog-->
      <md-dialog-actions>
        <md-button class="md-primary" @click="login = false">Log in</md-button> 
      </md-dialog-actions>
    </md-dialog>

    <!--Create the register dialog-->
    <md-dialog :md-active.sync="register">
      <md-dialog-title>Register</md-dialog-title>
        <div class="md-layout md-gutter">
          <div class = "md-layout-item md-small-size-100">
            <md-field>
              <label>Username</label>
              <md-input v-model="usernameRegister" required></md-input>
              <span class = "md-helper-text">Choose a username</span>
              <span class="md-error">Username required</span>


            </md-field>
          </div>
        </div>
      <md-dialog-actions>
        <md-button class="md-primary" @click="register = false">Close</md-button> 
      </md-dialog-actions>
    </md-dialog>

    <!--The main navigation bar-->
    <md-toolbar class="md=accent" md-elevation="1">
        <!--SiteName refer to AccueilVue-->
        <a class="md-title" style="flex: 1" href ="/">SiteName</a>
        <!--Register button refer to the register page (RegisterVue)-->
        <md-button class="md-primary md-raised" @click="register = true">Register</md-button>
        <!--Login button open the login dialog-->
        <md-button class="md-primary md-raised" @click="login = true">Login</md-button>
    </md-toolbar>
    </div>
</section>
</template>

    
<style lang="scss" scoped>
.md-dialog{width:50%;}
</style>


<script>
export default {
    name: 'DialogCustom',
    data: () => ({
      login: false,
      register: false,
      usernameLogin: '',
      passwordLogin: '',
      usernameRegister:''
    }),
    
}
</script>